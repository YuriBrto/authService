package org.example.authservice.Service;

import org.example.authservice.Entity.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service", url = "http://localhost:8081")
public interface UserServiceClient {
    @GetMapping("/api/v1/users/by-email")
    UserResponse getUserByEmail(@RequestParam String email);
}
