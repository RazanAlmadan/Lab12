package com.example.lab12.Controller;

import com.example.lab12.Api.ApiResponse;
import com.example.lab12.Model.User;
import com.example.lab12.Service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid User user){
        authService.register(user);
        return ResponseEntity.status(200).body(new ApiResponse("user registered"));
    }

    @PutMapping("/update/username")
    public ResponseEntity<?> updateUsername(@AuthenticationPrincipal User auth, @RequestBody @Valid User user) {
        authService.updateUsername(auth.getId(), user);
        return ResponseEntity.status(200).body(new ApiResponse("Username updated"));
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal User admin, @PathVariable Integer userId){
        authService.deleteUser(admin.getId(), userId);
        return ResponseEntity.status(200).body(new ApiResponse("user deleted"));

    }

    @GetMapping("/login")
    public ResponseEntity<?> login(){
        return ResponseEntity.status(200).body(new ApiResponse("login"));
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(){
        return ResponseEntity.status(200).body(new ApiResponse("logout"));
    }


}
