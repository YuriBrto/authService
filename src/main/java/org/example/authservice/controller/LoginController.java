package org.example.authservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.authservice.Entity.DTO.AuthRequest;
import org.example.authservice.Entity.DTO.AuthResponse;
import org.example.authservice.Entity.UserResponse;
import org.example.authservice.Service.UserServiceClient;
import org.example.authservice.Security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class LoginController {
    private final JwtService jwtService;
    private final UserServiceClient userServiceClient;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest authRequest) {
        try {
            UserResponse user = userServiceClient.getUserByEmail(authRequest.getEmail());

            if (!user.isEnabled() || user.isLocked()) {
                return ResponseEntity.status(403).body("Usuário bloqueado ou desabilitado");
            }

            if (!passwordEncoder.matches(authRequest.getPassword(), user.getPasswordHash())) {
                return ResponseEntity.status(401).body("Senha inválida");
            }

            String jwt = jwtService.generateToken(user.getEmail());

            return ResponseEntity.ok(new AuthResponse(jwt));

        } catch (Exception e) {
            return ResponseEntity.status(404).body("Usuário não encontrado");
        }
    }
}
