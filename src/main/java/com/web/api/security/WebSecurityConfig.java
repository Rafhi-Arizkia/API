package com.web.api.security;

import com.web.api.security.jwt.JwtAuthFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Lazy
    private final JwtAuthFilter jwtAuthFilter;

    @Lazy
    private final AuthenticationProvider authenticationProvider;


    @Autowired
    public WebSecurityConfig(JwtAuthFilter jwtAuthFilter, AuthenticationProvider authenticationProvider) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationProvider = authenticationProvider;
    }

    /**
     * Configuration filter chain untuk keamanan aplikasi web
     * @param http objek HttpSecurity yang digunakan untuk configuration fitur keamanan pada aplikasi
     * @return SecurityFilterChain yang sudah di configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Menonaktifkan proteksi CSRF
                .csrf()
                .disable()
                // digunakan untuk configuration aturan otorisasi akses ke endpoint tertentu.
                .authorizeHttpRequests()
                // bisa diakses oleh siapapun
                .requestMatchers("/api/user/**")
                .permitAll()
                .requestMatchers(AntPathRequestMatcher.antMatcher("/v3/api-docs/**"))
                .permitAll()
                .requestMatchers(AntPathRequestMatcher.antMatcher("/swagger-ui/**"))
                .permitAll()
                .requestMatchers(AntPathRequestMatcher.antMatcher("/swagger-ui.html"))
                .permitAll()
                .requestMatchers("/actuator/**")
                .permitAll()
                .requestMatchers("/actuator/cache/**")
                .permitAll()
                // selain endpoint diatas membutuhkan authentication
                .anyRequest()
                .authenticated()
                .and()
                // tidak memnbuat session untuk menyimpan data
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // menambahkan authentication provider yang didefinisikan sebelumnya
                .authenticationProvider(authenticationProvider)
                // digunakan untuk menambahkan filter JWT setelah filter authentication
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                // digunakan untuk menangani exception saat autentikasi gagal
                // exception saat permintaan ditolak
                .accessDeniedHandler(accessDeniedHandler());
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return ((request, response, accessDeniedException) -> {
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("{\"error\":\"Anda tidak memiliki hak akses untuk mengambil/melihat data ini\"}");
        });
    }


}
