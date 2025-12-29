package com.LocadoraFilmes.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;
import java.util.Objects;
@Entity
public class Plataforma {
    
 @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

     @Column(unique = true, nullable = false)
    private String nome;

 @OneToMany(mappedBy = "plataforma", cascade = CascadeType.ALL)
    private List<Locadora> filmes;


    public Plataforma() {}



    public Plataforma(String nome) {
        this.nome = nome;
    }
 public List<Locadora> getFilmes() {
        return filmes;
    }

      public void setFilmes(List<Locadora> filmes) {
        this.filmes = filmes;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

public void setId(Long id) {
        this.id = id;
    }

  

   


}
