    package com.LocadoraFilmes.model;

    import jakarta.persistence.Entity;
    import jakarta.persistence.GeneratedValue;
    import jakarta.persistence.GenerationType;
    import jakarta.persistence.Id;
    import jakarta.persistence.JoinColumn;
    import jakarta.persistence.ManyToOne;
    import jakarta.validation.constraints.NotBlank;
    import jakarta.validation.constraints.Size;

    @Entity
    public class Locadora {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

            
        @NotBlank(message = "O nome do filme é obrigatório")
        @Size(min=2, max =100, message = "o nome  deve ter entre 2 ou 20 letras")
        private  String nome;
    

    @ManyToOne
        @JoinColumn(name = "genero_id")
        private Genero genero;

@ManyToOne
@JoinColumn(name = "plataforma_id")
private Plataforma plataforma;


        public Locadora() {
        }

        public Locadora(String nome  ) {
            this.nome = nome;
        
        
        }
public Locadora(String nome, Genero genero, Plataforma plataforma) {
    this.nome = nome;
    this.genero = genero;
    this.plataforma = plataforma;
}

public Plataforma getPlataforma() {
    return plataforma;
}

public void setPlataforma(Plataforma plataforma) {
    this.plataforma = plataforma;
}

    
        public String getNome() {
            return nome;
        }

    
        public void setNome(String nome) {
            this.nome = nome;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Genero getGenero() {
            return genero;
        }

        public void setGenero(Genero genero) {
            this.genero = genero;
        }

        
    


    }
