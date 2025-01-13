package com.example.vinhomeproject.service;

import com.example.vinhomeproject.dto.PaymentDTO;
import com.example.vinhomeproject.dto.PaymentDTO_2;
import com.example.vinhomeproject.dto.RevenuePerMonthDTO;
import com.example.vinhomeproject.dto.RevenueYearDTO;
import com.example.vinhomeproject.mapper.PaymentMapper;
import com.example.vinhomeproject.models.Contract;
import com.example.vinhomeproject.models.Payment;
import com.example.vinhomeproject.repositories.ContractRepository;
import com.example.vinhomeproject.repositories.PaymentRepository;
import com.example.vinhomeproject.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository rs;
    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private PaymentMapper paymentMapper;

    public ResponseEntity<ResponseObject> getAllPayment() {
        List<Payment> paymentList = rs.findAll();

        return ResponseEntity.ok(new ResponseObject(
                "successfully",
                paymentList
        ));
    }

    public ResponseEntity<ResponseObject> getPaymentById(Long id) {
        return ResponseEntity.ok(new ResponseObject(
                "successfully",
                rs.findPaymentById(id)
        ));

    }


    public ResponseEntity<String> deletePayment(Long id) {
        Payment existingUser = rs.findPaymentById(id);

        if (existingUser != null) {
            existingUser.setStatus(!existingUser.isStatus());
            rs.save(existingUser);
            return ResponseEntity.ok("delete successfully");
        } else {
            return ResponseEntity.ok("id not exist");
        }


    }

    public ResponseEntity<String> updatePayment(Long id, Payment paypent) {
        Payment existingUser = rs.findPaymentById(id);

        if (existingUser != null) {
            if (paypent.getContent() != null) {
                existingUser.setContent(paypent.getContent());
            }
            if (paypent.getPayment_time() != null) {
                existingUser.setPayment_time(paypent.getPayment_time());
            }
            if (paypent.getPaymentType() != null) {
                existingUser.setPaymentType(paypent.getPaymentType());
            }
            if (paypent.getTotal_price() != 0) {
                existingUser.setTotal_price(paypent.getTotal_price());
            }
            rs.save(existingUser);
            return ResponseEntity.ok("update successfully");
        } else {
            return ResponseEntity.ok("id not exist");
        }
    }

    public ResponseEntity<String> createPayment(Payment id) {
        Payment existingUser = new Payment();
        existingUser.setContent(id.getContent());
        existingUser.setPayment_time(id.getPayment_time());
        existingUser.setPaymentType(id.getPaymentType());
        existingUser.setTotal_price(id.getTotal_price());
        existingUser.setStatus(true);
        rs.save(existingUser);
        return ResponseEntity.ok("create successfully");
    }

    public ResponseEntity<ResponseObject> compareRevenue(int year) {
        return ResponseEntity.ok(new ResponseObject(
                "",
                RevenueYearDTO.builder()
                        .compareYear(mapToRevenuePerMonthDTO(calculateRevenueByYear(year)))
                        .currentYear(mapToRevenuePerMonthDTO(calculateRevenueByYear(LocalDate.now().getYear())))
                        .build()
        ));
    }

    public ResponseEntity<ResponseObject> getAllPaymentByContractId(Long contractId) {
        return ResponseEntity.ok(new ResponseObject(
                "", rs.findAllByContractId(contractId)));
    }

    public ResponseEntity<ResponseObject> getAllUnpaidPayment(Long userId) {
        List<Contract> contracts = contractRepository.findContractsByUserId(userId);
        List<PaymentDTO_2> payments = new ArrayList<>();
        for (Contract x : contracts) {
            if (rs.getAllByContractIdUnpaid(x.getId()) != null) {
                payments.addAll(rs.getAllByContractIdUnpaid(x.getId()));
            }
        }
        if (!payments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                    "Get all payment successfully",
                    payments
            ));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                "Payment null",
                ""
        ));
    }

    public ResponseEntity<ResponseObject> calculateRevenueByMonth(int year, int month) {
        List<Payment> paymentsOfMonth = rs.findByPaymentTimeBetween(
                LocalDate.of(year, month, 1),
                LocalDate.of(year, month, 31)
        );

        double totalRevenueOfMonth = paymentsOfMonth.stream()
                .mapToDouble(Payment::getTotal_price)
                .sum();
        return ResponseEntity.ok(new ResponseObject("", totalRevenueOfMonth));
    }

    public void CreatePayment(Long contractId, int paymentOrder) {
        if (contractRepository.findById(contractId).isEmpty()) {
            return;
        }
        Contract contract = contractRepository.findById(contractId).get();
        Payment payment = Payment.builder()
                .payment_time(contract.getDateStartRent().plusMonths(paymentOrder))
                .contract(contract)
                .total_price(contract.getContractHistory().getPrice())
                .content("Semester " + paymentOrder)
                .build();
        payment.setStatus(false);
        rs.save(payment);
    }

    public ResponseEntity<ResponseObject> getUpcomingPayment(Long userId, int month, int year) {
        List<Contract> contracts = contractRepository.findContractsByUserId(userId);
        List<List<PaymentDTO>> paymentsForContracts = new ArrayList<>();

        for (Contract contract : contracts) {
            List<Payment> payments = rs.findAllByContractId(contract.getId(), month, year);
            List<PaymentDTO> paymentDTOS = payments.stream().map(paymentMapper::toDto).collect(Collectors.toList());
            paymentsForContracts.add(paymentDTOS);
        }
        return ResponseEntity.ok(new ResponseObject("Get successfully", paymentsForContracts));
    }

    private Map<Integer, Double> calculateRevenueByYear(int year) {
        Map<Integer, Double> revenueMap = new HashMap<>();
        List<Payment> paymentsOfYear = rs.findByPaymentTimeBetween(
                LocalDate.of(year, 1, 1),
                LocalDate.of(year, 12, 31)
        );
        paymentsOfYear.removeIf(payment -> !payment.isStatus());
        for (int month = 1; month <= 12; month++) {
            final int currentMonth = month;
            double totalRevenueOfMonth = paymentsOfYear.stream()
                    .filter(payment -> payment.getPayment_time().getMonthValue() == currentMonth)
                    .mapToDouble(Payment::getTotal_price)
                    .sum();
            revenueMap.put(month, totalRevenueOfMonth);
        }
        return revenueMap;
    }

    private List<RevenuePerMonthDTO> mapToRevenuePerMonthDTO(Map<Integer, Double> revenueMap) {
        List<RevenuePerMonthDTO> revenueList = new ArrayList<>();
        for (Map.Entry<Integer, Double> entry : revenueMap.entrySet()) {
            int month = entry.getKey();
            double revenue = entry.getValue();
            revenueList.add(new RevenuePerMonthDTO(month, revenue));
        }
        return revenueList;
    }

    public ResponseEntity<ResponseObject> allPaymentInMonth(Long userId, int month, int year) {
        List<Contract> contracts = contractRepository.findContractsByUserId(userId);
        List<PaymentDTO_2> payments = new ArrayList<>();
        for (Contract x : contracts) {
            if (rs.findByContractId(x.getId(), month, year) != null) {
                payments.add(rs.findByContractId(x.getId(), month, year));
            }
        }
        if (!payments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                    "Get payment by month, year successfully",
                    payments
            ));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                "Payment null",
                new ArrayList<PaymentDTO_2>()
        ));
    }

    public ResponseEntity<ResponseObject> allPaymentInUser(Long userId) {
        List<Contract> contracts = contractRepository.findContractsByUserId(userId);
        List<PaymentDTO_2> payments = new ArrayList<>();
        for (Contract x : contracts) {
            if (rs.getAllByContractId(x.getId()) != null) {
                payments.addAll(rs.getAllByContractId(x.getId()));
            }
        }
        if (!payments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                    "Get all payment successfully",
                    payments
            ));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                "Payment null",
                ""
        ));
    }

    public ResponseEntity<ResponseObject> checkPayment(String paymentId) {
        boolean check = false;
        paymentId = paymentId.substring(1, paymentId.length() - 1);
        if (paymentId.contains(",")) {
            String[] idValue = paymentId.split(",");
            for (String x : idValue) {
                Optional<Payment> payment = rs.findById(Long.parseLong(x));
                if (payment.get().isStatus()) {
                    check = true;
                }
            }
        } else {
            Optional<Payment> payment = rs.findById(Long.parseLong(paymentId));
            if (payment.get().isStatus()) {
                check = true;
            }
        }
        return check ? ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                "Payment successfully",
                ""
        )) : ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                "Payment failed",
                ""
        ));
    }


}
