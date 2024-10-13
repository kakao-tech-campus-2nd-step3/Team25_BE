package com.team25.backend.util;

import com.team25.backend.exception.EncryptionException;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class BillingKeyAttributeConverter implements AttributeConverter<String, String> {

    @Override
    public String convertToDatabaseColumn(String bid) {
        try {
            return EncryptionUtil.encrypt(bid);
        } catch (Exception e) {
            throw new EncryptionException("빌링키를 암호화 하는 데 실패하였습니다.");
        }
    }

    @Override
    public String convertToEntityAttribute(String encryptedBid) {
        try {
            return EncryptionUtil.decrypt(encryptedBid);
        } catch (Exception e) {
            throw new EncryptionException("빌링키를 복호화 하는 데 실패하였습니다.");
        }
    }
}
