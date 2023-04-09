package com.web.api.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
/**
 * Kelas ini bertanggung jawab untuk mengimplementasikan layanan JWT (JSON Web Token) untuk memvalidasi
 * pengguna dan memberikan akses ke berbagai sumber daya yang dilindungi di dalam aplikasi web.
 */
@Service
public class JwtService {
    /**
     * Kunci rahasia yang digunakan untuk menandatangani JWT
     */
    private static final String SECRET_KEY = "33743677397A24432646294A404E635266556A586E5A7234753778214125442A";

    /**
     * Mengestrak nama pengguna dari token JWT
     * @param token TOKEN JWT
     * @return nama pengguna
     */
    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    /**
     * Berfungsi untuk Mengesktrak semua claim yang terkandung dalam token JWT
     * @param token  Token JWT
     * @return semua claim dari token JWT
     */
    private Claims  extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInkey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Mengekstrak data lain dari token jwt, Seperti waktu kadaluwarsa dan informasi lainnya
     * yang telah disimpan dalam token jwt
     * @param token Token JWT
     * @param claimsResolver fungsi resolver untuk mengambil nilai claim yang diperlukan
     * @return Nilai claims yang diperlukan
     * @param <T> Nilai claim yang diperlukan
     */
    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Mendapatkan kunci rahasia yang digunakan untuk menandatangani token JWT
     * @return SECRET_KEY kunci rahasia
     */
    private Key getSignInkey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Menghasilkan token JWT untuk pengguna yang terauntentikasi
     * @param extractClaims Claim tambahan yang akan dimasukkan ke dalam token
     * @param userDetails Detail pengguna yang terautentikasi
     * @return Token JWT yang dihasilkan
     */
    public String generateToken(Map<String,Object> extractClaims, UserDetails userDetails){
        return Jwts
                .builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                // berfungsi untuk mengatur waktu kadaluwarsa dari token JWT yang dihasilkan 7.200.000 mile second
                // atau disebut 2 jam
                .setExpiration(new Date(System.currentTimeMillis()+ 1000 * 60 * 60 * 2))
                .signWith(getSignInkey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Menghasilkan token JWT untuk pengguna yang terautentikasi tanpa claim tambahan
     * @param userDetails detail pengguna yang terautentikasi
     * @return token jwt yang dihasilkan
     */
    public String generateUserToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Digunakan untuk memvalidasi token JWT yang diberikan masih berlaku atau tidak
     * @param token yang berisi token jwt
     * @param userDetails yang berisi informasi pengguna
     * @return untuk mengembalikan nilai true and false
     */
    public boolean validToken(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        // Jadi, jika nilai username.equals(userDetails.getUsername()) dan !isTokenExpired(token)
        // keduanya bernilai true, maka kode akan mengembalikan nilai true, yang menunjukkan bahwa
        // token masih berlaku (valid). Namun jika salah satu atau kedua nilai tersebut bernilai false,
        // maka kode akan mengembalikan nilai false, yang menunjukkan bahwa token tidak valid.
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
// 2 Method untuk megecek token kadaluwarsa atau tidak
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token,Claims:: getExpiration);
    }
}
