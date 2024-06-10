package org.example.api_security_jwt.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    @Column(nullable = false,unique = true,length = 100)
    private String username;
    @Column(nullable = false,length = 100)
    private String password;
    @Column(length = 70)
    private String fullName;
    @Column(length = 200)
    private String address;
    @Column(length = 100)
    @Email
    private String email;
    private Boolean status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
    joinColumns = @JoinColumn(name = "userId"),
    inverseJoinColumns = @JoinColumn(name = "roleId"))
    private List<Role> roles;
}
