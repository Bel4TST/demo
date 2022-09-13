package com.example.demo;

import javax.persistence.*;
@Entity
@Table(name = "students")

public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;
    @Column(name = "description")
    private String description;
    @Column(name = "password")
    private String password;
    public Student() {
    }
    public Student(String name, String email, String description, String password) {
        this.name = name;
        this.email = email;
        this.description = description;
        this.password = password;
    }
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {this.name = name;}

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "Student [id=" + id + ",name=" + name + ", email=" + email + ", desc=" + description + ", password=" + password + "]";
    }
}