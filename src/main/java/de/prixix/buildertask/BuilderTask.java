package de.prixix.buildertask;

import de.prixix.buildertask.commands.BuilderCommand;
import de.prixix.buildertask.utils.Messages;
import de.prixix.buildertask.utils.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

public final class BuilderTask extends JavaPlugin {

    private static BuilderTask instance;
    public FileConfiguration configuration = this.getConfig();

    public Server server = Bukkit.getServer();
    public ConsoleCommandSender console = server.getConsoleSender();

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        loadConfiguration();
        checkVersion();
        initializeCommands();
        initializeEvents();
        MySQL.connect();

        createTables();
    }

    private void checkVersion() {
        try {
            URL versionURL = new URL("https://raw.githubusercontent.com/Prixix/BuilderTask/main/version.txt");
            URLConnection urlConnection = versionURL.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String inputLine = in.readLine();
            in.close();

            if(!inputLine.equals(getDescription().getVersion())) {
                console.sendMessage(Messages.prefix + "§cThe current BuilderTask version (" + getDescription().getVersion() + ") is not up to date!");
                console.sendMessage(Messages.prefix + "§cPlease consider to update the current version at §ahttps://github.com/Prixix/BuilderTask/releases");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadConfiguration() {
        configuration.options().copyDefaults(true);
        saveConfig();
    }

    public static BuilderTask getInstance() {
        return instance;
    }

    private void initializeCommands() {
        this.getCommand("builder").setExecutor(new BuilderCommand());
    }

    private void initializeEvents() {

    }

    private void createTables() {
        Connection connection = MySQL.getConnection();

        try {
            connection.createStatement().execute(
                    "CREATE TABLE IF NOT EXISTS `task` (\n" +
                    "\t`TaskID` INT(255) NOT NULL AUTO_INCREMENT,\n" +
                    "\t`Name` VARCHAR(255) NOT NULL,\n" +
                    "\t`World` VARCHAR(255) NOT NULL,\n" +
                    "\t`Description` VARCHAR(255) NOT NULL DEFAULT '/',\n" +
                    "\t`expiryDate` TIMESTAMP,\n" +
                    "\t`creationDate` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,\n" +
                    "\t`Assignee` VARCHAR(255),\n" +
                    "\t`Creator` VARCHAR(255),\n" +
                    "\tPRIMARY KEY (`TaskID`)\n" +
                    ");");

            connection.createStatement().execute(
                    "CREATE TABLE IF NOT EXISTS `builder` (\n" +
                            "\t`UUID` VARCHAR(255) NOT NULL,\n" +
                            "\t`Registered` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                            "\tPRIMARY KEY (`UUID`)\n" +
                            ");");
        } catch (SQLException ignored) {}
    }

    public boolean createBuilder(UUID uuid) throws SQLException {
        Connection connection = MySQL.getConnection();

        String uuidString = uuid.toString();

        return connection.createStatement().execute("INSERT INTO builder (UUID) VALUES ('" + uuidString + "');");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        MySQL.disconnect();
    }
}
