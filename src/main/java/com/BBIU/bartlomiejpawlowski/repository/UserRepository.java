package com.BBIU.bartlomiejpawlowski.repository;

import com.BBIU.bartlomiejpawlowski.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT p FROM User p WHERE " +
            "p.name LIKE CONCAT('%',:query, '%')" +
            "Or p.username LIKE CONCAT('%', :query, '%')")
    List<User> searchUser(String query);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
}
