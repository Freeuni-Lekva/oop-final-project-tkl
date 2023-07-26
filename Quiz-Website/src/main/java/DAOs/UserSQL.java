package DAOs;

import DAOinterfaces.UserDao;
import HelperClasses.Hasher;
import Objects.User;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserSQL implements UserDao {

    private final BasicDataSource dataSource;

    public UserSQL(BasicDataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public List<User> getUsers(String condition) {

        String query = "SELECT *FROM users";
        if(!condition.equals("")) query += condition;

        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)){

            ResultSet resultSet = statement.executeQuery();
            List<User> result = new ArrayList<>();

            while (resultSet.next()){
                User newUser = new User(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getString(7));

                result.add(newUser);
            }

            resultSet.close();
            return result;

        } catch (SQLException ignored) {}

        return new ArrayList<>();
    }

    /** Simple helper function
     */
    private User getUserByCondition(String condition){

        List<User> allUser = getUsers(condition);

        if(allUser.size() == 1) return allUser.get(0);
        return null;
    }

    @Override
    public User getUserByName(String name) {
        return getUserByCondition(" WHERE BINARY name = \"" + name + "\"");
    }

    @Override
    public User getUserById(long id) {
        return getUserByCondition(" WHERE id = " + id);
    }

    @Override
    public int tryToLogin(String name, String password) {
        User user = getUserByName(name);
        if(user == null) return UserDao.ACCOUNT_NOT_FOUND;

        String hashedPassword = Hasher.generateHash(password, user.getId());
        if(user.getPassword().equals(hashedPassword)) return UserDao.SUCCESSFULLY_LOGIN;

        return UserDao.INCORRECT_PASSWORD;
    }

    @Override
    public int register(String name, String realName, String realLastName, String password) {

        if(name.equals("") || realName.equals("") || realLastName.equals("") || password.equals("")) return NOT_ENOUGH_INFORMATION;

        if(getUserByName(name) != null) return UserDao.ACCOUNT_FOUND_BY_NAME;

        String query = "INSERT INTO users (name, password, real_name, real_lastname, image_path, description) VALUES(?, ?, ?, ?, ?, ?)";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)){

            System.out.println("x");

            statement.setString(1, name);
            statement.setString(2, "X");
            statement.setString(3, realName);
            statement.setString(4, realLastName);
            statement.setString(5, "default_picture.jpg");
            statement.setString(6, "Welcome To My Profile!");

            statement.executeUpdate();

            User user = getUserByName(name);
            query = "UPDATE  users SET password=? WHERE id=?";
            PreparedStatement updateStatement = connection.prepareStatement(query);

            updateStatement.setString(1, Hasher.generateHash(password, user.getId()));
            updateStatement.setLong(2, user.getId());

            updateStatement.executeUpdate();
            return UserDao.ACCOUNT_CREATED;

        }catch (SQLException ignored) {}

        return UserDao.SERVER_ERROR;
    }

    /** helper function for profile editing
     */
    public int changeHelper(Long userId, String setOption, String setValue){

        User user = getUserById(userId);
        if(user == null) return ACCOUNT_NOT_FOUND;

        String query = "UPDATE users SET " + setOption + " = ? WHERE id =  ?";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){

            preparedStatement.setString(1, setValue);
            preparedStatement.setLong(2, userId);

            preparedStatement.executeUpdate();
            return SUCCESS_UPDATE;

        } catch (SQLException e) {
            return SERVER_ERROR;
        }
    }

    @Override
    public int changeRealName(Long userId, String newRealName) {
        return changeHelper(userId, "real_name", newRealName);
    }

    @Override
    public int changeRealLastName(Long userId, String newReaLastName) {
        return changeHelper(userId, "real_lastname", newReaLastName);
    }

    @Override
    public int changeImagePath(Long userId, String newImagePath) {
        return changeHelper(userId, "image_path", newImagePath);
    }

    @Override
    public int changeDescription(Long userId, String description) {
        return changeHelper(userId, "description", description);
    }
}
