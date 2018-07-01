package com.netcracker.jwt.service;

import com.netcracker.jwt.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.netcracker.jwt.domain.Status;

import java.util.ArrayList;
import java.util.List;


@Service
public class StatusServiceImpl implements StatusService {
    @Autowired
    private StatusRepository statusRepository;

    @Override
    public List<Status> getAllStatuses() {
        return statusRepository.findAll();
    }

    @Override
    public void addStatus(Status status) {
        statusRepository.save(status);
    }

    @Override
    public Status getStatusById(Long id) {
        return statusRepository.findOne(id);
    }

    @Override
    public boolean updateStatus(Status status) {
        Status st = statusRepository.save(status);
        return st != null;
    }
}
