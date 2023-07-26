package Tests;

import DAOinterfaces.UserDao;
import DAOs.UserSQL;
import Objects.User;
import junit.framework.TestCase;
import org.apache.commons.dbcp2.BasicDataSource;

public class TestUserSQL extends TestCase {

    public void testAllFunction(){

        ServerConfigurations.clearTable("users");

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(ServerConfigurations.URL);
        dataSource.setUsername(ServerConfigurations.USERNAME);
        dataSource.setPassword(ServerConfigurations.PASSWORD);

        UserDao users = new UserSQL(dataSource);

        newUsersTest(users);
        editProfileTest(users);
    }

    private void editProfileTest(UserDao users){

        User test1 = users.getUserByName("toms343");

        users.changeDescription(test1.getId(), "blabla");
        users.changeImagePath(test1.getId(), "naruto.jpg");
        users.changeRealLastName(test1.getId(), "gelovani");
        users.changeRealName(test1.getId(), "gela");

        test1 = users.getUserById(test1.getId());

        assertEquals(test1.getName(), "toms343");
        assertEquals(test1.getDescription(), "blabla");
        assertEquals(test1.getImagePath(), "naruto.jpg");
        assertEquals(test1.getRealName(), "gela");
        assertEquals(test1.getRealLastName(), "gelovani");
    }

    private void newUsersTest(UserDao users){

        int test1 = users.register("tyrion3", "Tyrion", "Lanister", "tyrion3");
        int test2 = users.register("toms343", "Toma", "Dadiani", "toms3");
        int test3 = users.register("toms343", "tom", "dad", "ravi");
        int test4 = users.register("katya", "asd", "a", "");
        int test5 = users.register("aSDasd", "test", "test", "test");

        assertEquals(test1, UserDao.ACCOUNT_CREATED);
        assertEquals(test2, UserDao.ACCOUNT_CREATED);
        assertEquals(test3, UserDao.ACCOUNT_FOUND_BY_NAME);
        assertEquals(test4, UserDao.NOT_ENOUGH_INFORMATION);
        assertEquals(test5, UserDao.INCORRECT_INFORMATION);

        test1 = users.tryToLogin("tyrion3", "tyrion3");
        test2 = users.tryToLogin("accountnotcreated", "asdasda");
        test3 = users.tryToLogin("toms343", "incorrectpass");

        assertEquals(test1, UserDao.SUCCESSFULLY_LOGIN);
        assertEquals(test2, UserDao.ACCOUNT_NOT_FOUND);
        assertEquals(test3, UserDao.INCORRECT_PASSWORD);
    }

}
