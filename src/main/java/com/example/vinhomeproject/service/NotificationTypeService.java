package com.example.vinhomeproject.service;

import com.example.vinhomeproject.models.NotificationType;
import com.example.vinhomeproject.models.Notifications;
import com.example.vinhomeproject.models.Payment;
import com.example.vinhomeproject.repositories.NotificationRepository;
import com.example.vinhomeproject.repositories.NotificationTypeRepository;
import com.example.vinhomeproject.response.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationTypeService {
    private final NotificationTypeRepository rs;


    public NotificationTypeService(NotificationTypeRepository rs) {
        this.rs = rs;
    }
    public ResponseEntity<ResponseObject> getAllNotificationType(){
        List<NotificationType> NotificationTypeList=rs.findAll();

        return ResponseEntity.ok(new ResponseObject(
                "successfully",
                NotificationTypeList
        ));
    }


    public ResponseEntity<ResponseObject> getNotificationTypeById(Long id){


        return ResponseEntity.ok(new ResponseObject(
                "successfully",
                rs.findNotificationTypeById(id)
        ));

    }

    public  ResponseEntity<String> deleteNotificationType(Long id) {
        NotificationType existingUser = rs.findNotificationTypeById(id);

        if (existingUser != null) {
            existingUser.setStatus(!existingUser.isStatus());
            rs.save(existingUser);
            return ResponseEntity.ok("delete successfully");
        }else {
            return ResponseEntity.ok("id not exist");
        }
    }

    public ResponseEntity<String> updateNotificationType(NotificationType id){
        NotificationType notificationType=rs.findNotificationTypeById(id.getId());
        if (notificationType!=null){
            if(id.getName()!=null){notificationType.setName(id.getName());}
            notificationType.setStatus(true);
            if(id.getCreateDate()!=null){notificationType.setCreateDate(id.getCreateDate());}

         rs.save(notificationType);
         return ResponseEntity.ok("update successfully ");
        }else {
            return ResponseEntity.ok("id not exist");
        }
    }
}
