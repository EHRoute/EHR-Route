package ehroute.gatewayservice.security;
/*import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.context.SecurityContextHolder;*/
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/*
public class AuthenticationFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		// Get the token
		String authToken = getRequestJwt(request);
		
		// Move on if token is invalid
		if (authToken == null) filterChain.doFilter(request, response);
		
		try {
			// Validate the token
			Claims claims = Jwts.parser()
			.setSigningKey("".getBytes())
			.parseClaimsJws(authToken)
			.getBody();
			
			// Authenticate user
			
							
		} catch (Exception Ex) {
			// Don't authenticate user
			SecurityContextHolder.clearContext();
		}

		filterChain.doFilter(request, response);
		
	}


	*//**
	 * @param request The Http request
	 * @return	The Json Web Token
	 *//*
	private String getRequestJwt(HttpServletRequest request) {
		// Get token string from the 'Authorization' header and return it if valid
		String authToken = request.getHeader("Authorization");
		boolean isTokenValid = authToken.length() > 0 && authToken.startsWith("Bearer ");
		return isTokenValid ? authToken.replace("Bearer ", "") : null;
	}
}*/
