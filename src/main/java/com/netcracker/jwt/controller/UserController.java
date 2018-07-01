package com.netcracker.jwt.controller;

import com.netcracker.jwt.additional.Statuses;
import com.netcracker.jwt.additional.UserWithStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import com.netcracker.jwt.domain.Status;
import com.netcracker.jwt.domain.User;
import com.netcracker.jwt.service.StatusService;
import com.netcracker.jwt.service.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private StatusService statusService;


    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserWithStatus>> getAllUsers() {
        List<User> list = userService.getAllUsers();
        List<UserWithStatus> result = new ArrayList<>();
        List<Status> statuses = statusService.getAllStatuses();
        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            Status status = null;
            for (int j = 0; j < statuses.size(); j++) {
                if (statuses.get(j).getId() == user.getId()) {
                    status = statuses.get(j);
                    break;
                }
            }
            if (System.currentTimeMillis() - user.getLastVisit() > 10000) status.setCurrentStatus(Statuses.OFFLINE);
            result.add(new UserWithStatus(user, status.getCurrentStatus()));
            statusService.updateStatus(status);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(path = "/add")
    public ResponseEntity<Void> addUser(@RequestBody User user, UriComponentsBuilder builder) {
        boolean flag = userService.addUser(user);
        if (flag == false) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        user.setLastVisit(System.currentTimeMillis());
        userService.updateUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/del/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
