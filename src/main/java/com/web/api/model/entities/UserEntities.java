package com.web.api.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tb_user")
public class UserEntities implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "first_name")
    @NotEmpty(message = "Required first Name")
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty(message = "Required Last Name")
    private String lastName;

    @Column(name = "full_name")
    @NotEmpty(message = "Required Full Name")
    private String fullName;

    @Column(name = "user_email", unique = true)
    @Email
    @NotEmpty(message = "Required E-Mail")
    private String userEmail;

    @NotEmpty(message = "Required password")
    @Column(name = "user_password")
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Builder
    public UserEntities(Long userId, String firstName, String lastName,
                        String fullName ,String userEmail, String password, UserRole role) {
        this.userId = userId;
        this.firstName = firstName;
        this.fullName = fullName;
        this.lastName = lastName;
        this.userEmail = userEmail;
        this.password = password;
        this.role =  role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
