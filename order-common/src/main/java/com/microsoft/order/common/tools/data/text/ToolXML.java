package com.microsoft.order.common.tools.data.text;


import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.mapper.MapperWrapper;
import com.thoughtworks.xstream.security.AnyTypePermission;

/**
 * @author 周松柏  zhousongbai@fang.com
 * @date 2018-04-17 8:39
 **/

public class ToolXML {
    private static String XML_TAG = "<?xml version='1.0' encoding='UTF-8'?>";

    /**
     * Description: 私有化构造
     */
    private ToolXML() {
        super();
    }

    /**
     * @return
     * @Description 为每次调用生成一个XStream
     * @Title getInstance
     */
    private static XStream getInstance(String encoding) {
        XStream xStream = new XStream(new DomDriver(encoding)) {
            /**
             * 忽略xml中多余字段
             */
            @Override
            protected MapperWrapper wrapMapper(MapperWrapper next) {
                return new MapperWrapper(next) {
                    @SuppressWarnings("rawtypes")
                    @Override
                    public boolean shouldSerializeMember(Class definedIn, String fieldName) {
                        if (definedIn == Object.class) {
                            return false;
                        }
                        return super.shouldSerializeMember(definedIn, fieldName);
                    }
                };
            }
        };

        // 设置默认的安全校验
        XStream.setupDefaultSecurity(xStream);
        // 使用本地的类加载器
        xStream.setClassLoader(ToolXML.class.getClassLoader());
        // 允许所有的类进行转换
        xStream.addPermission(AnyTypePermission.ANY);
        return xStream;
    }

    /**
     * @param xml
     * @param clazz
     * @return
     * @Description 将xml字符串转化为java对象
     * @Title xmlToBean
     */
    public final static <T> T xmlToBean(String xml, Class<T> clazz) {
        XStream xStream = getInstance("UTF-8");
        xStream.processAnnotations(clazz);
        Object object = xStream.fromXML(xml);
        T cast = clazz.cast(object);
        return cast;
    }

    /**
     * @param xml
     * @param clazz
     * @return
     * @Description 将xml字符串转化为java对象
     * @Title xmlToBean
     */
    public final static <T> T xmlToBean(String xml, Class<T> clazz, String encoding) {
        XStream xStream = getInstance(encoding);
        xStream.processAnnotations(clazz);
        Object object = xStream.fromXML(xml);
        T cast = clazz.cast(object);
        return cast;
    }

    /**
     * @param object
     * @return
     * @Description 将java对象转化为xml字符串
     * @Title beanToXml
     */
    public final static String beanToXml(Object object) {
        XStream xStream = getInstance("UTF-8");
        xStream.processAnnotations(object.getClass());
        // 剔除所有tab、制表符、换行符
        String xml = xStream.toXML(object).replaceAll("\\s+", " ");
        return xml;
    }

    /**
     * @param object
     * @return
     * @Description 将java对象转化为xml字符串（包含xml头部信息）
     * @Title beanToXml
     */
    public final static String beanToXmlWithTag(Object object) {
        String xml = XML_TAG + beanToXml(object);
        return xml;
    }


    /**
     * 测试main方法
     */
    public  static void main(String[] args) {

    }
}