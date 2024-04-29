package ru.lebruce.store.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import ru.lebruce.store.model.impl.UserDetailsImpl;

import java.util.Date;

@Component
public class JwtCore {
    @Value("${store.app.secret}")
    private String secret;

    @Value("${store.app.lifetime}")
    private String lifetime;

    public String generateToken(Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return Jwts.builder().setSubject((userDetails.getUsername())).setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime()+lifetime))
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();

    }
}
