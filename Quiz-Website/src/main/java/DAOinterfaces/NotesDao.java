package DAOinterfaces;

import Objects.Note;

import java.util.List;

public interface NotesDao {
    int SUCCESS_SENT = 1;
    int FAILED_SENT = 2;
    int SUCCESS_DELETED = 3;
    int FAILED_DELETED = 4;
    void send_note(Long sender_id, Long receiver_id, String text);
    void delete_note(Long sender_id, Long receiver_id, String text);
    List<Note> get_notes(Long user_id);
}
