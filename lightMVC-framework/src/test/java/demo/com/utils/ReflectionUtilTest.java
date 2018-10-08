package demo.com.utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class ReflectionUtilTest {

    static class ServiceA{

        public void say(String name){
            System.out.println("hello :" +name);
        }
    }



    @Test
    public void testReflect(){
        ServiceA a = new ServiceA();
        /*ReflectionUtil.invokeMethod(a, )*/

    }
}