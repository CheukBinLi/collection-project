package org.cp.net.xio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class SocketMsgReaderHandler extends BaseBufferUtil implements Runnable {

	SelectionKey key;
	SocketChannel client;

	public SocketMsgReaderHandler(SelectionKey key) {
		super();
		this.key = key;
	}

	public void run() {
		client = (SocketChannel) key.channel();
		Object id = key.attachment();
		try {
			// 前8位长度
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ByteBuffer bb = ByteBuffer.allocate(1024);
			String lenStr = new String(get(client, 8));// 长度
			System.out.println(lenStr);
			int len = Integer.valueOf(lenStr);
			int count = 0;
			if (len > 1024) {
				count = len / 1024;
				len = len - (count * 1024);
				for (int i = 0; i < count; i++) {
					client.read(bb);
					bb.flip();
					out.write(bb.array());
					bb.clear();
				}
			}
			out.write(get(client, len));
			// 输出
			System.out.println(new String(out.toByteArray()));
			client.register(key.selector(), SelectionKey.OP_WRITE, id).selector().wakeup();
			DoQueue.doFinish(key.attachment());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
