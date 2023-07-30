package Tests;

import DAOinterfaces.FriendRequestDao;
import DAOinterfaces.UserDao;
import DAOs.FriendRequestSQL;
import DAOs.UserSQL;
import Objects.User;
import junit.framework.TestCase;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class TestFriendRequestSQL extends TestCase {
    User fr1;
    User fr2;
    UserDao users;
    FriendRequestSQL friendRequest;

    // This method is executed before each test method
    @Before
    protected void setUp() {
        // Initialize variables here
        ServerConfigurations.clearTable("users");
        ServerConfigurations.clearTable("friend_requests");

        BasicDataSource dataSource = ServerConfigurations.getDataSource();

        users = new UserSQL(dataSource);
        friendRequest = new FriendRequestSQL(dataSource);

        addNewUsers(users);

        fr1 = users.getUserByName("fr1");
        fr2 = users.getUserByName("fr2");
    }

    @Test
    public void testAddFriendRequest(){
        long Success_sendFriendRequest = friendRequest.addFriendRequest(fr1.getId(), fr2.getId());
        long Failed_sendFriendRequest = friendRequest.addFriendRequest(fr1.getId(), fr2.getId());

        assertEquals(Success_sendFriendRequest, FriendRequestDao.SUCCESS_FRIEND_REQUEST);
        assertEquals(Failed_sendFriendRequest, FriendRequestDao.FAILED_FRIEND_REQUEST);
    }

    @Test
    public void testCheckIfFriendRequest(){
        assertEquals(false, friendRequest.checkFriendRequest(fr1.getId(), fr2.getId()));
        friendRequest.addFriendRequest(fr1.getId(), fr2.getId());
        assertEquals(true, friendRequest.checkFriendRequest(fr1.getId(), fr2.getId()));
    }

    @Test
    public void testRemoveFriendRequest(){
        int Remove_Failed = friendRequest.removeFriendRequest(fr1.getId(), fr2.getId());
        assertEquals(FriendRequestDao.FAILED_FRIEND_REQUEST_REMOVE, Remove_Failed);

        friendRequest.addFriendRequest(fr1.getId(), fr2.getId());

        int Remove_Success = friendRequest.removeFriendRequest(fr1.getId(), fr2.getId());
        assertEquals(FriendRequestDao.SUCCESS_FRIEND_REQUEST_REMOVE, Remove_Success);
    }

    @Test
    public void testGetReceivedFriendRequests(){
        friendRequest.addFriendRequest(fr1.getId(), fr2.getId());

        List<User> requests = friendRequest.getReceivedFriendRequests(fr2.getId());
        assertEquals(1, requests.size());

        User f1 = requests.get(0);
        assertEquals(f1.getId(), fr1.getId());
    }

    public void addNewUsers(UserDao users){
        users.register("fr1", "fr1", "fr","fr");
        users.register("fr2", "fr2", "fr","fr");
    }
}
