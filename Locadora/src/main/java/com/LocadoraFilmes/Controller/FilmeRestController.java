package com.LocadoraFilmes.Controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.LocadoraFilmes.Repository.GeneroRepository;
import com.LocadoraFilmes.Repository.LocadoraRepository;
import com.LocadoraFilmes.Repository.PlataformaRepository;
import com.LocadoraFilmes.model.Genero;
import com.LocadoraFilmes.model.Locadora;
import com.LocadoraFilmes.model.Plataforma;

import jakarta.validation.Valid;
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/api/filmes")
public class FilmeRestController {

    private final LocadoraRepository locadoraRepository;
    private final GeneroRepository generoRepository;
  private final PlataformaRepository plataformaRepository;
    
    public FilmeRestController(LocadoraRepository locadoraRepository, GeneroRepository generoRepository,
     PlataformaRepository plataformaRepository) {
        this.locadoraRepository = locadoraRepository;
        this.generoRepository = generoRepository;
        this.plataformaRepository = plataformaRepository;
    }

    // GET: Lista todos os filmes
    @GetMapping
    public List<Locadora> listarFilmes() {
        return locadoraRepository.findAll();
    }

    // POST: Adiciona novo filme
    @PostMapping
public ResponseEntity<?> adicionarFilme(@Valid @RequestBody Locadora locadora) {
    Optional<Locadora> existente = locadoraRepository.findByNomeIgnoreCase(locadora.getNome().trim());
    if (existente.isPresent()) {
        return ResponseEntity.badRequest()
            .body("Erro: Já existe um filme cadastrado com o nome \"" + locadora.getNome() + "\".");
    }

    if (locadora.getGenero() == null || locadora.getGenero().getId() == null) {
        return ResponseEntity.badRequest().body("Erro: Gênero deve ser informado.");
    }
  


    Optional<Genero> generoOpt = generoRepository.findById(locadora.getGenero().getId());
if (generoOpt.isEmpty()) {
    return ResponseEntity.badRequest().body("Erro: Gênero inválido.");
}
locadora.setGenero(generoOpt.get());

    if (locadora.getPlataforma() == null || locadora.getPlataforma().getId() == null) {
        return ResponseEntity.badRequest().body("Erro: Plataforma deve ser informada.");
    }
    Optional<Plataforma> plataformaOpt = plataformaRepository.findById(locadora.getPlataforma().getId());
if (plataformaOpt.isEmpty()) {
    return ResponseEntity.badRequest().body("Erro: Plataforma inválida.");

}locadora.setPlataforma(plataformaOpt.get());
    Locadora salvo = locadoraRepository.save(locadora);
    return ResponseEntity.ok(salvo);
}


    // DELETE: Exclui filme por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirFilme(@PathVariable Long id) {
        if (!locadoraRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        locadoraRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // PUT: Atualiza um filme por ID
    @PutMapping("/{id}")
    public ResponseEntity<Locadora> atualizarFilme(@PathVariable Long id, @RequestBody Locadora locadora) {
        return locadoraRepository.findById(id)
                .map(filmeExistente -> {
                    filmeExistente.setNome(locadora.getNome());

                    if (locadora.getGenero() != null && locadora.getGenero().getNome() != null) {
                        Genero genero = generoRepository.findByNome(locadora.getGenero().getNome())
                                .orElseGet(() -> generoRepository.save(new Genero(locadora.getGenero().getNome())));
                        filmeExistente.setGenero(genero);
                    }

      if (locadora.getPlataforma() != null && locadora.getPlataforma().getNome() != null) {
                        Plataforma plataforma = plataformaRepository.findByNome(locadora.getPlataforma().getNome())
                                .orElseGet(() -> plataformaRepository.save(new Plataforma(locadora.getPlataforma().getNome())));
                        filmeExistente.setPlataforma(plataforma);
                    }

                    Locadora atualizado = locadoraRepository.save(filmeExistente);
                    return ResponseEntity.ok(atualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

   @GetMapping("/buscar")
    public List<Locadora> buscarPorNome(@RequestParam String nome) {
        return locadoraRepository.findByNomeContainingIgnoreCase(nome);
    }


        
}
