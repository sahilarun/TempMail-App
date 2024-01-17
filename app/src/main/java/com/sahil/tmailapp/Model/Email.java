package com.sahil.tmailapp.Model;

public class Email {

    String Email;
    String Token;

    public Email(String email, String token) {
        Email = email;
        Token = token;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }
}
