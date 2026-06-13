package com.mrtkyr.classqroom.repository;

import com.mrtkyr.classqroom.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query(value = "from User where email = :email")
    Optional<User> findUserByEmail(String email);
}
