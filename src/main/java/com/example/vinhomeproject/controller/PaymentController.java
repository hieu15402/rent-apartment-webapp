package com.example.vinhomeproject.controller;

import com.example.vinhomeproject.models.Payment;

import com.example.vinhomeproject.response.ResponseObject;
import com.example.vinhomeproject.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/payment")
public class PaymentController {
    @Autowired
    private PaymentService sv;

    @GetMapping
    public ResponseEntity<ResponseObject> getPayment() {
        return sv.getAllPayment();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getPaymentById(@PathVariable Long id) {
        return sv.getPaymentById(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePayment(Long id) {
        return sv.deletePayment(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updatePayment(@PathVariable Long id, Payment payment) {
        return sv.updatePayment(id, payment);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createPayment(@RequestBody Payment id) {
        return sv.createPayment(id);
    }

    @GetMapping("/compareRevenue")
    public ResponseEntity<ResponseObject> compareRevenue(@RequestParam int year) {
        return sv.compareRevenue(year);
    }

    @GetMapping("/revenuePerMonth")
    public ResponseEntity<ResponseObject> calculateRevenueByMonth(@RequestParam(required = true) int year, @RequestParam(required = true) int month) {
        return sv.calculateRevenueByMonth(year, month);
    }

    @GetMapping("/get-upcoming-payment/{id}")
    public ResponseEntity<ResponseObject> getUpcomingPayment(@PathVariable Long id, @RequestParam(required = true) int month, @RequestParam(required = true) int year) {
        return sv.allPaymentInMonth(id, month, year);
    }

    @GetMapping("/get-unpaid-payment/{id}")
    public ResponseEntity<ResponseObject> getUnpaidPayment(@PathVariable Long id) {
        return sv.getAllUnpaidPayment(id);
    }

    @GetMapping("/getAllPaymentByContract/{id}")
    public ResponseEntity<ResponseObject> getAllPaymentByContract(@PathVariable Long id) {
        return sv.getAllPaymentByContractId(id);
    }

    @GetMapping("/get-all-payment/{id}")
    public ResponseEntity<ResponseObject> getAllPayment(@PathVariable Long id) {
        return sv.allPaymentInUser(id);
    }

}
