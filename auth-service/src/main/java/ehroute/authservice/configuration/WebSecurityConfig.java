package ehroute.authservice.configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import reactor.core.publisher.Mono;



@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${security.signing-key}")
	private String signingKey;

	@Value("${security.encoding-strength}")
	private Integer encodingStrength;

	@Value("${security.security-realm}")
	private String securityRealm;


	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	/*@Bean
	//FIXME: JwtAccessTokenConverter is Deprecated, find an alternative
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey(signingKey);
		return converter;
	}*/


	/*@Bean
	//FIXME: TokenStore is Deprecated, find an alternative
	public TokenStore tokenStore() {
		// return new JdbcTokenStore()
	}*/


	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		configSource.registerCorsConfiguration("/**", config);
		return new CorsFilter(configSource);
	}


	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		return http

				// Handle unauthorized requests by returning 401 unauthorized response
				.exceptionHandling().authenticationEntryPoint((swe, e) -> {
					return Mono.fromRunnable(() -> {
						swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
					});
				})

				// Handle denied requests by returning 403 forbidden response
				.accessDeniedHandler((swe, e) -> {
					return Mono.fromRunnable(() -> {
						swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
					});
				})

				// Disable CSRF
				.and().csrf().disable()

				// Disable SpringSecurity default form login
				.formLogin().disable()

				// Disable basic authentication
				.httpBasic().disable().build();
	}


	/*@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		// Disable CSRF (Cross Site Request Forgery)
		http.csrf().disable();

        http.authorizeRequests().anyRequest().authenticated();
		
		// Enable Stateless Sessions, as in no sessions are going to be created by spring security
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.httpBasic().realmName(securityRealm);

		// Handle unauthorized requests by returning 404 unauthorized response
		http.exceptionHandling().authenticationEntryPoint((req, res, e) ->  res.sendError(HttpServletResponse.SC_UNAUTHORIZED));
		
		// User credentials validation filter that returns JWT
		//http.addFilterAfter();
		
	}*/


	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	    //auth.userDetailsService().passwordEncoder(new BCryptPasswordEncoder());
    }


}
