package com.example.SpringDemoSecurity.servies;

import com.example.SpringDemoSecurity.dto.UserRequestDto;
import com.example.SpringDemoSecurity.dto.UserResponsesDto;
import com.example.SpringDemoSecurity.entity.Roles;
import com.example.SpringDemoSecurity.entity.UserEntity;
import com.example.SpringDemoSecurity.reposistory.RoleReposistory;
import com.example.SpringDemoSecurity.reposistory.UserReposistory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private UserReposistory userReposistory ;
    private PasswordEncoder passwordEncoder ;
    private RoleReposistory roleReposistory ;


    public  AuthService(UserReposistory userReposistory, PasswordEncoder passwordEncoder, RoleReposistory roleReposistory){
        this.userReposistory = userReposistory;
        this.passwordEncoder = passwordEncoder ;
        this.roleReposistory = roleReposistory ;
    }

    public UserResponsesDto rejstar(UserRequestDto userRequestDto) {

        UserEntity user = new UserEntity();
        user.setUsername(userRequestDto.getUsername());
        String password = passwordEncoder.encode(userRequestDto.getPassword());

        System.err.println("User name " + userRequestDto.getUsername());
        System.err.println("User Password : " + password);

        user.setPassword(password);
        user.setEnable(true);

          Roles  role = roleReposistory.findByName("USER_ROLE").get();
          user.getRoles().add(role);

          userReposistory.save(user);

          UserResponsesDto responsesDto = new UserResponsesDto();

          responsesDto.setUsername(user.getUsername());
          responsesDto.setMessage("User saved Successfully");


         return responsesDto ;

    }
}
