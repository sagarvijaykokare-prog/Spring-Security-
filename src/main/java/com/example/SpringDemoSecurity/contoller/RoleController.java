package com.example.SpringDemoSecurity.contoller;

import com.example.SpringDemoSecurity.entity.Roles;
import com.example.SpringDemoSecurity.servies.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/role")
@RestController
public class RoleController {

    private RoleService roleService ;

    public RoleController(RoleService roleService){
        this.roleService = roleService ;
    }

    @PostMapping("/Role")
    public ResponseEntity<String> addRoles(@RequestBody Roles roles){
        roleService.addRoles(roles);

        return ResponseEntity.ok("Role Saved ");
    }
}
