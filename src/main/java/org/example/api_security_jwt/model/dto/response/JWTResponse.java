package org.example.api_security_jwt.model.dto.response;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class JWTResponse {
    private String fullName;
    private String address;
    private String email;
    private Boolean status;
    private Collection<? extends GrantedAuthority> authorities;
    private String token;
}
