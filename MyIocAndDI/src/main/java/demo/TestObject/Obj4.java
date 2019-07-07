package demo.TestObject;

import demo.anntation.MyBean;

@MyBean
public class Obj4 {

	public void print() {
		System.out.println("成功创建class：" + this.getClass().getSimpleName() +
				"\t hashcode:" + this.hashCode()
		);
	}
}
