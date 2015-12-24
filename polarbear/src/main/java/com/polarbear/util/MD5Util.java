package com.polarbear.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import com.polarbear.domain.User;

public class MD5Util {

	/**
	 * 生成验证帐户的MD5校验码
	 * 
	 * @param user
	 *            要激活的帐户
	 * @return 将随机数和email组合后，通过md5加密后的16进制格式的字符串
	 */
	public static String generateCheckcode(User user) {
		String randomCode = user.getEmail();
		String s = getRandom(4);
		return encode2hex(s + ":" + randomCode);
	}
	
	public static String generateCheckcodeData() {
		//String randomCode = user.getEmail();
		String s = DateFormatUtil.formatDateH(new Date());
		return encode2hex(s);
	}

	/**
	 * 得到随机数字组成的字符串
	 * 
	 * @param bit
	 *            字符串位数
	 * @return String
	 */
	public static String getRandom(int bit) {
		String random = String.valueOf((Math.random() + 1) * Math.pow(10, bit - 1));
		return random.substring(0, bit);
	}

	/**
	 * 将源字符串使用MD5加密为字节数组 设置编码格式 UTF-8
	 * 
	 * @param source
	 * @return
	 */
	public static byte[] encode2bytes(String source) {
		byte[] result = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.reset();
			md.update(source.getBytes("UTF-8"));
			result = md.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 将源字符串使用MD5加密为32位16进制数
	 * 
	 * @param source
	 * @return
	 */
	public static String encode2hex(String source) {
		byte[] data = encode2bytes(source);
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			String hex = Integer.toHexString(0xff & data[i]);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}
		return hexString.toString();
	}

	public static void main(String[] args) {
        System.out.println(encode2hex("123456"));
    }
}
