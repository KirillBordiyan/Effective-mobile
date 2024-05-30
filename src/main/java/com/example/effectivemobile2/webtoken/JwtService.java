package com.example.effectivemobile2.webtoken;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class JwtService {
    private static final  String SECRET = "D249CED5EB3F93BB4B5B30819ADC5C1BB7D5160DB8DD81B6E3C983B45F347CD01F49DE7DEA1A75A0ADBCF9E39C1C7F86AAF11AB9CC9C3CCBC6B36DE605C3FEFE";
    private static final long VALIDITY = TimeUnit.MINUTES.toMillis(30);

    public String generateToken(UserDetails userDetails){
        Map<String, String> claims = new HashMap<>();
        claims.put("authorities", userDetails.getAuthorities().toString());
        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(Date.from(Instant.now())) //created at
                .expiration(Date.from(Instant.now().plusMillis(VALIDITY))) //be valid to
                .signWith(generateKey()) //signed key
                .compact();
    }

    private SecretKey generateKey(){
        byte[] decodedKey = Base64.getDecoder().decode(SECRET);
        return Keys.hmacShaKeyFor(decodedKey);
    }
}
