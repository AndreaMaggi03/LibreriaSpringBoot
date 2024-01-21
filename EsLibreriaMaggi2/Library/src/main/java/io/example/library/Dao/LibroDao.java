package io.example.library.Dao;

import io.example.library.Model.Libro;
import org.springframework.data.repository.CrudRepository;

public interface LibroDao extends CrudRepository<Libro, Long> {
    Libro findById(long id);
    Libro findByTitolo(String titolo);
}
