package ehroute.authservice.Security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class UserAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private AuthenticationManager authManager;
	
	public UserAuthenticationFilter(AuthenticationManager authManager) {
		this.authManager = authManager;
		
		//this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher());
	}
	
	
}
