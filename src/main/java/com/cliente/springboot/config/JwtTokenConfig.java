package com.cliente.springboot.config;

import com.dto.UserForLoginDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Key;
import java.util.Collections;
import java.util.Date;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

@Component
public class JwtTokenConfig {

    static final long EXPIRATION_TIME = 860_000_000;
    static final String TOKEN_PREFIX = "Bearer";
    static final String HEADER_STRING = "Authorization";

    // Use a chave gerada com segurança para o HMAC-SHA512
    private static final Key secret = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    // Generate token for user
    public String generateToken(UserForLoginDto user) {
        return doGenerateToken(user.getUsername());
    }

    // Gera o token com base no nome de usuário
    static String doGenerateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secret) // Use a chave segura gerada
                .compact();
    }

    // Adiciona a autenticação ao cabeçalho da resposta
    static void addAuthentication(HttpServletResponse response, String username) {
        String JWT = doGenerateToken(username);
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
    }

    // Obtém a autenticação a partir do token JWT
    static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);

        if (token != null) {
            // Faz parse do token
            String user = Jwts.parserBuilder()
                    .setSigningKey(secret) // Usando a chave segura
                    .build()
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();

            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
            }
        }
        return null;
    }
}
