package com.rungroup.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private CustomUserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                .authorizeHttpRequests(configurer -> configurer
                        .requestMatchers("/login", "/register", "/register/save", "/clubs","/clubs/{clubId}","/events","/events/{eventId}", "/css/**", "/js/**").permitAll()
                        .requestMatchers(HttpMethod.GET,  "/courses/new").hasRole("TEACHER")
                        .requestMatchers(HttpMethod.POST, "/courses/new").hasRole("TEACHER")
                        .requestMatchers("/courses/*/edit", "/courses/*/delete").hasRole("TEACHER")
                        .requestMatchers(HttpMethod.GET,  "/announcements/new").hasRole("TEACHER")
                        .requestMatchers(HttpMethod.POST, "/announcements/new").hasRole("TEACHER")
                        .requestMatchers("/announcements/*/edit", "/announcements/*/delete").hasRole("TEACHER")
                        .requestMatchers("/register", "/register/save").hasRole("ADMIN")
                        .requestMatchers("/housing/new", "/housing/*/edit", "/housing/*/delete").hasRole("ADMIN")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/clubs", true)
                        .loginProcessingUrl("/login")
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login?logout=true")
                        .permitAll()
                );
        return http.build();
    }

    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}
