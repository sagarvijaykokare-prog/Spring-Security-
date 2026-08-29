package com.example.SpringDemoSecurity.servies;

import com.example.SpringDemoSecurity.entity.Roles;
import com.example.SpringDemoSecurity.reposistory.RoleReposistory;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private RoleReposistory roleReposistory ;

    public  RoleService(RoleReposistory roleReposistory){
        this.roleReposistory = roleReposistory ;
    }

    public void addRoles(Roles roles) {
        roleReposistory.save(roles);
    }
}
