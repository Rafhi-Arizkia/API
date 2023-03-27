package com.web.api.service;

import com.web.api.dto.AuthResponse;
import com.web.api.dto.LoginRequest;
import com.web.api.dto.UserRegister;
import com.web.api.model.entities.UserEntities;
import com.web.api.model.entities.UserRole;
import com.web.api.model.repo.UserRepo;
import com.web.api.security.jwt.JwtService;
import org.hibernate.cache.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.NoSuchElementException;

@Service
@CacheConfig(cacheNames = "user")
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    @Lazy
    private AuthenticationManager authenticationManager;
    @Autowired
    private CacheManager cacheManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUserEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username" + username + "tidak ditemukan"));
    }

    public AuthResponse register(UserRegister userRegister) {
        try {
            var userDetails = UserEntities.builder()
                    .firstName(userRegister.getFirstName())
                    .lastName(userRegister.getLastName())
                    .fullName(userRegister.getFullName())
                    .userEmail(userRegister.getUserEmail())
                    .password(passwordEncoder.encode(userRegister.getPassword()))
                    .role(UserRole.USER)
                    .build();
            userRepo.save(userDetails);

            var jwtToken = jwtService.generateToken(new HashMap<>(), userDetails);
            return AuthResponse.builder()
                    .token(jwtToken)
                    .build();
        } catch (DataIntegrityViolationException exception) {
            String message = exception.getMessage();
            if (message.contains("UK_ky6rbh0axwmbamyg6wl4a7imb")) {
                // UK_ky6rbh0axwmbamyg6wl4a7imb adalah nama constraint atau batasan unik pada kolom
                // user_email pada database
                throw new RuntimeException("Email sudah digunakan");
            } else {
                throw new RuntimeException("Terjadi kesalahan saat menyimpan Data");
            }
        }
    }

    @Cacheable(value = "user", key = "#request.getEmail()")
    public AuthResponse authenticate(LoginRequest request) {
        Cache cache = cacheManager.getCache("user");
        Object value = null;
        try {
            if (cache != null){
                value = cache.get(request.getEmail());
            }
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
            ));
        } catch (BadCredentialsException ex) {
            throw new IllegalArgumentException("Email atau password salah");
        } catch (Exception e){
            throw new CacheException("Terjadi kesalahan memproses cache"+ e.getMessage());
        }
        if (value == null){
            throw new CacheException("cache entry not found");
        }

        var userOptional = userRepo.findByUserEmail(request.getEmail());
        if (userOptional.isPresent()) {
            var user = userOptional.get();
            var jwtToken = jwtService.generateToken(new HashMap<>(), user);
            return AuthResponse.builder()
                    .token(jwtToken)
                    .build();
        } else {
            throw new NoSuchElementException("User with email " + request.getEmail() + "not found");
        }

    }
}
