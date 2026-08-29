package com.example.SpringDemoSecurity.reposistory;

import com.example.SpringDemoSecurity.entity.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserReposistory extends JpaRepository<UserEntity , Long> {

    @EntityGraph(attributePaths = "roles")
    Optional<UserEntity> findByUsername(String name) ;
}
