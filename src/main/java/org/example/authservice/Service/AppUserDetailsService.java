package org.example.authservice.Service;


import lombok.RequiredArgsConstructor;
import org.example.authservice.Entity.AppUser;
import org.example.authservice.Repository.AppUserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

        private final AppUserRepository userRepository;

        @Override
        public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
            AppUser appUser = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado através do email : " + email));


            return User.builder()
                    .username(appUser.getEmail())
                    .password(appUser.getPassword())
                    .authorities(appUser.getAuthorities())
                    .accountExpired(false)
                    .accountLocked(false)
                    .credentialsExpired(false)
                    .disabled(!appUser.isEnabled())
                    .build();
        }
    }


