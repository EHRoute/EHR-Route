package ehroute.authservice.security;

import com.sun.security.auth.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;
import java.util.Map;


@Component
public class JwtUtil {

    @Value("${auth.jwt.secret}")
    private String jwtSecret;

    @Value("${auth.jwt.expiration}")
    private int jwtExpirationInMs;

    private Key key;


    @PostConstruct
    private void initKey() {
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }


    public String generateToken(Authentication authentication, Map<String, Object> claims)
    {
        // Get current authenticated user
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        // Create the token's expiration date
        Date expiryDate = new Date((new Date().getTime()) + jwtExpirationInMs * 1000);

        // Return the JWT
        return Jwts.builder()
            // .setSubject(Long.toString(userPrincipal.getId())) // Authenticated User ID as Subject
            .setClaims(claims)
            .setIssuedAt(new Date()) // Time at which the JWT is created and sent to client
            .setExpiration(expiryDate)
            .signWith(key) // JWT Signature
        .compact();
    }


    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public String getUsernameFromToken(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }


    public Date getExpirationDateFromToken(String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }


    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

}
