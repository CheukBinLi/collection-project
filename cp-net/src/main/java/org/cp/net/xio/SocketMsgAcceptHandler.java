package org.cp.net.xio;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.UUID;

public class SocketMsgAcceptHandler implements Runnable {

	SelectionKey key;
	SocketChannel client;

	public SocketMsgAcceptHandler(SelectionKey key) {
		super();
		this.key = key;
	}

	public void run() {
		try {
			SocketChannel client = ((ServerSocketChannel) key.channel()).accept();
			client.configureBlocking(false);
			client.register(key.selector(), SelectionKey.OP_READ, key.attachment()).selector().wakeup();
			DoQueue.doFinish(key.attachment());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
