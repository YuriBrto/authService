package org.example.authservice.Entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserResponse {
    private String email;
    private String passwordHash;
    private String role;
    private boolean enabled;
    private boolean locked;
}
