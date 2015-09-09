package org.cp.net.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Server implements Runnable {

	private ServerSocketChannel server;
	private Selector selector;
	private Thread thread;

	public void start() {

		try {
			selector = Selector.open();
			server = ServerSocketChannel.open();
			server.socket().bind(new InetSocketAddress(10086));
			server.configureBlocking(false);
			server.register(selector, SelectionKey.OP_ACCEPT);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {

		Set<SelectionKey> keys;
		SelectionKey key;
		Iterator<SelectionKey> it;
		SocketChannel client;
		while (!Thread.interrupted()) {
			try {
				if (selector.select() == 0) {
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				keys = selector.selectedKeys();
				it = keys.iterator();
				while (it.hasNext()) {

				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
