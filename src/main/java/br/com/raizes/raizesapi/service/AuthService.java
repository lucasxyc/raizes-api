package br.com.raizes.raizesapi.service;

import br.com.raizes.raizesapi.dto.auth.LoginRequest;
import br.com.raizes.raizesapi.dto.auth.RegisterRequest;
import br.com.raizes.raizesapi.entity.Usuario;
import br.com.raizes.raizesapi.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    public void register(RegisterRequest data) {
        if (repository.findByEmail(data.email()).isPresent()) {
            throw new RuntimeException("E-mail já cadastrado!");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(data.nome());
        usuario.setEmail(data.email());
        usuario.setSenha(passwordEncoder.encode(data.senha()));
        usuario.setRole(data.role());

        repository.save(usuario);
    }

    public String login(LoginRequest data) {

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
        var auth = authenticationManager.authenticate(usernamePassword);

        return jwtService.generateToken((Usuario) auth.getPrincipal());
    }
}