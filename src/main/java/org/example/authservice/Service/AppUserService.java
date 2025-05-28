package org.example.authservice.Service;

import lombok.RequiredArgsConstructor;
import org.example.authservice.Entity.AppUser;
import org.example.authservice.Entity.AppUserRole;
import org.example.authservice.Repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public String signUpUser(AppUser appUser) {
        boolean userExists = appUserRepository.existsByEmail(appUser.getEmail());

        if (userExists) {
            throw new IllegalStateException("Email already taken");
        }

        // Codifica a senha só uma vez
        String encodedPassword = passwordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);

        appUserRepository.save(appUser);

        // Aqui deveria gerar e enviar token de confirmação
        return "User registered successfully";
    }

    public void enableAppUser(String email) {
        appUserRepository.findByEmail(email).ifPresent(u -> {
            u.setEnabled(true);
            appUserRepository.save(u);
        });
    }
}


