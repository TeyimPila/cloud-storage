package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.repositories.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    final NoteRepository noteRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<Note> getUserNotes(User user) {
        return noteRepository.findByUser(user);
    }

    public Note createOrUpdate(Note note) {
        if (note.getNoteId() == null) {
            return saveNote(note);
        } else {
            return updateNote(note);
        }
    }

    public Note saveNote(Note note) {
        Integer id = noteRepository.save(note);
        return noteRepository.find(id);
    }

    public Note updateNote(Note note) {
        Integer id = noteRepository.update(note);
        return noteRepository.find(id);
    }
}
