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

    String ATTRIBUTE_NAME = "notesDao";

    /** function tries to insert new message to DB
     * function returns integer as a result
      */
    int add_note(Long sender_id, Long receiver_id, String text);

    /** function tries to delete message from DB
     */
    int delete_note(long id);

    /** function retrieves a list of notes (messages) associated with a user.
      */
    List<Note> get_notes(Long user_id);
}
