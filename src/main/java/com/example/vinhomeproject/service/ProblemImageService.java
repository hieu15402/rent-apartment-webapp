package com.example.vinhomeproject.service;

import com.example.vinhomeproject.dto.ProblemImageDTO;
import com.example.vinhomeproject.mapper.ProblemImageMapper;
import com.example.vinhomeproject.models.Post;
import com.example.vinhomeproject.models.PostImage;
import com.example.vinhomeproject.models.ProblemImage;
import com.example.vinhomeproject.models.Problems;
import com.example.vinhomeproject.repositories.ProblemImageRepository;
import com.example.vinhomeproject.repositories.ProblemsRepository;
import com.example.vinhomeproject.response.ResponseObject;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProblemImageService {
    @Autowired
    private ProblemImageRepository repository;
    @Autowired
    private ProblemsRepository problemsRepository;
    @Autowired
    private ProblemImageMapper mapper;

    public ResponseEntity<ResponseObject> getAll() {
        return ResponseEntity.ok(new ResponseObject(
                "successfully",
                repository.findAll()
        ));
    }
    public ResponseEntity<ResponseObject> getById(Long id) {

        return ResponseEntity.ok(new ResponseObject(
                "successfully",
                repository.findById(id)
        ));
    }
    public ResponseEntity<ResponseObject> delete(Long id) {
        Optional<ProblemImage> problemImage = repository.findById(id);
        if (problemImage.isPresent()) {
            problemImage.get().setStatus(!problemImage.get().isStatus());
            return ResponseEntity.ok(new ResponseObject(
                    "Delete successfully",
                   null
            ));
        }else {
            return ResponseEntity.ok(new ResponseObject(
                    "Not found problem image",
                    null
            ));
        }
    }
    public ResponseEntity<ResponseObject> update(Long id, ProblemImageDTO problemImageDTO) {
        Optional<ProblemImage> ps = repository.findById(id);
        if (ps.isPresent()) {
            mapper.update(problemImageDTO,ps.get());
            repository.save(ps.get());
            return ResponseEntity.ok(new ResponseObject(
                    "Update successfully",
                    null
            ));
        }else {
            return ResponseEntity.ok(new ResponseObject(
                    "Not found problem image",
                    null
            ));
        }
    }
    public ResponseEntity<String> createPostImage(MultipartFile multipartFile, Long id) {
        Optional<Problems> problems = problemsRepository.findById(id);
        if(problems.isPresent()){
            ProblemImage ps = new ProblemImage();
            if (multipartFile != null) {
                String imageUrl = this.upload(multipartFile);
                ps.setImage_url(imageUrl);
                ps.setProblems(problems.get());
                ps.setStatus(true);
                repository.save(ps);
                return ResponseEntity.ok("Image uploaded successfully. Image URL: " + imageUrl);
            } else {
                return ResponseEntity.badRequest().body("PostImage object is null");
            }
        }
        return ResponseEntity.badRequest().body("Problems is not exist");
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
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
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
            fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));

            File file = this.convertToFile(multipartFile, fileName);
            String URL = this.uploadFile(file, fileName);
            file.delete();
            return URL;
        } catch (Exception e) {
            e.printStackTrace();
            return "Image couldn't upload, Something went wrong";
        }
    }
}
