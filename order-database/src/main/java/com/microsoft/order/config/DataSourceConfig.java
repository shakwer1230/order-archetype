package com.microsoft.order.config;

import com.alibaba.druid.pool.DruidDataSource;
import javafx.util.Pair;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import static com.microsoft.order.config.DbItem.getDbItemMap;
import static java.io.FileDescriptor.out;

/**
 * @
 * @Author:lvxiaoke
 * @Date: 2018/12/5
 * @
 */
@Configuration
public class DataSourceConfig {
    private static String DB_DRUID = "spring.datasource.druid";
    private static String DATASOURCE_TYPE_DEFAULT = "com.alibaba.druid.pool.DruidDataSource";
    private Logger log = LoggerFactory.getLogger(DataSourceConfig.class);

    @Autowired
    private Environment environment;

    /**
     * 数据库配置文件路径
     */
    @Value("${dbconfig.file.location}")
    private String location;

    /**
     * 数据库配置文件的默认数据源名称
     */
    @Value("{dbconfig.defaultDataName}")
    private String defaultDataName;

    /**
     * 被@PostConstruct修饰的方法会在服务器加载Servlet的时候运行，并且只会被服务器调用一次，类似于Serclet的inti()方法。
     * 被@PostConstruct修饰的方法会在构造函数之后，init()方法之前运行。
     */
    @PostConstruct
    private void init(){
        String filters = this.environment.getProperty(DB_DRUID+".filters"); //this.environment!!.getProperty("$DB_DRUID.filters")
        if (!(filters==null||filters.equals(""))) {
            System.setProperty("druid.filters", filters);
            if (log.isInfoEnabled()) {
                log.info("加载 druid.filters");
            }
        }

        //开启空闲线程检测（getConnection时，计算连接的空闲时间，超过TimeBetweenEvictionRunsMillis会使用该连接执行validationQuery验证连接是否可用,如不可用，将抛弃改连接，取下一个连接）,并不能完全屏蔽错误连接的返回，
        System.setProperty("druid.testWhileIdle", "true");
        System.setProperty("druid.validationQuery", "select 1");
        //取消testOnBorrow，该值标识每次getConnection时都进行检测，比较影响性能
        System.setProperty("druid.testOnBorrow", "false");

        if (log.isInfoEnabled()) {
            log.info("初始化 druid配置");
        }
    }

    public  DynamicDataSource dynamicDataSource()  throws  Exception{
       val targetDataSources = new HashMap<Object,Object>();

       try {
           val dbItemMap = getDbItemMap(location);
           val dynamicDataSource = new DynamicDataSource();
           for (Map.Entry<String,DbItem> entry:dbItemMap.entrySet()){
               val dataSource = buildDataSource(entry.getValue());
               targetDataSources.put(entry.getKey(),dataSource);

               if (entry.getKey() == defaultDataName) {
                   log.info("设置默认数据源");
                   dynamicDataSource.setDefaultTargetDataSource(dataSource);
               }
           }
           dynamicDataSource.setTargetDataSources(targetDataSources);
           return dynamicDataSource;
       }catch (Exception e){
           log.error("dynamicDataSource报错", e);
           throw e;
       }
    }


    /**
     * 创建数据源
     */
    private DataSource buildDataSource(DbItem dbItem) throws Exception{
        if (dbItem == null) {
            throw new Exception("buildDataSource 参数dbItem 为空");
        }
        try {
            val dataSourceType = (Class<DataSource>) Class.forName(DATASOURCE_TYPE_DEFAULT);

            Pair<String,String>driverAndUrl = dbItem.getDriverAndUrl();

            val factory = DataSourceBuilder.create()
                    .driverClassName(driverAndUrl.getKey())
                    .url(driverAndUrl.getValue())
                    .username(dbItem.dbUserName)
                    .password(dbItem.dbPassword)
                    .type(dataSourceType);

            val dataSourceFactory = factory.build();

            if (dataSourceFactory instanceof DruidDataSource) {
                val dataSource=(DruidDataSource)dataSourceFactory;
                //配置初始化大小、最小、最大
                dataSource.setInitialSize(5);
                //最小连接池数量
                dataSource.setMinIdle(1);
                //最大连接池数量
                dataSource.setMaxActive(40);

                //获取连接时最大等待时间，单位毫秒。配置了maxWait之后，缺省启用公平锁，
                //并发效率会有所下降，如果需要可以通过配置useUnfairLock属性为true使用非公平锁。
                dataSource.setMaxWait(10*1000);

                //建议配置为true，不影响性能，并且保证安全性。申请连接的时候检测，
                //如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
                dataSource.setTestWhileIdle(true);

                //申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。
                dataSource.setTestOnBorrow(false);

                //归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能
                dataSource.setTestOnReturn(false);

                /**
                 * 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
                 * 有两个含义：
                 1) Destroy线程会检测连接的间隔时间
                 2) testWhileIdle的判断依据，详细看testWhileIdle属性的说明
                 * 1.testWhileIdle的空闲时间判断依据
                 * 2.destroyThread检查的间隔时间（为什么这两个会用一个时间，大概是testWhileIdle进行主动检测后，
                 *    destroy的间隔就没必要小于这个时间了。destroy 依赖 min/mixEvictableIdleTimeMillis 时间进行链接驱逐)
                 */
                dataSource.setTimeBetweenEvictionRunsMillis(60 * 1000);

                //配置一个连接在池中最小生存的时间，单位是毫秒
                dataSource.setMinEvictableIdleTimeMillis(300*1000);

                //连接检查超时，如果执行检查sql超过这个时间认为失败
                dataSource.setValidationQueryTimeout(2000);

                dataSource.setValidationQuery("select 1");


                log.info("构建datasource:" + dataSource.getRawJdbcUrl());
            }

            return dataSourceFactory;
        } catch ( ClassNotFoundException e) {
            log.error("buildDataSource报错", e);
            throw e;
        }
    }
}
