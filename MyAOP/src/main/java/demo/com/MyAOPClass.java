package demo.com;

public class MyAOPClass {
    public Object begin() {
        System.out.println("begin");
        return null;
    }

    public Object end() {
        System.out.println("end");
        return null;
    }
}
