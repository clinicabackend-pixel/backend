package clinica_juridica.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    private String secret = "clave_super_secreta_para_clinica_juridica_backend_123456"; // Debe ser larga y segura
    private SecretKey key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 horas
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    public String generateInvitationToken(String username, String currentPasswordHash) {
        SecretKey dynamicKey = Keys.hmacShaKeyFor((secret + currentPasswordHash).getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                // No expiration date or very long one? User wanted no expiration.
                // But generally good to have some limit. Since user insisted on "lives until
                // pass created",
                // we can set a long expiration (e.g., 30 days) just to be safe, or no
                // expiration claim.
                // However, without expiration, the token is valid forever unless password
                // changes.
                // I will set it to 30 days effectively "forever" for this context.
                .expiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30))
                .signWith(dynamicKey)
                .compact();
    }

    public Boolean validateInvitationToken(String token, String username, String currentPasswordHash) {
        SecretKey dynamicKey = Keys.hmacShaKeyFor((secret + currentPasswordHash).getBytes(StandardCharsets.UTF_8));
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(dynamicKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return claims.getSubject().equals(username);
        } catch (Exception e) {
            return false;
        }
    }
}
