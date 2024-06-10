package org.example.api_security_jwt.security.principals;

import org.example.api_security_jwt.model.entity.Role;
import org.example.api_security_jwt.model.entity.User;
import org.example.api_security_jwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username).orElseThrow(() -> new NoSuchElementException("username khong ton tai"));
        return CustomerUserDetail.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .fullName(user.getFullName())
                .address(user.getAddress())
                .email(user.getEmail())
                .status(user.getStatus())
                .authorities(mapToAuthorities(user.getRoles()))
                .build();
    }

    private List<? extends GrantedAuthority> mapToAuthorities(List<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).toList();
    }
}
