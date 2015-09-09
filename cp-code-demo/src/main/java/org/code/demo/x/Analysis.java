package org.code.demo.x;

public class Analysis {

//	public String printCStyle(int len, int value) {
//		String result = String.valueOf(value);
//		while (result.length() < len)
//			result = "0" + result;
//		return result;
//	}
//
//	public String UnionGenerateKeyByZMK(String keyName, String zmkName)
//			throws Exception {
//		if (keyName.length() == 0) {
//			throw new Exception("in UnionGenerateKeyByZMK:: parameter error!");
//		}
//		String packageBuf = printCStyle(3, 2);
//		int offset = 3;
//		esscFldTagDef fldTag = new esscFldTagDef();
//
//		String packageBufFld = UnionPutFldIntoStr(fldTag.conEsscFldKeyName,
//				keyName, keyName.length());
//		if (packageBufFld.length() == 0) {
//			throw new Exception(
//					"in UnionGenerateKeyByZMK:: UnionPutFldIntoStr for conEsscFldKeyName!\n");
//		}
//		offset += packageBufFld.length();
//		packageBuf = packageBuf + packageBufFld;
//
//		packageBufFld = UnionPutFldIntoStr(fldTag.conEsscFldZMKName, zmkName,
//				zmkName.length());
//		if (packageBufFld.length() == 0) {
//			throw new Exception(
//					"in UnionGenerateKeyByZMK:: UnionPutFldIntoStr for conEsscFldZMKName!\n");
//		}
//		offset += packageBufFld.length();
//		packageBuf = packageBuf + packageBufFld;
//
//		System.out.print(packageBuf);
//
//		CommWithEsscSvr commWithEsscSvr = new CommWithEsscSvr(this.esscIp,
//				this.esscPort, this.timeOut, this.gunionIDOfEsscAPI);
//		int ret = commWithEsscSvr.UnionCommWithEsscSvr("519", packageBuf,
//				offset);
//		if (ret < 0) {
//			throw new Exception(
//					"in UnionGenerateKeyByZMK:: UnionCommWithEsscSvr return result < 0!");
//		}
//		String tmpStr = "";
//		tmpStr = UnionBitReadSpecFldFromStr(commWithEsscSvr.returnPackage, ret,
//				fldTag.conEsscFldKeyValue);
//
//		unionStr us = new unionStr();
//		byte[] bcdStr = us.aschex_to_bcdhex(tmpStr);
//		String pinBlock2 = new String(bcdStr);
//		return pinBlock2;
//	}
}
