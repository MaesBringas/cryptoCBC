
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;
import javax.crypto.NoSuchPaddingException;


import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;


public class SymmetricEncryption {

	final int AES_BLOCK_SIZE = 16;
	final String ALGORITHM = "AES";
	final String MODE_OF_OPERATION = "AES/ECB/NoPadding";

	Cipher aesEnc, aesDec;
	SecretKeySpec key;

	public SymmetricEncryption (byte[] byteKey) throws InvalidKeyException {
		
		try {
			key = new SecretKeySpec(byteKey, ALGORITHM);

   			aesEnc = Cipher.getInstance(MODE_OF_OPERATION);
    		aesEnc.init(Cipher.ENCRYPT_MODE, key);
		
   			aesDec = Cipher.getInstance(MODE_OF_OPERATION);
    		aesDec.init(Cipher.DECRYPT_MODE, key);
		
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Exception: " + e.getMessage());
			System.exit(-1);

		} catch (NoSuchPaddingException e) {
			System.out.println("Exception: " + e.getMessage());
			System.exit(-1);
		}
	}

	public byte[] encryptBlock(byte[] input) throws IllegalBlockSizeException, BadPaddingException {

		return aesEnc.doFinal(input);
	}


	public byte[] decryptBlock(byte[] input) throws IllegalBlockSizeException, BadPaddingException {

		return aesDec.doFinal(input);
	}
}
