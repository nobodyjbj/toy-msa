package org.toy.userservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.IpAddressMatcher;
import org.toy.userservice.service.UserService;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserService userService;
    private final Environment environment;
    private final PasswordEncoder passwordEncoder;

    // 여러 개의 허용 IP를 리스트로 정의
    public static final List<String> ALLOWED_IP_ADDRESSES = List.of("127.0.0.1/32", "172.19.207.191/32", "10.0.0.1/32");
    public static final List<IpAddressMatcher> ALLOWED_IP_ADDRESS_MATCHERS =
            ALLOWED_IP_ADDRESSES.stream().map(IpAddressMatcher::new).collect(Collectors.toList());

    public SecurityConfig(UserService userService, Environment environment, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.environment = environment;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Configure AuthenticationManagerBuilder
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(passwordEncoder);

        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                // h2 사용하려면 필요한 옵션
                .headers(headersConfigurer -> headersConfigurer
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
                );

        http
                .authorizeHttpRequests(authorize -> authorize
                                .requestMatchers(new AntPathRequestMatcher("/actuator/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/user-service/users", "POST")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/user-service/welcome")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/user-service/health-check")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/swagger-ui/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/swagger-resources/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/v3/api-docs/**")).permitAll()
                                .requestMatchers("/**").access(this::hasIpAddress)
                                .anyRequest().authenticated()
                        )
                .authenticationManager(authenticationManager)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilter(getAuthenticationFilter(authenticationManager));

        return http.build();
    }

    private AuthorizationDecision hasIpAddress(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        return new AuthorizationDecision(
                ALLOWED_IP_ADDRESS_MATCHERS.stream()
                        .anyMatch(matcher -> matcher.matches(object.getRequest()))
        );
    }

    private AuthenticationFilter getAuthenticationFilter(AuthenticationManager authenticationManager) {
        return new AuthenticationFilter(authenticationManager, userService, environment);
    }
}
