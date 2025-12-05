package com.jminiapp.examples.notesapp.model;

import java.util.ArrayList;
import java.util.List;

public class NotesState {

    private final List<Note> notes = new ArrayList<>();

    public List<Note> getNotes() {
        return notes;
    }

    public void addNote(String text) {
        notes.add(new Note(text));
    }

    public boolean removeNote(int index) {
        if (index >= 0 && index < notes.size()) {
            notes.remove(index);
            return true;
        }
        return false;
    }
}
