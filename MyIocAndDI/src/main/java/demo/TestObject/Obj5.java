package demo.TestObject;

import demo.anntation.MyAutoWired;
import demo.anntation.MyBean;

@MyBean
public class Obj5 {

    @MyAutoWired
    private Obj4 obj4;

    public void print() {
        System.out.println("成功创建class：" + this.getClass().getSimpleName() +
                "\t hashcode:" + this.hashCode() +
                "\t obj4:" + obj4.hashCode()
        );
    }
}
