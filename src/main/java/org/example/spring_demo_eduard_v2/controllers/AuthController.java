package org.example.spring_demo_eduard_v2.controllers;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.spring_demo_eduard_v2.dto.*;
import org.example.spring_demo_eduard_v2.services.AuthenticationService;
import org.example.spring_demo_eduard_v2.services.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
//@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

//    private final AuthenticationManager authenticationManager;
//    private final UserDetailsService userDetailsService;
    private AuthenticationService authenticationService;
//    private final JwtService jwtService;

    //    MOVE TO SERVICES!!!!!
//    @PostMapping("/api/auth/signin")

//    public ResponseEntity<JwtResponse> signIn(@RequestBody SignInRequest signInRequest) {
//        Authentication authentication = UsernamePasswordAuthenticationToken
//                .unauthenticated(signInRequest.getUsername(), signInRequest.getPassword());
//        authenticationManager.authenticate(authentication);
//        UserDetails userDetails = userDetailsService.loadUserByUsername(signInRequest.getUsername());
//        String token = jwtService.generateToken(userDetails);
//        String refreshToken = jwtService.generateRefreshToken(userDetails);
//        return ResponseEntity.ok(new JwtResponse(token, refreshToken));
//    }
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest){
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }
    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refresh (@RequestBody RefreshRequest refreshRequest){
        return ResponseEntity.ok(authenticationService.refresh(refreshRequest));
    }



//    @PostMapping("/api/auth/refresh")
//    public ResponseEntity<JwtResponse> refresh(@RequestBody RefreshRequest refreshRequest) {
//        String refreshToken = refreshRequest.getRefreshToken();
//        if (jwtService.isTokenExpired(refreshToken)) {
//            return ResponseEntity.badRequest().build();
//        }
//
//        String username = jwtService.extractUsername(refreshToken);
//        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//        String accessToken = jwtService.generateToken(userDetails);
//        return ResponseEntity.ok(new JwtResponse(accessToken, refreshToken));
//    }

}
