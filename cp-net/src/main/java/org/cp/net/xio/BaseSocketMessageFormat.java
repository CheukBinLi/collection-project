package org.cp.net.xio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/***
 * SOCKET数据结构
 * 
 * @author Administrator
 *
 */
public abstract class BaseSocketMessageFormat {

	// 4+16+msg
	private static BaseSocketMessageFormat bmf = new BaseSocketMessageFormat() {
	};

	private BaseSocketMessageFormat() {
	}

	public static synchronized BaseSocketMessageFormat newInstance() {
		return bmf;
	}

	/***
	 * socket格式化数据
	 *
	 * @param msg
	 * @return
	 * @throws IOException
	 */
	public byte[] convertStream(byte[] msg) throws IOException {
		// 16位MAC
		// String mac = this.getMACValue(msg);
		return getData(new byte[16], msg);
	};

	protected byte[] getData(byte[] mac, byte[] msg) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		String len = Integer.toString(mac.length + msg.length);
		// 4位长度
		for (int i = len.length(); i < 4; i++)
			out.write("0".getBytes());
		// 格式拼接
		out.write(len.getBytes());
		out.write(mac);
		out.write(msg);
		return out.toByteArray();
	}

	/***
	 * socket接收数据
	 * 
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static byte[] receiveData(InputStream in) throws IOException {
		byte[] buffer = new byte[4];
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		in.read(buffer);
		int len = Integer.valueOf(new String(buffer));
		// mac
		for (int i = 0; i < 16; i++) {
			len--;
			in.read();
		}
		while (len-- > 0) {
			out.write(in.read());
		}
		return out.toByteArray();
	}

	public static byte[] receiveData(SocketChannel client) throws IOException {
		ByteBuffer[] buffer = new ByteBuffer[1];
		buffer[0] = ByteBuffer.allocate(1024);
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		client.read(buffer, 0, 4);// 长度
		int len = Integer.valueOf(new String(get(buffer[0])));

//		out.write(buffer.array());
//		buffer = ByteBuffer.allocate(16);
//		client.read(buffer);// MAC
//		buffer = buffer.allocate(1024);
		// if(len)
		return null;
	}

	static byte[] get(ByteBuffer buff) {
		byte[] a = new byte[buff.limit()];
		for (int i = 0; i < buff.limit(); i++) {
			a[i] = buff.get();
		}
		return a;
	}

	public static void main(String[] args) throws IOException {
		// // 配置文件
		// System.out.println(System.getProperty("boomhope_tsbank_config"));
		// byte[] x = new BaseSocketMessageFormat() {
		// //
		// }.convertStream("<?xml version='1.0' encoding='GBK'?><ROOT><HEAD><JRNL_NO>1012210591</JRNL_NO><CHANNEL>0001</CHANNEL><TRAN_CODE>01330</TRAN_CODE><INST_NO>050100122</INST_NO><TERM_NO>lds</TERM_NO><TELLER_NO>55081</TELLER_NO><SUP_TELLER_NO></SUP_TELLER_NO><SUP_ZW_TELLER_NO></SUP_ZW_TELLER_NO><SUP_TELLER_PWD></SUP_TELLER_PWD><SUP_INST_NO></SUP_INST_NO><TRAN_DATE>20151012</TRAN_DATE><TRAN_TIME>08:59:04</TRAN_TIME><ENC_TERM_NO></ENC_TERM_NO><TASK_ID>1510120012255081142102</TASK_ID><OPER_DATE></OPER_DATE><APPR_JRNL_NO></APPR_JRNL_NO><CHL_TRAN_CODE></CHL_TRAN_CODE><AGENT_NAME>ACSCLT.AGENT</AGENT_NAME><MQM_NAME>ABS_GCD_TSCB</MQM_NAME><COMP_TELLER></COMP_TELLER><CHK_TELLER></CHK_TELLER><RESV1></RESV1><RESV2></RESV2><IMG_JRNL_NO></IMG_JRNL_NO><NODE_ID></NODE_ID><OPER_TELLER_NO>55081</OPER_TELLER_NO></HEAD><RESPONSE><RET_CODE></RET_CODE><RET_MSG></RET_MSG></RESPONSE><BODY><QRY_TELLER_NO>55081</QRY_TELLER_NO></BODY></ROOT>");
		// }.convertStream("abcdefg");
		// System.out.println(x.length);
		// System.err.println(new String(x));
	}

}
