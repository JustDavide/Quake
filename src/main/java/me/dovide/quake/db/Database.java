package me.dovide.quake.db;

import me.dovide.quake.QuakeMain;
import me.dovide.quake.utils.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private final Config config;

    public Database(QuakeMain instance){
        this.config = instance.getConfig();
    }

    private Connection connection;

    public Connection getConnection() throws SQLException {

        if(connection != null){
            return connection;
        }

        String url = config.getString("db.url");
        String port = config.getString("db.port");
        String database = config.getString("db.database");
        String user = config.getString("db.user");
        String password = config.getString("db.password");

        Connection connection = DriverManager.getConnection("jdbc:mysql://" +
                        url + ":" + port + "/" + database + "?useSSL=false",
                user, password);

        this.connection = connection;

        return connection;

    }
}
