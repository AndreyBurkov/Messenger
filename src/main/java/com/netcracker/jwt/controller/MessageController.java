package com.netcracker.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;
import com.netcracker.jwt.domain.Message;
import com.netcracker.jwt.service.MessageService;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable("id") Long id) {
        Message message = messageService.getMessageById(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> list = messageService.getAllMessages();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/correspondence/{fromUser}/{toUser}/{id}")
    public ResponseEntity<List<Message>> getCorrespondence(@PathVariable("fromUser") Long fromUser,
                                                           @PathVariable("toUser") Long toUser,
                                                           @PathVariable("id") Long id) {
        List<Message> list = messageService.getCorrespondence(toUser, fromUser, id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping(path = "/add")
    public ResponseEntity<Void> addMessage(@RequestBody Message message, UriComponentsBuilder builder) {
        boolean flag = messageService.addMessage(message);
        if (flag == false) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/{id}").buildAndExpand(message.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Message> updateMessage(@RequestBody Message message) {
        messageService.updateMessage(message);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/del/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        messageService.deleteMessage(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/addImage")
    public ResponseEntity<Void> uploadFileMulti(
            @RequestParam("file") MultipartFile file,
            @RequestParam("fromUser") Long fromUser,
            @RequestParam("toUser") Long toUser,
            @RequestParam("timestamp") Long timestamp,
            UriComponentsBuilder builder
    ) throws IOException {
        Message message = new Message();
        message.setMessage("");
        message.setFromUser(fromUser);
        message.setToUser(toUser);
        message.setTimestamp(timestamp);

        byte[] encodedBytes = Base64.getEncoder().encode(file.getBytes());
        System.out.println("\n\n\n\t\t\tencodedBytes " + new String(encodedBytes) + "\n\n\n");
        message.setPic("data:image/jpg;base64," + new String(encodedBytes));

        boolean flag = messageService.addMessage(message);
        if (flag == false) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/{id}").buildAndExpand(message.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
}
