package com.LocadoraFilmes.model;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "roles")
public class Role {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false, unique = true)
    private String nome;
   public Role() {
    }

    public Role(String nome) {
        this.nome = nome;
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
   @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;
        Role role = (Role) o;
        return Objects.equals(getNome(), role.getNome());
    }
 @Override
    public int hashCode() {
        return Objects.hash(getNome());
    }
  @Override
    public String toString() {
        return "Role{" +
               "id=" + id +
               ", nome='" + nome + '\'' +
               '}';
    }

}