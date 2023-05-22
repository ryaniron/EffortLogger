package application;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;



public class Encryptor {

	static SecretKey key;
	static Cipher cipher_DES;
	static byte[] coolData;

	public static String encrypt(String s) {
		coolData = "fffe7a6b".getBytes();
	    try {
	    	cipher_DES = Cipher.getInstance("DES");
	    	DESKeySpec desKeySpec = new DESKeySpec(coolData);
	    	key = SecretKeyFactory.getInstance("DES").generateSecret(desKeySpec);
	    	//System.out.print(key);
	            
	    	byte[] text = s.getBytes("UTF8");
	    	cipher_DES.init(Cipher.ENCRYPT_MODE, key);
	    	return new String(cipher_DES.doFinal(text));
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    return "";
	}


	public static String decrypt(String s) {
		coolData = "fffe7a6b".getBytes();
		try {
	    	cipher_DES = Cipher.getInstance("DES");
	    	DESKeySpec desKeySpec = new DESKeySpec(coolData);
	    	key = SecretKeyFactory.getInstance("DES").generateSecret(desKeySpec);
	    	//System.out.print(key);
	    	
			byte[] text = s.getBytes("UTF8");
			cipher_DES.init(Cipher.DECRYPT_MODE, key);
			return new String(cipher_DES.doFinal(text));
	    } catch (Exception e) {
	    	e.printStackTrace();
	     }
		return "";
	}
}
