package de.prixix.buildertask;

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
        console.sendMessage(Messages.prefix + "Config has been loaded");
    }

    public static BuilderTask getInstance() {
        return instance;
    }

    private void initializeCommands() {

    }

    private void initializeEvents() {

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        MySQL.disconnect();
    }
}
