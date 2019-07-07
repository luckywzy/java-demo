package demo.com.serialize.impl;

import demo.com.serialize.ISerializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * java 默认序列化实现
 */
public class DefaultJavaSerializer implements ISerializer {
	@Override
	public <T> byte[] serialize(T obj) {

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

			objectOutputStream.writeObject(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return outputStream.toByteArray();
	}

	@Override
	public <T> T deSerialize(byte[] data, Class<T> tClass) {
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
		try {
			ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
			return (T) objectInputStream.readObject();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
