package com.htsevv.service;

import com.htsevv.entity.Token;
import com.htsevv.entity.User;
import com.htsevv.exception.BadCredentialsException;
import com.htsevv.exception.CannotResetPasswordException;
import com.htsevv.exception.EmailNotSentException;
import com.htsevv.exception.UserNotFoundException;
import com.htsevv.repository.TokenRepository;
import com.htsevv.repository.UserRepository;
import com.htsevv.request.EmailRequest;
import com.htsevv.request.PasswordRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

@Service
@Slf4j
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private MessageSource messageSource;

    private final MailService mailService;

    public AuthService(MailService mailService) {
        this.mailService = mailService;
    }

    @Value("${token.expiration}")
    private Integer tokenExpiration;

    @Value("${api.url}")
    private String baseUrl;

    /**
     * This method is to authenticate the user login information.
     *
     * @param username
     * @param password
     * @return user
     */
    public User authenticate(String username, String password) {
        User user = userRepository.findByEmail(username).orElseThrow(() -> {
            throw new UserNotFoundException();
        });
        boolean isPasswordMatch = passwordEncoder.matches(password, user.getPassword());
        if (!isPasswordMatch) {
            log.error("Password mismatched {}", password);
            throw new BadCredentialsException();
        }
        this.userRepository.save(user);
        log.info("User Authenticated Successfully {}", username);
        return userRepository.findById(user.getId()).orElseThrow(() -> {
            throw new UserNotFoundException();
        });

    }

    /**
     * This method is to validate user by passing email.
     *
     * @param email
     * @return user information
     */
    public User validateUser(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> {
            throw new UserNotFoundException();
        });
    }

    /**
     * This method is to set password reset token for user.
     *
     * @param userId
     * @param token
     */
    public void createPasswordResetTokenForUser(UUID userId, String token) {
        Token passwordResetToken = this.tokenRepository.findByUserId(userId);
        if (passwordResetToken == null) {
            passwordResetToken = new Token();
        }
        passwordResetToken.setUserId(userId);
        passwordResetToken.setForgotPasswordToken(token);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, tokenExpiration);
        passwordResetToken.setExpiryDate(cal.getTime());
        this.tokenRepository.save(passwordResetToken);
    }

    /**
     * This method is to send mail to user for reset password.
     *
     * @param user
     * @param token
     */
    public void sendMail(User user, String token) {
        try {
            EmailRequest emailRequest = new EmailRequest();
            emailRequest.setFrom(user.getEmail());
            emailRequest.setSubject("Reset Password");
            String url = baseUrl + "/set-password/" + token;
            emailRequest.setMessage("Click on the following link to reset your password: <a href='" + url + "'>Reset Password</a>");
            mailService.sendMail(emailRequest.getFrom(), emailRequest.getSubject(), emailRequest.getMessage());
        } catch (EmailNotSentException ex) {
            ex.printStackTrace();
            log.error("Exception :" + ex);
        }
    }

    /**
     * This method is to validate password reset token.
     * @param token
     * @return user Id
     */
    public UUID validatePasswordResetToken(String token) {
        final Token passToken = tokenRepository.findByForgotPasswordToken(token);
        if (passToken == null) {
            throw new CannotResetPasswordException(messageSource.getMessage("resetPwd.failed",null, Locale.getDefault()));
        }
        final Calendar cal = Calendar.getInstance();
        if (passToken.getExpiryDate().before(cal.getTime())) {
            throw new CannotResetPasswordException(messageSource.getMessage("token.expired",null,Locale.getDefault()));
        }
        return passToken.getUserId();
    }

    /**
     * This method is to validate password.
     *
     * @param passwordRequest
     */
    public void validatePassword(PasswordRequest passwordRequest) {
        if (!passwordRequest.getNewPassword().equals(passwordRequest.getConfirmPassword())) {
            throw new CannotResetPasswordException(messageSource.getMessage("pwd.missmatch",null,Locale.getDefault()));
        }
    }

    /**
     * This method is to set new password.
     *
     * @param userId
     * @param passwordRequest
     */
    public void setNewPassword(UUID userId, PasswordRequest passwordRequest) {
        User user=this.userRepository.findById(userId).orElseThrow(() -> {
            throw new UserNotFoundException();
        });
        user.setPassword(passwordEncoder.encode(passwordRequest.getNewPassword()));
        this.userRepository.save(user);
    }
}
