/* 
 * CBC block cipher
 * version 1.0
 * @authors  100307077 100338415
 */

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;

import javax.crypto.*;
import java.security.InvalidKeyException;
import java.util.Arrays;

public class SymmetricCipher {

	private final int AES_BLOCK_SIZE = 16;
	byte[] byteKey;
	SymmetricEncryption s;
	SymmetricEncryption d;
	
	// Initialization Vector 
	
	byte[] iv = new byte[] { (byte)49, (byte)50, (byte)51, (byte)52, (byte)53, (byte)54, 
		(byte)55, (byte)56, (byte)57, (byte)48, (byte)49, (byte)50, (byte)51, (byte)52,
		(byte)53, (byte)54};

	public void SymmetricCipher() {
	}

	public byte[] encryptCBC (byte[] input, byte[] byteKey) throws Exception {
		
		byte[] ciphertext = null;	
		s = new SymmetricEncryption(byteKey);
		int lengthText=input.length;
		int Padding = AES_BLOCK_SIZE - (lengthText % AES_BLOCK_SIZE);
		int numPadding;
		
		if (Padding == 0 ){
			numPadding = AES_BLOCK_SIZE;
		}else{
			numPadding = Padding;
		}
		
		byte[] padd = new byte[numPadding];
	
        for (int i = 0; i< numPadding;i++){
			padd[i] = (byte)numPadding;
		}

		byte[] p = new byte[lengthText + padd.length];		
		int NumBlocks = p.length/AES_BLOCK_SIZE;
		byte[] b = new byte[AES_BLOCK_SIZE];
		int msgLength = AES_BLOCK_SIZE * NumBlocks;
		byte[] firstBlock = iv;
		ciphertext = new byte[msgLength];
		
        System.arraycopy(input, 0, p, 0, lengthText);
		System.arraycopy(padd, 0, p, lengthText, padd.length);

		for(int i = 0; i < NumBlocks; i++){
			
			b = Arrays.copyOfRange(p, i*AES_BLOCK_SIZE, i*AES_BLOCK_SIZE + AES_BLOCK_SIZE);
			byte[] xored = new byte[AES_BLOCK_SIZE];
			
            for(int j = 0; j < AES_BLOCK_SIZE; j++){	
				xored[j] = (byte) (b[j] ^ firstBlock[j]);
			}
			
            firstBlock = s.encryptBlock(xored);
			System.arraycopy(firstBlock, 0, ciphertext, i * AES_BLOCK_SIZE, AES_BLOCK_SIZE);
		}
        
		return ciphertext;
	}
	
	
	public byte[] decryptCBC (byte[] input, byte[] byteKey) throws Exception {

		byte [] finalplaintext = null;
		d = new SymmetricEncryption(byteKey);
		int NumBlocks = input.length/AES_BLOCK_SIZE;
		byte[] b = new byte[AES_BLOCK_SIZE];
		byte[] firstBlock = iv;		
		byte[] padded = new byte[input.length];	

		for(int i = 0; i < NumBlocks; i++){
			b = Arrays.copyOfRange(input, i*AES_BLOCK_SIZE, i*AES_BLOCK_SIZE + AES_BLOCK_SIZE);
			byte[] deciphered = s.decryptBlock(b);		
			byte[] xored = new byte[AES_BLOCK_SIZE];
			for(int j = 0; j < AES_BLOCK_SIZE; j++){	
				xored[j] = (byte) (deciphered[j] ^ firstBlock[j]);
			}
			firstBlock = b;
			System.arraycopy(xored, 0, padded, i * AES_BLOCK_SIZE, b.length);
		}
		
        finalplaintext = new byte[padded.length - (int)padded[padded.length -1]];
		System.arraycopy(padded, 0, finalplaintext, 0, padded.length - (int)padded[padded.length -1]);
		return finalplaintext;
	}
	
}
