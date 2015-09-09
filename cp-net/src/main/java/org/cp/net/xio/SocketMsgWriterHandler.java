package org.cp.net.xio;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class SocketMsgWriterHandler extends BaseBufferUtil implements Runnable {
	SelectionKey key;
	SocketChannel client;

	public SocketMsgWriterHandler(SelectionKey key) {
		super();
		this.key = key;
	}

	public void run() {
		client = (SocketChannel) key.channel();
		try {
			client.write(get("你好吗？".getBytes(), 8));
			key.cancel();
			client.close();
			DoQueue.doFinish(key.attachment());
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
