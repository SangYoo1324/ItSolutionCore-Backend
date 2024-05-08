package com.example.ItSolutionCore.common.auth.jwt;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    private String exp;

    private SecretKey secretKey;

    public JwtUtil(  @Value("${spring.jwt.exp}") String exp, @Value("${spring.jwt.secret}")String secret){
        this.exp = exp;
        /*
        * secret.getBytes(StandardCharsets.UTF_8) -> change secret string to byteCode
        * 2nd parameter: MAC algorithm
        *  */
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    public String getUsername(String token){
        // if secretKey is forged, parser will throw an error
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
    }

    public String getEmail(String token){
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("email", String.class);
    }

    public String getRole(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    public Boolean isExpired(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }



    public String createJwt(String username, String role){
        long expDura = Long.parseLong(this.exp)*1000*60;
        return Jwts.builder()
                .claim("username", username)
                .claim("role",role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+ expDura))
                .signWith(secretKey)
                .compact();
    }

    public String createSignupToken(String username, String email){
        long expDura = 10*1000*60;  // 10min dura
        return Jwts.builder()
                .claim("username", username)
                .claim("email",email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+ expDura))
                .signWith(secretKey)
                .compact();
    }
}
