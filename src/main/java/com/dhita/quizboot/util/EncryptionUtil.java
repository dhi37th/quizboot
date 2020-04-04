package com.dhita.quizboot.util;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
public class EncryptionUtil {

  private String aesKey = "ukPLOkuqozUQAqwf"; // store in properties file for security

  private String initializationVector = "ukPLOkuqozUQAqwf";

  /**
   * Encrypt the text using 128 bit AES encryption.
   *
   * @param textToEncrypt text to encrypt
   * @return encryptedString
   * @throws GeneralSecurityException
   */
  public String encrypt(String textToEncrypt) throws GeneralSecurityException {
    IvParameterSpec iv = new IvParameterSpec(initializationVector.getBytes(StandardCharsets.UTF_8));
    SecretKeySpec skeySpec = new SecretKeySpec(aesKey.getBytes(StandardCharsets.UTF_8), "AES");
    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
    cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
    byte[] encrypted = cipher.doFinal(textToEncrypt.getBytes());
    return Base64.getEncoder().encodeToString(encrypted);
  }

  /**
   * Decrypt the string using 128bit AES decryption. Throws generalized Exception if any of
   * (UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException,
   * InvalidKeyException, IllegalBlockSizeException, BadPaddingException) occurs
   *
   * @param textToDecrypt text to decrypt
   * @return  decryptedString
   * @throws GeneralSecurityException
   */
  public String decrypt(String textToDecrypt) throws GeneralSecurityException {
    IvParameterSpec iv = new IvParameterSpec(initializationVector.getBytes(StandardCharsets.UTF_8));
    SecretKeySpec skeySpec = new SecretKeySpec(aesKey.getBytes(StandardCharsets.UTF_8), "AES");

    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
    cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
    byte[] original = cipher.doFinal(Base64.getDecoder().decode(textToDecrypt));

    return new String(original);
  }
}

