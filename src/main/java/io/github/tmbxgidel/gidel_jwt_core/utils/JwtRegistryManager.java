package io.github.tmbxgidel.gidel_jwt_core.utils;

import io.github.tmbxgidel.gidel_jwt_core.enums.PurposeType;

import java.util.Map;

public class JwtRegistryManager {
    private final Map<PurposeType, JwtKeyRegistry> registries;
    public JwtRegistryManager(Map<PurposeType, JwtKeyRegistry> registries) {
        this.registries = Map.copyOf(registries);
    }

    public JwtKeyRegistry getRegistry(PurposeType purpose) {
        JwtKeyRegistry registry = registries.get(purpose);
        if (registry == null) {
            throw new IllegalArgumentException(
                    "Unknown purpose: " + purpose
            );
        }
        return registry;
    }
}
