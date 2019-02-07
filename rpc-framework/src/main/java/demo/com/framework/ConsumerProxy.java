package demo.com.framework;

import lombok.extern.slf4j.Slf4j;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.net.Socket;

/**
 * 服务消费者代理类
 */
@Slf4j
public class ConsumerProxy {

    /**
     * 服务消费代理接口
     *
     * @param interfaceCls
     * @param host
     * @param port
     * @param <T>
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static <T> T consume(final Class<T> interfaceCls,
                                final String host,
                                final int port
    ) throws Exception {
        return (T) Proxy.newProxyInstance(interfaceCls.getClassLoader(),
                new Class<?>[]{interfaceCls},
                (InvocationHandler) (proxy, method, args) -> {
                    try (
                            Socket socket = new Socket(host, port);
                            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream())) {
                        try {
                            //写入socket
                            //方法名
                            output.writeUTF(method.getName());
                            //参数
                            output.writeObject(args);
                            //获取结果
                            try (ObjectInputStream input = new ObjectInputStream(socket.getInputStream());) {
                                Object result = input.readObject();
                                if (result instanceof Throwable)
                                    throw (Throwable) result;
                                return result;
                            } catch (Exception e) {
                               /* log.wran("Exception:{}", e);*/
                                throw e;
                            }
                        } catch (Exception e) {
                            /* log.warn("Exception:{}", e);*/
                            throw e;
                        }
                    } catch (Exception e) {
                        /* log.warn("Exception:{}", e);*/
                        throw e;
                    }
                });

    }
}
