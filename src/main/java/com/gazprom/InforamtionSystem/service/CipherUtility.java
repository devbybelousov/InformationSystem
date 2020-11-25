package com.gazprom.InforamtionSystem.service;

import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.io.InputStream;
import java.io.StringWriter;
import java.security.*;
import java.security.cert.Certificate;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;


public class CipherUtility {

    private static final Logger logger = LoggerFactory.getLogger(CipherUtility.class);

    public static KeyPair generateKeyPair() throws Exception{
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(4096, new SecureRandom());
        KeyPair keyPair = generator.generateKeyPair();
        return keyPair;
    }

    public static KeyPair getKeyPairFromKeyStore() throws Exception{
        InputStream inputStream = CipherUtility.class.getResourceAsStream("/keystore.jks");

        KeyStore keyStore = KeyStore.getInstance("JCEKS");
        keyStore.load(inputStream, "S3wQNPON".toCharArray());
        KeyStore.PasswordProtection keyPassword = new KeyStore.PasswordProtection("S3wQNPON".toCharArray());

        KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry("gazprom", keyPassword);
        Certificate certificate = keyStore.getCertificate("gazprom");
        PublicKey publicKey = certificate.getPublicKey();
        PrivateKey privateKey = privateKeyEntry.getPrivateKey();

        return new KeyPair(publicKey, privateKey);
    }

    public static String getPublicKey(){
        StringWriter writer = new StringWriter();
        PemWriter pemWriter = new PemWriter(writer);
        try {
            pemWriter.writeObject(new PemObject("PUBLIC KEY", getKeyPairFromKeyStore().getPublic().getEncoded()));
            pemWriter.flush();
            pemWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return writer.toString();
    }

    public static String encrypt(String plainText, String publicKey) throws Exception{
        String newKeyPublic = publicKey.replace("-----BEGIN PUBLIC KEY-----", "").
                replace("-----END PUBLIC KEY-----", "").replace(" ", "+");;
        byte[] encoded = org.apache.commons.codec.binary.Base64.decodeBase64(newKeyPublic);
        X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(encoded);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey key = keyFactory.generatePublic(keySpecX509);
        return CipherUtility.encrypt(plainText, key);
    }

    public static String encrypt(String plainText, PublicKey publicKey) throws Exception {
        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] cipherText = encryptCipher.doFinal(plainText.getBytes(UTF_8));
        return Base64.getEncoder().encodeToString(cipherText);
    }

    public static Object decrypt(String cipherText){
        String decText = "";
        try {
            KeyPair keyPair = getKeyPairFromKeyStore();
            decText = decrypt(cipherText, keyPair.getPrivate());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ConverterJson.parseJSON(decText);
    }

    public static String decrypt(String cipherText, PrivateKey privateKey) throws Exception {
        byte[] bytes = Base64.getDecoder().decode(cipherText);

        Cipher decriptCipher = Cipher.getInstance("RSA");
        decriptCipher.init(Cipher.DECRYPT_MODE, privateKey);

        return new String(decriptCipher.doFinal(bytes), UTF_8);
    }

    public static String sign(String plainText, PrivateKey privateKey) throws Exception {
        Signature privateSignature = Signature.getInstance("SHA256withRSA");
        privateSignature.initSign(privateKey);
        privateSignature.update(plainText.getBytes(UTF_8));

        byte[] signature = privateSignature.sign();

        return Base64.getEncoder().encodeToString(signature);
    }

    public static boolean verify(String plainText, String signature, PublicKey publicKey) throws Exception {
        Signature publicSignature = Signature.getInstance("SHA256withRSA");
        publicSignature.initVerify(publicKey);
        publicSignature.update(plainText.getBytes(UTF_8));

        byte[] signatureBytes = Base64.getDecoder().decode(signature);

        return publicSignature.verify(signatureBytes);
    }
}
