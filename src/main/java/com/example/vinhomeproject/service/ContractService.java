package com.example.vinhomeproject.service;


import com.example.vinhomeproject.dto.*;
import com.example.vinhomeproject.models.*;
import com.example.vinhomeproject.repositories.*;
import com.example.vinhomeproject.response.ResponseObject;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import com.google.firebase.cloud.StorageClient;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ContractService {
    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private ContractHistoryRepository contractHistoryRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private FileService fileService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private PaymentRepository paymentRepository;

    public ResponseEntity<ResponseObject> getAll(){
        List<ContractDTO_2> contracts = contractRepository.getAll();
        return ResponseEntity.ok(new ResponseObject(
                "successfully",
                contracts
        ));
    }

    public ResponseEntity<ResponseObject> getById(Long id){
        ContractDTO_2 contract = contractRepository.getByIdNew(id);
        if(contract != null){
            return ResponseEntity.ok(new ResponseObject(
                    "Get contract by id successfully",
                    contract
            ));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
                "Contract not exist",
                ""
        ));
    }

    public ResponseEntity<ResponseObject> create(ContractDTO contractDTO){
        Contract contract;
        long durationMonth;
        contractDTO.setContractHistory(contractHistoryRepository.findById(contractDTO.getContractHistory().getId()).get());
        contract = Contract.builder()
                .dateSign(contractDTO.getDateSign())
                .description(contractDTO.getDescription())
                .dateStartRent(contractDTO.getDateStartRent())
                .contractHistory(contractDTO.getContractHistory())
                .appointment(appointmentRepository.findById(contractDTO.getAppointmentId()).get())
                .statusOfPayment(false)
                .build();
        contractRepository.save(contract);

        durationMonth = ChronoUnit.MONTHS.between(contract.getDateStartRent()
                ,  contract.getContractHistory().getExpiredTime());
        for (int i = 1 ; i <= durationMonth; i++){
            paymentService.CreatePayment(contract.getId(), i);
        }
        Optional<Appointment> appointment = appointmentRepository.findById(contractDTO.getAppointmentId());
        appointment.get().setContract(contract);
        appointmentRepository.save(appointment.get());

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                "Create contract successfully",
                contractRepository.getByIdNew(contract.getId())
        ));
    }

    public ResponseEntity<ResponseObject> delete(Long id){
        Optional<Contract> contract = contractRepository.findById(id);
        if(contract.isPresent()){
            contract.get().setStatus(!contract.get().isStatus());
            contractRepository.save(contract.get());
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                    "Delete contract successfully",
                    contract
            ));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
                "Not found contract",
                ""
        ));
    }

    public ResponseEntity<ResponseObject> update(Long id, ContractDTO contractDTO){
        Optional<Contract> contract = contractRepository.findById(id);
        if(contract.isPresent()){
            if(contractDTO.getDateSign()!=null){contract.get().setDateSign(contractDTO.getDateSign());}
            if(contractDTO.getDescription()!=null){contract.get().setDescription(contractDTO.getDescription());}
            if(contractDTO.getDateStartRent()!=null){contract.get().setDateStartRent(contractDTO.getDateStartRent());}
            if(contractDTO.getContractHistory()!=null){contract.get().setContractHistory(contractHistoryRepository.findById(contractDTO.getContractHistory().getId()).get());}
            contractRepository.save(contract.get());
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                    "Update contract successfully",
                    contract
            ));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
                "Not found contract",
                ""
        ));
    }
    public ResponseEntity<ResponseObject> countAll(){
        List<Contract> contracts = contractRepository.findAll();
        return ResponseEntity.ok(new ResponseObject(
                "successfully",
                contracts.size()
        ));
    }

    public ResponseEntity<ResponseObject> uploadFile(MultipartFile multipartFile,Long id) {
        Optional<Contract> contract = contractRepository.findById(id);
        if(contract.isPresent()){
            if (multipartFile != null) {
                String imageUrl = this.upload(multipartFile);
                contract.get().setUrlFile(imageUrl);
                contractRepository.save(contract.get());
                return ResponseEntity.status (HttpStatus.OK).body(new ResponseObject(
                        "File uploaded successfully",
                        contract.get()
                ));
            } else {
                return ResponseEntity.status (HttpStatus.NOT_FOUND).body(new ResponseObject(
                        "File is null",
                        ""
                ));
            }
        }
        return ResponseEntity.status (HttpStatus.NOT_FOUND).body(new ResponseObject(
                "Contract is not exist",
                ""
        ));
    }
    public ResponseEntity<ResponseObject> getListContract(Long userId){
        List<Contract> contracts = contractRepository.findContractsByUserId(userId);
        if(contracts.isEmpty()){
            return ResponseEntity.status (HttpStatus.OK).body(new ResponseObject(
                    "Do not exist contract or user id",
                    contracts
            ));
        }

        return ResponseEntity.status (HttpStatus.OK).body(new ResponseObject(
                "Get list contract",
                contracts.stream().map(this::toContractDTO_4).collect(Collectors.toList())
        ));
    }
    public ResponseEntity<ResponseObject> getDetailContract(Long id)
    {
        if(contractRepository.findById(id).isPresent()){
            Contract c = contractRepository.findById(id).get();
            long durationMonth = ChronoUnit.MONTHS.between(c.getDateStartRent()
                    ,  c.getContractHistory().getExpiredTime());
            double totalPrice = c.getContractHistory().getPrice() * durationMonth;
            var contract = ContractDTO_3.builder()
                    .createDateContract(c.getDateStartRent())
                    .expireDateContract(c.getContractHistory().getExpiredTime())
                    .durationMonth(durationMonth)
                    .totalPrice(totalPrice)
                    .pricePerMonth(c.getContractHistory().getPrice())
                    .areaName(c.getAppointment().getApartment().getBuilding().getZone().getArea().getName())
                    .zoneName(c.getAppointment().getApartment().getBuilding().getZone().getName())
                    .buildingName(c.getAppointment().getApartment().getBuilding().getName())
                    .apartment(c.getAppointment().getApartment())
                    .landlord(usersRepository.findByEmail(c.getCreateBy()).get())
                    .bank(new BankDTO())
                    .renter(c.getAppointment().getUsers())
                    .urlContract(c.getUrlFile())
                    .build();
            return ResponseEntity.status (HttpStatus.OK).body(new ResponseObject(
                    "Get successfully",
                    contract));
        }

        return ResponseEntity.status (HttpStatus.OK).body(new ResponseObject(
                "Id do not exist",
                ""
        ));
    }
    private ContractDTO_4 toContractDTO_4(Contract contract){
        long durationMonth = ChronoUnit.MONTHS.between(contract.getDateStartRent()
                ,  contract.getContractHistory().getExpiredTime());
        double totalPrice = contract.getContractHistory().getPrice() * durationMonth;
        return ContractDTO_4.builder()
                .id(contract.getId())
                .apartmentName(contract.getAppointment().getApartment().getName())
                .contractStatus(contract.isStatusOfPayment())
                .totalPrice(totalPrice)
                .landlordName(usersRepository.findByEmail(contract.getCreateBy()).get().getFullName())
                .urlFile(contract.getUrlFile())
                .renterName(contract.getAppointment().getUsers().getFullName())
                .durationMonth(countPaymentsWithFalseStatus(contract.getId()))
                .address(contract.getAppointment().getApartment().getName()
                +", "+contract.getAppointment().getApartment().getBuilding().getName()
                +", "+contract.getAppointment().getApartment().getBuilding().getZone().getName()
                +", "+contract.getAppointment().getApartment().getBuilding().getZone().getArea().getName()
                +", Q9,TP.HCM"
                )
                .build();
    }
    public long countPaymentsWithFalseStatus(Long contractId) {
        List<Payment> payments = paymentRepository.findAllByContractId(contractId);

        // Đếm số lượng thanh toán có trạng thái false
        return payments.stream()
                .filter(payment -> !payment.isStatus())
                .count();
    }
    private File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File tempFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
        }
        return tempFile;
    }

    private String uploadFile(File file, String fileName) throws IOException {
        BlobId blobId = BlobId.of("whalehome-project.appspot.com", fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType("application/pdf")
                .build();
        InputStream inputStream = PostImageService.class.getClassLoader().getResourceAsStream("firebase.json");
        Credentials credentials = GoogleCredentials.fromStream(inputStream);
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));
        String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/whalehome-project.appspot.com/o/%s?alt=media";
        return String.format(DOWNLOAD_URL, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }
    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }
    private String upload(MultipartFile multipartFile) {
        try {
            String fileName = multipartFile.getOriginalFilename();
            String fileExtension = getExtension(fileName);

            // Check if the file is a PDF
            if (!fileExtension.equalsIgnoreCase(".pdf")) {
                return "Invalid file format. Only PDF files are allowed.";
            }

            fileName = UUID.randomUUID().toString().concat(fileExtension);
            File file = convertToFile(multipartFile, fileName);
            String URL = uploadFile(file, fileName);
            file.delete();
            return URL;
        } catch (Exception e) {
            e.printStackTrace();
            return "Something went wrong while uploading the file.";
        }
    }
    public ResponseEntity<byte[]> downloadFile(Long id) throws IOException {
        Optional<Contract> contract = contractRepository.findById(id);
        if(contract.isPresent()){
            URL url = new URL(contract.get().getUrlFile());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return ResponseEntity.notFound().build();
            }

            InputStream inputStream = connection.getInputStream();
            byte[] bytes = IOUtils.toByteArray(inputStream);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDispositionFormData("attachment", getFileNameFromUrl(contract.get().getUrlFile()));
            headers.setContentLength(bytes.length);

            return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
        }
        return null;
    }
    private String getFileNameFromUrl(String fileUrl) {
        String[] parts = fileUrl.split("/");
        return parts[parts.length - 1].split("\\?")[0];
    }
}
