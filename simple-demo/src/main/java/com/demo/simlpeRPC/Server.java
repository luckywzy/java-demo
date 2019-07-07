package com.demo.simlpeRPC;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


@Slf4j
public class Server {

	private static final Map<String, Class<?>> serviceHolder = new HashMap<>();
	private int port;

	public Server(int port) {
		this.port = port;
	}

	public static void main(String[] args) throws IOException {
		int port = 18000;
		Server.registerService(HelloService.class, HelloServiceImpl.class);
		Server server = new Server(port);
		server.startService();

	}

	//服务注册
	public static void registerService(Class serverInterface, Class impl) {
		serviceHolder.put(serverInterface.getName(), impl);
	}

	//服务启动
	public void startService() throws IOException {
		ServerSocket serverSocket = new ServerSocket();
		serverSocket.bind(new InetSocketAddress(port));
		log.info("server start......");
		while (true) {
			//每接收到一个请求就新起一个线程去处理
			new Thread(new ServerTask(serverSocket.accept())).start();
		}
	}

	@AllArgsConstructor
	private static class ServerTask implements Runnable {

		private Socket client = null;


		@Override
		public void run() {
			try (ObjectInputStream inputStream = new ObjectInputStream(
					client.getInputStream());
			     ObjectOutputStream outputStream = new ObjectOutputStream(client.getOutputStream())) {
				// 接受客户端请求
				String serviceName = inputStream.readUTF();
				String methodName = inputStream.readUTF();
				Class<?>[] paraType = (Class<?>[]) inputStream.readObject();
				Object[] args = (Object[]) inputStream.readObject();
				// 进行业务处理，并返回结果
				Class serviceClass = serviceHolder.get(serviceName);
				if (null == serviceClass) {
					throw new ClassNotFoundException(serviceClass + " not found!");
				}
				Method method = serviceClass.getMethod(methodName, paraType);
				Object result = method.invoke(serviceClass.newInstance(), args);
				outputStream.writeObject(result);
				outputStream.flush();
				log.info("request:[{}] [{}] [{}] [{}] [{}]", serviceClass, method, paraType, args, result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
