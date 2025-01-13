package com.example.vinhomeproject.controller;

import com.example.vinhomeproject.dto.PageList;
import com.example.vinhomeproject.dto.UpdateUserDTO;
import com.example.vinhomeproject.dto.UserDTO;
import com.example.vinhomeproject.dto.UserDTO_2;
import com.example.vinhomeproject.mapper.UserMapper;
import com.example.vinhomeproject.models.Users;
import com.example.vinhomeproject.request.ChangePasswordRequest;
import com.example.vinhomeproject.response.ResponseObject;
import com.example.vinhomeproject.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UsersService serivce;
    @Autowired
    private UserMapper userMapper;

    @GetMapping()
    public ResponseEntity<ResponseObject> getAllUser() {
        return serivce.getAllUser();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> deleteUser(@PathVariable Long id) {
        return serivce.deleteUser(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updateUser(@RequestBody UpdateUserDTO userDTO, @PathVariable Long id) {
        return serivce.updateUser(id, userDTO);
    }

    @PutMapping("/update-image/{id}")
    public ResponseEntity<ResponseObject> updateImageUser(@RequestParam(value = "file") MultipartFile multipartFile,
                                                          @PathVariable Long id
    ) {
        return serivce.updateImageUser(id, multipartFile);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getbyId(@PathVariable Long id) {
        return serivce.getById(id);
    }

    @GetMapping("/count-all")
    public ResponseEntity<ResponseObject> countAll() {
        return serivce.countAllUser();
    }

    @PatchMapping("/change-password")
    public ResponseEntity<ResponseObject> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectdUser) {
        return serivce.changePassword(request, connectdUser);
    }

    @GetMapping("/get-page/{currentPage}")
    public ResponseEntity<ResponseObject> getPage(@PathVariable int currentPage, @RequestParam(defaultValue = "3") int sizePage, @RequestParam(defaultValue = "email") String field) {
        if (sizePage > serivce.count()) {
            return serivce.getAllUser();
        }
        Page<Users> users = serivce.getPage(currentPage, sizePage, field);
        var pageList = PageList.<Users>builder()
                .totalPage(users.getTotalPages())
                .currentPage(currentPage)
                .listResult(users.getContent())
                .build();
        return ResponseEntity.ok(new ResponseObject(
                "Get page " + currentPage + " successfully",
                pageList
        ));
    }

    @GetMapping("/get-list-apartment")
    public ResponseEntity<ResponseObject> getListApartment(@RequestParam Long id){
        return serivce.getListApartmentByUserId(id);
    }
    @GetMapping("/search-by-email")
    public ResponseEntity<ResponseObject> searchByEmail(@RequestParam(defaultValue = "") String email,@RequestParam(defaultValue = "1") int currentPage, @RequestParam(defaultValue = "3") int sizePage, @RequestParam(defaultValue = "email") String field) {
        if (email.isEmpty()) {
            Page<Users> users = serivce.getPage(currentPage, sizePage, field);
            var pageList = PageList.<Users>builder()
                    .totalPage(users.getTotalPages())
                    .currentPage(currentPage)
                    .listResult(users.getContent())
                    .build();
            return ResponseEntity.ok(new ResponseObject(
                    "Get page " + currentPage + " successfully",
                    pageList));
        }
        Page<Users> users = serivce.searchByEmail(email,currentPage, sizePage, field);
        var pageList = PageList.<Users>builder()
                .totalPage(users.getTotalPages())
                .currentPage(currentPage)
                .listResult(users.getContent())
                .build();
        if(users.getSize() == 0) return ResponseEntity.ok(new ResponseObject(
                "No result",""));
        return ResponseEntity.ok(new ResponseObject(
                "Get page " + currentPage + " successfully",
                pageList
        ));
    }
    @GetMapping("/search-user-complete-appointment-by-email")
    public ResponseEntity<ResponseObject> searchByEmailAndLatestAppointmentComplete(@RequestParam String email) {
       return serivce.searchAppointmentCompleteByEmail(email);
    }
    @GetMapping("/get-top-potential-customers")
    public ResponseEntity<ResponseObject> getTopPotentialCustomers() {
        return serivce.getTopPotentialCustomers();
    }
}
