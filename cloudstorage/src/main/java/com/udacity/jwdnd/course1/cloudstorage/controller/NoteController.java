package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.security.token.SuperDuperDriveToken;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/note")
public class NoteController {
    private NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping("/saveNote")
    public String saveNote(Model model, Note note, Authentication authentication) {
        SuperDuperDriveToken token = (SuperDuperDriveToken) authentication;

        int result = 0;
        note.setUserId(token.getUserId());

        if (note.getNoteDescription().length() <= 1000) {
            if (note.getNoteId() == null) {
                //create note
                result = noteService.addNote(note);
            } else {
                //update note
                result = noteService.updateNote(note);
            }

            if (result <= 0) {
                model.addAttribute("resultMessage", "Your changes were not saved. ");
            }
        } else {
            model.addAttribute("resultMessage", "Note can't be saved as description exceed 1000 characters. ");
        }

        return "/result";
    }

    @PostMapping("/deleteNote")
    public String deleteNote(Model model, Authentication authentication, Note note) {
        SuperDuperDriveToken token = (SuperDuperDriveToken) authentication;

        //delete note
        int result = 0;
        result = noteService.deleteNoteByNoteId(note.getNoteId());

        if (result <= 0) {
            model.addAttribute("resultMessage", "Your changes were not saved.");
        }
        return "/result";
    }
}
