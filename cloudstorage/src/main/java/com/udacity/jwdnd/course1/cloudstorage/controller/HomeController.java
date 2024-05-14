package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.entity.FileUpload;
import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.security.token.SuperDuperDriveToken;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {
    private FileService fileService;
    private NoteService noteService;
    private CredentialService credentialService;

    public HomeController(FileService fileService, NoteService noteService, CredentialService credentialService) {
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialService = credentialService;
    }

    @GetMapping
    public String getHomePage(Model model, Authentication authentication) {
        //check if user logged in
        if (authentication != null) {
            SuperDuperDriveToken token = (SuperDuperDriveToken) authentication;

            //get file list
            List<FileUpload> fileUploadList = fileService.getFileListByUserId(token.getUserId());
            model.addAttribute("fileUploadList", fileUploadList);

            //get note list
            List<Note> noteList = noteService.getNoteListByUserId(token.getUserId());
            model.addAttribute("noteList", noteList);

            //get credential list
            List<Credential> credentialList = credentialService.getCredentialListByUserId(token.getUserId());
            model.addAttribute("credentialList", credentialList);

            return "/home";
        } else {
            return "redirect:/login";
        }
    }
}
