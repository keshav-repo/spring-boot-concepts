package com.example.security.config;

import jakarta.servlet.Filter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        UserDetails admin = User.withUsername("Basant")
                .password(encoder.encode("Pwd1"))
                .roles("ADMIN")
                .build();
        UserDetails user = User.withUsername("John")
                .password(encoder.encode("Pwd2"))
                .roles("USER", "ADMIN", "HR")
                .build();
        return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        SecurityFilterChain securityFilterChain = http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/public").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/hr").hasRole("HR")
                        .anyRequest().permitAll()
                )
                .httpBasic(withDefaults())
                .build();

        List<Filter> filters = securityFilterChain.getFilters();
        System.out.println("Filter value is");
        for(Filter filter: filters){
            System.out.println(filter.toString());
        }
        return securityFilterChain;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
