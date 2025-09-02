
    package com.LocadoraFilmes;

    import java.util.Optional;


    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;
    

    @Repository
    public interface GeneroRepository extends JpaRepository<Genero, Long> {
        Optional<Genero> findByNome(String nome);
    }