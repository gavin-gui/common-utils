package com.easycms.common.util;

import com.sun.crypto.provider.SunJCE;
import java.security.Key;
import java.security.Security;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Tea {
	private static String strDefaultKey = "national";
	private Cipher encryptCipher = null;
	private Cipher decryptCipher = null;

	public static String byteArr2HexStr(byte[] arrB) throws Exception {
		int iLen = arrB.length;

		StringBuffer sb = new StringBuffer(iLen * 2);

		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i];
			while (intTmp < 0) {
				intTmp += 256;
			}
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}

	public static byte[] hexStr2ByteArr(String strIn) throws Exception {
		byte[] arrB = strIn.getBytes();
		int iLen = arrB.length;
		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i += 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[(i / 2)] = (byte) Integer.parseInt(strTmp, 16);
		}

		return arrOut;
	}

	public Tea() throws Exception {
		this(strDefaultKey);
	}

	public Tea(String strKey) throws Exception {
		Security.addProvider(new SunJCE());
		Key key = getKey(strKey.getBytes());
		this.encryptCipher = Cipher.getInstance("DES");
		this.encryptCipher.init(1, key);
		this.decryptCipher = Cipher.getInstance("DES");
		this.decryptCipher.init(2, key);
	}

	public byte[] encrypt(byte[] arrB) throws Exception {
		return this.encryptCipher.doFinal(arrB);
	}

	public String encrypt(String strIn) throws Exception {
		return byteArr2HexStr(encrypt(strIn.getBytes()));
	}

	public byte[] decrypt(byte[] arrB) throws Exception {
		return this.decryptCipher.doFinal(arrB);
	}

	public String decrypt(String strIn) throws Exception {
		return new String(decrypt(hexStr2ByteArr(strIn)));
	}

	private Key getKey(byte[] arrBTmp) throws Exception {
		byte[] arrB = new byte[8];
		for (int i = 0; (i < arrBTmp.length) && (i < arrB.length); i++) {
			arrB[i] = arrBTmp[i];
		}
		Key key = new SecretKeySpec(arrB, "DES");

		return key;
	}
}