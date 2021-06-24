/*
package ehroute.gatewayservice.security;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import javax.servlet.http.HttpServletResponse;


public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// Disable CSRF (Cross Site Request Forgery)
		//http.csrf().disable();

		// Enable Stateless Sessions, as in no sessions are going to be created by spring security
		//http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		// Handle unauthorized requests by returning 404 unauthorized response
		//http.exceptionHandling().authenticationEntryPoint((req, res, e) ->  res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		// Add a filter to validate tokens with every request
		//http.addFilterAfter()
		
		*/
/*http.authorizeRequests()
		.antMatchers("/provider/**").hasRole("PROVIDER")
		.antMatchers()*//*

		
	}
	
}
*/
