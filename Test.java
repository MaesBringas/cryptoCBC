/*
 * Test: CBC block cipher
 * CBC PKCS#5 AES
 * version 1.0
 * @authors  100307077 100338415
 */

import java.io.*;

public class Test {

   public static void main(String [] arg) {
      File file = null;
      FileReader fileReader = null;
      
      
      SymmetricCipher sc = new SymmetricCipher();
      
      byte[] byteKey = new byte[] { (byte)49, (byte)50, (byte)51, (byte)52, (byte)53, (byte)54, 
    			(byte)55, (byte)56, (byte)57, (byte)48, (byte)49, (byte)50, (byte)51, (byte)52,
    			(byte)53, (byte)54};
      
      byte[] message = null;
      
      byte[] encrypted;
      byte[] decrypted;
      
      OutputStream outputStream = null;
      OutputStream outputStream2 = null;
      FileInputStream fileInput = null;
      int content;
      int contador = 0;
      
      try {

    	file = new File ("C:/Users/Jorge/Desktop/plaintext.txt");
    	fileInput = new FileInputStream(file); 
        String line = null;
        
        while((content=fileInput.read())!=-1) {
            if(contador != 0){
            	line = line + (char) content;
            }else{
            	line = ""+(char) content;
            }
            contador++;    
        }
        
        message = line.getBytes();
        String prueba = new String(message, "UTF-8");
        System.out.println("UTF-8 encoded > " + prueba);
        System.out.println("Bytes array > " + message);
        
        encrypted = sc.encryptCBC(message, byteKey);
        String prueba2 = new String(encrypted, "UTF-8");
        System.out.println("Encrypted text UTF-8: "+prueba2);
        System.out.println("Encrypted text: "+encrypted);
        
        outputStream2 =new FileOutputStream(new File("C:/Users/Jorge/Desktop/EncryptedFile.txt"));

        outputStream2.write(encrypted, 0, prueba2.length());
        
        
        decrypted = sc.decryptCBC(encrypted, byteKey);
        String prueba3 = new String(decrypted, "UTF-8");
        System.out.println("Decrypted text: "+prueba3);
        
        outputStream =new FileOutputStream(new File("C:/Users/Jorge/Desktop/DecryptedFile.txt"));

        outputStream.write(decrypted, 0, prueba3.length());
        

        System.out.println("Done!");
        

      }
      catch(Exception e){
         e.printStackTrace();
      }finally{
         try{                    
            if( null != fileReader ){   
               fileReader.close();     
            }                  
         }catch (Exception e2){ 
            e2.printStackTrace();
         }
         if (outputStream != null) {
             try {
                 // outputStream.flush();
                 outputStream.close();
             } catch (IOException e) {
                 e.printStackTrace();
             }
      }

   }
   }
   }
