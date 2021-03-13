package de.prixix.buildertask;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public final class BuilderTask extends JavaPlugin {

    Server server = Bukkit.getServer();
    ConsoleCommandSender console = server.getConsoleSender();

    @Override
    public void onEnable() {
        // Plugin startup logic
        try {
            URL versionURL = new URL("https://raw.githubusercontent.com/Prixix/BuilderTask/main/version.txt");
            URLConnection urlConnection = versionURL.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String inputLine = in.readLine();
            in.close();

            if(!inputLine.equals(getDescription().getVersion())) {
                console.sendMessage("The current BuilderTask version (" + getDescription().getVersion() + ") is not up to date!");
                console.sendMessage("Please consider to update the current version at https://github.com/Prixix/BuilderTask/releases");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
