
package com.LocadoraFilmes.Controller;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.LocadoraFilmes.Repository.RollsRepository;
import com.LocadoraFilmes.Repository.UsuarioRepository;
import com.LocadoraFilmes.Security.JwtUtil;
import com.LocadoraFilmes.dto.LoginRequest;
import com.LocadoraFilmes.dto.RegisterRequest;
import com.LocadoraFilmes.model.Usuario;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.core.AuthenticationException;

@RestController
@RequestMapping("/Top")
public class TopController{

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RollsRepository rollsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

   @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            Usuario usuario = (Usuario) authentication.getPrincipal();
            return jwtUtil.generateToken(usuario);
        } catch (AuthenticationException e) {
            throw new RuntimeException("Credenciais inválidas.");
        }
    }

  @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        if (usuarioRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Usuário já existe.");
        }
        Usuario usuario = new Usuario(request.getUsername(), passwordEncoder.encode(request.getPassword()));
        usuario.getRoles().add(rollsRepository.findByNome("ROLE_USER").orElseThrow(() -> new RuntimeException("Role não encontrada.")));
        usuarioRepository.save(usuario);
        return "Usuário cadastrado com sucesso.";
    }
}





