package io.github.tmbxgidel.gidel_jwt_core.service;

import io.github.tmbxgidel.gidel_jwt_core.enums.PurposeType;
import io.jsonwebtoken.Claims;

import java.util.Map;
import java.util.Optional;

public interface GidelJwtService {
    String generateToken(PurposeType purposeType, Map<String, Object> claims, String subject);
    Claims validateToken(PurposeType purposeType, String token);
    Optional<Claims> validateAndExtractClaims(PurposeType purposeType, String token);
}
