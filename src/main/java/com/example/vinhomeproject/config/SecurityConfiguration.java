package com.example.vinhomeproject.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private static final String[] WHITE_LIST_URL = {
            "/v3/api-docs/**",
            "/v3/api-docs.yaml/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/ws/**",
            "/**"
    };
    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers(WHITE_LIST_URL)
                                .permitAll()
//                                .requestMatchers(HttpMethod.GET, "/api/v1/apartmentclasses/**").hasAnyRole( "ADMIN", "USER")
//                                .requestMatchers(HttpMethod.POST,"/api/v1/apartmentclasses/**").hasAnyRole("ADMIN")
//                                .requestMatchers(HttpMethod.PUT,"/api/v1/apartmentclasses/**").hasAnyRole("ADMIN")
//                                //---------------- apartment ---------------------------
//                                .requestMatchers(HttpMethod.GET, "/api/v1/apartments/**").hasAnyRole( "ADMIN", "USER")
//                                .requestMatchers(HttpMethod.POST,"/api/v1/apartments/**").hasAnyRole("ADMIN")
//                                .requestMatchers(HttpMethod.PUT,"/api/v1/apartments/**").hasAnyRole("ADMIN")
//                                //---------------- appointment -------------------------
//                                .requestMatchers(HttpMethod.GET, "/api/v1/appointments/**").hasAnyRole( "ADMIN", "USER")
//                                .requestMatchers(HttpMethod.POST,"/api/v1/appointments/**").hasAnyRole("ADMIN")
//                                .requestMatchers(HttpMethod.PUT,"/api/v1/appointments/**").hasAnyRole("ADMIN")
//                                //---------------- area -------------------------------
//                                .requestMatchers(HttpMethod.GET, "/api/v1/areas/**").hasAnyRole( "ADMIN", "USER")
//                                .requestMatchers(HttpMethod.POST,"/api/v1/areas/**").hasAnyRole("ADMIN")
//                                .requestMatchers(HttpMethod.PUT,"/api/v1/areas/**").hasAnyRole("ADMIN")
//                                //---------------- authentication ---------------------
//                                .requestMatchers(HttpMethod.POST, "/api/v1/auth/**").hasAnyRole( "ADMIN", "USER")
//                                //---------------- bank -------------------------------
//                                .requestMatchers(HttpMethod.GET, "/api/v1/bank/**").hasAnyRole( "ADMIN", "USER")
//                                //---------------- building ---------------------------
//                                .requestMatchers(HttpMethod.GET, "/api/v1/buildings/**").hasAnyRole( "ADMIN", "USER")
//                                .requestMatchers(HttpMethod.POST,"/api/v1/buildings/**").hasAnyRole("ADMIN")
//                                .requestMatchers(HttpMethod.PUT,"/api/v1/buildings/**").hasAnyRole("ADMIN")
//                                //---------------- card -------------------------------
//                                .requestMatchers(HttpMethod.GET, "/api/v1/card/**").hasAnyRole( "ADMIN", "USER")
//                                .requestMatchers(HttpMethod.POST,"/api/v1/card/**").hasAnyRole("ADMIN")
//                                .requestMatchers(HttpMethod.PUT,"/api/v1/card/**").hasAnyRole("ADMIN")
//                                //---------------- contract ---------------------------
//                                .requestMatchers(HttpMethod.GET, "/api/v1/contracts/**").hasAnyRole( "ADMIN", "USER")
//                                .requestMatchers(HttpMethod.POST,"/api/v1/contracts/**").hasAnyRole("ADMIN")
//                                .requestMatchers(HttpMethod.PUT,"/api/v1/contracts/**").hasAnyRole("ADMIN")
//                                //---------------- contract history -------------------
//                                .requestMatchers(HttpMethod.GET, "/api/v1/contracthistories/**").hasAnyRole( "ADMIN", "USER")
//                                .requestMatchers(HttpMethod.POST,"/api/v1/contracthistories/**").hasAnyRole("ADMIN")
//                                .requestMatchers(HttpMethod.PUT,"/api/v1/contracthistories/**").hasAnyRole("ADMIN")
//                                //---------------- issue -------------------------------
//                                .requestMatchers(HttpMethod.GET, "/api/v1/issue/**").hasAnyRole( "ADMIN", "USER")
//                                .requestMatchers(HttpMethod.POST,"/api/v1/issue/**").hasAnyRole("ADMIN")
//                                .requestMatchers(HttpMethod.PUT,"/api/v1/issue/**").hasAnyRole("ADMIN")
//                                //---------------- payment -----------------------------
//                                .requestMatchers(HttpMethod.GET, "/api/v1/payment/**").hasAnyRole( "ADMIN", "USER")
//                                .requestMatchers(HttpMethod.POST,"/api/v1/payment/**").hasAnyRole("ADMIN")
//                                .requestMatchers(HttpMethod.PUT,"/api/v1/payment/**").hasAnyRole("ADMIN")
//                                //---------------- post ---------------------------------
//                                .requestMatchers(HttpMethod.GET, "/api/v1/post/**").hasAnyRole( "ADMIN", "USER")
//                                .requestMatchers(HttpMethod.POST,"/api/v1/post/**").hasAnyRole("ADMIN")
//                                .requestMatchers(HttpMethod.PUT,"/api/v1/post/**").hasAnyRole("ADMIN")
//                                //---------------- post image ---------------------------
//                                .requestMatchers(HttpMethod.GET, "/api/v1/postimage/**").hasAnyRole( "ADMIN", "USER")
//                                .requestMatchers(HttpMethod.POST,"/api/v1/postimage/**").hasAnyRole("ADMIN")
//                                .requestMatchers(HttpMethod.PUT,"/api/v1/postimage/**").hasAnyRole("ADMIN")
//                                //---------------- problem ------------------------------
//                                .requestMatchers(HttpMethod.GET, "/api/v1/problem/**").hasAnyRole( "ADMIN", "USER")
//                                .requestMatchers(HttpMethod.POST,"/api/v1/problem/**").hasAnyRole("ADMIN","USER")
//                                .requestMatchers(HttpMethod.PUT,"/api/v1/problem/**").hasAnyRole("ADMIN","USER")
//                                //---------------- problem image ------------------------
//                                .requestMatchers(HttpMethod.GET, "/api/v1/problem-image/**").hasAnyRole( "ADMIN", "USER")
//                                .requestMatchers(HttpMethod.POST,"/api/v1/problem-image/**").hasAnyRole("ADMIN", "USER")
//                                .requestMatchers(HttpMethod.PUT,"/api/v1/problem-image/**").hasAnyRole("ADMIN", "USER")
//                                //---------------- review -------------------------------
//                                .requestMatchers(HttpMethod.GET, "/api/v1/review/**").hasAnyRole( "ADMIN", "USER")
//                                .requestMatchers(HttpMethod.POST,"/api/v1/contracts/**").hasAnyRole("ADMIN", "USER")
//                                .requestMatchers(HttpMethod.PUT,"/api/v1/contracts/**").hasAnyRole("ADMIN", "USER")
//                                //---------------- user ---------------------------------
//                                .requestMatchers(HttpMethod.GET, "/api/v1/user/**").hasAnyRole( "ADMIN", "USER")
//                                .requestMatchers(HttpMethod.POST,"/api/v1/user/**").hasAnyRole("ADMIN", "USER")
//                                .requestMatchers(HttpMethod.PUT,"/api/v1/user/**").hasAnyRole("ADMIN", "USER")
//                                //---------------- zone ---------------------------------
//                                .requestMatchers(HttpMethod.GET, "/api/v1/zone/**").hasAnyRole( "ADMIN", "USER")
//                                .requestMatchers(HttpMethod.POST,"/api/v1/zone/**").hasAnyRole("ADMIN")
//                                .requestMatchers(HttpMethod.PUT,"/api/v1/zone/**").hasAnyRole("ADMIN")
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        ;
        return http.build();
    }
}
