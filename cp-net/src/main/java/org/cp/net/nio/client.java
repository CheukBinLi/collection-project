package org.cp.net.nio;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.cp.net.nio.model.BaseDataModel;

public class client {

	public static void main(String[] args) throws UnknownHostException, IOException {

//		Socket socket = new Socket("127.0.0.1", 10086);
//		DataOutputStream out = new DataOutputStream(socket.getOutputStream());
//		BaseDataModel model = new BaseDataModel("zxcvbnmlkjhgfdsaqwertyuioplkjhgf".getBytes(), "客户端发送消息".getBytes());
//		out.write(model.getDataFormat());
//		out.flush();
//		DataInputStream in = new DataInputStream(socket.getInputStream());
//		model = BaseDataModel.readData(in);
//		System.out.println(new String(model.getData()));
//		in.close();
	}
}
