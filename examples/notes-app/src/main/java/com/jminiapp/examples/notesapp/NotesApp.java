package com.jminiapp.examples.notesapp;

import com.jminiapp.examples.notesapp.adapters.NotesJsonAdapter;
import com.jminiapp.examples.notesapp.model.NotesState;

import java.util.Scanner;

public class NotesApp {

    private final NotesJsonAdapter adapter = new NotesJsonAdapter("notes.json");
    private final NotesState state = adapter.load();
    private boolean running = true;

    public void onStart() {
        System.out.println("Welcome to Notes App!");
    }

    public void onLoop() {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n--- NOTES APP ---");
        System.out.println("1) List notes");
        System.out.println("2) Add note");
        System.out.println("3) Remove note");
        System.out.println("4) Exit");
        System.out.print("Choose: ");
        int option = sc.nextInt();
        sc.nextLine();

        switch (option) {
            case 1 -> {
                System.out.println("\nYour notes:");
                for (int i = 0; i < state.getNotes().size(); i++) {
                    System.out.println(i + ") " + state.getNotes().get(i).getText());
                }
            }
            case 2 -> {
                System.out.print("Enter note text: ");
                String text = sc.nextLine();
                state.addNote(text);
                adapter.save(state);
            }
            case 3 -> {
                System.out.print("Enter index to remove: ");
                int index = sc.nextInt();
                if (state.removeNote(index)) {
                    System.out.println("Removed.");
                    adapter.save(state);
                } else {
                    System.out.println("Invalid index.");
                }
            }
            case 4 -> running = false;
        }
    }

    public void start() {
        onStart();
        while (running) {
            onLoop();
        }
    }

    public static void main(String[] args) {
        new NotesApp().start();
    }
}
