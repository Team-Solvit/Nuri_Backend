package nuri.nuri_server.global.util;

import java.security.SecureRandom;
import java.util.Base64;

public class OAuthStateUtil {

    public static String generateState() {
        byte[] randomBytes = new byte[24]; // 24 bytes = 32자 정도
        new SecureRandom().nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }
}