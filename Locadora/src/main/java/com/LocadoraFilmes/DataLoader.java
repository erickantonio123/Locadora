package com.LocadoraFilmes;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final GeneroRepository generoRepository;
    private final RollsRepository rollsRepository;
    private final UsuarioRepository usuarioRepository;
    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    public DataLoader(GeneroRepository generoRepository,
                      RollsRepository rollsRepository,
                      UsuarioRepository usuarioRepository,
                      org.springframework.security.crypto.password.PasswordEncoder passwordEncoder) {
        this.generoRepository = generoRepository;
        this.rollsRepository = rollsRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // gêneros (mantive sua lógica)
        java.util.List<String> generos = java.util.List.of("Ação","Comédia","Drama","Terror","Romance","Suspense","Ficção");
        for (String nomeGenero : generos) {
            if (generoRepository.findByNome(nomeGenero).isEmpty()) {
                generoRepository.save(new Genero(nomeGenero));
            }
        }

        // Roles
        Role roleUser = rollsRepository.findByNome("ROLE_USER")
                .orElseGet(() -> rollsRepository.save(new Role("ROLE_USER")));
        Role roleAdmin = rollsRepository.findByNome("ROLE_ADMIN")
                .orElseGet(() -> rollsRepository.save(new Role("ROLE_ADMIN")));

        // Admin user
        if (usuarioRepository.findByUsername("admin").isEmpty()) {
    Usuario admin = new Usuario("admin", passwordEncoder.encode("123456"));
    admin.getRoles().add(roleAdmin);
    admin.getRoles().add(roleUser);
    usuarioRepository.save(admin);
    System.out.println("Admin criado com roles: " + admin.getRoles()); 
}

if (usuarioRepository.findByUsername("cliente").isEmpty()) {
        Usuario user = new Usuario("cliente", passwordEncoder.encode("123456"));
        user.getRoles().add(roleUser);
        usuarioRepository.save(user);
        System.out.println("✅ Usuário criado com role USER");
    }
    }


    
}
