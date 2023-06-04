package com.example.myapplication;

public class User {
    public static User currentUser;
    private String fullName;
    private String email;
    private String password;
    private String contactNo;
    private String gender;
    private int id;

    public User(String fullName, String email, String password, String contactNo, String gender) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.contactNo = contactNo;
        this.gender = gender;
    }

    // Getters and Setters

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    public int getId () {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}

