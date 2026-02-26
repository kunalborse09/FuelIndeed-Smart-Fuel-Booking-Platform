package com.fuelindeed.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class SecurityConfig {
	
	 // 🔐 PASSWORD ENCODER
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // =========================================================
    // 🔵 ADMIN SECURITY
    // =========================================================
    @Bean
    @Order(1)
    public SecurityFilterChain adminSecurity(HttpSecurity http) throws Exception {

        http
            .securityMatcher("/admin/**")
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session ->
                    session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            )
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(
                            "/admin/login",
                            "/admin/register",
                            "/admin/forgot-password",
                            "/admin/reset-password"
                    ).permitAll()
                    .anyRequest().hasAuthority("ROLE_ADMIN")
            )
            .formLogin(login -> login
                    .loginPage("/admin/login")
                    .loginProcessingUrl("/admin/login")
                    .defaultSuccessUrl("/admin/dashboard", true)
                    .failureUrl("/admin/login?error")
                    .permitAll()
            )
            .logout(logout -> logout
                    .logoutUrl("/admin/logout")
                    .logoutSuccessUrl("/admin/login?logout")
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .deleteCookies("JSESSIONID")
                    .permitAll()
            );

        return http.build();
    }

    // =========================================================
    // 🟢 USER SECURITY
    // =========================================================
    @Bean
    @Order(2)
    public SecurityFilterChain userSecurity(HttpSecurity http) throws Exception {

        http
            .securityMatcher("/user/**")
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session ->
                    session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            )
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(
                            "/user/login",
                            "/user/register",
                            "/user/forgot-password",
                            "/user/reset-password"
                            
                    ).permitAll()
                    .anyRequest().hasAuthority("ROLE_USER")
            )
            .formLogin(login -> login
                    .loginPage("/user/login")
                    .loginProcessingUrl("/user/login")
                    .defaultSuccessUrl("/user/dashboard", true)
                    .failureUrl("/user/login?error")
                    .permitAll()
            )
            .logout(logout -> logout
                    .logoutUrl("/user/logout")
                    .logoutSuccessUrl("/user/login?logout")
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .deleteCookies("JSESSIONID")
                    .permitAll()
            );

        return http.build();
    }

    // =========================================================
    // 🟠 STATION SECURITY
    // =========================================================
    @Bean
    @Order(3)
    public SecurityFilterChain stationSecurity(HttpSecurity http) throws Exception {

        http
            .securityMatcher("/station/**")
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session ->
                    session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            )
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(
                            "/station/login",
                            "/station/register",
                            "/station/forgot-password",
                            "/station/reset-password"
                    ).permitAll()
                    .anyRequest().hasAuthority("ROLE_STATION")
            )
            .formLogin(login -> login
                    .loginPage("/station/login")
                    .loginProcessingUrl("/station/login")
                    .defaultSuccessUrl("/station/dashboard", true)
                    .failureUrl("/station/login?error")
                    .permitAll()
            )
            .logout(logout -> logout
                    .logoutUrl("/station/logout")
                    .logoutSuccessUrl("/station/login?logout")
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .deleteCookies("JSESSIONID")
                    .permitAll()
            );

        return http.build();
    }

    // =========================================================
    // 🟣 DELIVERY SECURITY
    // =========================================================
    @Bean
    @Order(4)
    public SecurityFilterChain deliverySecurity(HttpSecurity http) throws Exception {

        http
            .securityMatcher("/delivery/**")
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session ->
                    session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            )
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(
                            "/delivery/login",
                            "/delivery/register",
                            "/delivery/forgot-password",
                            "/delivery/reset-password"
                    ).permitAll()
                    .anyRequest().hasAuthority("ROLE_DELIVERY")
            )
            .formLogin(login -> login
                    .loginPage("/delivery/login")
                    .loginProcessingUrl("/delivery/login")
                    .defaultSuccessUrl("/delivery/dashboard", true)
                    .failureUrl("/delivery/login?error")
                    .permitAll()
            )
            .logout(logout -> logout
                    .logoutUrl("/delivery/logout")
                    .logoutSuccessUrl("/delivery/login?logout")
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .deleteCookies("JSESSIONID")
                    .permitAll()
            );

        return http.build();
    }

    // =========================================================
    // ⚪ DEFAULT FALLBACK (VERY IMPORTANT)
    // =========================================================
    @Bean
    @Order(5)
    public SecurityFilterChain defaultSecurity(HttpSecurity http) throws Exception {

        http
            .authorizeHttpRequests(auth -> auth
                    .anyRequest().permitAll()
            );

        return http.build();
    }

    

    // 🔐 AUTHENTICATION MANAGER
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
