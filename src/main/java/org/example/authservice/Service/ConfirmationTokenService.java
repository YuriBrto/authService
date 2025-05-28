package org.example.authservice.Service;

import lombok.RequiredArgsConstructor;
import org.example.authservice.Entity.ConfirmationToken;
import org.example.authservice.Repository.ConfirmationTokenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }

    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    public void setConfirmedAt(String token) {
        confirmationTokenRepository.findByToken(token).ifPresent(t -> {
            t.setConfirmedAt(LocalDateTime.now());
            confirmationTokenRepository.save(t);
        });
    }
}
