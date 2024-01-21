package io.example.library.Dao;

import io.example.library.Model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UtenteDao extends CrudRepository<User, Long> {
    User findById(long id);

    @Query("select s from User s where username= :username and password= :password")
    public User login(String username, String password);

}
