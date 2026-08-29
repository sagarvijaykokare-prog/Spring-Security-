package com.example.SpringDemoSecurity.contoller;

import com.example.SpringDemoSecurity.dto.UserRequestDto;
import com.example.SpringDemoSecurity.dto.UserResponsesDto;
import com.example.SpringDemoSecurity.servies.AuthService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/user")
@RestController
public class UserController {

    private AuthService authService ;

    public UserController(AuthService authService){
        this.authService = authService ;
    }

    @GetMapping("/mess")
    public String message(){

        return "This is Spring Security Implmentation " ;
    }


    @PostMapping("/rejstar")
    public UserResponsesDto rejstar(@RequestBody UserRequestDto userRequestDto){

        System.out.println(userRequestDto.getUsername() + userRequestDto.getPassword());
        UserResponsesDto res_dto = authService.rejstar(userRequestDto);
        return res_dto ;
    }

}
