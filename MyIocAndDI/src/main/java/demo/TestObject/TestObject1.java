package demo.TestObject;

import demo.anntation.MyAutoWired;

public class TestObject1 {

	//@MyAutoWired(name = "testObject2")
	private TestObject2 obj2;

	@MyAutoWired(name = "testObject2")
	public void setObj2(TestObject2 obj2) {
		this.obj2 = obj2;
	}

	public void print() {
		System.out.println("成功创建class：" + this.getClass().getSimpleName() +
				"\t hashcode:" + this.hashCode() +
				"\t 成功注入属性：" + obj2.hashCode()
		);
	}
}
