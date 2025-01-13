package com.example.vinhomeproject.service;

import com.example.vinhomeproject.config.VNPayConfig;
import com.example.vinhomeproject.models.Contract;
import com.example.vinhomeproject.models.Payment;
import com.example.vinhomeproject.models.Users;
import com.example.vinhomeproject.repositories.ContractRepository;
import com.example.vinhomeproject.repositories.PaymentRepository;
import com.example.vinhomeproject.repositories.UsersRepository;
import com.example.vinhomeproject.response.ResponseObject;
import com.example.vinhomeproject.utils.SendMailUtils;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class VNPayService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private SendMailUtils sendMailUtils;
    @Autowired
    private UsersRepository usersRepository;

    private boolean checkStatusOfPayment(Long id) {
        boolean check = false;
        List<Payment> list = paymentRepository.findAllByContractId(id);
        for (Payment x : list) {
            if (x.isStatus()) {
                check = true;
            } else {
                check = false;
            }
        }
        return check;
    }

    public String paymentCallback(Map<String, String> queryParams) {
        String vnp_ResponseCode = queryParams.get("vnp_ResponseCode");
        String paymentId = queryParams.get("paymentId");
        if (paymentId != null && !paymentId.equals("")) {
            if ("00".equals(vnp_ResponseCode)) {
                if (paymentId.contains(",")) {
                    String[] idValue = paymentId.split(",");
                    for (String x : idValue) {
                        Optional<Payment> payment = paymentRepository.findById((long) Integer.parseInt(x));
                        if (payment.isPresent()) {
                            payment.get().setStatus(true);
                            if (checkStatusOfPayment(payment.get().getContract().getId())) {
                                Optional<Contract> contract = contractRepository.findById(payment.get().getContract().getId());
                                contract.get().setStatusOfPayment(true);
                                contractRepository.save(contract.get());
                            }
                            paymentRepository.save(payment.get());
                        }
                    }
                    return "payment-success";
                } else {
                    Optional<Payment> payment = paymentRepository.findById((long) Integer.parseInt(paymentId));
                    if (payment.isPresent()) {
                        payment.get().setStatus(true);
                        if (checkStatusOfPayment(payment.get().getContract().getId())) {
                            Optional<Contract> contract = contractRepository.findById(payment.get().getContract().getId());
                            contract.get().setStatusOfPayment(true);
                            contractRepository.save(contract.get());
                        }
                        paymentRepository.save(payment.get());
                        return "payment-success";
                    }
                    return "payment-failed";
                }
                // Giao dịch thành công
                // Thực hiện các xử lý cần thiết, ví dụ: cập nhật CSDL

            } else {
                // Giao dịch thất bại
                // Thực hiện các xử lý cần thiết, ví dụ: không cập nhật CSDL\
                return "payment-failed";
            }
        }
        return "payment-failed";
    }

    public ResponseEntity<ResponseObject> payment(long price, String paymentId, String bankCode) throws UnsupportedEncodingException {
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        long amount = price * 25000 * 100;

        String vnp_TxnRef = VNPayConfig.getRandomNumber(8);
        String vnp_IpAddr = "127.0.0.1";

        String vnp_TmnCode = VNPayConfig.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");

        vnp_Params.put("vnp_BankCode", bankCode);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);

        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", VNPayConfig.vnp_ReturnUrl + "?paymentId=" + paymentId);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        cld.add(Calendar.HOUR_OF_DAY, 7);
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VNPayConfig.vnp_PayUrl + "?" + queryUrl;

        return ResponseEntity.ok(new ResponseObject(
                "Successfully",
                paymentUrl
        ));

    }
    public int getUserByPaymentId(String paymentId){
        int userId = 0;
        if(paymentId.contains(",")){
            String[] idValue = paymentId.split(",");
            userId = paymentRepository.getUserIdByPaymentId(Integer.parseInt(idValue[1]));
        }else{
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
}
