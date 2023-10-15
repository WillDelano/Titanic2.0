package com.core;

public abstract class User {
    private String username;
    private String password;
    private Long id;
    private String firstName;
    private String lastName;

    public User(String username, String password, Long id, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

}






