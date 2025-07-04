package br.com.lume.lumemarketplace.config.crypto;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class AttributeEncryptor implements AttributeConverter<String, String> {

    private static final String AES = "AES";

    private static final String SECRET = System.getenv("ENCRYPTION_KEY") != null
        ? System.getenv("ENCRYPTION_KEY")
        : "LumeKey16Chars!!";

    private static final Key key;

    static {
        if (SECRET.length() != 16 && SECRET.length() != 24 && SECRET.length() != 32) {
            throw new IllegalArgumentException("A chave de criptografia deve ter 16, 24 ou 32 caracteres.");
        }
        key = new SecretKeySpec(SECRET.getBytes(StandardCharsets.UTF_8), AES);
    }

    @Override
    public String convertToDatabaseColumn(String attribute) {
        if (attribute == null) return null;
        try {
            Cipher cipher = Cipher.getInstance(AES);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return Base64.getEncoder().encodeToString(cipher.doFinal(attribute.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new IllegalStateException("Erro ao criptografar", e);
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        try {
            Cipher cipher = Cipher.getInstance(AES);
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(Base64.getDecoder().decode(dbData)), StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.err.println("Erro ao descriptografar: " + e.getMessage());
            return dbData;
        }
    }
}
