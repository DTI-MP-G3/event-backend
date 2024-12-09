package com.event.event.infrastructure.users.utils;


import com.event.event.infrastructure.users.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Random;

@Service
@Slf4j
public class ReferralCodeService {
    private static final int CODE_LENGTH = 6;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int MAX_ATTEMPTS = 10;

    private UsersRepository usersRepository;

    public ReferralCodeService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public String generateUniqueReferralCode() {
        String referralCode;
        int attempts = 0;

        do {
            if (attempts >= MAX_ATTEMPTS) {
                throw new RuntimeException("Unable to generate unique referral code after " + MAX_ATTEMPTS + " attempts");
            }

            referralCode = generateRandomCode();
            attempts++;
        } while (usersRepository.existsByReferralCode(referralCode));

        return referralCode;
    }


    private String generateRandomCode() {
        StringBuilder code = new StringBuilder();
        Random random = new SecureRandom();

        for (int i = 0; i < CODE_LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            code.append(CHARACTERS.charAt(randomIndex));
        }

        return code.toString();
    }
}
