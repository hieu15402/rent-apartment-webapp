package com.example.vinhomeproject.service;

import com.example.vinhomeproject.models.Users;
import com.example.vinhomeproject.models.VerificationToken;
import com.example.vinhomeproject.utils.SendMailUtils;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private VerificationTokenService service;
    @Autowired
    private SendMailUtils sendMailUtils;
    public  void sendMail(Users users) throws MessagingException{
        VerificationToken verificationToken = service.findByUser(users);

        if(verificationToken!= null){
            String token = verificationToken.getToken();
            sendMailUtils.sendSimpleEmail(
                    users.getEmail(),
                    "Verification account - Whalhome",
                    "Xin chào," + users.getFullName() + "\n" +
                            "\n" +
                            "You have been added to Whale Home System.\n" +
                            "\n" +
                            "Active Your Account now\n" +
                            "To enjoy the service, this is code: "+token +" to activate\n" +
                            "your account first. The activation is valid for 1 days and can only be\n" +
                            "used once.\n" +
                            "Trân trọng,\n" +
                            "Whalhome"
                    );

        }
    }
}
