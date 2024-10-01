package utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.MessageDigest;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class HashingUtil {

    public static String gerarHashComSalt(String senha) {
        try {
            byte[] salt = new byte[16];
            SecureRandom sr = new SecureRandom();
            sr.nextBytes(salt);

            PBEKeySpec spec = new PBEKeySpec(senha.toCharArray(), salt, 65536, 128);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hash = skf.generateSecret(spec).getEncoded();

            String saltBase64 = Base64.getEncoder().encodeToString(salt);
            String hashBase64 = Base64.getEncoder().encodeToString(hash);

            return saltBase64 + ":" + hashBase64;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Erro ao gerar hash", e);
        }
    }

    public static boolean validarSenha(String senha, String hashComSaltArmazenado) {
        try {
            String[] partes = hashComSaltArmazenado.split(":");
            byte[] salt = Base64.getDecoder().decode(partes[0]);
            byte[] hashArmazenado = Base64.getDecoder().decode(partes[1]);

            PBEKeySpec spec = new PBEKeySpec(senha.toCharArray(), salt, 65536, 128);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] novoHash = skf.generateSecret(spec).getEncoded();

            return MessageDigest.isEqual(novoHash, hashArmazenado);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Erro ao validar senha", e);
        }
    }
}
