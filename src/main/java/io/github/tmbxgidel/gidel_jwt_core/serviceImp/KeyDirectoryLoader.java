package io.github.tmbxgidel.gidel_jwt_core.serviceImp;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class KeyDirectoryLoader {

    private final Map<String, PrivateKey> privateKeys = new HashMap<>();
    private final Map<String, PublicKey> publicKeys = new HashMap<>();

    public void load(Path basePath) throws Exception {

        try (Stream<Path> files = Files.list(basePath)) {

            files.filter(Files::isRegularFile)
                    .forEach(file -> {

                        try {
                            String name = file.getFileName().toString();

                            // token_v1_private_key.pem
                            String[] parts = name.split("_");

                            if (parts.length < 4) return;

                            String kid = parts[0] + "_" + parts[1];
                            String type = parts[2]; // private / public

                            if ("private".equalsIgnoreCase(type)) {

                                PrivateKey key =
                                        PemKeyLoader.loadPrivateKey(file);

                                privateKeys.put(kid, key);
                            }

                            if ("public".equalsIgnoreCase(type)) {

                                PublicKey key =
                                        PemKeyLoader.loadPublicKey(file);

                                publicKeys.put(kid, key);
                            }

                        } catch (Exception e) {
                            throw new RuntimeException(
                                    "Failed loading key: " + file, e
                            );
                        }
                    });
        }
    }

    public Map<String, PrivateKey> getPrivateKeys() {
        return privateKeys;
    }

    public Map<String, PublicKey> getPublicKeys() {
        return publicKeys;
    }

    public Set<String> getAvailableKeyIds() {
        return privateKeys.keySet();
    }

    public Set<String> getAvailableAccessTokenKeyIds() {
        return privateKeys.keySet().stream()
                .filter(kid -> kid.startsWith("token_"))
                .collect(Collectors.toSet());
    }

     public Set<String> getAvailableRefreshTokenKeyIds() {
        return privateKeys.keySet().stream()
                .filter(kid -> kid.startsWith("refresh_"))
                .collect(Collectors.toSet());
    }
}