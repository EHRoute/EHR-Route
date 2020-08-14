package ehroute.authservice.Configuration;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.HttpMethod;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		// Disable CSRF (Cross Site Request Forgery)
		http.csrf().disable();
		
		// Enable Stateless Sessions, as in no sessions are going to be created by spring security
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// Handle unauthorized requests by returning 404 unauthorized response
		http.exceptionHandling().authenticationEntryPoint((req, res, e) ->  res.sendError(HttpServletResponse.SC_UNAUTHORIZED));
		
		// User credentials validation filter that returns JWT
		//http.addFilterAfter();
		
		http.authorizeRequests().anyRequest().authenticated();
		
		http.authorizeRequests().antMatchers(HttpMethod.POST, "");
		
	}

}
