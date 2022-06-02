package com.sreenath.bookstore.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import org.springframework.stereotype.Component;

@Component
public class TokenUtil {
    private static final String TOKEN = "sreenath";

    public String createToken(int id) {
        Algorithm algorithm = Algorithm.HMAC256(TOKEN);
        return JWT.create().withClaim("id", id).sign(algorithm);
    }
}
