package br.com.raizes.raizesapi.controller;

import br.com.raizes.raizesapi.dto.auth.LoginRequest;
import br.com.raizes.raizesapi.dto.auth.LoginResponse;
import br.com.raizes.raizesapi.dto.auth.RegisterRequest;
import br.com.raizes.raizesapi.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Endpoints para registro de usuários e geração de tokens JWT")
public class AuthController {

    private final AuthService authenticationService;

    // POST /auth/register -> Realiza o cadastro de um novo usuário no sistema
    @PostMapping("/register")
    @Operation(summary = "Registrar usuário", description = "Cria uma nova credencial de usuário com senha criptografada em BCrypt")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequest request) {
        authenticationService.register(request);
        return ResponseEntity.ok().build();
    }

    // POST /auth/login -> Autentica o usuário e retorna o token Bearer JWT (Fluxo Crítico 1 do MVP)
    @PostMapping("/login")
    @Operation(summary = "Efetuar login", description = "Autentica o usuário e gera o Bearer Token JWT necessário para acessar as rotas protegidas")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        String token = authenticationService.login(request);
        return ResponseEntity.ok(new LoginResponse(token));
    }
}
