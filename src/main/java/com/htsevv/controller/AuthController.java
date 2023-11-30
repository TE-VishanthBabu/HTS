package com.htsevv.controller;

import com.htsevv.config.JwtTokenUtil;
import com.htsevv.entity.User;
import com.htsevv.request.PasswordRequest;
import com.htsevv.request.SignInRequest;
import com.htsevv.response.CommonResponse;
import com.htsevv.response.JwtResponse;
import com.htsevv.service.AuthService;
import com.htsevv.utils.AsymmetricCryptography;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.validation.Valid;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.UUID;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private AuthService authService;
    @Autowired
    AsymmetricCryptography asymmetricCryptography;
    @Autowired
    private MessageSource messageSource;

    private final String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKPsXQ2TudEka35ocexVp8atgd9nMlLB78TZw0MemZ7sUKc1V7uE8rX63kWDJuVGxHsZ566afpiSjYmzYTxYXtJLefXNBUQY3+eyBkPAQV7SPENmGyeOiWtXB2Q8CvuG1VAopF2IKBbWIynrWbsUCf0TxM8JoNtK07KxtLWzWTGHAgMBAAECgYAo1IMJErbCBVRRFCrsNxmnPyiOQOIkAvJ6EdO1MDYLg8lDHOJRbvgVo/shTV2u1kWyc+1lbE4EKtzeY7PgmBJl32hXch9FpJ7dQS4ay/jQMTZnNmB/wdDEs1pPrQcCoTcAzEVVb9gYojNpVgjTCR8Y4S++QvkRM8ABHfHcsjqZAQJBAPYi3VT6jcwmH6jY5nhdnRJq3HKz4wnpjHudF467Wuo6Es+G1bBJ15/BH/FLdOFT6faZqMBl00Hg/knimibY/mECQQCqfhGXvY7oOYhNP76a0xFybniAvOxIpYobek+Sq+h/GQs9Cq9YMGpVnyUdtxAoQ7/uDHu+e6Pnzs9JOYHsHKjnAkAsV+WmLKmlMO0dP05uOvdUXRowNAZgP8pGbuvSlCBx+FpxNuqCoxsMtf9qOlzpR3PsMIy9dNrVkJJff0qkWw2hAkBlFylBSuFQasSZ4UEYNixzKHOFCgAkNxuDnWtPMWVC70uNnydG6DY0nI96ZhBLf5hvArHgyRjvmt2nslSJ9phjAkEAlbx3kx87y2B8cDE4HfV1I3JVAQm0OE40GS8bceMW24M0M5WkB9uBSH4UV1HoE42hbuseHC/c/G9MMagRwVdnJQ==";

    /**
     * This method is to authenticate the user and generate the jwt token for the user for the current session.
     *
     * @param request
     * @return ResponseEntity<CommonResponse>
     */

    @SneakyThrows
    @PostMapping("/authenticate")
    public ResponseEntity<CommonResponse> createAuthenticationToken(@Valid @RequestBody SignInRequest request) {
        request.setPassword(asymmetricCryptography.decrypt(request.getPassword(), privateKey));
        User user = this.authService.authenticate(request.getUsername(),request.getPassword());
        log.info("Init Token Generation");
        final String token = jwtTokenUtil.generateToken(user.getId(),user.getEmail(),user.getRole());
        log.info("Access Token Generated {}", token);
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setData(new JwtResponse(token,user.getRole().name(), user.getName()));
        commonResponse.setMessage(messageSource.getMessage("authentication.success",null, Locale.getDefault()));
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * This method is to reset password using email.
     *
     * @param email
     * @return ResponseEntity<CommonResponse>
     */
    @PostMapping("/reset-password")
    public ResponseEntity<CommonResponse> resetPassword(@RequestParam String email){
        User user = this.authService.validateUser(email);
        String token = UUID.randomUUID().toString();
        authService.createPasswordResetTokenForUser(user.getId(), token);
        authService.sendMail(user, token);
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setMessage(messageSource.getMessage("resetPwd.sentSuccess",null,Locale.getDefault()));
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * This method is to update new password using token.
     *
     * @param token
     * @param passwordRequest
     * @return ResponseEntity<CommonResponse>
     */
    @PutMapping("/reset-password/{token}")
    public ResponseEntity<CommonResponse> updateNewPassword(@PathVariable String token,@Valid @RequestBody PasswordRequest passwordRequest) throws IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        UUID userId = this.authService.validatePasswordResetToken(token);
        passwordRequest.setNewPassword(asymmetricCryptography.decrypt(passwordRequest.getNewPassword(), privateKey));
        passwordRequest.setConfirmPassword(asymmetricCryptography.decrypt(passwordRequest.getConfirmPassword(), privateKey));
        authService.validatePassword(passwordRequest);
        authService.setNewPassword(userId, passwordRequest);
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setMessage(messageSource.getMessage("resetPwd.success",null,Locale.getDefault()));
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }
}
