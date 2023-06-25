package DAOinterfaces;

import Objects.User;

import java.util.List;

public interface UserDao {

    int INCORRECT_PASSWORD = -1;
    int ACCOUNT_NOT_FOUND = 0;
    int SUCCESSFULLY_LOGIN = 1;
    int ACCOUNT_FOUND_BY_NAME = 3;
    int ACCOUNT_CREATED = 4;
    int SERVER_ERROR = 5;
    int SUCCESS_UPDATE = 6;
    int ERROR_UPDATE = 7;

    String ATTRIBUTE_NAME = "userDao";
    String MESSAGE_ATTRIBUTE_NAME = "userMessage";

    /** function bellow returns list of all users which database contains, if exception occurs
     * function returns null
     */
    List<User> getUsers(String condition);

    /** function returns User objects which name is equals to received argument, if user doesn't
     * exist with received name function returns null
     */
    User getUserByName(String name);

    /** function returns User object from sql database, which id equals to received argument
     */
    User getUserById(long id);

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

    int changeReal_Name(String userName, String newRealName);
    int changeReal_LastName(String userName, String newReaLastName);
    int changeImage_Path(String userName, String newImagePath);
    int changeDescription(String userName, String description);
}
