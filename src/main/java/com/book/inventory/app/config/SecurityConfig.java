package com.book.inventory.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable) // Disable CSRF protection (optional)
            .authorizeHttpRequests(authorizeRequests ->
                    authorizeRequests
                            .requestMatchers("/login", "/css/**", "/js/**", "/images/**", "/logout").permitAll() // Allow access to login and resources
                            .requestMatchers("/add-book", "/show-books", "/index", "/").authenticated() // Require authentication for specific endpoints
                            .anyRequest().authenticated() // Other pages require authentication
            )
            .formLogin(formLogin ->
                    formLogin
                            .loginPage("/login") // Specify the custom login page
                            .defaultSuccessUrl("/", true) // Redirect to home page after login
                            .permitAll()
            )
            .logout(LogoutConfigurer::permitAll); // Allow logout for authenticated users

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("dino") // Your username
                .password(passwordEncoder().encode("dino")) // Your password
                .roles("USER") // User role
                .build());
        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
