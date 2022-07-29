package com.makeuplab.service.impl;

import com.makeuplab.model.Quiz;
import com.makeuplab.model.User;
import com.makeuplab.model.enumerations.Role;
import com.makeuplab.model.exceptions.UserNotFoundException;
import com.makeuplab.repository.UserRepository;
import com.makeuplab.service.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService , UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findById(Long id) {
        return this.userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
    }

    @Override
    public User add(String first_name, String last_name, String username, String password, Role role) {
        List<Quiz> quizList=new ArrayList<>();
        User user = new User(first_name,last_name,username,passwordEncoder.encode(password),role,false,quizList);
        return this.userRepository.save(user);
    }

    @Override
    public User addQuiz(Long id,Quiz quiz) {
        User user=this.findById(id);
        List<Quiz> quizList=user.getQuizList();
        quizList.add(quiz);
        user.setQuizList(quizList);

        return this.userRepository.save(user);
    }

    @Override
    public User changeCertificateStatus(Long id,boolean certificate) {
        User user=this.findById(id);

        user.setCertificate(certificate);
        return this.userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        User user=this.findById(id);

        this.userRepository.delete(user);
    }

    @Override
    public User findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public User update(Long id, String first_name, String last_name, String username, String password, Role role, boolean certificate, List<Quiz> quizList) {
        User user=this.findById(id);

        user.setFirst_name(first_name);
        user.setLast_name(last_name);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);
        user.setCertificate(certificate);
        user.setQuizList(quizList);

        return this.userRepository.save(user);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=this.userRepository.findByUsername(username);
        UserDetails userDetails=new org.springframework.security.core.userdetails.User(
                user.getUsername(),user.getPassword(), Stream.of(new SimpleGrantedAuthority(user.getRole().toString())).collect(Collectors.toList())
        );
        return userDetails;
    }

    @Override
    public void setQuiz(Long userId, Quiz quiz) {
        User user=this.findById(userId);
        List<Quiz> quizList=user.getQuizList();
        quizList.add(quiz);
        user.setQuizList(quizList);
        this.userRepository.save(user);

    }
}
