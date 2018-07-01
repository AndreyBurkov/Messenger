package com.netcracker.jwt.domain;

import com.netcracker.jwt.additional.Statuses;
import org.springframework.context.annotation.Primary;

import javax.persistence.*;

@Entity
@Table(name = "statuses")
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "currentStatus")
    @Enumerated(EnumType.ORDINAL)
    private Statuses currentStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Statuses getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(Statuses currentStatus) {
        this.currentStatus = currentStatus;
    }

    @Override
    public String toString() {
        return "Status{" +
                "id=" + id +
                ", currentStatus=" + currentStatus +
                '}';
    }
}
