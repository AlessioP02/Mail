package com.example.Mail.controllers;

import com.example.Mail.dto.NotificationDTO;
import com.example.Mail.email.service.EmailService;
import com.example.Mail.entities.Student;
import com.example.Mail.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    StudentService studentService;

    @Autowired
    EmailService emailService;

    @PostMapping("/notification")
    public ResponseEntity sendNotification(@RequestBody NotificationDTO payload){
        try{
            Student studentToSendNoti = studentService.getStudentById(payload.getContactId());
            System.out.println("studentToNotify: " + studentToSendNoti);
            if (studentToSendNoti == null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("I couldn't find the student :(");
            emailService.sendTo(studentToSendNoti.getEmail(), payload.getTitle(), payload.getText());
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("The server is broken :(");
        }
    }
}
