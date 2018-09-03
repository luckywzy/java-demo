package main.builder;

public class A {
    private String s;
    private String t;
    private Integer i;
    private Long b;

    //私有构造函数
    private A(Buider buider) {
        this.s = buider.s;
        this.t = buider.t;
        this.i = buider.i;
        this.b = buider.b;
    }

    @Override
    public String toString() {
        return "A{" +
                "s='" + s + '\'' +
                ", t='" + t + '\'' +
                ", i=" + i +
                ", b=" + b +
                '}';
    }

    public static class Buider {
        private String s;
        private String t;
        private Integer i;
        private Long b;

        // 返回外部对象并且将本身作为参数
        public A build() {
            return new A(this);
        }

        //返回对象本身
        public Buider setS(String s) {
            this.s = s;
            return this;
        }

        public Buider setT(String t) {
            this.t = t;
            return this;
        }

        public Buider setI(Integer i) {
            this.i = i;
            return this;
        }

        public Buider setB(Long b) {
            this.b = b;
            return this;
        }
    }

    public static void main(String[] args) {
        A a = new Buider().setB(2L).setI(1).setS("s").setT("t").build();
        System.out.println(a.toString());
    }

}
