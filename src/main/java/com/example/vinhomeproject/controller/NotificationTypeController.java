package com.example.vinhomeproject.controller;

import com.example.vinhomeproject.models.NotificationType;
import com.example.vinhomeproject.models.Payment;
import com.example.vinhomeproject.response.ResponseObject;
import com.example.vinhomeproject.service.NotificationTypeService;
import com.example.vinhomeproject.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/notificationtype")
public class NotificationTypeController {
    private NotificationTypeService sv;
    @Autowired
    public void NotificationTypeService(NotificationTypeService sv){this.sv=sv;}
    @GetMapping
    public ResponseEntity<ResponseObject> getNotificationType(){return sv.getAllNotificationType();}
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getNotificationTypeById(@PathVariable Long id){
        return sv.getNotificationTypeById(id);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteNotificationType(Long id){return sv.deleteNotificationType(id);}
    @PutMapping("/update/{id}")
    public ResponseEntity<String>  updateNotificationType(@RequestBody NotificationType id){return sv.updateNotificationType(id);}



}
