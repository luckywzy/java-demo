package demo.com.framework;

import org.apache.commons.lang3.reflect.MethodUtils;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//@Slf4j
public class ProviderReflect {

    private static final ExecutorService executor = Executors.newFixedThreadPool(4);

    public static void provider(final Object service, int port) throws Exception {
        ServerSocket serverSocket = new ServerSocket(port);
        while (true) {
            //接受连接
            final Socket socket = serverSocket.accept();
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try (ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                    ) {
                        String methodName = input.readUTF();
                        Object[] args = (Object[]) input.readObject();
                        ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                        try {
                            Object result = MethodUtils.invokeExactMethod(service, methodName, args);
                            output.writeObject(result);
                        } catch (Throwable e) {
                            output.writeObject(e);
                        }
                    } catch (Exception e) {

                    }
                }
            });
        }
    }
}
