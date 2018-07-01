package com.netcracker.jwt.additional;

import com.netcracker.jwt.domain.User;

public class UserWithStatus {
    private Long id;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private Long lastVisit;
    private String status;

    public UserWithStatus() {
    }

    public UserWithStatus(Long id, String firstname, String lastname, String username, String password, Long lastVisit, Statuses status) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.lastVisit = lastVisit;
        this.status = status.toString();
    }

    public UserWithStatus(User user, Statuses status) {
        this.id = user.getId();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.lastVisit = user.getLastVisit();
        this.status = status.toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(Long lastVisit) {
        this.lastVisit = lastVisit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
