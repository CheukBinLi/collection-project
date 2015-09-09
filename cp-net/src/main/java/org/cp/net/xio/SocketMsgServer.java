package org.cp.net.xio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.StandardSocketOptions;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SocketMsgServer implements Runnable {

	static ServerSocketChannel server;
	static Selector selector;
	static Thread listener;
	static Executor executor = Executors.newCachedThreadPool();
	static SocketMsgServer newInstance = new SocketMsgServer();

	protected static Properties properties = new Properties();

	static {
		// 加载配置文件
		System.setProperty("boomhope_tsbank_config", ClassLoader.getSystemClassLoader().getResource("").getPath());
		try {
			// properties.load(new FileInputStream(System.getProperty("boomhope_tsbank_config").toString() + File.separator + "mq.properties"));
		} catch (Exception e) {
			System.err.println("error for load config");
			e.printStackTrace();
		}
	}

	private SocketMsgServer() {
		try {
			server = ServerSocketChannel.open();
			server.setOption(StandardSocketOptions.SO_REUSEADDR, true);// 端口重用
			server.configureBlocking(false);
			// server.socket().bind(new InetSocketAddress(Integer.valueOf(properties.getProperty("SOCKET_SERVER_PORT"))));
			server.socket().bind(new InetSocketAddress(8300));
			selector = Selector.open();
			server.register(selector, SelectionKey.OP_ACCEPT);
			DoQueue.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SocketMsgServer newInstance() {
		return newInstance;
	}

	public static void start() {
		if (null == listener) {
			listener = new Thread(newInstance);
		}
		listener.start();
	}

	public static void stop() {
		if (null != listener && listener.isInterrupted()) {
			listener.interrupt();
		}
		listener = null;
	}

	public void run() {
		SelectionKey key;
		Set<SelectionKey> keys;
		Iterator<SelectionKey> it;
		while (!Thread.interrupted()) {
			try {
				if (selector.select(50) == 0)
					continue;
				keys = selector.selectedKeys();
				it = keys.iterator();
				while (it.hasNext()) {
					key = it.next();

					/** 有问题 */
					System.err.println(key.attachment());
					if (null == key.attachment())
						key.attach(UUID.randomUUID().toString());
					else if (DoQueue.contains(key.attachment()))
						continue;
					DoQueue.doWork(key.attachment());
					it.remove();
					if (key.channel().isOpen()) {
						if (key.isAcceptable()) {
							// executor.execute(new SocketMsgAcceptHandler(key));
							SocketChannel client = ((ServerSocketChannel) key.channel()).accept();
							client.configureBlocking(false);
							client.register(key.selector(), SelectionKey.OP_READ, key.attachment()).selector().wakeup();
							DoQueue.doFinish(key.attachment());
						} else if (key.isReadable()) {
							executor.execute(new SocketMsgReaderHandler(key));
						} else if (key.isWritable()) {
							executor.execute(new SocketMsgWriterHandler(key));
						}
					} else {
						key.channel().close();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
		}
	}

	public static void main(String[] args) {
		// System.setProperty("boomhope_tsbank_config", ClassLoader.getSystemClassLoader().getResource("").getPath());
		start();
	}

}
