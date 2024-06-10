package org.example.api_security_jwt.service;

import org.example.api_security_jwt.model.dto.request.DtoFormLogin;
import org.example.api_security_jwt.model.dto.request.DtoFormRegister;
import org.example.api_security_jwt.model.dto.response.JWTResponse;

public interface UserService {
    boolean register(DtoFormRegister formRegister);
    JWTResponse login(DtoFormLogin formLogin);
}
