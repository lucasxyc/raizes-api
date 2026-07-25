package br.com.raizes.raizesapi.controller.auth;

import br.com.raizes.raizesapi.dto.auth.LoginRequest;
import br.com.raizes.raizesapi.dto.auth.LoginResponse;
import br.com.raizes.raizesapi.dto.auth.RegisterRequest;
import br.com.raizes.raizesapi.security.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterRequest request) {
        authenticationService.register(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        String token = authenticationService.login(request);
        return ResponseEntity.ok(new LoginResponse(token));
    }
}