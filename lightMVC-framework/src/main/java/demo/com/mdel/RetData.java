package demo.com.mdel;

/**
 * servlet 还可以返回 数据，将此数据写入到 response中，从而直接输出到浏览器上
 */
public class RetData {

	//数据
	private Object model;

	public RetData(Object model) {
		this.model = model;
	}

	public Object getModel() {
		return model;
	}
}


