package it.unical.inf.ea.auth.config.security;

import it.unical.inf.ea.auth.config.security.filter.CustomAuthenticationFilter;
import it.unical.inf.ea.auth.config.security.filter.CustomAuthorizationFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                // .requiresChannel(channel -> channel.anyRequest().requiresSecure())

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(
                        exceptionHandling -> exceptionHandling.authenticationEntryPoint((request, response, authEx) -> {
                            response.setHeader("WWW-Authenticate", "Basic realm=\"Access to /login authentication endpoint\"");
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.getWriter().write("{ \"Error\": \"" + authEx.getMessage() + " - You are not authenticated.\" }");
                        })
                )
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/login/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/refreshToken").permitAll()
                        .requestMatchers(HttpMethod.GET, "/products/welcome").permitAll()
                )

                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/products/all").hasRole("ADMIN")
                        .requestMatchers("/products/*").hasRole("BASIC")
                        .requestMatchers("/products/random").hasAnyRole("ADMIN", "BASIC")

                        .requestMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )

                .addFilter(new CustomAuthenticationFilter(authenticationManager))
                .addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .headers(headers -> headers.cacheControl(cache -> cache.disable()))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
