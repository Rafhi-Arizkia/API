package com.web.api.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.List;

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

    public UserEntities() {
    }

    public UserEntities(Long userId, String firstName, String lastName, String userEmail, String password) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userEmail = userEmail;
        this.password = password;
    }



    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

//    BUILDER CLASS
//    Dalam method builder, umumnya setiap setter akan mengembalikan objek yang sama, sehingga kita dapat terus
//    memanggil setter lainnya pada objek yang sama. Hal ini memungkinkan untuk membuat kode yang lebih mudah
//    dibaca dan lebih mudah dimengerti.
//    Dengan mengembalikan objek yang sama (dalam hal ini, this), kita dapat membangun objek secara bertahap dengan memanggil satu atau lebih setter pada setiap baris kode.
    public static class UserEntitiesBuilder {

        private String firstName;
        private String lastName;
        private String fullName;
        private String userEmail;
        private String password;
        private UserRole role;


        public UserEntitiesBuilder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserEntitiesBuilder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserEntitiesBuilder setFullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public UserEntitiesBuilder setUserEmail(String userEmail) {
            this.userEmail = userEmail;
            return this;
        }

        public UserEntitiesBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public UserEntitiesBuilder setRole(UserRole role) {
            this.role = role;
            return this;
        }

        public UserEntities build() {
            UserEntities user = new UserEntities();
            user.setFirstName(this.firstName);
            user.setLastName(this.lastName);
            user.setFullName(this.fullName);
            user.setUserEmail(this.userEmail);
            user.setPassword(this.password);
            user.setRole(this.role);
            return user;
        }
    }
}
