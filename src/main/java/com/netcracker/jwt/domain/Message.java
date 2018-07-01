package com.netcracker.jwt.domain;

import javax.persistence.*;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "message")
    private String message;

    @Column(name = "fromUser")
    private Long fromUser;

    @Column(name = "toUser")
    private Long toUser;

    @Lob
    @Column(name = "pic")
    private String pic;

    @Column(name = "timestamp")
    private Long timestamp;

    public Message() {
    }

    public Message(String message, Long fromUser, Long toUser, String pic, Long timestamp) {
        this.message = message;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.pic = pic;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getFromUser() {
        return fromUser;
    }

    public void setFromUser(Long fromUser) {
        this.fromUser = fromUser;
    }

    public Long getToUser() {
        return toUser;
    }

    public void setToUser(Long toUser) {
        this.toUser = toUser;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", fromUser=" + fromUser +
                ", toUser=" + toUser +
                ", pic='" + pic + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
