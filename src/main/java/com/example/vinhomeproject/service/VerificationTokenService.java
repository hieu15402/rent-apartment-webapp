package com.example.vinhomeproject.service;

import com.example.vinhomeproject.models.Users;
import com.example.vinhomeproject.models.VerificationToken;
import com.example.vinhomeproject.repositories.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Calendar;

@Service
public class VerificationTokenService {

    @Autowired
    private VerificationTokenRepository repository;
    @Transactional
    public VerificationToken findByToken(String token){
        return repository.findByToken(token);
    }
    @Transactional
    public VerificationToken findByUser(Users users){
        return repository.findByUsers(users);
    }

    @Transactional
    public void save(Users users, String token){
        VerificationToken verificationToken = new VerificationToken(token,users);
        verificationToken.setExpiryDate(calculateExpiryDate(24*60));
        repository.save(verificationToken);
    }

    private Timestamp calculateExpiryDate(int expiryTimeInMinutes){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Timestamp(cal.getTime().getTime());
    }

}
