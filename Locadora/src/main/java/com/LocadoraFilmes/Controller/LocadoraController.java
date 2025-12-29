package com.LocadoraFilmes.Controller;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.LocadoraFilmes.Repository.GeneroRepository;
import com.LocadoraFilmes.Repository.LocadoraRepository;
import com.LocadoraFilmes.Repository.PlataformaRepository;
import com.LocadoraFilmes.Service.LocadoraService;
import com.LocadoraFilmes.model.Genero;
import com.LocadoraFilmes.model.Locadora;
import com.LocadoraFilmes.model.Plataforma;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@Controller
public class LocadoraController {

    private final LocadoraRepository locadoraRepository;
    private final GeneroRepository generoRepository;
    private final LocadoraService locadoraService;
    private final PlataformaRepository plataformaRepository;

    public LocadoraController(LocadoraRepository locadoraRepository, GeneroRepository generoRepository,
            LocadoraService locadoraService, PlataformaRepository  plataformaRepository) {
        this.locadoraRepository = locadoraRepository;
        this.generoRepository = generoRepository;
        this.locadoraService = locadoraService;
        this.plataformaRepository = plataformaRepository;
    }

    @GetMapping("/")
    public String listar(Model memoria) {
        Locadora novaLocadora = new Locadora();
   
        memoria.addAttribute("listaFilmes", locadoraRepository.findAll());
        memoria.addAttribute("locadorat", novaLocadora);
        memoria.addAttribute("listaGeneros", generoRepository.findAll());
        memoria.addAttribute("listaPlataformas", plataformaRepository.findAll());// objeto para preencher o formulário
        return "index";
    }
@PostMapping("/adicionar")
public String adicionar(@Valid @ModelAttribute("locadorat") Locadora locadorat, BindingResult result,
        Model memoria) {    

  // ✅ DEBUG: Log do que está chegando
    System.out.println("Tentando salvar filme:");
    System.out.println("Nome: " + locadorat.getNome());
    System.out.println("Plataforma ID: " + 
        (locadorat.getPlataforma() != null ? locadorat.getPlataforma().getId() : "null"));
    
    // ✅ Verificar TODAS as plataformas disponíveis
    System.out.println("Plataformas no banco:");
    plataformaRepository.findAll().forEach(p -> 
        System.out.println("ID: " + p.getId() + " - " + p.getNome())
    );




    // Normalizar nome
    String nomeNormalizado = (locadorat.getNome() != null)
            ? locadorat.getNome().trim().replaceAll("\\s+", " ")
            : "";

    // Validação do nome
    if (nomeNormalizado.isEmpty()) {
        result.rejectValue("nome", "erro.nome", "O nome do filme é obrigatório.");
    } else {
        // 🔥 CORREÇÃO: Usar nome normalizado na busca
        Optional<Locadora> existente = locadoraRepository.findByNomeIgnoreCase(nomeNormalizado);
        if (existente.isPresent()) {
            result.rejectValue("nome", "erro.nome", "Já existe um filme com esse nome.");
        }
        locadorat.setNome(nomeNormalizado);
    }

    // 🔥 CORREÇÃO: Validação completa do gênero
    if (locadorat.getGenero() == null || locadorat.getGenero().getId() == null) {
        result.rejectValue("genero", "erro.genero", "Selecione um gênero");
    } else {
        // Verificar se o gênero existe no banco
        Optional<Genero> generoOpt = generoRepository.findById(locadorat.getGenero().getId());
        if (generoOpt.isEmpty()) {
            result.rejectValue("genero", "erro.genero", "Gênero selecionado é inválido");
        }
    }

    // 🔥 CORREÇÃO: Validação completa da plataforma
    if (locadorat.getPlataforma() != null && locadorat.getPlataforma().getId() != null) {
        Optional<Plataforma> plataformaOpt = plataformaRepository.findById(locadorat.getPlataforma().getId());
        if (plataformaOpt.isPresent()) {
            // 🔥 IMPORTANTE: Usar a entidade carregada do banco, não a que veio do formulário
            locadorat.setPlataforma(plataformaOpt.get());
        } else {
            result.rejectValue("plataforma", "erro.plataforma", "Plataforma selecionada é inválida");
        }
    } else {
        result.rejectValue("plataforma", "erro.plataforma", "Selecione uma plataforma");
    }

    // Se houver erros, retorna para a página com mensagens
    if (result.hasErrors()) {
        memoria.addAttribute("listaFilmes", locadoraRepository.findAll());
        memoria.addAttribute("listaGeneros", generoRepository.findAll());
        memoria.addAttribute("listaPlataformas", plataformaRepository.findAll());
        return "index";
    }

    try {
        // 🔥 CORREÇÃO: Carregar entidades completas do banco
        Genero generoCompleto = generoRepository.findById(locadorat.getGenero().getId())
                .orElseThrow(() -> new IllegalArgumentException("Gênero inválido"));
        
        Plataforma plataformaCompleta = plataformaRepository.findById(locadorat.getPlataforma().getId())
                .orElseThrow(() -> new IllegalArgumentException("Plataforma inválida"));

        // Definir as entidades carregadas
        locadorat.setGenero(generoCompleto);
        locadorat.setPlataforma(plataformaCompleta);

        // Salvar no banco
        locadoraRepository.save(locadorat);
        
    } catch (Exception e) {
        // Em caso de erro, adiciona mensagem e retorna
        result.reject("erro.salvar", "Erro ao salvar filme: " + e.getMessage());
        memoria.addAttribute("listaFilmes", locadoraRepository.findAll());
        memoria.addAttribute("listaGeneros", generoRepository.findAll());
        memoria.addAttribute("listaPlataformas", plataformaRepository.findAll());
        return "index";
    }

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
if(locadora.getPlataforma() == null) {
    locadora.setPlataforma(new Plataforma());
}

        memoria.addAttribute("locadorat", locadora);
        memoria.addAttribute("listaFilmes", locadoraRepository.findAll());
        memoria.addAttribute("listaGeneros", generoRepository.findAll());
        memoria.addAttribute("listaPlataformas", plataformaRepository.findAll());
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


        Plataforma plat = plataformaRepository.findById(locadorat.getPlataforma().getId())
    .orElseThrow(() -> new IllegalArgumentException("Plataforma inválida"));


            // Atualiza o gênero do filme

            filmeExistente.setGenero(generoCompleto);
            filmeExistente.setPlataforma(plat);
            locadoraRepository.save(filmeExistente);
        }
        return "redirect:/";
    }

    @GetMapping("/buscarfilmes")
    public String buscarFilmes(@RequestParam(defaultValue = "") String titulo,
            @RequestParam(defaultValue = "0") int page, Model model) {

        Pageable pageable = PageRequest.of(page, 5, Sort.by("nome").ascending());
        Page<Locadora> pagina = locadoraRepository.findByNomeContainingIgnoreCase(titulo, pageable);
        model.addAttribute("listaFilmes", pagina.getContent());
        model.addAttribute("paginaAtual", page);
        model.addAttribute("totalPaginas", pagina.getTotalPages());
        model.addAttribute("titulo", titulo);
        return "busca";

    }



}
