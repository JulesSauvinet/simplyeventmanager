package fr.univlyon1.sem.validator;

import fr.univlyon1.sem.bean.FileUpload;
import org.springframework.stereotype.Repository;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Repository("fileValidator")
public class FileValidator implements Validator{

    @Override
    public boolean supports(Class classe) {
        return FileUpload.class.isAssignableFrom(classe);
    }

    @Override
    public void validate(Object target, Errors errors) {

        FileUpload file = (FileUpload) target;

        if (file.getFiles().size() == 0) {
            errors.rejectValue("file", "uploadFile.selectFile", "Please select a file!");
        }
        List<MultipartFile> files = file.getFiles();
        for (MultipartFile mp : files){
            if (mp.isEmpty() || mp.getSize() == 0)
            errors.rejectValue("file", "uploadFile.selectFiles", "One of the file is empty!");
        }
    }
}
