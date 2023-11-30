package com.htsevv.utils;

import com.htsevv.constants.Constant;
import com.htsevv.exception.InvalidFileFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
public class ValidationUtils {

    public String truncateString(String name, int length){
        if(name!=null && name.length() > length)
            name = name.substring(0, length);
        return name;
    }

    public void validateUploadPhoto(MultipartFile file){
        String fileName = file.getOriginalFilename();
        if (fileName != null && (!(fileName.toLowerCase().endsWith(Constant.JPEG) || fileName.toLowerCase().endsWith(Constant.JPG)
                || fileName.toLowerCase().endsWith(Constant.GIF) || fileName.toLowerCase().endsWith(Constant.PNG) ||
                fileName.toLowerCase().endsWith("jfif")))) {
            log.error("Invalid Image format");
            throw new InvalidFileFormat("photo upload failed");
        }
    }

    public void validateUploadPdf(MultipartFile file){
        String fileName = file.getOriginalFilename();
        if (fileName != null && !(fileName.toLowerCase().endsWith(Constant.PDF))){
            log.error("Invalid File format. Please upload pdf file");
            throw new InvalidFileFormat("Please upload pdf file");
        }
    }
}
