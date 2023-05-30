package de.schwarz.templateMailer.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JwtTokenUtil {

    private static final String SECRET_KEY = "your-secret-key";

    public static Claims parseToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    public static boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getUserRole(String token) {
        Claims claims = parseToken(token);
        return claims.get("role", String.class);
    }
}
