package demo.com.annotation;

import demo.com.mdel.Request;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * 2018/10/8
 */
public class MyRequestMethodTest {

    @Test
    public void testEqual(){
        MyRequestMethod get = MyRequestMethod.valueOf("GET");
        MyRequestMethod get1 = MyRequestMethod.valueOf("GET");

        assertEquals(true,get.equals(get1));
    }

    @Test
    public void testHashMapEntryEqual(){
        MyRequestMethod get = MyRequestMethod.valueOf("GET");
        HashMap<Request, Object> map = new HashMap<>();
        map.put(new Request(get,"/hello"), new Object());
        MyRequestMethod get1 = MyRequestMethod.valueOf("GET");

        assertEquals(true,map.containsKey(new Request(get1,"/hello")));
        System.out.println(map.get(new Request(get1,"/hello")));
    }
}