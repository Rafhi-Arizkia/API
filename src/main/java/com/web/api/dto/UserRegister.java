package com.web.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class UserRegister {
    @NotEmpty(message = "Required First Name")
    private String firstName;
    @NotEmpty(message = "Required Last Name")
    private String lastName;
    @NotEmpty(message = "Required Full Name")
    private String fullName;
    @Email
    @NotEmpty(message = "Required Email")
    private String userEmail;
    @NotEmpty(message = "Required Password")
    private String password;
    private String userRole;

    private UserRegister(String firstName, String lastName, String fullName, String userEmail, String password, String userRole) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.userEmail = userEmail;
        this.password = password;
        this.userRole = userRole;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getPassword() {
        return password;
    }

    public String getUserRole() {
        return userRole;
    }

    public static UserRegisterBuilder builder() {
        return new UserRegisterBuilder();
    }

    public static class UserRegisterBuilder {
        private String firstName;
        private String lastName;
        private String fullName;
        private String userEmail;
        private String password;
        private String userRole;


        public UserRegisterBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserRegisterBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserRegisterBuilder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public UserRegisterBuilder userEmail(String userEmail) {
            this.userEmail = userEmail;
            return this;
        }

        public UserRegisterBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserRegisterBuilder userRole(String userRole) {
            this.userRole = userRole;
            return this;
        }

        public UserRegister build() {
            return new UserRegister(firstName, lastName, fullName, userEmail, password, userRole);
        }
    }
}
