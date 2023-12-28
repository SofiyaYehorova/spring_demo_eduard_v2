package org.example.spring_demo_eduard_v2.services;

import lombok.AllArgsConstructor;
import org.example.spring_demo_eduard_v2.dto.AuthenticationRequest;
import org.example.spring_demo_eduard_v2.dto.AuthenticationResponse;
import org.example.spring_demo_eduard_v2.dto.RefreshRequest;
import org.example.spring_demo_eduard_v2.dto.RegisterRequest;
import org.example.spring_demo_eduard_v2.entity.Customer;
import org.example.spring_demo_eduard_v2.entity.Role;
import org.example.spring_demo_eduard_v2.repository.CustomerRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private PasswordEncoder passwordEncoder;
    private CustomerRepository customerRepository;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        Customer customer = Customer.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.BUYER)
                .build();

        String jwtToken = jwtService.generateToken(customer);

        String refreshToken = jwtService.generateRefreshToken(customer);
        customer.setRefreshToken(refreshToken);

        customerRepository.save(customer);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );
        Customer customer = customerRepository
                .findCustomerByEmail(authenticationRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("no such user"));

        String token = jwtService.generateToken(customer);
        String refreshToken = jwtService.generateRefreshToken(customer);
        customer.setRefreshToken(refreshToken);
        customerRepository.save(customer);

        return AuthenticationResponse
                .builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse refresh(RefreshRequest refreshRequest) {
        String token = refreshRequest.getRefreshToken();
        String username = jwtService.extractUsername(token);
        Customer customer = customerRepository.findCustomerByEmail(username).orElseThrow(() -> new UsernameNotFoundException("not found user"));

        String newAccessToken=null;
        String newRefreshToken=null;

        if(customer
                .getRefreshToken()
                .equals(token)){
            newAccessToken= jwtService.generateToken(customer);
            newRefreshToken= jwtService.generateRefreshToken(customer);

            customer.setRefreshToken(newRefreshToken);

            customerRepository.save(customer);
        }
        return AuthenticationResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }
}
