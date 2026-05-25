package io.github.tmbxgidel.gidel_jwt_core.utils;

import io.jsonwebtoken.security.SignatureAlgorithm;

import javax.crypto.SecretKey;
import java.security.Key;
import java.security.PublicKey;

public record JwtKeyMaterial(String kid, SignatureAlgorithm algorithm, Key signingKey,
                             Key verificationKey) {

    public boolean isSymmetric() {
        return verificationKey instanceof SecretKey;
    }

    public boolean isAsymmetric() {
        return verificationKey instanceof PublicKey;
    }
}
