package br.com.raizes.raizesapi.controller.auth;

import br.com.raizes.raizesapi.dto.auth.LoginRequest;
import br.com.raizes.raizesapi.dto.auth.LoginResponse;
import br.com.raizes.raizesapi.dto.auth.RegisterRequest;
import br.com.raizes.raizesapi.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor // Injeta o service pelo construtor automaticamente de forma limpa e segura
public class AuthController {

    private final AuthService authenticationService;

    // POST /auth/register -> Realiza o cadastro de um novo usuário no sistema
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterRequest request) {
        authenticationService.register(request);
        return ResponseEntity.ok().build();
    }

    // POST /auth/login -> Autentica o usuário e retorna o token Bearer JWT (Fluxo Crítico 1 do MVP)
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        String token = authenticationService.login(request);
        return ResponseEntity.ok(new LoginResponse(token));
    }
}
