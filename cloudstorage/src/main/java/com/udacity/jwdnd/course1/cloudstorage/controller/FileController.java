package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.FileUpload;
import com.udacity.jwdnd.course1.cloudstorage.security.token.SuperDuperDriveToken;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/file")
public class FileController {
    private FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/uploadFile")
    public String uploadFile(Model model, MultipartFile fileUpload, Authentication authentication) {
        SuperDuperDriveToken token = (SuperDuperDriveToken) authentication;

        //upload file
        int result = 0;
        if (!fileUpload.isEmpty()) {
            //check if filename not existed
            if (fileService.getFileByUserIdAndFileName(token.getUserId(), fileUpload.getOriginalFilename()) == null) {
                try {
                    FileUpload file = new FileUpload(null, fileUpload.getOriginalFilename(), fileUpload.getContentType(),
                            String.valueOf(fileUpload.getSize()), ((SuperDuperDriveToken) authentication).getUserId(), fileUpload.getBytes());
                    result = fileService.uploadFile(file);

                    if (result <= 0) {
                        model.addAttribute("resultMessage", "Your changes were not saved.");
                    }
                    return "/result";
                } catch (IOException e) {
                    model.addAttribute("resultMessage", "Your changes were not saved.");
                }
            } else {
                model.addAttribute("resultMessage", "You can't upload two files with the same name.");
                return "/result";
            }
        }

        return "/home";
    }

    @PostMapping(params = "viewFile")
    public ResponseEntity viewFile(HttpServletRequest request, FileUpload fileUpload, HttpServletResponse response) {
        FileUpload file = fileService.getFileByFileId(fileUpload.getFileId());

        //download file
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getFileName())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(file.getFileData());
    }

    @PostMapping(params = "deleteFile")
    public String deleteFile(Model model, Authentication authentication, FileUpload fileUpload) {
        SuperDuperDriveToken token = (SuperDuperDriveToken) authentication;

        //delete file
        int result = 0;
        result = fileService.deleteFileByFileId(fileUpload.getFileId());

        if (result <= 0) {
            model.addAttribute("resultMessage", "Your changes were not saved.");
        }
        return "/result";
    }
}
