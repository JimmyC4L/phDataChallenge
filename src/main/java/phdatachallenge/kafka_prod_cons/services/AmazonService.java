package phdatachallenge.kafka_prod_cons.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

@Service
public class AmazonService {

    private final AmazonS3 s3client;

    private final String ENDPOINT_URL = "https://s3.us-east-2.amazonaws.com";
    private final String BUCKET_NAME = "phdata-challenge-bucket";

    @Autowired
    public AmazonService(AmazonS3 s3client) {
        this.s3client = s3client;
    }


    public String uploadFile(MultipartFile multipartFile) {
        String fileUrl = "";
        try {
            File file = convertMultiPartToFile(multipartFile);
            String fileName =  multipartFile.getOriginalFilename();
            fileUrl = ENDPOINT_URL + "/" + BUCKET_NAME + "/" + fileName;
            uploadFileTos3bucket(fileName, file);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileUrl;
    }

    public String uploadFile(File file) {
        String fileUrl = "";
        try {
            String fileName = "attacker" + "_" + new Date().getTime() + ".json";
            fileUrl = ENDPOINT_URL + "/" + BUCKET_NAME + "/" + fileName;
            uploadFileTos3bucket(fileName, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileUrl;
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private void uploadFileTos3bucket(String fileName, File file) {
        s3client.putObject(new PutObjectRequest(BUCKET_NAME, fileName, file));
    }

    public String deleteFileFromS3Bucket(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        s3client.deleteObject(new DeleteObjectRequest(BUCKET_NAME, fileName));
        return "Successfully deleted";
    }

}