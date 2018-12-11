package com.microsoft.order.common.tools.redis;


import com.microsoft.order.common.tools.data.text.ToolJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.nio.charset.Charset;

/**
 *
 * @param <T>
 */
public class GsonRedisSerializer<T> implements RedisSerializer<T> {

    private static final Logger log = LoggerFactory.getLogger(GsonRedisSerializer.class);
    private static final byte[] EMPTY_ARRAY = new byte[0];

    @Override
    public byte[] serialize(T t)  {
        if (t == null) {
            return EMPTY_ARRAY;
        }
        String tmp = String.format("%s_%s", t.getClass().getName(), ToolJson.modelToJson(t));
        return tmp.getBytes(Charset.forName("UTF8"));
    }

    @Override
    public T deserialize(byte[] bytes) {
        try {
            if (bytes == null || bytes.length <= 0) {
                return null;
            }
            String tmp = new String(bytes, Charset.forName("UTF8"));
            String className = tmp.substring(0, tmp.indexOf("_{"));
            String json = tmp.substring(tmp.indexOf("_{") + 1);
            return (T) ToolJson.jsonToModel(json, Class.forName(className));
        } catch (ClassNotFoundException ex) {
            log.error(String.format("反序列化数据出错:%s", ex.getException()));
        }catch (Exception ex){
            log.error(String.format("反序列化数据出错:%s", ex.getMessage()));
        }
        return null;
    }
}
