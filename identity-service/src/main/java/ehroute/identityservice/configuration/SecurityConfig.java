package ehroute.identityservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
// @EnableWebFluxSecurity
// @EnableReactiveMethodSecurity
public class SecurityConfig {

    private String[] publicRoutes = {
        "/favicon.ico", 
        "/csrf", 
        "/vendor/**", 
        "/webjars/**",
        "/*/actuator/**",
        "/",
        "/graphql",
        "/login",
        "/logout",
        "/auth/**",
        "/user/register",
        "/files/**",
        "/swagger/**",
        "/swagger-ui/**",
        "/swagger-ui.html",
        "/v3/api-docs/**",
        "/test",
        "/account/**",
        "/register"
    };


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // @Bean
    // public ReactiveAuthenticationManager authenticationManager(ReactiveUserDetailsService userDetailsService) {

    //     UserDetailsRepositoryReactiveAuthenticationManager authenticationManager = 
    //     new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);

    //     authenticationManager.setPasswordEncoder(passwordEncoder());
        
    //     return authenticationManager;

    // }


    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
        .csrf(csrf -> csrf.disable())
        .authorizeExchange(auth -> auth
            .pathMatchers(publicRoutes).permitAll()
            // .anyExchange().authenticated()
        )
        .build();
    }

}
