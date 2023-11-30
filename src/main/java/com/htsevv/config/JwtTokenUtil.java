package com.htsevv.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.htsevv.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Component
public class JwtTokenUtil implements Serializable {

    private static final String AUTHORITY_CLAIM = "Authority";
    private static final String ID_CLAIM = "Id";
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    @Value("${jwt.secret.key}")
    private String secret_key;

    public String generateToken(UUID id, String  email, User.Role authority) {
        Date notBefore = Date.from(Instant.now());
        Date expiresAt = Date.from(Instant.now().plusSeconds(JWT_TOKEN_VALIDITY));

        JWTCreator.Builder jwtTokenBuilder = JWT.create().withSubject(email);
        if (id != null) {
            jwtTokenBuilder.withClaim(ID_CLAIM, String.valueOf(id));
        }
        if (authority != null) {
            jwtTokenBuilder.withClaim(AUTHORITY_CLAIM, authority.name());
        }
        return jwtTokenBuilder
                .withExpiresAt(expiresAt)
                .withNotBefore(notBefore)
                .sign(HMAC512(secret_key.getBytes()));
    }

    public AuthenticationPrinciple parseToken(String token) {
        DecodedJWT jwt = JWT.require(HMAC512(secret_key.getBytes())).build().verify(token);
        if (Instant.now().isBefore(jwt.getNotBefore().toInstant())) {
            throw new TokenExpiredException("expired");
        }
        if (Instant.now().isAfter(jwt.getExpiresAt().toInstant())) {
            throw new TokenExpiredException("expired");
        }
        return new AuthenticationPrinciple(jwt.getClaim(ID_CLAIM).asString(), jwt.getSubject(), jwt.getClaim(AUTHORITY_CLAIM).asString());
    }

}
