package com.testproject.blogapp.config;

import com.testproject.blogapp.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(request->
                        request.requestMatchers(Constants.AUTH_FILTER_PATH)
                                .permitAll()
                                .requestMatchers(Constants.POST_FILTER_PATH).hasAnyRole(
                                        Constants.MODERATOR_FILTER_ROLE,
                                        Constants.ADMIN_FILTER_ROLE,
                                        Constants.USER_FILTER_ROLE
                                )
                                .requestMatchers(Constants.USER_FILTER_PATH).hasAnyRole(
                                        Constants.USER_FILTER_ROLE,
                                        Constants.ADMIN_FILTER_ROLE
                                )
                                .requestMatchers(Constants.ADMIN_FILTER_PATH).hasRole(
                                        Constants.ADMIN_FILTER_ROLE
                                )
                                .requestMatchers(Constants.MODERATOR_FILTER_PATH).hasAnyRole(
                                        Constants.MODERATOR_FILTER_ROLE,
                                        Constants.ADMIN_FILTER_ROLE
                                )
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOrigins(Constants.CORS_ALLOWED_ORIGINS);
        corsConfiguration.setAllowedHeaders(Constants.CORS_ALLOWED_HEADERS);
        corsConfiguration.setExposedHeaders(Constants.CORS_EXPOSED_HEADERS);
        corsConfiguration.setAllowedMethods(Constants.CORS_ALLOWED_METHODS);
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration(Constants.CORS_CONFIGURATION_PATTERN, corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
}
