package com.web.api.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtAuthFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    /**
     *
     * @param request          Objek HttpServletRequest dari permintaan HTTP saat ini
     * @param response         Objek HttpServletResponse untuk memberikan respons HTTP saat ini
     * @param filterChain      FilterChain yang dapat digunakan oleh servlet untuk mengalirkan permintaan ke sisa rantai filter
     * @throws ServletException  Jika terjadi kesalahan pada servlet
     * @throws IOException   Jika terjadi kesalahan input/output saat melakukan operasi I/O pada servlet
     * @throws AuthenticationException   Jika terjadi kesalahan saat melakukan autentikasi
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException, AuthenticationException {
        // untuk mengambil header authorization
        final String authHeader = request.getHeader("Authorization");
        // untuk menyimpan token yang sudah dipisahkan dari header authorization
        final String jwt;
        // untuk menyimpan nama penngguna token jwt menggunakan objek jwt service
        final String userEmail;

        // Jika header Authorization kosong atau tidak diawali dengan "Bearer",
        // maka kode akan menyelesaikan proses filter dan melanjutkan ke filter selanjutnya
        // tanpa melakukan proses autentikasi.
        // Hal ini menandakan bahwa permintaan client tidak mengirimkan token JWT yang valid.
        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }

        //  kita menggunakan substring(7) karena kita ingin mengambil karakter ke-7 sampai akhir string.
        jwt = authHeader.substring(7);

        // Pada kode ini, kita menggunakan jwtService.extractUsername(jwt) untuk mengambil email user dari token JWT.
        // Hasil dari method extractUsername kemudian disimpan dalam variabel userEmail yang digunakan pada kode
        // selanjutnya untuk melakukan validasi token dan autentikasi user.
        userEmail = jwtService.extractUsername(jwt);

        // Memeriksa apakah ada nilai pengguna dan tidak ada objek autententikasi dalam konteks keamanan saat ini
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // selanjutnya memuat UserDetails menggunakan userDetailsService dengan parameter user email
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            // memeriksa apakah token jwt yang diterima dari client(jwt) valid
            if (jwtService.validToken(jwt, userDetails)) {
                // kode ini untuk menyimpan tentang pengguna yang terautentikasi
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        // userDetails objek yang mempresentasikan pengguna seperti password dan email
                        userDetails,
                        null,
                        // untuk otoritas yang diberikan kepada pengguna
                        userDetails.getAuthorities()
                );
                // digunakan untuk mengisi informasi detail pada objek authenticationToken.
                // detail tersebut diambil dari informasi request yang dikirim dari pengguna,seperti ip adders dan
                // browser yang digunakan
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                // bertujuan untuk set objek authenticationToken ke dalam SecurityContextHolder
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        // setelah filter dijalankan, maka respon akan dikirimkan kembali ke client.
        // kode ini bertugas untuk meneruskan permintaan dan respon ke filter selanjutnya dalam filter chain
        filterChain.doFilter(request, response);
    }
}
