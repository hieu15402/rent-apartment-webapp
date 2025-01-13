package com.example.vinhomeproject.controller;

import com.example.vinhomeproject.dto.UserDTO;
import com.example.vinhomeproject.models.Users;
import com.example.vinhomeproject.models.VerificationToken;
import com.example.vinhomeproject.repositories.UsersRepository;
import com.example.vinhomeproject.repositories.VerificationTokenRepository;
import com.example.vinhomeproject.request.*;
import com.example.vinhomeproject.response.ResponseObject;
import com.example.vinhomeproject.response.SendCodeResponse;
import com.example.vinhomeproject.response.VerifyCodeRequest;
import com.example.vinhomeproject.service.AuthenticationService;
import com.example.vinhomeproject.service.UsersService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    @Autowired
    private AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<ResponseObject> register(@RequestBody UserDTO request) {
        return  service.register(request);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<ResponseObject> authenticate(@RequestBody AuthenticationRequest request) {
          return service.authenticate(request);
    }
    @PostMapping("/authenticate-mobile")
    public ResponseEntity<ResponseObject> authenticateMobile(@RequestBody AuthenticationMobileRequest request) {
        return service.authenticateMobile(request);
    }
    @PostMapping("/getUser")
    public ResponseEntity<ResponseObject> getUserFromAccessToken(@RequestBody AuthenticationUserRequest ar) {
        return service.getUserFromAccessToken(ar.getAccess_token());
    }
    @PostMapping("/reset-password")
    public ResponseEntity<ResponseObject> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        return  service.resetPassword(resetPasswordRequest);
    }
    @PostMapping("/forgot-password")
    public ResponseEntity<ResponseObject> sendCode(@RequestBody SendCodeRequest email) {
        return service.sendCode(email.getEmail());
    }
    @PostMapping("/verify")
    public ResponseEntity<ResponseObject> verifyCode(@RequestBody VerifyCodeRequest code) {
        return service.verifyCode(code);
    }
    @PostMapping("/verified-account")
    public ResponseEntity<ResponseObject> activeAccount(@RequestParam("token") String token){
        return service.activeAccount(token);
    }
    @PostMapping("/refresh-send-mail")
    public ResponseEntity<ResponseObject> refreshSendMail(@RequestParam("email") String email) throws MessagingException {
        return service.refreshSendMail(email);
    }
    @PostMapping("/logout")
    public ResponseEntity<ResponseObject> logout(@RequestParam("token") String token){
        return service.logout(token);
    }
}
