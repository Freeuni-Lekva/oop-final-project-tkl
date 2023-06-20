package DAOinterfaces;

import Objects.User;

import java.util.List;

public interface UserDao {

    int INCORRECT_PASSWORD = -1;
    int ACCOUNT_NOT_FOUND = 0;
    int SUCCESSFULLY_LOGIN = 1;
    int ACCOUNT_FOUND = 2;
    int ACCOUNT_FOUND_BY_NAME = 3;
    int ACCOUNT_FOUND_BY_EMAIL = 4;
    int ACCOUNT_CREATED = 5;
    int SERVER_ERROR = 6;

    public static final String ATTRIBUTE_NAME = "userDao";

    /** function bellow returns list of all users which database contains, if exception occurs
     * function returns null
     */
    List<User> getUsers(String condition);

    /** function returns User objects which name is equals to received argument, if user doesn't
     * exist with received name function returns null
     */
    User getUserByName(String name);

    /** function works as same as getUserByName function
     */
    User getUserByEmail(String email);

    /** function tries to log in to existing user, function returns int
     * if there is no account with received name function returns "ACCOUNT_NOT_FOUND"
     * if received password is incorrect function returns "INCORRECT_PASSWORD"
     * if received password is correct function returns "SUCCESSFULLY_LOGIN"
     * function returns "SERVER_ERROR" if something happens on database
     */
    int tryToLogin(String name, String password);

    /** function tries to add new account on database, function returns int as same as "tryToLogin" function,
     * returns - > ACCOUNT_FOUND_BY_NAME, ACCOUNT_FOUND_BY_EMAIL, ACCOUNT_CREATED or SERVER_ERROR values
     */
    int register(String name, String realName, String realLastName, String email, String password);

    /** function returns unique id for new user.
     */
    long getNewUserID();
}
