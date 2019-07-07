package demo.com.serialize;

public interface ISerializer {

	/**
	 * 序列化接口
	 *
	 * @param obj
	 * @param <T>
	 * @return
	 */
	<T> byte[] serialize(T obj);

	/**
	 * 反序列化接口
	 *
	 * @param data
	 * @param tClass
	 * @param <T>
	 * @return
	 */
	<T> T deSerialize(byte[] data, Class<T> tClass);


}
