package io.example.library.Dao;

import io.example.library.Model.UserLibro;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UtenteLibroDao extends CrudRepository<UserLibro, Long> {
    UserLibro findById(long id);
    List<UserLibro> findByUserId(long id);
}
