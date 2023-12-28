package org.example.spring_demo_eduard_v2.config;

import lombok.AllArgsConstructor;
import org.example.spring_demo_eduard_v2.filters.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {


    private JwtAuthenticationFilter jwtAuthenticationFilter;
    private AuthenticationProvider authenticationProvider;


//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {

//        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
//
//        inMemoryUserDetailsManager.createUser(User.builder()
//                .username("buyer_user")
//                .password(passwordEncoder.encode("buyer_password"))
//                .roles(Role.BUYER.name())
//                .build());
//
//        inMemoryUserDetailsManager.createUser(User.builder()
//                .username("seller_user")
//                .password(passwordEncoder.encode("seller_password"))
//                .roles(Role.SELLER.name())
//                .build());
//
//        inMemoryUserDetailsManager.createUser(User.builder()
//                .username("admin")
//                .password(passwordEncoder.encode("admin_password"))
//                .roles(Role.ADMIN.name())
//                .build());
//
//        return inMemoryUserDetailsManager;
//    }


//    @Bean
//    public AuthenticationProvider authenticationProvider(PasswordEncoder passwordEncoder,
//                                                         UserDetailsService userDetailsService) {
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
//        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
//        return daoAuthenticationProvider;
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity
//            ,
//                                                   AuthenticationProvider authenticationProvider,
//                                                   JwtAuthenticationFilter jwtAuthenticationFilter
    ) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(matcherRegistry ->
                        matcherRegistry
                                .requestMatchers("/api/auth/**")
                                .permitAll()
//                        .requestMatchers(HttpMethod.POST, "/products").hasRole("SELLER")
                                .anyRequest().authenticated())
//                .exceptionHandling(exceptionHandlingConfigurer -> exceptionHandlingConfigurer.authenticationEntryPoint(authenticationEntryPoint))
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                .getSharedObject(AuthenticationManagerBuilder.class)
//                .build();
//    }
}
