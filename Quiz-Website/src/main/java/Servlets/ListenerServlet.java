package Servlets;

import DAOinterfaces.FriendRequestDao;
import DAOinterfaces.FriendsDao;
import DAOinterfaces.UserDao;
import DAOs.FriendRequestSQL;
import DAOs.FriendsSQL;
import DAOs.UserSQL;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

public class ListenerServlet implements ServletContextListener {

    private static final String URL = "jdbc:mysql://localhost:3306/final_project";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "17031923";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setMaxTotal(-1);

        UserDao userDao = new UserSQL(dataSource);
        FriendRequestDao friendRequestDao = new FriendRequestSQL(dataSource);
        FriendsDao friendsDao = new FriendsSQL(dataSource);

        servletContextEvent.getServletContext().setAttribute(UserDao.ATTRIBUTE_NAME, userDao);
        servletContextEvent.getServletContext().setAttribute(FriendRequestDao.ATTRIBUTE_NAME, friendRequestDao);
        servletContextEvent.getServletContext().setAttribute(friendsDao.ATTRIBUTE_NAME, friendsDao);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
