package com.LocadoraFilmes;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.LocadoraFilmes.Repository.GeneroRepository;
import com.LocadoraFilmes.Repository.LocadoraRepository;
import com.LocadoraFilmes.Repository.PlataformaRepository;
import com.LocadoraFilmes.Repository.RollsRepository;
import com.LocadoraFilmes.Repository.UsuarioRepository;
import com.LocadoraFilmes.model.Genero;
import com.LocadoraFilmes.model.Plataforma;
import com.LocadoraFilmes.model.Role;
import com.LocadoraFilmes.model.Usuario;

@Component
public class DataLoader implements CommandLineRunner {

    private final GeneroRepository generoRepository;
    private final PlataformaRepository plataformaRepository;
    private final RollsRepository rollsRepository;
    private final UsuarioRepository usuarioRepository;
    private final LocadoraRepository locadoraRepository;
    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    public DataLoader(GeneroRepository generoRepository,
                      RollsRepository rollsRepository,
                      UsuarioRepository usuarioRepository,
                     LocadoraRepository locadoraRepository,
                     PlataformaRepository plataformaRepository,
                      org.springframework.security.crypto.password.PasswordEncoder passwordEncoder) {
        this.generoRepository = generoRepository;
        this.rollsRepository = rollsRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.locadoraRepository = locadoraRepository;
        this.plataformaRepository = plataformaRepository;
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

   java.util.List<String> plataformas = java.util.List.of("Disney+", "Netflix", "Amazon Prime", "HBO Max", "Star plus", "Apple TV", "Paramount+", "Globoplay");
for (String nomePlataforma : plataformas) {
        if (plataformaRepository.findByNome(nomePlataforma).isEmpty()) {
            Plataforma plataforma = new Plataforma(nomePlataforma);
            plataformaRepository.save(plataforma);
            System.out.println("✅ Plataforma criada: " + nomePlataforma);
        }
    }
        /* if (plataformaRepository.findByNome(nomePlataforma).isEmpty()) {
            Plataforma plataforma = new Plataforma(nomePlataforma);
            plataformaRepository.save(plataforma);
            System.out.println("✅ Plataforma criada: " + nomePlataforma);
        }
        */
    

System.out.println("=== PLATAFORMAS NO BANCO ===");
plataformaRepository.findAll().forEach(plat -> {
    System.out.println("ID: " + plat.getId() + " | Nome: " + plat.getNome());
});


// Salva somente se não tiver nenhum filme cadastrado

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
