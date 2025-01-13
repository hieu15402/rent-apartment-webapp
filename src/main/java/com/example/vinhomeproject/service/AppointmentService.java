package com.example.vinhomeproject.service;

import com.example.vinhomeproject.dto.AppointmentDTO;
import com.example.vinhomeproject.dto.AppointmentDTO_2;
import com.example.vinhomeproject.dto.AppointmentUpdateDTO;
import com.example.vinhomeproject.mapper.AppointmentMapper;
import com.example.vinhomeproject.models.Appointment;
import com.example.vinhomeproject.repositories.ApartmentRepository;
import com.example.vinhomeproject.repositories.AppointmentRepository;
import com.example.vinhomeproject.repositories.UsersRepository;
import com.example.vinhomeproject.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private ApartmentRepository apartmentRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private AppointmentMapper mapper;
    public ResponseEntity<ResponseObject> getAll(){
        List<Appointment> appointments = appointmentRepository.findAll();
        return ResponseEntity.ok(new ResponseObject(
                "successfully",
                appointments
        ));
    }

    public ResponseEntity<ResponseObject> getById(Long id){
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        return ResponseEntity.ok(new ResponseObject(
                "Get appointment by id successfully",
                appointment
        ));
    }

    public ResponseEntity<ResponseObject> create(AppointmentDTO appointmentDTO){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = appointmentDTO.getTime().format(formatter);
        Appointment appointment = Appointment.builder()
                .statusAppointment("Pending")
                .dateTime(appointmentDTO.getDateTime())
                .users(usersRepository.findById(appointmentDTO.getUsersId()).get())
                .time(formattedTime)
                .note(appointmentDTO.getNote())
                .apartment(apartmentRepository.findById(appointmentDTO.getApartmentId()).get())
                .build();
        appointmentRepository.save(appointment);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                "Create appointment successfully",
                appointmentDTO
        ));
    }

    public ResponseEntity<ResponseObject> delete(Long id){
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        if(appointment.isPresent()){
            appointment.get().setStatus(!appointment.get().isStatus());
            appointmentRepository.save(appointment.get());
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                    "Delete appointment successfully",
                    appointment.get()
            ));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
                "Not found appointment",
                ""
        ));
    }

    public ResponseEntity<ResponseObject> update(Long id, AppointmentUpdateDTO appointmentDTO){
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        if(appointment.isPresent()){
            appointment.get().setStatusAppointment(appointmentDTO.getStatusAppointment());
            appointmentRepository.save(appointment.get());
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                    "Update appointment successfully",
                    appointment.get()
            ));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
                "Not found appointment",
                ""
        ));
    }

    public ResponseEntity<ResponseObject> getbyUserId(Long id){
        List<AppointmentDTO_2> appointments = appointmentRepository.findByUserIdNew(id);
        if(appointments.isEmpty()) return ResponseEntity.ok(new ResponseObject(
                "Do not have appointment with this user",
                appointments
        ));
        return ResponseEntity.ok(new ResponseObject(
                "Get by user successfully",
                appointments
        ));
    }
}
