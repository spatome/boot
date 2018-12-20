package com.spatome.boot.netty.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.runtime.RuntimeSchema;

public class ProtostuffUtil {
    // 缓存schema对象的map
    private static Map<Class<?>, RuntimeSchema<?>> cachedSchema = new ConcurrentHashMap<Class<?>, RuntimeSchema<?>>();

    /**
     * 根据获取相应类型的schema方法
     * 
     * @param clazz
     * @return
     */
    @SuppressWarnings({ "unchecked", "unused" })
    private <T> RuntimeSchema<T> getSchema(Class<T> clazz) {
        // 先尝试从缓存schema map中获取相应类型的schema
        RuntimeSchema<T> schema = (RuntimeSchema<T>) cachedSchema.get(clazz);
        // 如果没有获取到对应的schema，则创建一个该类型的schema
        // 同时将其添加到schema map中
        if (schema == null) {
            schema = RuntimeSchema.createFrom(clazz);
            if (schema != null) {
                cachedSchema.put(clazz, schema);
            }
        }
        // 返回schema对象
        return schema;
    }

    /**
     * 序列化方法，将对象序列化为字节数组（对象 ---> 字节数组）
     * 
     * @param obj
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> byte[] serialize(T obj) {
		// 获取泛型对象的类型
		Class<T> clazz = (Class<T>) obj.getClass();
		// 创建LinkedBuffer对象
		LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try {
    		// 创建泛型对象的schema对象
    		RuntimeSchema<T> schema = RuntimeSchema.createFrom(clazz);
			// 序列化
			byte[] array = ProtostuffIOUtil.toByteArray(obj, schema, buffer);
			// 返回序列化对象
			return array;
		} finally {
			buffer.clear();
		}
    }

    /**
     * 反序列化方法，将字节数组反序列化为对象（字节数组 ---> 对象）
     * 
     * @param data
     * @param clazz
     * @return
     */
    public static <T> T deserialize(byte[] data, Class<T> clazz) {
        // 创建泛型对象的schema对象
        RuntimeSchema<T> schema = RuntimeSchema.createFrom(clazz);
        // 根据schema实例化对象
        T message = schema.newMessage();
        // 将字节数组中的数据反序列化到message对象
        ProtostuffIOUtil.mergeFrom(data, message, schema);
        // 返回反序列化对象
        return message;
    }
}
