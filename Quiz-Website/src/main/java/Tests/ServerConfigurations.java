package Tests;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public final class ServerConfigurations {

    public static final String URL = "jdbc:mysql://localhost:3306/final_project";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "babajana111";

    public static BasicDataSource getDataSource(){

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);

        return dataSource;
    }

    public static void clearTable(String table){

        BasicDataSource dataSource = getDataSource();

        try(Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement()){

            String query = "DELETE FROM " + table;
            statement.executeUpdate(query);
            dataSource.close();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
