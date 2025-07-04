package br.com.lume.lumemarketplace.config.crypto;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDate;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class LocalDateEncryptor implements AttributeConverter<LocalDate, String> {

    private static final String AES = "AES";
    private static final String SECRET = System.getenv("ENCRYPTION_KEY") != null
        ? System.getenv("ENCRYPTION_KEY")
        : "LumeKey16Chars!!";

    private static final Key key;

    static {
        if (SECRET.length() != 16 && SECRET.length() != 24 && SECRET.length() != 32) {
            throw new IllegalArgumentException("Chave inválida");
        }
        key = new SecretKeySpec(SECRET.getBytes(StandardCharsets.UTF_8), AES);
    }

    @Override
    public String convertToDatabaseColumn(LocalDate attribute) {
        if (attribute == null) return null;
        try {
            Cipher cipher = Cipher.getInstance(AES);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return Base64.getEncoder().encodeToString(
                cipher.doFinal(attribute.toString().getBytes(StandardCharsets.UTF_8))
            );
        } catch (Exception e) {
            throw new IllegalStateException("Erro ao criptografar data", e);
        }
    }

    @Override
    public LocalDate convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        try {
            Cipher cipher = Cipher.getInstance(AES);
            cipher.init(Cipher.DECRYPT_MODE, key);
            String decrypted = new String(cipher.doFinal(Base64.getDecoder().decode(dbData)), StandardCharsets.UTF_8);
            return LocalDate.parse(decrypted);
        } catch (Exception e) {
            System.err.println("Erro ao descriptografar data: " + e.getMessage());
            return null;
        }
    }
}
