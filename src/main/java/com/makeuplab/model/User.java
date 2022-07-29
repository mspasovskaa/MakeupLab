package com.makeuplab.model;

import com.makeuplab.model.enumerations.Role;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "makeuplab_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String first_name;
    private String last_name;
    private String username;
    private String password;
    private Role role;
    private boolean certificate;
    @ManyToMany
    private List<Quiz> quizList;



    public User(){}
    public User(String first_name, String last_name, String username, String password, Role role, boolean certificate, List<Quiz> quizList) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.username = username;
        this.password = password;
        this.role = role;
        this.certificate = certificate;
        this.quizList = quizList;
    }
}
