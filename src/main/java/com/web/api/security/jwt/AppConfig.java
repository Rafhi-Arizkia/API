package com.web.api.security.jwt;

import com.web.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {
    private final UserService userService;
    @Autowired
    public AppConfig(UserService userService) {
        this.userService = userService;
    }

    /**
     * Untuk melakukan hashing pada password yang dimasukkan oleh user
     * @return BCryptPasswordEncoder()  yang digunakan untuk melakukan encoding password dalam aplikasi
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Method yang mengembalikan bean AuthenticationProvider
     * yang digunakan untuk melakukan autentikasi pengguna
     * @return daoAuthenticationProvider yang digunakan untuk autentikasi pengguna
     */
    @Bean
    public AuthenticationProvider authenticationProvider(){
        // membuat objek DaoAuthenticationProvider
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        // menetapkan userService sebagai UserDetailsService pada DaoAuthenticationProvider
        daoAuthenticationProvider.setUserDetailsService(userService);
        // menetapkan passwordEncoder yang digunakan pada DaoAuthenticationProvider
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        // mengembalikan objek DaoAuthenticationProvider yang telah di-set-up
        return daoAuthenticationProvider;
    }

}
