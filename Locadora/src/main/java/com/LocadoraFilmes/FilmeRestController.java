package com.LocadoraFilmes;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/api/filmes")
public class FilmeRestController {

    private final LocadoraRepository locadoraRepository;
    private final GeneroRepository generoRepository;

    
    public FilmeRestController(LocadoraRepository locadoraRepository, GeneroRepository generoRepository) {
        this.locadoraRepository = locadoraRepository;
        this.generoRepository = generoRepository;
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

    Optional<Genero> generoOpt = Optional.empty();
    if (generoOpt.isEmpty()) {
        return ResponseEntity.badRequest().body("Erro: Gênero inválido.");
    }

    locadora.setGenero(generoOpt.get());

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
