package com.poc.time_report.configuration;

import static com.poc.time_report.utils.Constants.SAMPLE_ADMIN_USERNAME;
import static com.poc.time_report.utils.Constants.SAMPLE_EMPLOYEE_NAME;
import static com.poc.time_report.utils.Constants.SAMPLE_PASSWORD_ADMIN;
import static com.poc.time_report.utils.Constants.SAMPLE_PASSWORD_USER;
import static com.poc.time_report.utils.Constants.SAMPLE_ROLE_ADMIN;
import static com.poc.time_report.utils.Constants.SAMPLE_ROLE_USER;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  public UserDetailsService userDetailsService() {
    return new InMemoryUserDetailsManager(
        User.withUsername(SAMPLE_ADMIN_USERNAME)
            .password(SAMPLE_PASSWORD_ADMIN)
            .roles(SAMPLE_ROLE_ADMIN)
            .build(),

        User.withUsername(SAMPLE_EMPLOYEE_NAME)
            .password(SAMPLE_PASSWORD_USER)
            .roles(SAMPLE_ROLE_USER)
            .build()
    );
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/report").hasAnyRole("ADMIN", "EMPLOYEE")
            .anyRequest().permitAll()
        )
        .formLogin(form -> form
            .defaultSuccessUrl("/report", true)
            .permitAll()
        )
        .logout(logout -> logout.logoutSuccessUrl("/"))
        .build();
  }
}
