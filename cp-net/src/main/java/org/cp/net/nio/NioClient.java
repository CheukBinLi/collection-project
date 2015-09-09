package org.cp.net.nio;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.cp.net.nio.model.BaseDataModel;

public class NioClient implements Runnable {

	private static SocketChannel socketChannel;
	private static Selector selector;
	private Thread thread;
	boolean isWrite = false;
	Iterator<SelectionKey> it;
	Set<SelectionKey> keys;
	SelectionKey key;
	SelectionKey newKey;
	SocketChannel sc;

	public void start() throws IOException {

		selector = Selector.open();
		socketChannel = SocketChannel.open();
		socketChannel.configureBlocking(false);
		socketChannel.register(selector, SelectionKey.OP_CONNECT);
		socketChannel.connect(new InetSocketAddress("127.0.0.1", 10010));
		thread = new Thread(this);
		thread.start();
	}

	public void run() {

		while (!Thread.interrupted()) {
			try {
				if (selector.select() == 0) {
					Thread.sleep(50);
					continue;
				}
				keys = selector.selectedKeys();
				it = keys.iterator();
				while (it.hasNext()) {
					key = it.next();
					// it.remove();
					if (key.isConnectable()) {
						sc = (SocketChannel) key.channel();
						if (sc.isConnectionPending()) {
							if (sc.finishConnect()) {
								System.out.println("完成连接");
							}
							BaseDataModel model = new BaseDataModel(new byte[4], new byte[32], "客户端发送消息：连接完成!".getBytes());
							sc.write(model.getDataFormats());
						}
						newKey = sc.register(key.selector(), SelectionKey.OP_READ);
						// newKey.selector().wakeup();
					} else if (key.isReadable()) {
						sc = (SocketChannel) key.channel();
						BaseDataModel model = BaseDataModel.readData(sc);
						System.out.println(new String("消息:" + new String(model.getData())));
						// socketChannel = null;
						// 要写时注册
						// newKey = sc.register(key.selector(), SelectionKey.OP_WRITE);
						// newKey.selector().wakeup();
					} else if (key.isWritable()) {
						// socketChannel = (SocketChannel) key.channel();
						// BaseDataModel model = new BaseDataModel(new byte[4], new byte[32], "".getBytes());
						// sc.write(model.getDataFormats());
						// newKey = sc.register(key.selector(), SelectionKey.OP_READ);
						// newKey.selector().wakeup();
						isWrite = !isWrite;
					}

				}
				keys.clear();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void send(String msg) throws IOException {
		newKey = socketChannel.register(key.selector(), SelectionKey.OP_WRITE);
		while (isWrite) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		BaseDataModel model = new BaseDataModel(new byte[4], new byte[32], msg.getBytes());
		socketChannel.write(model.getDataFormats());
		newKey = socketChannel.register(key.selector(), SelectionKey.OP_READ);
		newKey.selector().wakeup();
	}

	TextArea ta = new TextArea();

	public static void main(String[] args) throws IOException {
		final NioClient client = new NioClient();
		client.start();
		// PrintWriter pw = new PrintWriter(System.out);
		// InputStreamReader in = new InputStreamReader(System.in);
		// int code = 0;
		// ByteArrayOutputStream out = new ByteArrayOutputStream();
		// System.out.println("输入内容");
		// while (true) {
		// code = in.read();
		// if (code == '\n') {
		// System.out.println(new String(out.toByteArray()));
		// out.reset();
		// } else
		// out.write(code);
		// }

		JFrame f = new JFrame("NIO客户端");
		f.setSize(500, 300);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Panel p = new Panel(new FlowLayout(1));
		client.ta.setSize(100, 200);
		p.add(client.ta);
		JButton button = new JButton("发送");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					client.send(client.ta.getText());
					client.ta.setText("");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		p.add(button);
		f.add(p);
		f.setVisible(true);
	}

}
