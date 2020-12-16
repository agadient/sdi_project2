package com.example.demo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Map;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query(value =  "SELECT user_id, password_hash, salt from users u WHERE u.username = ?1", nativeQuery = true)
    Iterable<Map<String, Object>> findAuthByUsername(String username);
}