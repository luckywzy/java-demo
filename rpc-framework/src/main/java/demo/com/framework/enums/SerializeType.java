package demo.com.framework.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 *
 */
public enum SerializeType {
	/**
	 * 默认序列化方式
	 */
	DEFAULTJAVASERIALIZER("DefaultJavaSerializer"),
	/**
	 * hessian
	 */
	HESSIANSERIALIZER("HessianSerializer"),
	/**
	 * protostuff
	 */
	PROTOSTUFFSERIALIZER("ProtostuffSerializer");

	/**
	 * 序列化类名
	 */
	@Getter
	private String serializeType;

	SerializeType(String serializeType) {
		this.serializeType = serializeType;
	}

	/**
	 * 获取序列化实现类
	 *
	 * @param serializeType
	 * @return
	 */
	public static SerializeType queryByType(String serializeType) {
		if (StringUtils.isBlank(serializeType)) {
			return null;
		}
		for (SerializeType type : SerializeType.values()) {
			if (StringUtils.equals(serializeType, type.getSerializeType())) {
				return type;
			}
		}
		return null;
	}
}
