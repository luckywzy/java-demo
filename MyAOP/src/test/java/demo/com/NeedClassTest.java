package demo.com;

import org.junit.Test;

public class NeedClassTest {

    @Test
    public void funA() {
        NeedInterface target = (NeedInterface) AOPProxy.getProxyInstance(new NeedClass(),
                new MyAOPClass());
        target.funA("a");
    }
}