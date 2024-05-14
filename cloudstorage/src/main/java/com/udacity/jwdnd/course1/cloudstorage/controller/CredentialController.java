package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.security.token.SuperDuperDriveToken;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/credential")
public class CredentialController {

    private CredentialService credentialService;

    public CredentialController(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @PostMapping("/saveCredential")
    public String saveCredential(Model model, Credential credential, Authentication authentication) {
        SuperDuperDriveToken token = (SuperDuperDriveToken) authentication;

        int result = 0;
        credential.setUserid(token.getUserId());
        credential.setKey(token.getSalt());
        if (credential.getCredentialId() == null) {
            //create credential
            result = credentialService.addCredential(credential);
        } else {
            //update credential
            result = credentialService.updateCredential(credential);
        }

        if (result <= 0) {
            model.addAttribute("resultMessage", "Your changes were not saved.");
        }

        return "/result";
    }

    @PostMapping("/deleteCredential")
    public String deleteNote(Model model, Authentication authentication, Credential credential) {
        SuperDuperDriveToken token = (SuperDuperDriveToken) authentication;
        //delete credential

        int result = 0;
        result = credentialService.deleteCredentialByCredentialId(credential.getCredentialId());

        if (result <= 0) {
            model.addAttribute("resultMessage", "Your changes were not saved.");
        }
        return "/result";
    }
}
