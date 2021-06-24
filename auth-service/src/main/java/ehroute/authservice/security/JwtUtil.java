package ehroute.authservice.security;
import ehroute.authservice.security.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
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


    public String generateToken(User user, Map<String, Object> claims) {

        // Create the token's expiration date
        Date expiryDate = new Date((new Date().getTime()) + jwtExpirationInMs * 1000);

        // Return the JWT
        return Jwts.builder()
            .setSubject(Long.toString(user.getId())) // Authenticated User ID as Subject
            .setClaims(claims)
            .setIssuedAt(new Date()) // Time at which the JWT is created and sent to client
            .setExpiration(expiryDate)
            .signWith(key) // JWT Signature
        .compact();

    }


    public Claims getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }


    public String getUserId(String token) {
        return getClaims(token).getSubject();
    }


    public Date getExpirationDate(String token) {
        return getClaims(token).getExpiration();
    }


    private Boolean isExpired(String token) {
        final Date expiration = getExpirationDate(token);
        return expiration.before(new Date());
    }

}
