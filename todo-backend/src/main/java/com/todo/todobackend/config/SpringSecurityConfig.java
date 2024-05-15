package com.todo.todobackend.config;

import com.todo.todobackend.security.JwtAuthenticationEntryPoint;
import com.todo.todobackend.security.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@AllArgsConstructor
public class SpringSecurityConfig {

    private UserDetailsService userDetailsService;

    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private JwtAuthenticationFilter jwtAuthenticationFilter;


    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf((csrf) -> csrf.disable())

                .authorizeHttpRequests((autho) -> {
//                    autho.requestMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN");
//                    autho.requestMatchers(HttpMethod.PUT, "/api/**").hasRole("ADMIN");
//                    autho.requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN");
//                    autho.requestMatchers(HttpMethod.GET, "/api/**").hasAnyRole("ADMIN", "USER");
//                    autho.requestMatchers(HttpMethod.PATCH, "/api/**").hasAnyRole("ADMIN, USER");
//                    autho.requestMatchers(HttpMethod.GET,"/api/**").permitAll();
                    //allow all the user sto register:
                    autho.requestMatchers("/api/auth/**").permitAll();
                    autho.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
                    autho.anyRequest().authenticated();
                }).httpBasic(Customizer.withDefaults());

        httpSecurity.exceptionHandling(exception ->
                exception.authenticationEntryPoint(jwtAuthenticationEntryPoint));

        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    //The below code used In memory authentiction, as we are using database authentication, we need to comment this out
//    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails swaroop = User.builder()
//                .username("swaroop")
//                .password(passwordEncoder().encode("123456"))
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(swaroop, admin);
//    }
}
