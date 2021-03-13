package de.prixix.buildertask.utils;

import de.prixix.buildertask.BuilderTask;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQL {

    private static BuilderTask buildertask = BuilderTask.getInstance();

    private static String host = buildertask.configuration.getString("MySQL.Host");
    private static String port = String.valueOf(buildertask.configuration.getInt("MySQL.Port"));
    private static String database = buildertask.configuration.getString("MySQL.Database");
    private static String username = buildertask.configuration.getString("MySQL.Username");
    private static String password = buildertask.configuration.getString("MySQL.Password");
    private static Connection connection;

    public static void connect() {
        if(!isConnected()) {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
                buildertask.console.sendMessage(Messages.prefix + "§aMySQL connection successfully connected");
            } catch (Exception e) {
                e.printStackTrace();
                buildertask.console.sendMessage(Messages.prefix + "§cMySQL connection failed");
            }
        }
    }

    public static void disconnect() {
        if(isConnected()) {
            try {
                connection.close();
                buildertask.console.sendMessage(Messages.prefix + "MySQL connection closed!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean isConnected() {
        return (connection != null);
    }

    public static Connection getConnection() {
        return connection;
    }

}
