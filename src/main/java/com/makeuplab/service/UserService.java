package com.makeuplab.service;

import com.makeuplab.model.Quiz;
import com.makeuplab.model.User;
import com.makeuplab.model.enumerations.Role;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService {
    User findById(Long id);
    User add(String first_name, String last_name, String username, String password, Role role);
    User addQuiz(Long id,Quiz quiz);
    User changeCertificateStatus(Long id,boolean certificate);
    void delete(Long id);
    User findByUsername(String username);
    User update(Long id,String first_name, String last_name, String username, String password, Role role, boolean certificate, List<Quiz> quizList);

    UserDetails loadUserByUsername(String s) throws UsernameNotFoundException;

    void setQuiz(Long userId,Quiz quiz);
}
