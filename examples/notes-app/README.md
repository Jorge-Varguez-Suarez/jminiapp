# Notes App Example

A simple notes management application demonstrating the JMiniApp framework.

## Overview

This example shows how to create a mini-app using JMiniApp core that manages a list of notes. Users can add, list, and remove notes through an interactive menu, with automatic persistence to JSON storage.

## Features

- **List Notes**: View all saved notes
- **Add Note**: Create a new note with text content
- **Remove Note**: Delete a note by index
- **Persistent Storage**: Notes are automatically saved to JSON file

## Project Structure

```
notes-app/
├── pom.xml
├── README.md
└── src/main/java/com/jminiapp/examples/notesapp/
    ├── NotesApp.java                 # Main application class
    ├── adapters/
    │   └── NotesJsonAdapter.java     # JSON storage adapter
    └── model/
        ├── Note.java                 # Note model
        └── NotesState.java           # Application state
```

## Key Components

### Note
A simple model representing a text note.

### NotesState
Manages the list of notes with methods to:
- `addNote(text)`: Create a new note
- `removeNote(index)`: Delete a note by index
- `getNotes()`: Retrieve all notes

### NotesJsonAdapter
Handles JSON persistence:
- Loads notes from `notes.json` on startup
- Saves notes after each modification
- Creates the file automatically if it doesn't exist

### NotesApp
The main application class that:
- `onStart()`: Display welcome message
- `onLoop()`: Main menu loop handling user input
- `start()`: Run the application

## Building and Running

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Build the project

From the **project root** (not the examples/notes-app directory):
```bash
mvn clean install
```

This will build both the jminiapp-core module and the notes-app example.

### Run the application

Option 1: Using Maven exec plugin (from the examples/notes-app directory)
```bash
cd examples/notes-app
mvn exec:java
```

Option 2: Using the packaged JAR (from the examples/notes-app directory)
```bash
cd examples/notes-app
java -jar target/notes-app.jar
```

Option 3: From the project root
```bash
cd examples/notes-app && mvn exec:java
```

## Usage Example

### Basic Operations

```
Welcome to Notes App!

--- NOTES APP ---
1) List notes
2) Add note
3) Remove note
4) Exit
Choose: 2
Enter note text: Buy groceries
```

```
--- NOTES APP ---
1) List notes
2) Add note
3) Remove note
4) Exit
Choose: 2
Enter note text: Call dentist
```

```
--- NOTES APP ---
1) List notes
2) Add note
3) Remove note
4) Exit
Choose: 1

Your notes:
0) Buy groceries
1) Call dentist
```

```
--- NOTES APP ---
1) List notes
2) Add note
3) Remove note
4) Exit
Choose: 3
Enter index to remove: 0
Removed.
```

## Storage

The application stores notes in a JSON file that is automatically created:
```
notes.json
```

The file is created in the directory where the application is executed and follows this format:
```json
{
  "notes": [
    {"text": "Buy groceries"},
    {"text": "Call dentist"}
  ]
}
```

## Author

Jorge Gabriel Várguez Suárez
Notes App Example – 2025
