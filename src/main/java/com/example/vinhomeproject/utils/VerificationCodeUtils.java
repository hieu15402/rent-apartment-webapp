package com.example.vinhomeproject.utils;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
@Component
public class VerificationCodeUtils {
    private static final long EXPIRATION_TIME_MS = 5 * 60 * 1000; //5 minutes
    private Map<String, VerificationCode> verificationCodes = new HashMap<>();

    public String generateVerificationCode(String email) {
        String code = generateRandomCode();
        VerificationCode verificationCode = new VerificationCode(code, System.currentTimeMillis(), email);
        verificationCodes.put(code, verificationCode);
        return code;
    }

    public boolean isValidCode(String code) {
        VerificationCode verificationCode = verificationCodes.get(code);
        return verificationCode != null && !isCodeExpired(verificationCode);
    }

    public String getEmailByCode(String code) {
        VerificationCode verificationCode = verificationCodes.get(code);
        return (verificationCode != null && !isCodeExpired(verificationCode)) ? verificationCode.getEmail() : null;
    }

    private boolean isCodeExpired(VerificationCode verificationCode) {
        long currentTime = System.currentTimeMillis();
        return (currentTime - verificationCode.getCreationTime()) > EXPIRATION_TIME_MS;
    }

    private String generateRandomCode() {
        String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"; //only digits and Alphabet chars

        int codeLength = 5;

        SecureRandom random = new SecureRandom();
        StringBuilder code = new StringBuilder(codeLength);

        for (int i = 0; i < codeLength; i++) {
            int randomIndex = random.nextInt(characters.length());
            code.append(characters.charAt(randomIndex));
        }
        return code.toString();
    }

    private static class VerificationCode {
        private final String code;
        private final long creationTime;
        private final String email;

        public VerificationCode(String code, long creationTime, String email) {
            this.code = code;
            this.creationTime = creationTime;
            this.email = email;
        }

        public String getCode() {
            return code;
        }

        public long getCreationTime() {
            return creationTime;
        }

        public String getEmail() {
            return email;
        }
    }
}
