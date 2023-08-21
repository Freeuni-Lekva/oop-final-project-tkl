package DAOs;

import DAOinterfaces.NotesDao;
import Objects.Note;
import Objects.User;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotesSQL implements NotesDao {
    private final BasicDataSource dataSource;
    public NotesSQL(BasicDataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public int send_note(Long sender_id, Long receiver_id, String text) {
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO notes(sender_id,receiver_id, note) VALUES(?,?,?)");
            preparedStatement.setLong(1, sender_id);
            preparedStatement.setLong(2, receiver_id);
            preparedStatement.setString(3, text);
            if(preparedStatement.executeUpdate() != 0) return SUCCESS_SENT;
        } catch (SQLException e) {
            return FAILED_SENT;
        }
        return FAILED_SENT;
    }

    @Override
    public int delete_note(Long sender_id, Long receiver_id, String text) {
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM notes WHERE sender_id = ? AND receiver_id = ? AND note = ?");
            preparedStatement.setLong(1, sender_id);
            preparedStatement.setLong(2, receiver_id);
            preparedStatement.setString(3, text);

            if(preparedStatement.executeUpdate() != 0) return SUCCESS_DELETED;

        } catch (SQLException e) {
            return FAILED_DELETED;
        }
        return FAILED_DELETED;
    }

    @Override
    public List<Note> get_notes(Long user_id) {
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT sender_id, note FROM notes where receiver_id = ?");
            preparedStatement.setLong(1, user_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Note> notes = new ArrayList<>();

            while (resultSet.next()) {
                int senderId = resultSet.getInt("sender_id");
                String noteText = resultSet.getString("note");
                notes.add(new Note(senderId, noteText));
            }

            resultSet.close();
            return notes;


        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
