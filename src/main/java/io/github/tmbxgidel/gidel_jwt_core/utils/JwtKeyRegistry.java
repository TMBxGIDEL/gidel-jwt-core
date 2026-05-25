package io.github.tmbxgidel.gidel_jwt_core.utils;

import java.util.Map;

public class JwtKeyRegistry {
    private final String activeKid;

    private final Long ttlMillis;

    private final Map<String, JwtKeyMaterial> keys;

    public JwtKeyRegistry(String activeKid, Map<String, JwtKeyMaterial> keys, Long ttlMillis) {
        this.activeKid = activeKid;
        this.keys = Map.copyOf(keys);
        this.ttlMillis = ttlMillis;
    }

    public JwtKeyMaterial active() {
        return get(activeKid);
    }

    public JwtKeyMaterial get(String kid) {
        JwtKeyMaterial material = keys.get(kid);
        if (material == null) {
            throw new IllegalArgumentException(
                    "Unknown kid: " + kid
            );
        }
        return material;
    }
    public long getTtlMillis() {
        return ttlMillis;
    }

}
