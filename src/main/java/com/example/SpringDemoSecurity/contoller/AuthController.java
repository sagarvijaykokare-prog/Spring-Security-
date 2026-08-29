package com.example.SpringDemoSecurity.contoller;

import com.example.SpringDemoSecurity.dto.loginRequestDto;
import com.example.SpringDemoSecurity.dto.loginResponsesDto;
import com.example.SpringDemoSecurity.servies.JwtService;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/auth")
@RestController
public class AuthController {

    private AuthenticationManager authenticationManager ;
    private JwtService jwtService;
    private final ResourceLoader resourceLoader ;

    public AuthController(AuthenticationManager authenticationManager , JwtService jwtService , ResourceLoader resourceLoader){
        this.authenticationManager = authenticationManager ;
        this.jwtService = jwtService ;
        this.resourceLoader = resourceLoader ;
    }

    @PostMapping("/login")
    public loginResponsesDto login(@RequestBody loginRequestDto loginRequestDto){

        Authentication authentication_request =
                UsernamePasswordAuthenticationToken.unauthenticated(
                        loginRequestDto.getUsername(),
                        loginRequestDto.getPassword()
                );
        Authentication authentication =
                authenticationManager.authenticate(authentication_request);


        String token = jwtService.generateToken(authentication);

        return new loginResponsesDto(token) ;

    }
}
