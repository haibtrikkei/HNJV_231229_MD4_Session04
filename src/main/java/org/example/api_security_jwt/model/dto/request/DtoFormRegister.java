package org.example.api_security_jwt.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DtoFormRegister {
    private String username;
    private String password;
    private String fullName;
    private String address;
    private String email;
    private List<String> roles;
}
