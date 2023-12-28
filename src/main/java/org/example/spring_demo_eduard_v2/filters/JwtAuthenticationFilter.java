package org.example.spring_demo_eduard_v2.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.example.spring_demo_eduard_v2.repository.CustomerRepository;
import org.example.spring_demo_eduard_v2.services.JwtService;
//import org.example.spring_demo_eduard_v2.services.JwtServiceImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
//@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER_PREFIX = "Bearer ";

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final CustomerRepository customerRepository;
//    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {
            String authHeader = request.getHeader("Authorization"); /* Bearer hgvjbguybguvdbfguvygguvbgdbyvubgdyu*/
            String jwt;
            String userEmail;
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            if (authHeader == null || !authHeader.startsWith(AUTHORIZATION_HEADER_PREFIX)) {
                filterChain.doFilter(request, response);
                return;
            }
//            jwt = authHeader.substring(7);
//            OR
            jwt = authHeader.substring(AUTHORIZATION_HEADER_PREFIX.length());
            userEmail = jwtService.extractUsername(jwt);

            if (userEmail != null && SecurityContextHolder
                    .getContext()
                    .getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

                if (jwtService.isTokenValid(jwt, userDetails)
                && !jwt.equals(customerRepository.findCustomerByEmail(userEmail).get().getRefreshToken())) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (UsernameNotFoundException e) {
            throw new RuntimeException(e);
        } catch (ExpiredJwtException e){
            response.setHeader("Error", "token is dead");
        }


//        if (!StringUtils.startsWithIgnoreCase(authorization, AUTHORIZATION_HEADER_PREFIX)) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        String token = authorization.substring(AUTHORIZATION_HEADER_PREFIX.length());

//        try {
//            if (jwtService.isTokenExpired(token)) {
//                filterChain.doFilter(request, response);
//                return;
//            }
//
//            String username = jwtService.extractUsername(token);
//            SecurityContext securityContext = SecurityContextHolder.getContext();
//
//            if (StringUtils.hasText(username) && securityContext.getAuthentication() == null && !jwtService.isRefreshType(token)) {
//                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//                Authentication authentication = UsernamePasswordAuthenticationToken
//                        .authenticated(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
//                securityContext.setAuthentication(authentication);
//            }
//        } catch (JwtException e) {
//            ErrorDto errorDto = ErrorDto.builder()
//                    .statusCode(HttpStatus.BAD_REQUEST.value())
//                    .messages(List.of(e.getMessage()))
//                    .build();
//
//            response.setStatus(errorDto.getStatusCode());
//            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//            response.getWriter().write(objectMapper.writeValueAsString(errorDto));
//            return;
//        }

//------------------------------------------------------------------------------------------------------
        filterChain.doFilter(request, response);
    }
}