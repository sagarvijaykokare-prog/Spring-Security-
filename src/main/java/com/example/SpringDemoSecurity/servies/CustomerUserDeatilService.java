package com.example.SpringDemoSecurity.servies;

import com.example.SpringDemoSecurity.entity.CustomerUserDeatil;
import com.example.SpringDemoSecurity.entity.UserEntity;
import com.example.SpringDemoSecurity.reposistory.UserReposistory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomerUserDeatilService implements UserDetailsService {

    private UserReposistory userReposistory ;

    public CustomerUserDeatilService(UserReposistory userReposistory){
        this.userReposistory = userReposistory ;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity user = userReposistory.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User is not found "));

        return new CustomerUserDeatil(user);
    }
}
