package com.LocadoraFilmes;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
  @Repository
public interface PlataformaRepository  extends JpaRepository<Plataforma, Long> {
    Optional<Plataforma> findByNome(String nome);
}



   