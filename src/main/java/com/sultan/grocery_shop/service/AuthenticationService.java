package com.sultan.grocery_shop.service;

import com.sultan.grocery_shop.dto.AuthenticationRequestDTO;
import com.sultan.grocery_shop.dto.AuthenticationResponseDTO;
import com.sultan.grocery_shop.dto.RegisterRequestDTO;
import com.sultan.grocery_shop.model.User;
import com.sultan.grocery_shop.repository.UserRepository;
import com.sultan.grocery_shop.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponseDTO register(RegisterRequestDTO request) {
        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        userRepository.save(user);

        var jwtToken = jwtService.generateToken((UserDetails) user);
        return AuthenticationResponseDTO.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));

        var user = userRepository.findByUsername(request.getUsername());
        var jwtToken = jwtService.generateToken((UserDetails) user);

        return AuthenticationResponseDTO.builder()
                .token(jwtToken)
                .build();
    }
}