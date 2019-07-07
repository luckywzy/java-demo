package demo.com.serialize.impl;

import demo.com.serialize.ISerializer;

import java.io.ByteArrayOutputStream;

public class HessianSerializer implements ISerializer {
	@Override
	public <T> byte[] serialize(T obj) {
		if (obj == null) {
			throw new NullPointerException();
		}
		try {
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			/*HessianOutput hessianOutput = new HessianOutput(output);*/
		} catch (Exception e) {

		}
		return new byte[0];
	}

	@Override
	public <T> T deSerialize(byte[] data, Class<T> tClass) {
		return null;
	}
}
