package com.tecdesoftware.market.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    // Bean para encriptar contraseñas con BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Define las reglas de seguridad: qué rutas son públicas, etc.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS habilitado
                .csrf(csrf -> csrf.disable()) // Se desactiva CSRF para uso como API REST
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll() // Auth endpoints públicos (/** en lugar de /*)
                        .requestMatchers("/swagger-ui/**").permitAll() // Swagger endpoints
                        .requestMatchers("/v3/**").permitAll() // OpenAPI docs
                        .requestMatchers("/api-docs/**").permitAll() // API docs
                        .requestMatchers("/h2-console/**").permitAll() // H2 console
                        .anyRequest().authenticated() // Todo lo demás requiere JWT válido
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // No hay sesiones
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.sameOrigin()) // Para H2 console
                );

        // Se agrega el filtro JWT personalizado antes del filtro de autenticación por defecto
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Configuración CORS más específica para Flutter web
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Orígenes permitidos para desarrollo (Flutter web usa puertos dinámicos)
        configuration.setAllowedOriginPatterns(Arrays.asList(
                "http://localhost:*",
                "http://127.0.0.1:*"
        ));

        // Métodos permitidos
        configuration.setAllowedMethods(Arrays.asList(
                "GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"
        ));

        // Headers permitidos
        configuration.setAllowedHeaders(List.of("*"));

        // Permitir credentials si usas cookies (false para Bearer tokens)
        configuration.setAllowCredentials(false);

        // Headers expuestos al cliente
        configuration.setExposedHeaders(Arrays.asList(
                "Authorization", "Content-Type"
        ));

        // Tiempo de cache para preflight requests
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}