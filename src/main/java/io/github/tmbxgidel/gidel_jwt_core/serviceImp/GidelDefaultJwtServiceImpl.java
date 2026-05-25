package io.github.tmbxgidel.gidel_jwt_core.serviceImp;

import io.github.tmbxgidel.gidel_jwt_core.enums.PurposeType;
import io.github.tmbxgidel.gidel_jwt_core.service.GidelJwtService;
import io.github.tmbxgidel.gidel_jwt_core.utils.JwtRegistryManager;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

public class GidelDefaultJwtServiceImpl implements GidelJwtService {

    private final JwtRegistryManager jwtRegistryManager;

    public GidelDefaultJwtServiceImpl( JwtRegistryManager jwtRegistryManager) {
        this.jwtRegistryManager = jwtRegistryManager;
    }

    @Override
    public String generateToken(PurposeType purposeType, Map<String, Object> claims, String subject) {
        var registry = jwtRegistryManager.getRegistry(purposeType);
        var key = registry.active();
        return Jwts.builder()
                .subject(subject)
                .claims(claims)
                .header()
                .add("kid", key.kid())
                .and()
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + registry.getTtlMillis()))
                .signWith(key.signingKey())
                .compact();
    }

    @Override
    public Claims validateToken(PurposeType purposeType, String token) {
        try {
            var registry = jwtRegistryManager.getRegistry(purposeType);
            var kid = extractKid(token);
            var key = registry.get(kid);
            if (key.isSymmetric()) {
                return Jwts.parser()
                        .verifyWith((SecretKey) key.verificationKey())
                        .build()
                        .parseSignedClaims(token)
                        .getPayload();
            }

            return Jwts.parser()
                    .verifyWith((PublicKey) key.verificationKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            throw new RuntimeException("Invalid JWT token", e);
        }
    }

    @Override
    public Optional<Claims> validateAndExtractClaims(PurposeType purposeType, String token) {
        try {
            return Optional.ofNullable(validateToken(purposeType, token));
        } catch (JwtException | IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    private String extractKid(String token) {
        String[] parts = token.split("\\.");
        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid JWT format");
        }
        String json = new String(
                Base64.getUrlDecoder().decode(parts[0])
        );
        int k = json.indexOf("\"kid\"");
        if (k == -1) return null;
        int colon = json.indexOf(":", k);
        int firstQuote = json.indexOf("\"", colon);
        int secondQuote = json.indexOf("\"", firstQuote + 1);
        return json.substring(firstQuote + 1, secondQuote);
    }
}
