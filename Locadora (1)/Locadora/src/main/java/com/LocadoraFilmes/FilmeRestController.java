package com.LocadoraFilmes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/filmes")
public class FilmeRestController {

    private final LocadoraRepository locadoraRepository;
    private final GeneroRepository generoRepository;

    @Autowired
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
    public ResponseEntity<Locadora> adicionarFilme(@RequestBody Locadora locadora) {
        if (locadora.getGenero() != null && locadora.getGenero().getNome() != null) {
            String nomeGenero = locadora.getGenero().getNome();
            Genero genero = generoRepository.findByNome(nomeGenero)
                    .orElseGet(() -> generoRepository.save(new Genero(nomeGenero)));
            locadora.setGenero(genero);
        }
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
}
