import java.io.File  // This library is required to export text files

// This is an object to hold note title and content. I wanted to use dictiorany/map, but it did not work
// so I made own object. It is also part of strech
data class Note(
    var id: Int,
    var title: String,
    var content: String
)

//  object to store notes.
object NoteDatabase {
    private val notes = mutableListOf<Note>()
    private var nextId = 1// adds id for notes
    // Function to add or update a note
    fun addOrUpdate(note: Note) {
        if (note.id == 0) {
            note.id = nextId++
            notes.add(note)
        } else {
            val existingNoteIndex = notes.indexOfFirst { it.id == note.id }
            if (existingNoteIndex != -1) {
                notes[existingNoteIndex] = note
            }
        }
    }
    // Function to get all notes
    fun getAllNotes(): List<Note> {
        return notes.toList()
    }
    // Function to get  notes based on their title
    fun getNoteByTitle(title: String): Note? {
        return notes.find { it.title == title }
    }
}
// Main function to run the note-taking app
fun main() {
    var choice: Int
    //Do while loop for error hanlding
    do {
        println("1. Write a new note")
        println("2. View a note")
        println("3. Edit an existing note")
        println("4. Export notes to a text file")
        println("5. Exit")
        print("Enter your choice: ")
        choice = readLine()?.toIntOrNull() ?: 0
        // Handling user's choice using when expression. Same like cases in Java, also part of stretch
        when (choice) {
            1 -> writeNewNote()
            2 -> viewNote()
            3 -> editExistingNote()
            4 -> exportNotesToFile()
            5 -> println("Exiting...")
            else -> println("Invalid choice. Please enter a number between 1 and 5.")
        }
    } while (choice != 5)
}
// Function to write a new note
fun writeNewNote() {
    println("Enter the title of the note:")
    val title = readLine() ?: ""
    println("Enter the content of the note:")
    val content = readLine() ?: ""
    val note = Note(0, title, content)
    NoteDatabase.addOrUpdate(note)
    println("Note added successfully!")
}
// Function to view existing note's
fun viewNote() {
    println("Enter the title of the note you want to view:")
    val titleToView = readLine() ?: ""
    val note = NoteDatabase.getNoteByTitle(titleToView)
    if (note != null) {
        println("Title: ${note.title}")
        println("Content: ${note.content}")
    } else {
        println("Note not found with title: $titleToView")
    }
}
//Function that edits note's
fun editExistingNote() {
    println("Enter the title of the note you want to edit:")
    val titleToEdit = readLine() ?: ""
    val existingNote = NoteDatabase.getNoteByTitle(titleToEdit)
    if (existingNote != null) {
        println("Enter the new title of the note:")
        val newTitle = readLine() ?: ""
        println("Enter the new content of the note:")
        val newContent = readLine() ?: ""
        val updatedNote = Note(existingNote.id, newTitle, newContent)
        NoteDatabase.addOrUpdate(updatedNote)
        println("Note edited successfully!")
    } else {
        println("Note not found with title: $titleToEdit")
    }
}
// Function to export notes to a text file
fun exportNotesToFile() {
    val notes = NoteDatabase.getAllNotes() // Getting all notes
    if (notes.isNotEmpty()) {
        val file = File("notes.txt") // Creating the File
        file.bufferedWriter().use { out ->
            notes.forEach { note ->
                out.write("Title: ${note.title}\n")
                out.write("Content: ${note.content}\n\n")
            }
        }
        println("Notes exported to notes.txt successfully!")
    } else {
        println("No notes found to export.")
    }
}
