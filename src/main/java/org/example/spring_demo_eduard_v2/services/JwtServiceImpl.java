//package org.example.spring_demo_eduard_v2.services;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import jakarta.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Service;
//
//import java.security.Key;
//import java.time.Duration;
//import java.util.Date;
//import java.util.Map;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//
//@Service
//public class JwtServiceImpl implements JwtService {
//
//    @Value("${jwt.signingKey}")
//    private String signingKey;
//
//    private Key key;
//
////    @PostConstruct
////    public void setUpKey() {
////        key = Keys.hmacShaKeyFor(signingKey.getBytes());
////    }
//    private Key getSigningKey(){
//        byte[] keyBytes = Decoders.BASE64.decode(signingKey);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }
//
//
//
//    @Override
//    public String extractUsername(String token) {
//        return resolveClaim(token, Claims::getSubject);
//    }
//
//
//    @Override
//    public String generateToken(UserDetails userDetails) {
//        String roles = userDetails
//                .getAuthorities()
//                .stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.joining(", "));
//
//        return generateToken(Map.of("roles", roles), userDetails, Duration.ofSeconds(30));
//    }
//
//    @Override
//    public String generateRefreshToken(UserDetails userDetails) {
//        return generateToken(Map.of("type", "refresh"), userDetails, Duration.ofDays(2));
//    }
//
//    @Override
//    public boolean isRefreshType(String token) {
//        return resolveClaim(token, claims -> claims.get("type", String.class)).equals("refresh");
//    }
//
//    @Override
//    public boolean isTokenValid(String token, UserDetails userDetails) {
//        String username = extractUsername(token);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//    }
//
////    @Override
////    public boolean isTokenExpired(String token) {
////        return resolveClaim(token, Claims::getExpiration).before(new Date());
////    }
//    @Override
//    public boolean isTokenExpired(String token) {
//        return extractExpiration(token).before(new Date());
//    }
//
//    private Date extractExpiration(String token) {
//        return resolveClaim(token, Claims::getExpiration);
//    }
//
//    private String generateToken(Map<String, String> claims, UserDetails userDetails, Duration duration) {
//        return Jwts
//                .builder()
//                .setClaims(claims)
//                .setSubject(userDetails.getUsername())
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + duration.toMillis()))
//                .signWith(key, SignatureAlgorithm.HS256)
//                .compact();
//    }
//
//    private <T> T resolveClaim(String token, Function<Claims, T> resolver) {
//        Claims claims = extractClaims(token);
//        return resolver.apply(claims);
//    }
//
////    private Claims extractClaims(String token) {
////        return Jwts
////                .parserBuilder()
////                .setSigningKey(key)
////                .build()
////                .parseClaimsJws(token)
////                .getBody();
////    }
//
//    private Claims extractClaims(String token) {
//        return Jwts
//                .parserBuilder()
//                .setSigningKey(getSigningKey())
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }
//}