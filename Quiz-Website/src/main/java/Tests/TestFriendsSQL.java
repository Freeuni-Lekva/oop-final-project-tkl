package Tests;

import DAOinterfaces.FriendsDao;
import DAOinterfaces.UserDao;
import DAOs.FriendsSQL;
import DAOs.UserSQL;
import Objects.User;
import junit.framework.TestCase;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class TestFriendsSQL extends TestCase {
    User fr1;
    User fr2;
    UserDao users;
    FriendsSQL friends;

    // This method is executed before each test method
    @Before
    protected void setUp() {
        // Initialize variables here
        ServerConfigurations.clearTable("users");
        ServerConfigurations.clearTable("friendships");

        BasicDataSource dataSource = ServerConfigurations.getDataSource();

        users = new UserSQL(dataSource);
        friends = new FriendsSQL(dataSource);

        addNewUsers(users);

        fr1 = users.getUserByName("f1");
        fr2 = users.getUserByName("f2");
    }

    @Test
    public void testAddFriendship(){
        int Success_Add = friends.addFriendship(fr1.getId(), fr2.getId());
        assertEquals(Success_Add, FriendsDao.SUCCESS_ADDED);

        int Failed_Add = friends.addFriendship(fr1.getId(), fr2.getId());
        assertEquals(Failed_Add, FriendsDao.FAILED_ADDED);
    }

    @Test
    public void testRemoveFriendship(){
        int Failed_Remove = friends.removeFriendship(fr1.getId(), fr2.getId());
        assertEquals(Failed_Remove, FriendsDao.FAILED_REMOVED);

        friends.addFriendship(fr1.getId(), fr2.getId());

        int Success_Remove = friends.removeFriendship(fr1.getId(), fr2.getId());
        assertEquals(Success_Remove, FriendsDao.SUCCESS_REMOVED);
    }

    @Test
    public void testCheckIfFriends(){
        boolean notFriends = friends.checkIfFriends(fr1.getId(), fr2.getId());
        assertEquals(notFriends, false);

        friends.addFriendship(fr1.getId(), fr2.getId());

        boolean areFriends = friends.checkIfFriends(fr1.getId(), fr2.getId());
        assertEquals(areFriends, true);
    }

    @Test
    public void testGetUserFriends(){
        friends.addFriendship(fr1.getId(), fr2.getId());

        List<User> getFriends = friends.getUserFriends(fr1.getId());

        assertEquals(1, getFriends.size());
        assertEquals(fr2.getId(), getFriends.get(0).getId());
    }

    public void addNewUsers(UserDao users){
        users.register("f1", "f1", "f","f");
        users.register("f2", "f2", "f","f");
    }
}
