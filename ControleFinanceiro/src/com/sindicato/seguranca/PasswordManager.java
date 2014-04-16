package com.sindicato.seguranca;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordManager {

	public static String generated(String password){
	    MessageDigest m = null;
		try {
			m = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	    try {
			m.update(password.getBytes("UTF8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	    byte s[] = m.digest();
	    String result = "";
	    for (int i = 0; i < s.length; i++) {
	      result += Integer.toHexString((0x000000ff & s[i]) | 0xffffff00).substring(6);
	    }
	    return result;
	}
	
}
