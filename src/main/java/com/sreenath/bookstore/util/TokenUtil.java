package com.sreenath.bookstore.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.auth0.jwt.interfaces.Verification;
import org.springframework.stereotype.Component;

@Component
public class TokenUtil {
    private static final String TOKEN = "sreenath";

    /***
     * Implemented createToken method to create token by using JWT
     * @param id - passing id param
     * @return
     */
    public String createToken(int id) {
        Algorithm algorithm = Algorithm.HMAC256(TOKEN);
        return JWT.create().withClaim("id", id).sign(algorithm);
    }

    /***
     * Implemented decodeToken to decode the created token
     * @param token - passing token param
     * @return
     * @throws SignatureVerificationException
     */
    public int decodeToken(String token) throws SignatureVerificationException {
        Verification verification = JWT.require(Algorithm.HMAC256(TOKEN));
        JWTVerifier jwtVerifier = verification.build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        Claim claim = decodedJWT.getClaim("id");
        return claim.asInt();
    }
}
