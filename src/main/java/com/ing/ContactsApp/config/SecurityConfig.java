package com.ing.ContactsApp.config;

import com.ing.ContactsApp.domain.entity.UserRole;
import com.ing.ContactsApp.service.security.UserDetailsServiceImp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    private final UserDetailsServiceImp securityUserDetails;

    public SecurityConfig(UserDetailsServiceImp securityUserDetails) {

        this.securityUserDetails = securityUserDetails;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeRequests(auth -> auth
                        .antMatchers("/user/**").hasAuthority(UserRole.USER.name())
                        .antMatchers("/admin/**").hasAuthority(UserRole.ADMIN.name())
                        .anyRequest()
                        .authenticated()
                )
                .userDetailsService(securityUserDetails)
                .httpBasic(withDefaults())
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();

    }
}
