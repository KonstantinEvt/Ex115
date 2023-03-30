package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class Util {
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    //  private static final String driver = "org.h2.Driver";
    private static final String url = "jdbc:mysql://localhost:3306/first";
    //  private static final String url = "jdbc:h2:~/first";
    private static final String login = "Kostya";
    private static final String password = "Kostya_0306_911";

    public static Connection getConnection() {
        Connection connect = null;
        try {
            Class.forName(driver);
            connect = DriverManager.getConnection(url, login, password);
        } catch (SQLException e) {
            System.out.println("Connection failed");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return connect;
    }

    public static SessionFactory getHiberConnection() {
        Configuration config = new Configuration();
        Properties prop = new Properties();
        prop.put(Environment.URL, url);
        prop.put(Environment.USER, login);
        prop.put(Environment.PASS, password);
        prop.put(Environment.DRIVER, driver);
        prop.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
        prop.put(Environment.SHOW_SQL, "true");
        prop.put(Environment.HBM2DDL_AUTO, "update");

        config.setProperties(prop);
        config.addAnnotatedClass(User.class);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(config.getProperties()).build();
        return config.buildSessionFactory(serviceRegistry);
    }
}
