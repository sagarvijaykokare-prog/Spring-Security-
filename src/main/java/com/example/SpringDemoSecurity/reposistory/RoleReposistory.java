package com.example.SpringDemoSecurity.reposistory;

import com.example.SpringDemoSecurity.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleReposistory extends JpaRepository<Roles , Long> {

    Optional<Roles> findByName(String name) ;
}
