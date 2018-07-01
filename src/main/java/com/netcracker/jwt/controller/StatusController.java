package com.netcracker.jwt.controller;

import com.netcracker.jwt.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.netcracker.jwt.domain.Status;

@RestController
@RequestMapping("/status")
public class StatusController {
    @Autowired
    private StatusService statusService;

    @PostMapping(path = "/add")
    public ResponseEntity<Void> addStatus(@RequestBody Status status) {
        statusService.addStatus(status);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Status> getStatusById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(statusService.getStatusById(id), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestBody Status status) {
        statusService.updateStatus(status);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
