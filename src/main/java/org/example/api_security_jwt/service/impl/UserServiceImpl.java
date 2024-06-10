package org.example.api_security_jwt.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.api_security_jwt.model.dto.request.DtoFormLogin;
import org.example.api_security_jwt.model.dto.request.DtoFormRegister;
import org.example.api_security_jwt.model.dto.response.JWTResponse;
import org.example.api_security_jwt.model.entity.Role;
import org.example.api_security_jwt.model.entity.User;
import org.example.api_security_jwt.repository.RoleRepository;
import org.example.api_security_jwt.repository.UserRepository;
import org.example.api_security_jwt.security.jwt.JWTProvider;
import org.example.api_security_jwt.security.principals.CustomerUserDetail;
import org.example.api_security_jwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTProvider jwtProvider;

    @Override
    public boolean register(DtoFormRegister formRegister) {
        User user = User.builder()
                .username(formRegister.getUsername())
                .password(passwordEncoder.encode(formRegister.getPassword()))
                .fullName(formRegister.getFullName())
                .address(formRegister.getAddress())
                .email(formRegister.getEmail())
                .status(true)
                .build();
        List<Role> roles = new ArrayList<>();
        if(formRegister.getRoles()!=null && !formRegister.getRoles().isEmpty()){
            formRegister.getRoles().forEach(role -> {
                switch (role){
                    case "ROLE_ADMIN":
                        roles.add(roleRepository.findRoleByRoleName(role).orElseThrow(()->new NoSuchElementException("role admin khong ton tai")));
                        break;
                    case "ROLE_USER":
                        roles.add(roleRepository.findRoleByRoleName(role).orElseThrow(()->new NoSuchElementException("role user khong ton tai")));
                        break;
                    case "ROLE_MODERATOR":
                        roles.add(roleRepository.findRoleByRoleName(role).orElseThrow(()->new NoSuchElementException("role moderator khong ton tai")));
                        break;
                }
            });
        }else{
            roles.add(roleRepository.findRoleByRoleName("ROLE_USER").orElseThrow(()->new NoSuchElementException("role user khong ton tai")));
        }
        user.setRoles(roles);
        userRepository.save(user);
        return true;
    }

    @Override
    public JWTResponse login(DtoFormLogin formLogin) {
        Authentication authentication = null;
        try {
            //check thông tin đăng nhập gồm username và password có đúng hay không?
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(formLogin.getUsername(),formLogin.getPassword()));
        }catch (AuthenticationException e){
            log.error("Username or password not valid!");
        }
        //Lấy thông tin tài khoản đã đăng nhập lưu thông authentication
        CustomerUserDetail userDetail = (CustomerUserDetail) authentication.getPrincipal();

        //Tạo chuỗi token từ thông tin tài khoản
        String token = jwtProvider.generateToken(userDetail);

        //Trả về cho người dùng dữ liệu khi login xong (bao gồm cả chuỗi token và các thông tin liên quan tài khoản)
        return JWTResponse.builder()
                .fullName(userDetail.getFullName())
                .address(userDetail.getAddress())
                .email(userDetail.getEmail())
                .status(userDetail.getStatus())
                .authorities(userDetail.getAuthorities())
                .token(token)
                .build();
    }
}
