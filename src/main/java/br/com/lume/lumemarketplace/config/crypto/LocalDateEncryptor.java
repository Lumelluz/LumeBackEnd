package br.com.lume.lumemarketplace.config.crypto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDate;
import java.util.Base64;

@Component
@Converter
public class LocalDateEncryptor implements AttributeConverter<LocalDate, String> {

    private static final String AES = "AES";
    private final Key key;

    public LocalDateEncryptor(@Value("${encryption.key}") String secret) {
        if (secret == null || (secret.length() != 16 && secret.length() != 24 && secret.length() != 32)) {
            throw new IllegalArgumentException("A chave de criptografia deve ser definida e ter 16, 24 ou 32 caracteres.");
        }
        this.key = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), AES);
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
            throw new IllegalStateException("Erro ao descriptografar data", e);
        }
    }
}