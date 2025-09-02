package com.LocadoraFilmes;
import org.springframework.stereotype.Service;


@Service
public class LocadoraService {

private final LocadoraRepository locadoraRepository;
private final GeneroRepository generoRepository;

 public LocadoraService(LocadoraRepository locadoraRepository, GeneroRepository generoRepository) {
        this.locadoraRepository = locadoraRepository;
        this.generoRepository = generoRepository;
    }

       public boolean nomeFilmeExiste(String nome) {
        return locadoraRepository.findByNome(nome).isPresent();
    }

    public Locadora salvarLocadora(Locadora locadora) {
        String nomeGenero = locadora.getGenero().getNome();


        Genero generoExistente = generoRepository.findByNome(nomeGenero)
            .orElseGet(() -> generoRepository.save(new Genero(nomeGenero)));
             locadora.setGenero(generoExistente);

              return locadoraRepository.save(locadora);
}


}