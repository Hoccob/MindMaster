package net.hoccob.mindmaster;

import com.google.android.gms.common.util.Hex;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Encryption {

    static byte[] key = Hex.stringToBytes("36CF8F8B2D7922090222FF8887F3D28E");

    public Encryption() throws Exception {

    }


    public static String encrypt(String clear) throws Exception{
        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(clear.getBytes());
        return Hex.bytesToStringUppercase(encrypted);
    }

    public static String decrypt(String encrypted) throws Exception{
        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] decrypted = cipher.doFinal(Hex.stringToBytes(encrypted));
        return new String(decrypted, "UTF-8");
    }

}
