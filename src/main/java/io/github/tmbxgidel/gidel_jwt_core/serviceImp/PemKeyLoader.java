package io.github.tmbxgidel.gidel_jwt_core.serviceImp;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.*;
import java.security.spec.*;
import java.util.Base64;

public final class PemKeyLoader {

    // ---------------- PRIVATE KEY ----------------
    public static PrivateKey loadPrivateKey(String pem) throws Exception {

        String clean = pem
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");

        byte[] decoded = Base64.getDecoder().decode(clean);

        PKCS8EncodedKeySpec spec =
                new PKCS8EncodedKeySpec(decoded);

        return KeyFactory.getInstance("RSA")
                .generatePrivate(spec);
    }

    public static PrivateKey loadPrivateKey(Path file) throws Exception {
        return loadPrivateKey(Files.readString(file));
    }

    // ---------------- PUBLIC KEY ----------------
    public static PublicKey loadPublicKey(String pem) throws Exception {

        String clean = pem
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");

        byte[] decoded = Base64.getDecoder().decode(clean);

        X509EncodedKeySpec spec =
                new X509EncodedKeySpec(decoded);

        return KeyFactory.getInstance("RSA")
                .generatePublic(spec);
    }

    public static PublicKey loadPublicKey(Path file) throws Exception {
        return loadPublicKey(Files.readString(file));
    }
}