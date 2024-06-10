package org.example.api_security_jwt.controller;

import org.example.api_security_jwt.model.dto.request.DtoFormLogin;
import org.example.api_security_jwt.model.dto.request.DtoFormRegister;
import org.example.api_security_jwt.model.dto.response.JWTResponse;
import org.example.api_security_jwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class HomeController {
    @Autowired
    private UserService userService;

    @PostMapping("/public/register")
    public ResponseEntity<?> register(@RequestBody DtoFormRegister formRegister){
        return new ResponseEntity<>(userService.register(formRegister), HttpStatus.OK);
    }

    @PostMapping("/public/login")
    public ResponseEntity<JWTResponse> login(@RequestBody DtoFormLogin formLogin){
        return new ResponseEntity<>(userService.login(formLogin),HttpStatus.OK);
    }

    @GetMapping("/user/list")
    public ResponseEntity<List<String>> userList(){
        return new ResponseEntity<>(List.of("One","Two","Three"),HttpStatus.OK);
    }

    @GetMapping("/admin/list")
    public ResponseEntity<List<String>> adminList(){
        return new ResponseEntity<>(List.of("Four","Five","Six"),HttpStatus.OK);
    }
}
