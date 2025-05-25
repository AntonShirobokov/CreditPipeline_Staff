package com.shirobokov.creditpipelinestaff.config.security;


import com.shirobokov.creditpipelinestaff.service.EmployeeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity(debug=true)
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
       http
               .formLogin(formLogin -> formLogin.loginPage("/login")
                       .defaultSuccessUrl("/applications")
                       .permitAll())
               .logout(logout -> logout.logoutUrl("/logout"))
               .authorizeHttpRequests(authorizeRequests ->
                       authorizeRequests
                               .requestMatchers("/api/receivedApplication").permitAll()
                               .requestMatchers("/registration").hasRole("ADMIN")
                               .requestMatchers("/error").permitAll()
                               .requestMatchers("/css/*.css").permitAll()
                               .requestMatchers("/js/*.js").permitAll()
                               .anyRequest().hasAnyRole("EMPLOYEE", "ADMIN"));

       return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
