package com.cosyit.lmbbs.util.des;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

public class DESUtils {
	private static Key key;
	private static String KEY_STR = "mumu_teacher";
	static {
		try {
			KeyGenerator generator = KeyGenerator.getInstance("DES");
			generator.init(new SecureRandom(KEY_STR.getBytes()));
			key = generator.generateKey();
			generator = null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 加密
	 * 
	 * @param str
	 * @return
	 */
	public static String encode(String str) {

		//BASE64Encoder base64en = new BASE64Encoder();
		// 从JKD 9开始rt.jar包已废除，从JDK 1.8开始使用java.util.Base64.Encoder
		Encoder encoder = Base64.getEncoder();

		try {
			byte[] strBytes = str.getBytes("UTF8");
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] encryptStrBytes = cipher.doFinal(strBytes);
			//return base64en.encode(encryptStrBytes);
			return encoder.encodeToString(encryptStrBytes);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 解密
	 * 
	 * @param str
	 * @return
	 */
	public static String decode(String str) {
		Decoder decoder = Base64.getDecoder();
		try {
			byte[] strBytes = decoder.decode(str);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] decryptStrBytes = cipher.doFinal(strBytes);
			return new String(decryptStrBytes, "UTF8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) throws Exception {

		try {
			String encodePassword = DESUtils.encode("root");
			System.out.println(encodePassword);//ny6yrajIKKY=
			System.out.println(decode(encodePassword));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
}
