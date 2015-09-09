package org.cp.net.nio.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class BaseDataModel {

	private byte[] type;// 4位
	private byte[] verificationCode;// 32位
	private long length;// 8位
	private byte[] data;

	private BaseDataModel(InputStream in) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[4];// 类型
		in.read(buffer);
		type = buffer;
		buffer = new byte[32];// 验证码
		in.read(buffer);
		verificationCode = buffer;
		buffer = new byte[8];// 数据长度
		in.read(buffer);
		length = Long.valueOf(new String(buffer));
		// 数据
		for (int i = 0; i < length; i++) {
			out.write(in.read());
		}
		data = out.toByteArray();
	}

	private BaseDataModel(SocketChannel in) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteBuffer buffer = ByteBuffer.allocate(4);// 类型
		in.read(buffer);
		type = buffer.array();
		buffer = ByteBuffer.allocate(32);// 验证码
		in.read(buffer);
		verificationCode = buffer.array();
		buffer = ByteBuffer.allocate(8);// 数据长度
		in.read(buffer);
		length = Long.valueOf(new String(buffer.array()));
		buffer = ByteBuffer.allocate(1);// 数据
		for (int i = 0; i < length; i++) {
			in.read(buffer);
			out.write(buffer.array());
			buffer.flip();
		}
		data = out.toByteArray();
	}

	public byte[] getDataFormat() throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		String len = Long.toString(length);
		out.write(type);// 类型
		out.write(verificationCode);// 验证
		for (int i = len.length(); i < 8; i++) {
			out.write("0".getBytes());
		}
		out.write(len.getBytes());// 长度
		out.write(data);// 数据
		return out.toByteArray();
	}

	public ByteBuffer getDataFormats() throws IOException {
		return ByteBuffer.wrap(getDataFormat());
	}

	public static BaseDataModel readData(InputStream in) throws IOException {
		return new BaseDataModel(in);
	}

	public static BaseDataModel readData(SocketChannel in) throws IOException {
		return new BaseDataModel(in);
	}

	public BaseDataModel() {
		super();
	}

	/***
	 * 
	 * @param type 4位字节
	 * @param verificationCode 32位字节
	 * @param data 消息
	 */
	public BaseDataModel(byte[] type, byte[] verificationCode, byte[] data) {
		super();
		this.type = type;
		this.verificationCode = verificationCode;
		this.data = data;
		this.length = this.data.length;
	}

	/***
	 * 
	 * @param type 4位字节
	 * @param verificationCode 32位字节
	 * @param data 消息
	 */
	public BaseDataModel(String type, String verificationCode, String data) {
		super();
		this.type = type.getBytes();
		this.verificationCode = verificationCode.getBytes();
		this.data = data.getBytes();
		this.length = this.data.length;
	}

	public long getLength() {
		return length;
	}

	public byte[] getVerificationCode() {
		return verificationCode;
	}

	public BaseDataModel setVerificationCode(byte[] verificationCode) {
		this.verificationCode = verificationCode;
		return this;
	}

	public byte[] getData() {
		return data;
	}

	public BaseDataModel setData(byte[] data) {
		this.data = data;
		this.length = this.data.length;
		return this;
	}

	public byte[] getType() {
		return type;
	}

	public void setType(byte[] type) {
		this.type = type;
	}

	public static void main(String[] args) throws IOException {
		// BaseDataModel bdm = new BaseDataModel("zxcvbnmasdfghjklqwertyuiopasdfgh".getBytes(), "你好吗？".getBytes());
		// System.out.println(new String(bdm.getDataFormat()));
		// BaseDataModel.padding(bdm.getDataFormat());
	}
}
