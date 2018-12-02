package com.demo.simlpeRPC;

import org.junit.Test;

import java.net.InetSocketAddress;

import static org.junit.Assert.assertEquals;

public class ClientTest {

    @Test
    public void helloTest() {
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 18000);
        HelloService helloService = Client.getRemoteProxyObj(HelloService.class, address);
        String res = helloService.say("张三");
        assertEquals("hello,张三", res);
    }

}