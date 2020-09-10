package com.dsactivies.activitiesDsTch.Services;

import com.dsactivies.activitiesDsTch.models.FilesDoc;
import com.dsactivies.activitiesDsTch.repository.CodeActivitiesRepository;
import com.dsactivies.activitiesDsTch.repository.FilesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import com.cloudinary.Cloudinary;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.io.IOException;
import java.util.Map;

@Service
public class FIleService {
    @Autowired
    private Cloudinary cloudinaryConfig;
    
    @Value("${app.upload.dir:${user.home}}")
    public String uploadDir;

    public void uploadFile(MultipartFile file) {
        try {
            File uploadedFile = convertMultiPartToFile(file);
            Map uploadResult = cloudinaryConfig.uploader().upload(uploadedFile, ObjectUtils.emptyMap());
            Path copyLocation = Paths
                    .get(uploadDir + File.separator + StringUtils.cleanPath(file.getOriginalFilename()));
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
            return  uploadResult.get("url").toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new FileStorageException("Could not store file " + file.getOriginalFilename()
                    + ". Please try again!");
        }
    }
    public class FileStorageException extends RuntimeException throws IOException{

        private static final long serialVersionUID = 1L;
        private String msg;

        public FileStorageException(String msg) {
            this.msg = msg;
        }

        public String getMsg() {
            return msg;
        }
    }

}
