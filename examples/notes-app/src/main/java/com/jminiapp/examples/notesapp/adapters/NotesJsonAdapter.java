package com.jminiapp.examples.notesapp.adapters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jminiapp.examples.notesapp.model.NotesState;

import java.io.*;

public class NotesJsonAdapter {

    private final File file;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public NotesJsonAdapter(String filePath) {
        this.file = new File(filePath);
    }

    public NotesState load() {
        if (!file.exists()) {
            return new NotesState();
        }

        try (Reader reader = new FileReader(file)) {
            return gson.fromJson(reader, NotesState.class);
        } catch (IOException e) {
            throw new RuntimeException("Error loading notes state", e);
        }
    }

    public void save(NotesState state) {
        try (Writer writer = new FileWriter(file)) {
            gson.toJson(state, writer);
        } catch (IOException e) {
            throw new RuntimeException("Error saving notes state", e);
        }
    }
}
