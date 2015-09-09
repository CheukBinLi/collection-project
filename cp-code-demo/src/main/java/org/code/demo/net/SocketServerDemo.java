package org.code.demo.net;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServerDemo {

	private ServerSocket serverSocket;
	private Socket socket;

	public void listen(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		new Thread(new listener()).start();
	}

	class listener implements Runnable {

		public void run() {
			while (!Thread.interrupted()) {
				try {
					socket = serverSocket.accept();
					new Thread(new Accept(socket)).start();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	class Accept implements Runnable {

		private Socket socket;
		private DataOutputStream dos;
		private DataInputStream dis;
		private ByteArrayInputStream tempIn;
		private ByteArrayOutputStream tempOut = new ByteArrayOutputStream();
		protected byte[] buffer = new byte[512];
		protected int len = 0;

		public Accept(Socket socket) {
			super();
			this.socket = socket;
		}

		public void run() {
			try {
				dis = new DataInputStream(this.socket.getInputStream());
				dos = new DataOutputStream(this.socket.getOutputStream());
				// 接收
				int code = 0;
				System.out.println(dis.readInt());
				while ((code = dis.read()) != -1)
					System.out.println(code);
				System.out.println(dis.readUTF());
				// 发送
				dos.writeUTF("hhhh");
				dos.flush();
				dos.close();
				dis.close();
				socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) throws IOException {
		new SocketServerDemo().listen(10086);
	}

}
