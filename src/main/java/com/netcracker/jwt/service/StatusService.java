package com.netcracker.jwt.service;

import com.netcracker.jwt.domain.Status;

import java.util.List;

public interface StatusService {
    List<Status> getAllStatuses();
    Status getStatusById(Long id);
    boolean updateStatus(Status status);
    void addStatus(Status status);
}
