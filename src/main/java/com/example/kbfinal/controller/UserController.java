package com.example.kbfinal.controller;

import com.example.kbfinal.entity.User;
import com.example.kbfinal.service.UserService;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private  UserService userService;


    // user 정보를 입력, 삭제, 수정하는 API 생성
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User createdUser = userService.registerUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<User> editUser(@PathVariable Long id, @RequestBody User user) {
        User updatedUser = userService.editUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // 전체 user List를 조회하는 api 생성
    @GetMapping("/list")
    public  ResponseEntity<List<User>>  getAllUsers() {

        return (ResponseEntity<List<User>>) userService.getAllUsers();
    }

    // 전체 user 의 숫자를 조회하는 api 생성
    @GetMapping("/count")
    public ResponseEntity<Long> getUserCount() {
        long userCount = userService.getUserCount();
        return ResponseEntity.ok(userCount);
    }

    //test
    @GetMapping("/create")
    public List<String> createUser() {
        userService.createUser();
        return Collections.singletonList("?");
    }



}
