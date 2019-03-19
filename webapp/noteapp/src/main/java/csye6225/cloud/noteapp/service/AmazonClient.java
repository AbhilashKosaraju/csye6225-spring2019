package csye6225.cloud.noteapp.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
//import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Service
public class AmazonClient {

    private AmazonS3 s3client;

    @Value("${spring.amazonProperties.endpoint}")
    private String endpointUrl;
    @Value("${spring.amazonProperties.bucketName}")
    private String bucketName;
    @Value("${spring.datasource.url}")
    private String rdsUrl;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    @PostConstruct
    private void initializeAmazon() {
        this.s3client = AmazonS3ClientBuilder.standard()
                .withCredentials(new InstanceProfileCredentialsProvider(false))
                .build();
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        try {
            File convFile = new File(file.getOriginalFilename());
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
            return convFile;
        } catch (IOException e) {
            //LOG.error("Error converting file", e);
            throw new IOException("Error converting file", e);
        }
    }

    private void uploadFileTos3bucket(String fileName, File file) {
        System.out.println("Uploading file started ");
        s3client.putObject(new PutObjectRequest(bucketName, fileName, file).withCannedAcl(CannedAccessControlList.PublicRead));
        System.out.println("Uploading file done");
    }

    public String uploadFile(MultipartFile multipartFile, String uuid) {

        String fileUrl = "";
        try {
            File file = convertMultiPartToFile(multipartFile);
            String fileName = uuid + "-" + multipartFile.getOriginalFilename();
            fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
            uploadFileTos3bucket(fileName, file);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileUrl;
    }

    public String deleteFileFromS3Bucket(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        s3client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
        return "Successfully deleted";
    }

    public Connection getRemoteConnection() {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                String jdbcUrl = rdsUrl + "?user=" + username + "&password=" + password;
                Connection con = DriverManager.getConnection(jdbcUrl);
                return con;
            }
            catch (ClassNotFoundException e) {
            }
            catch (SQLException e) {
            }

        return null;
    }
}