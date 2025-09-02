package com.LocadoraFilmes;




import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.validation.Valid;

import java.util.Optional;





@Controller
public class LocadoraController {

 private final LocadoraRepository locadoraRepository;
 private final GeneroRepository generoRepository;
private final LocadoraService locadoraService;    

 public LocadoraController(LocadoraRepository locadoraRepository,GeneroRepository generoRepository,LocadoraService locadoraService) {
    this.locadoraRepository = locadoraRepository;
    this.generoRepository = generoRepository;
    this.locadoraService = locadoraService;
}   

 @GetMapping("/")
    public String listar(Model memoria) {
Locadora novaLocadora = new Locadora();
   

         memoria.addAttribute("listaFilmes", locadoraRepository.findAll());
    memoria.addAttribute("locadorat", novaLocadora);
    memoria.addAttribute("listaGeneros", generoRepository.findAll());// objeto para preencher o formulário
        return "index";
    }


    @PostMapping("/adicionar")
public String adicionar(@Valid @ModelAttribute ("locadorat")Locadora locadorat, BindingResult result,Model memoria) {
  



String nomeNormalizado = (locadorat.getNome() != null)
            ? locadorat.getNome().trim().replaceAll("\\s+", " ")
            : "";


 if (nomeNormalizado.isEmpty()) {
        result.rejectValue("nome", "erro.nome", "O nome do filme é obrigatório.");
    } else {
            
Optional<Locadora> existente = locadoraRepository.findByNome(locadorat.getNome());
    if (existente.isPresent()) {
        result.rejectValue("nome", "erro.nome", "Já existe um filme com esse nome.");
    }
         }
if (result.hasErrors()) {
        memoria.addAttribute("listaFilmes", locadoraRepository.findAll());
         memoria.addAttribute("listaGeneros", generoRepository.findAll());
        return "index"; // retorna para a mesma tela com os erros
    }
     Long generoId = locadorat.getGenero().getId();
  String nomeGenero = locadorat.getGenero().getNome();
    // Verifica se o gênero já existe
  Genero generoCompleto = generoRepository.findById(locadorat.getGenero().getId())
            .orElseThrow(() -> new IllegalArgumentException("Gênero inválido"));

    // Define o gênero persistido no objeto locadorat
    locadorat.setGenero(generoCompleto);

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
    memoria.addAttribute("listaGeneros", generoRepository.findAll());
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

       Genero generoCompleto = generoRepository.findById(locadorat.getGenero().getId())
            .orElseThrow(() -> new IllegalArgumentException("Gênero inválido"));

        // Atualiza o gênero do filme
        filmeExistente.setGenero(generoCompleto);

        locadoraRepository.save(filmeExistente);
    }

    return "redirect:/";
}


@GetMapping("/buscarfilmes")
public String buscarFilmes(@RequestParam (defaultValue ="")String titulo,
@RequestParam (defaultValue = "0") int page, Model model) {
 
    Pageable pageable = PageRequest.of(page, 5, Sort.by("nome").ascending());
Page<Locadora> pagina = locadoraRepository.findByNomeContainingIgnoreCase(titulo, pageable);
 model.addAttribute("listaFilmes", pagina.getContent());
    model.addAttribute("paginaAtual", page);
    model.addAttribute("totalPaginas", pagina.getTotalPages());
    model.addAttribute("titulo", titulo);
    return "busca";
    
}








}
