package DAOinterfaces;

import Objects.Note;

import java.util.List;

// An interface representing the contract for interacting with notes in a data storage.
public interface NotesDao {

    // Constants for result status of note operations
    int SUCCESS_SENT = 1;
    int FAILED_SENT = 2;
    int SUCCESS_DELETED = 3;
    int FAILED_DELETED = 4;

    // Sends a note from a sender to a receiver.
    int sendNote(Long sender_id, Long receiver_id, String text);

    // Deletes a note sent from a sender to a receiver.
    int deleteNote(Long sender_id, Long receiver_id, String text);

    // Retrieves a list of notes associated with a user.
    List<Note> getNotes(Long user_id);
}
