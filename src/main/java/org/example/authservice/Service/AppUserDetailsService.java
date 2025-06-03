package org.example.authservice.Service;


import lombok.RequiredArgsConstructor;
import org.example.authservice.Entity.UserResponse;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private final UserServiceClient userServiceClient;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserResponse userResponse = userServiceClient.getUserByEmail(email);

        if (userResponse == null) {
            throw new UsernameNotFoundException("Usuário não encontrado: " + email);
        }

        return User.builder()
                .username(userResponse.getEmail())
                .password(userResponse.getPasswordHash())
                .authorities(List.of(new SimpleGrantedAuthority(userResponse.getRole())))
                .accountExpired(false)
                .accountLocked(userResponse.isLocked())
                .credentialsExpired(false)
                .disabled(!userResponse.isEnabled())
                .build();
    }
}



