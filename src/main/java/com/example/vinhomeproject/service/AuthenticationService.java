package com.example.vinhomeproject.service;

import com.example.vinhomeproject.config.JwtService;
import com.example.vinhomeproject.dto.UserDTO;
import com.example.vinhomeproject.models.*;
import com.example.vinhomeproject.repositories.TokenRepository;
import com.example.vinhomeproject.repositories.UsersRepository;
import com.example.vinhomeproject.repositories.VerificationTokenRepository;
import com.example.vinhomeproject.request.AuthenticationMobileRequest;
import com.example.vinhomeproject.request.AuthenticationRequest;
import com.example.vinhomeproject.request.ResetPasswordRequest;
import com.example.vinhomeproject.request.VerifyCodeResponse;
import com.example.vinhomeproject.response.AuthenticationResponse;
import com.example.vinhomeproject.response.ResponseObject;
import com.example.vinhomeproject.response.SendCodeResponse;
import com.example.vinhomeproject.response.VerifyCodeRequest;
import com.example.vinhomeproject.utils.SendMailUtils;
import com.example.vinhomeproject.utils.VerificationCodeUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthenticationService {

    @Autowired
    private UsersRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private VerificationCodeUtils verificationCodeUtils;

    @Autowired
    private SendMailUtils sendMailUtils;

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private UsersRepository userRepository;

    public ResponseEntity<ResponseObject> register(UserDTO request) {
        if (!userRepository.findByEmail(request.getEmail()).isPresent()) {
            var user = Users.builder()
                    .email(request.getEmail())
                    .gender(request.getGender())
                    .image(request.getImage())
                    .phone(request.getPhone())
                    .address(request.getAddress())
                    .fullName(request.getFullName())
                    .dateOfBirth(request.getDateOfBirth())
                    .isVerified(true)
                    .status(true)
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.USER)
                    .build();
            Optional<Users> saved = Optional.of(repository.save(user));
            saved.ifPresent(users -> {
                try {
                    String token = UUID.randomUUID().toString();
                    verificationTokenService.save(saved.get(), token);
                    emailService.sendMail(users);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            var jwtToken = jwtService.generateToken(user);
//        var refreshToken = jwtService.generateRefreshToken(user);
            AuthenticationResponse auth = AuthenticationResponse.builder()
                    .access_token(jwtToken)
//                .refresh_token(refreshToken)
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                    "Login successfully",
                    auth
            ));
        }
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ResponseObject(
                "Email already exist",
                null
        ));
    }
    public ResponseEntity<ResponseObject> refreshSendMail(String email) throws MessagingException {
        Optional<Users> user = repository.findByEmail(email);
        if(user.isPresent()){
            String token = UUID.randomUUID().toString();
            verificationTokenService.save(user.get(), token);
            emailService.sendMail(user.get());
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ResponseObject(
                    "Resend token in email successfully",
                    null
            ));
        }
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ResponseObject(
                "Email not exist",
                null
        ));
    }
    public ResponseEntity<ResponseObject> authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        if (user.isVerified() && user.isStatus()) {
//        var refreshToken = jwtService.generateRefreshToken(user);
            saveUserToken(user, jwtToken);
            AuthenticationResponse auth = AuthenticationResponse.builder()
                    .access_token(jwtToken)
//                .refresh_token(refreshToken)
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                    "Login successfully",
                    auth
            ));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseObject(
                "Login failed",
                null
        ));
    }

    public ResponseEntity<ResponseObject> authenticateMobile(AuthenticationMobileRequest request) {
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        if (user.isVerified() && user.isStatus()) {
//        var refreshToken = jwtService.generateRefreshToken(user);
            saveUserToken(user, jwtToken);
            AuthenticationResponse auth = AuthenticationResponse.builder()
                    .access_token(jwtToken)
//                .refresh_token(refreshToken)
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                    "Login successfully",
                    auth
            ));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseObject(
                "Login failed",
                ""
        ));
    }
    private void saveUserToken(Users user, String jwtToken) {
        var token = Token.builder()
                .users(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(Users user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.repository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .access_token(accessToken)
//                        .refresh_token(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    public ResponseEntity<ResponseObject> getUserFromAccessToken(String accessToken) {
        try{
            String userEmail = jwtService.extractUsername(accessToken);
            if (userEmail != null) {
                Users user = repository.findByEmail(userEmail)
                        .orElseThrow();
                return ResponseEntity.ok(new ResponseObject("Access Token is valid", user));
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new ResponseObject("Access Token is not valid", ""));
        }
        return ResponseEntity.badRequest().body(new ResponseObject("Access Token is not valid", ""));
    }

    public ResponseEntity<ResponseObject> resetPassword(ResetPasswordRequest request) {
        boolean result = false;
        if(Objects.equals(request.getNewPassword(), request.getConfirmPassword())){
            Optional<Users> user = repository.findByEmail(request.getEmail());
            if (user.isPresent()) {
                user.get().setPassword(passwordEncoder.encode(request.getNewPassword()));
                repository.save(user.get());
                result = true;
            }
            if (result) {
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                        "Successfully",
                        ""
                ));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(
                    "Bad request",
                    ""
            ));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(
                "New password not equals confirm password",
                ""
        ));
    }

    public ResponseEntity<ResponseObject> sendCode(String email) {
        SendCodeResponse sendCodeResponse = new SendCodeResponse();
        String _email = "";
        try {
            Optional<Users> user = repository.findByEmail(email);
            if (user.isPresent()) {
                if (user.get().isStatus()) {
                    String code = verificationCodeUtils.generateVerificationCode(user.get().getEmail());
                    sendMailUtils.sendSimpleEmail(
                            email,
                            "Verification code - Whalhome",
                            "Xin chào,\n" +
                                    "\n" +
                                    "Bạn đã yêu cầu đổi mật khẩu cho tài khoản của mình trên Whalhome. Dưới đây là mã xác nhận của bạn:\n" +
                                    "\n" +
                                    "Mã Xác Nhận: " + code + "\n" +
                                    "\n" +
                                    "Vui lòng sử dụng mã này để xác nhận quy trình đổi mật khẩu. Hãy nhớ rằng mã xác nhận chỉ có hiệu lực trong một khoảng thời gian ngắn.\n" +
                                    "\n" +
                                    "Nếu bạn không yêu cầu đổi mật khẩu, vui lòng bỏ qua email này. Để bảo vệ tài khoản của bạn, không chia sẻ mã xác nhận với người khác.\n" +
                                    "\n" +
                                    "Trân trọng,\n" +
                                    "Whalhome"
                    );
                    _email = user.get().getEmail();
                } else {
                    throw new LockedException("");
                }
            }
            if (!Objects.equals(_email, "")) {
                sendCodeResponse.setEmail(_email);
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                        "Successfully",
                        ""
                ));
            } else {
                sendCodeResponse.setMessage("The email account does not exist in the system!");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(
                        "The email account does not exist in the system!",
                        ""
                ));
            }
        } catch (Exception e) {
            if (e instanceof LockedException) {
                sendCodeResponse.setMessage("This email account has been disabled!");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(
                        "This email account has been disabled!",
                        ""
                ));
            } else {
                e.printStackTrace();
                sendCodeResponse.setMessage("Error!");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(
                        "Error!",
                      ""
                ));
            }
        }

    }

    public ResponseEntity<ResponseObject> verifyCode(VerifyCodeRequest request) {
        VerifyCodeResponse verifyCodeResponse = new VerifyCodeResponse();
        String email = "";
        if (verificationCodeUtils.isValidCode(request.getCode())) {
            String emailOfCode = verificationCodeUtils.getEmailByCode(request.getCode());
            if (Objects.equals(emailOfCode, request.getEmail())) {
                email = emailOfCode;
            }
        } else {
            email = "";
        }

        if (!Objects.equals(email, "")) {
            verifyCodeResponse.setMail(email);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                    "Successfully",
                    ""
            ));
        } else {
            verifyCodeResponse.setMessage("");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(
                    "Code is incorrect or has expired.",
                    ""
            ));
        }

    }

    public ResponseEntity<ResponseObject> activeAccount(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if (verificationToken == null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                    "Token not exist",
                    ""
            ));
        } else {
            Users users = verificationToken.getUsers();
            if (!users.isVerified()) {
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                if (verificationToken.getExpiryDate().before(timestamp)) {
                    return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                            "Token has expired",
                            ""
                    ));
                } else {
                    users.setVerified(true);
                    userRepository.save(users);
                    return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                            "Account has active",
                            null
                    ));
                }
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                        "Account already active",
                        null
                ));
            }
        }
    }

    public ResponseEntity<ResponseObject> logout(String token) {
        var storedToken = tokenRepository.findByToken(token)
                .orElse(null);
        if(storedToken != null){
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
            SecurityContextHolder.clearContext();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                    "Logout successfully",
                    ""
            ));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                "Token not exist",
                ""
        ));
    }
}
