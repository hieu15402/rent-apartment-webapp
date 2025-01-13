package com.example.vinhomeproject.service;

import com.example.vinhomeproject.models.Contract;
import com.example.vinhomeproject.models.Users;
import com.example.vinhomeproject.models.VerificationToken;
import com.example.vinhomeproject.models.paypal.PaypalPaymentIntent;
import com.example.vinhomeproject.models.paypal.PaypalPaymentMethod;
import com.example.vinhomeproject.repositories.ContractRepository;
import com.example.vinhomeproject.repositories.PaymentRepository;
import com.example.vinhomeproject.repositories.UsersRepository;
import com.example.vinhomeproject.request.PaypalRequest;
import com.example.vinhomeproject.response.ResponseObject;
import com.example.vinhomeproject.utils.PaypalUtils;
import com.example.vinhomeproject.utils.SendMailUtils;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class PaypalService {
    @Autowired
    private APIContext apiContext;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private SendMailUtils sendMailUtils;
    @Autowired
    private UsersRepository usersRepository;
//
    public Payment createPayment(
            Double total,
            String currency,
            String method,
            String intent,
            String cancelUrl,
            String successUrl
    ) throws PayPalRESTException {
        Amount amount = new Amount();
        amount.setCurrency(currency);
        amount.setTotal(String.format(Locale.forLanguageTag(currency), "%.2f", total));

        Transaction transaction = new Transaction();
        transaction.setDescription("Pay the bill");
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(method);

        Payment payment = new Payment();
        payment.setIntent(intent);
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);

        payment.setRedirectUrls(redirectUrls);
        return payment.create(apiContext);
    }

    public Payment executePayment(
            String paymentId,
            String payerId
    ) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);

        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);

        return payment.execute(apiContext, paymentExecution);
    }

    private boolean checkStatusOfPayment(Long id) {
        boolean check = false;
        List<com.example.vinhomeproject.models.Payment> list = paymentRepository.findAllByContractId(id);
        for (com.example.vinhomeproject.models.Payment x : list) {
            if (x.isStatus()) {
                check = true;
            } else {
                check = false;
            }
        }
        return check;
    }

    public String paymentSuccessfully(String id, String paymentId, String payerId) {
        try {
            Payment payment = this.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                if (id.contains(",")) {
                    String[] idValue = id.split(",");
                    for (String x : idValue) {
                        Optional<com.example.vinhomeproject.models.Payment> _payment = paymentRepository.findById(Long.parseLong(x));
                        if (_payment.isPresent()) {
                            _payment.get().setStatus(true);
                            if (checkStatusOfPayment(_payment.get().getContract().getId())) {
                                Optional<Contract> contract = contractRepository.findById(_payment.get().getContract().getId());
                                contract.get().setStatusOfPayment(true);
                                contractRepository.save(contract.get());
                            }
                            paymentRepository.save(_payment.get());
                        }
                    }
                } else {
                    Optional<com.example.vinhomeproject.models.Payment> _payment = paymentRepository.findById(Long.parseLong(id));
                    if (_payment.isPresent()) {
                        _payment.get().setStatus(true);
                        if (checkStatusOfPayment(_payment.get().getContract().getId())) {
                            Optional<Contract> contract = contractRepository.findById(_payment.get().getContract().getId());
                            contract.get().setStatusOfPayment(true);
                            contractRepository.save(contract.get());
                        }
                        paymentRepository.save(_payment.get());
                    }
                }
                return "payment-success";
            }
        } catch (PayPalRESTException e) {
        }
        return "payment-success";
    }

    public int getUserByPaymentId(String paymentId) {
        int userId = 0;
        if (paymentId.contains(",")) {
            String[] idValue = paymentId.split(",");
            userId = paymentRepository.getUserIdByPaymentId(Integer.parseInt(idValue[1]));
        } else {
            userId = paymentRepository.getUserIdByPaymentId(Integer.parseInt(paymentId));
        }
        return userId;
    }

    public void sendMail(int userId,String paymentId) throws MessagingException {
        Optional<Users> users = usersRepository.findById((long) userId);
        String text = "";
        if(paymentId.contains(",")){
            text = "PAYMENT SUCCESS\n" +
                    "Xin chào," + users.get().getFullName() + "\n";
            String[] idValue = paymentId.split(",");
            for(String x:idValue){
                Optional<com.example.vinhomeproject.models.Payment> payment = paymentRepository.findById(Long.parseLong(x));
                text += "Payment " + payment.get().getContent() + " with: " +payment.get().getContract().getAppointment().getApartment().getName() + "\n";
            }
            text += "Trân trọng,\n" +
                    "Whalehome";
        }else{
            text = "PAYMENT SUCCESS" +
                    "Xin chào," + users.get().getFullName();
                Optional<com.example.vinhomeproject.models.Payment> payment = paymentRepository.findById(Long.parseLong(paymentId));
                text += "Payment " + payment.get().getContent() + " with: " +payment.get().getContract().getAppointment().getApartment().getName() + "\n";

                text += "Trân trọng,\n" +
                    "Whalhome";
        }
        sendMailUtils.sendSimpleEmail(
                "trungkiennguyen0310@gmail.com",
                "Payment success - Whalehome",
                text
        );

    }
    public String getUserName(int userId){
        return usersRepository.findById((long) userId).get().getFullName();
    }
}
