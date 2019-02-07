package demo.com.serialize.impl;

import demo.com.serialize.ISerializer;

/**
 *
 */
public class ProtostuffSerializer implements ISerializer {
	@Override
	public <T> byte[] serialize(T obj) {
		return new byte[0];
	}

	/**
	 *
	 * @param data
	 * @param tClass
	 * @param <T>
	 * @return
	 */
	@Override
	public <T> T deSerialize(byte[] data, Class<T> tClass) {
		return null;
	}
}
