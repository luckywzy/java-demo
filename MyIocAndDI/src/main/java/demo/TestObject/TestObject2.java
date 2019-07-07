package demo.TestObject;

import demo.anntation.MyAutoWired;

public class TestObject2 {

	//@MyAutoWired
	TestObjectThree obj3;


	public void print() {
		System.out.println("成功创建class：" + this.getClass().getSimpleName() +
				"\t hashcode:" + this.hashCode() +
				"\t 注入属性：" + obj3.hashCode()
		);
	}

	@MyAutoWired
	public void setObj3(TestObjectThree obj3) {
		this.obj3 = obj3;
	}
}
