package com.LocadoraFilmes;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Locadora {

     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private  String nome;
   

@ManyToOne
    @JoinColumn(name = "genero_id")
    private Genero genero;

     public Locadora() {
    }

    public Locadora(String nome  ) {
        this.nome = nome;
      
       
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
