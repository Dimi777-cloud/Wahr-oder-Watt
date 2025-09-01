package com.school.project.wahr_oder_watt.config;

import com.school.project.wahr_oder_watt.security.CustomUserDetailsService;
import com.school.project.wahr_oder_watt.security.JwtAuthenticationFilter;
import com.school.project.wahr_oder_watt.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security configuration class for setting up authentication and authorization.
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

  private final CustomUserDetailsService userDetailsService;
  private final JwtUtil jwtUtil;

  /**
   * Bean for password encoding using BCrypt.
   *
   * @return BCryptPasswordEncoder instance
   */
  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * Bean for AuthenticationManager.
   *
   * @param http HttpSecurity instance
   * @return AuthenticationManager instance
   * @throws Exception if an error occurs during configuration
   */
  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
    AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
    builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    return builder.build();
  }

  /**
   * Configures the security filter chain.
   *
   * @param http HttpSecurity instance
   * @return SecurityFilterChain instance
   * @throws Exception if an error occurs during configuration
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/**").permitAll()
            .anyRequest().authenticated()
        )
        .addFilterBefore(
            new JwtAuthenticationFilter(jwtUtil, userDetailsService),
            UsernamePasswordAuthenticationFilter.class
        );
    return http.build();
  }
}
