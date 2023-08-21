package DAOs;

import DAOinterfaces.NotesDao;
import Objects.Note;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotesSQL implements NotesDao {
    private final BasicDataSource dataSource;
    private FriendsSQL friendsSQL;
    public NotesSQL(BasicDataSource dataSource){
        this.dataSource = dataSource;
        friendsSQL = new FriendsSQL(dataSource);
    }

    @Override
    public int sendNote(Long sender_id, Long receiver_id, String text) {
        if(!friendsSQL.checkIfFriends(sender_id, receiver_id)) {
            return FAILED_SENT;
        }
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
    public int deleteNote(long id) {
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM notes WHERE id = ?");
            preparedStatement.setLong(1, id);


            if(preparedStatement.executeUpdate() != 0) return SUCCESS_DELETED;

        } catch (SQLException e) {
            return FAILED_DELETED;
        }
        return FAILED_DELETED;
    }

    @Override
    public List<Note> getNotes(Long user_id) {
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, sender_id, note FROM notes where receiver_id = ? ORDER BY id DESC");
            preparedStatement.setLong(1, user_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Note> notes = new ArrayList<>();

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                long senderId = resultSet.getLong("sender_id");
                String noteText = resultSet.getString("note");
                notes.add(new Note(id, senderId, noteText));
            }

            resultSet.close();
            return notes;

        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
