---
sidebar_position: 2
---

# Notes App Example Application

A simple notes management application demonstrating persistent state and JSON storage.

**Features:**

- Add new notes with text content
- List all saved notes
- Remove notes by index
- Automatic JSON persistence
- Interactive menu system

### Key Concepts Demonstrated

- Custom model creation (Note, NotesState)
- JSON adapter for file persistence
- List-based state management
- CRUD operations (Create, Read, Delete)
- Automatic save on data modification

### Quick Start

```bash
cd examples/notes-app
mvn clean install
mvn exec:java
```

### Step-by-Step Tutorial

#### 1. Project Setup

Create the Maven project structure:

```
notes-app/
├── pom.xml
└── src/main/java/com/jminiapp/examples/notesapp/
    ├── NotesApp.java
    ├── adapters/
    │   └── NotesJsonAdapter.java
    └── model/
        ├── Note.java
        └── NotesState.java
```

**pom.xml:**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
         http://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>com.jminiapp</groupId>
        <artifactId>jminiapp-parent</artifactId>
        <version>1.0.0-SNAPSHOT</version>
        <relativePath>../..</relativePath>
    </parent>

    <artifactId>notes-app</artifactId>
    <packaging>jar</packaging>

    <name>Notes App Example</name>
    <description>Notes application using JMiniApp framework</description>

    <dependencies>
        <dependency>
            <groupId>com.jminiapp</groupId>
            <artifactId>jminiapp-core</artifactId>
            <version>${project.version}</version>
        </dependency>

        <dependency>
            <groupId>com.google.code.gson</groupId>
            <artifactId>gson</artifactId>
            <version>2.10.1</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.11.0</version>
                <configuration>
                    <source>17</source>
                    <target>17</target>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>exec-maven-plugin</artifactId>
                <version>3.1.0</version>
                <configuration>
                    <mainClass>com.jminiapp.examples.notesapp.NotesApp</mainClass>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-shade-plugin</artifactId>
                <version>3.5.0</version>
                <executions>
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <goal>shade</goal>
                        </goals>
                        <configuration>
                            <transformers>
                                <transformer implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
                                    <mainClass>com.jminiapp.examples.notesapp.NotesApp</mainClass>
                                </transformer>
                            </transformers>
                            <finalName>notes-app</finalName>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```

#### 2. Create the Note Model

**Note.java:**

```java
package com.jminiapp.examples.notesapp.model;

public class Note {

    private String text;

    public Note() {}

    public Note(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
```

#### 3. Create the State Manager

**NotesState.java:**

```java
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
```

#### 4. Implement the JSON Adapter

**NotesJsonAdapter.java:**

```java
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
```

#### 5. Build the Main Application

**NotesApp.java:**

```java
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
```

### How It Works

1. **Initialization**: On startup, `NotesJsonAdapter` loads existing notes from `notes.json` or creates an empty state
2. **User Interaction**: The main loop presents a menu for managing notes
3. **Persistence**: Every add/remove operation automatically saves the state to `notes.json`
4. **Data Structure**: Notes are stored as a list within `NotesState`, managed through simple CRUD methods

### Usage Example

```
Welcome to Notes App!

--- NOTES APP ---
1) List notes
2) Add note
3) Remove note
4) Exit
Choose: 2
Enter note text: Buy groceries

--- NOTES APP ---
1) List notes
2) Add note
3) Remove note
4) Exit
Choose: 2
Enter note text: Call dentist

--- NOTES APP ---
1) List notes
2) Add note
3) Remove note
4) Exit
Choose: 1

Your notes:
0) Buy groceries
1) Call dentist

--- NOTES APP ---
1) List notes
2) Add note
3) Remove note
4) Exit
Choose: 3
Enter index to remove: 0
Removed.
```

### Storage Format

The `notes.json` file stores all notes:

```json
{
  "notes": [
    {
      "text": "Buy groceries"
    },
    {
      "text": "Call dentist"
    }
  ]
}
```

This example demonstrates a practical application with persistent storage, showing how to build a simple CRUD app with JSON serialization using plain Java and Gson.

## Author

Jorge Gabriel Várguez Suárez
Notes App Example – 2025
