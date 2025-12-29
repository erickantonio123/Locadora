package com.LocadoraFilmes.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;

@Entity
public class Genero{

 @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nome;


  @OneToMany(mappedBy = "genero", cascade = CascadeType.ALL)
    private List<Locadora> filmes;

     public Genero() {
    }

    public Genero(String nome) {
    this.nome = nome;
}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
       public List<Locadora> getFilmes() {
        return filmes;
    }
 public void setFilmes(List<Locadora> filmes) {
        this.filmes = filmes;
    }



}