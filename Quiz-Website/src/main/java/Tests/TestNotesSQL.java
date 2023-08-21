package Tests;

import DAOinterfaces.NotesDao;
import DAOinterfaces.UserDao;
import DAOs.FriendsSQL;
import DAOs.NotesSQL;
import DAOs.UserSQL;
import Objects.Note;
import Objects.User;
import junit.framework.TestCase;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class TestNotesSQL extends TestCase {

    User sender;
    User receiver;
    String text;
    UserDao users;
    NotesSQL notes;
    FriendsSQL friends;

    // This method is executed before each test method
    @Before
    protected void setUp() {
        ServerConfigurations.clearTable("users");
        ServerConfigurations.clearTable("friendships");
        ServerConfigurations.clearTable("notes");

        BasicDataSource dataSource = ServerConfigurations.getDataSource();

        users = new UserSQL(dataSource);
        notes = new NotesSQL(dataSource);
        friends = new FriendsSQL(dataSource);

        addNewUsers(users);

        sender = users.getUserByName("s");
        receiver = users.getUserByName("r");
        text = "send not from sender_id to receiver_id";
    }

    @Test
    public void testSendNote(){
        // FAILED SEND NOTE
        int failed = notes.sendNote(sender.getId(), receiver.getId(), text);
        assertEquals(failed, NotesDao.FAILED_SENT);

        // SUCCESS SEND NOTE
        friends.addFriendship(sender.getId(), receiver.getId());
        int success = notes.sendNote(sender.getId(), receiver.getId(), text);
        assertEquals(success, NotesDao.SUCCESS_SENT);
    }

    @Test
    public void testDeleteNote(){
//        // FAILED DELETE NOTE
//        int failed1 = notes.deleteNote(sender.getId(), receiver.getId(), text);
//        assertEquals(failed1, NotesDao.FAILED_DELETED);
//
//        friends.addFriendship(sender.getId(), receiver.getId());
//        String randomText = "RandomText";
//
//        // SENT NOTE
//        notes.sendNote(sender.getId(),receiver.getId(), text);
//
//        int failed2 = notes.deleteNote(sender.getId(), receiver.getId(), randomText);
//        assertEquals(failed2, NotesDao.FAILED_DELETED);
//
//        // SUCCESS DELETE NOTE
//        int success = notes.deleteNote(sender.getId(), receiver.getId(), text);
//        assertEquals(success, NotesDao.SUCCESS_DELETED);
    }

    @Test
    public void testGetNotes(){
        friends.addFriendship(sender.getId(), receiver.getId());
        notes.sendNote(sender.getId(), receiver.getId(), text);
        List<Note> list = notes.getNotes(receiver.getId());

        assertEquals(1, list.size());
        assertEquals(text, list.get(0).getText());
    }

    public void addNewUsers(UserDao users){
        users.register("s", "s", "s","s");
        users.register("r", "r", "r","r");
    }
}
