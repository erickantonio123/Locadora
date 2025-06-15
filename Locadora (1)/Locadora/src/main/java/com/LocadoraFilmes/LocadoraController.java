package com.LocadoraFilmes;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;







@Controller
public class LocadoraController {

 private final LocadoraRepository locadoraRepository;


 private final GeneroRepository generoRepository;
    

 public LocadoraController(LocadoraRepository locadoraRepository,GeneroRepository generoRepository) {
    this.locadoraRepository = locadoraRepository;
    this.generoRepository = generoRepository;
}   

 @GetMapping("/")
    public String listar(Model memoria) {
Locadora novaLocadora = new Locadora();
    novaLocadora.setGenero(new Genero());

        memoria.addAttribute("listaFilmes", locadoraRepository.findAll());
        memoria.addAttribute("locadorat", novaLocadora); // objeto para preencher o formulário
        return "index";
    }


    @PostMapping("/adicionar")
public String adicionar(@ModelAttribute Locadora locadorat) {
    String nomeGenero = locadorat.getGenero().getNome();

    // Verifica se o gênero já existe
    Genero generoExistente = generoRepository.findByNome(nomeGenero)
        .orElseGet(() -> generoRepository.save(new Genero(nomeGenero)));

    // Define o gênero persistido no objeto locadorat
    locadorat.setGenero(generoExistente);

    locadoraRepository.save(locadorat);
    return "redirect:/";
}

@GetMapping("/excluir/{id}")
public String excluir(@PathVariable Long id) {
    locadoraRepository.deleteById(id);
    return "redirect:/";
}

@GetMapping("/preparaalterar")
public String prepararalterar(@RequestParam Long id, Model memoria) {
    Locadora locadora = locadoraRepository.findById(id).orElse(new Locadora());

    // Garante que o gênero nunca será nulo (evita erro no Thymeleaf)
    if (locadora.getGenero() == null) {
        locadora.setGenero(new Genero());
    }

    memoria.addAttribute("locadorat", locadora);
    memoria.addAttribute("listaFilmes", locadoraRepository.findAll());

    return "index";
}


@PostMapping("/alterar")
public String alterar(@RequestParam Long id, @ModelAttribute Locadora locadorat) {
    var filmeOptional = locadoraRepository.findById(id);
    if (filmeOptional.isPresent()) {
        Locadora filmeExistente = filmeOptional.get();

        // Atualiza o nome do filme
        filmeExistente.setNome(locadorat.getNome());

        // Busca o gênero no banco de dados para evitar duplicação
        String nomeGenero = locadorat.getGenero().getNome();
        Genero generoExistente = generoRepository.findByNome(nomeGenero)
            .orElseGet(() -> new Genero(locadorat.getGenero().getNome()));

        // Atualiza o gênero do filme
        filmeExistente.setGenero(generoExistente);

        locadoraRepository.save(filmeExistente);
    }

    return "redirect:/";
}







}
