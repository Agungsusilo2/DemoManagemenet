package org.example.demomanagemenet.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(String username, String role) {
        return JWT.create()
                .withSubject("User Detail")
                .withClaim("username", username)
                .withClaim("role", role)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600000)) // Expire dalam 1 jam
                .withIssuer("Gudang Management")
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateTokenAndRetrieveSubject(String token) {
        try {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret))
                    .withSubject("User Detail")
                    .withIssuer("Gudang Management")
                    .build();

            DecodedJWT jwt = jwtVerifier.verify(token);
            return jwt.getClaim("username").asString();
        } catch (TokenExpiredException e) {
            throw new RuntimeException("Token has expired");
        } catch (Exception e) {
            throw new RuntimeException("Invalid token");
        }
    }

    public String getRoleFromToken(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("role").asString();
    }
}
