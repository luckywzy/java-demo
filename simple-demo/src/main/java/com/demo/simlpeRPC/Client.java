package com.demo.simlpeRPC;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {
    public static <T> T getRemoteProxyObj(final Class<?> serviceInterface, final InetSocketAddress address) {
        return (T) Proxy.newProxyInstance(serviceInterface.getClassLoader(), new Class<?>[]{serviceInterface}, new DynProxy(serviceInterface, address));
    }

    private static class DynProxy implements InvocationHandler {
        private final Class<?> serviceInterface;
        private final InetSocketAddress address;

        public DynProxy(Class<?> serviceInterface, InetSocketAddress address) {
            this.serviceInterface = serviceInterface;
            this.address = address;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Socket socket = null;
            ObjectOutputStream output = null;
            ObjectInputStream input = null;
            try {
                socket = new Socket();
                socket.connect(address);
                output = new ObjectOutputStream(socket.getOutputStream());
                //发送客户端的调用请求
                //接口名
                output.writeUTF(serviceInterface.getName());
                //调用接口
                output.writeUTF(method.getName());
                //参数类型
                output.writeObject(method.getParameterTypes());
                //参数值
                output.writeObject(args);
                output.flush();
                //同步操作
                input = new ObjectInputStream(socket.getInputStream());
                return input.readObject();
            } finally {
                if (output != null) output.close();
                if (input != null) input.close();
                if (socket != null) socket.close();
            }
        }
    }
}

