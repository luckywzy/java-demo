package demo.com.framework;

import demo.com.framework.enums.SerializeType;
import demo.com.serialize.ISerializer;
import demo.com.serialize.impl.DefaultJavaSerializer;
import demo.com.serialize.impl.HessianSerializer;
import demo.com.serialize.impl.ProtostuffSerializer;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class SerialzerEngine {

	private static final Map<SerializeType, ISerializer> serializeMap = new ConcurrentHashMap<>();

	//注册序列化工具
	static {
		serializeMap.put(SerializeType.DEFAULTJAVASERIALIZER, new DefaultJavaSerializer());
		serializeMap.put(SerializeType.HESSIANSERIALIZER, new HessianSerializer());
		serializeMap.put(SerializeType.PROTOSTUFFSERIALIZER, new ProtostuffSerializer());
	}


	/**
	 * 序列化
	 * @param obj
	 * @param serializeType
	 * @param <T>
	 * @return
	 */
	public static <T> byte[] serialize(T obj, String serializeType) {
		SerializeType type = SerializeType.queryByType(serializeType);
		if (type == null) {
			throw new RuntimeException("SerializeType not found");
		}

		ISerializer serializer = serializeMap.get(type);
		if(serializer == null)
		{
			throw new RuntimeException("Serialize error");
		}
		try {
			return serializer.serialize(obj);
		}catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * 反序列化
	 * @param data
	 * @param cls
	 * @param serializeType
	 * @param <T>
	 * @return T
	 */
	public static <T> T  deserialize(byte[] data,Class<T> cls, String serializeType)
	{
		SerializeType type = SerializeType.queryByType(serializeType);
		if (type == null) {
			throw new RuntimeException("SerializeType not found");
		}

		ISerializer serializer = serializeMap.get(type);
		if(serializer == null)
		{
			throw new RuntimeException("Serialize error");
		}
		try {
			return serializer.deSerialize(data,cls);
		}catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

}
