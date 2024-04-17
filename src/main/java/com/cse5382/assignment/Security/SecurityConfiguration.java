package com.cse5382.assignment.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
   
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .authorizeHttpRequests(auth -> auth
                .antMatchers(HttpMethod.GET, "/phoneBook/list**").permitAll()
                .antMatchers(HttpMethod.PUT, "/phoneBook/delete**").hasAuthority("ROLE_DELETE")
                .antMatchers(HttpMethod.POST, "/phoneBook/add**").hasAuthority("ROLE_WRITE")
                .anyRequest().authenticated() //LAST --> all other requests must be authenticated
            )
            .csrf(csrf -> csrf.disable())
            .httpBasic(Customizer.withDefaults())
            .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

}
