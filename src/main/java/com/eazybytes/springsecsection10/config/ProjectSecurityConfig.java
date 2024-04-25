package com.eazybytes.springsecsection10.config;

import com.eazybytes.springsecsection10.filter.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class ProjectSecurityConfig {

    //Custom security configuration

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        CsrfTokenRequestAttributeHandler requestHandler= new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName("_csrf");

        JwtAuthenticationConverter jwtAuthenticationConverter= new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//                .securityContext().requireExplicitSave(false)
//                .and().sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .cors().configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration configuration= new CorsConfiguration();
                        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                        configuration.setAllowedMethods(Collections.singletonList("*"));
                        configuration.setAllowCredentials(true);
                        configuration.setAllowedHeaders(Collections.singletonList("*"));
                        configuration.setExposedHeaders(Arrays.asList("Authorization"));
                        configuration.setMaxAge(3600L);
                        return configuration;
                    }
                }).and().csrf((csrf) -> {
                    csrf.csrfTokenRequestHandler(requestHandler).ignoringRequestMatchers("/contact","/register")
                            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
        }).addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class);
        http.authorizeHttpRequests((requests) -> {
            requests.requestMatchers("/myAccount").hasRole("USER")
                    .requestMatchers("/myBalance").hasAnyRole("USER","ADMIN")
                    .requestMatchers("/myLoans").hasRole("USER")
                    .requestMatchers("/myCards").hasRole("USER")
//                    .requestMatchers("/myAccount").hasAuthority("VIEWACCOUNT")
//                    .requestMatchers("/myBalance").hasAnyAuthority("VIEWACCOUNT","VIEWBALANCE")
//                    .requestMatchers("/myLoans").hasAuthority("VIEWLOANS")
//                    .requestMatchers("/myCards").hasAuthority("VIEWCARDS")
                    .requestMatchers("/user").authenticated()
                    .requestMatchers("/contact","/notices","/register").permitAll();
        });
        http.oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtAuthenticationConverter);

        return (SecurityFilterChain)http.build();
    }
}
