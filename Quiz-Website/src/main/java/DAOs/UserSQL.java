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

        try{
            Connection connection = dataSource.getConnection();
            String query = "SELECT *FROM users";
            if(!condition.equals("")) query += condition;

            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery();
            List<User> result = new ArrayList<>();

            while (resultSet.next()){
                User newUser = new User(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getString(5), resultSet.getString(6),
                        resultSet.getString(7), resultSet.getString(8));

                result.add(newUser);
            }

            return result;

        } catch (SQLException ignored) {}

        return null;
    }

    @Override
    public User getUserByName(String name) {

        List<User> allUser = getUsers(" WHERE name = \"" + name + "\"");

        if(allUser.size() == 1) return allUser.get(0);
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        List<User> allUser = getUsers(" WHERE email = \"" + email + "\"");

        if(allUser.size() == 1) return allUser.get(0);
        return null;
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
    public int register(String name, String realName, String realLastName, String email, String password) {

        if(getUserByName(name) != null) return UserDao.ACCOUNT_FOUND_BY_NAME;
        if(!email.equals("") && getUserByEmail(email) != null) return UserDao.ACCOUNT_FOUND_BY_EMAIL;

        try{
            Connection connection = dataSource.getConnection();
            String query = "INSERT INTO users (id, name, password, email, real_name, real_lastname) VALUES(?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setLong(1, getNewUserID());
            statement.setString(2, name);
            statement.setString(3, Hasher.generateHash(password, getNewUserID()));
            statement.setString(4, email);
            statement.setString(5, realName);
            statement.setString(6, realLastName);

            statement.executeUpdate();
            return UserDao.ACCOUNT_CREATED;

        }catch (SQLException ignored) {}

        return UserDao.SERVER_ERROR;
    }

    @Override
    public long getNewUserID() {

        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT MAX(id) AS max_id FROM users");

            if(resultSet.next()) return resultSet.getLong("max_id") + 1;

        } catch (SQLException ignored) {}

        return 1;
    }
}
