package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public List<Note> getNoteListByUserId(int userId) {
        return noteMapper.getNoteListByUserId(userId);
    }

    public int addNote(Note note) {
        return noteMapper.addNote(note);
    }

    public int updateNote(Note note) {
        return noteMapper.updateNote(note);
    }

    public int deleteNoteByNoteId(Integer noteId) {
        return noteMapper.deleteNoteByNoteId(noteId);
    }
}
