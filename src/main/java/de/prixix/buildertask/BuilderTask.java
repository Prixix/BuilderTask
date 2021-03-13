package de.prixix.buildertask;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public final class BuilderTask extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        try {
            URL versionURL = new URL("");
            URLConnection urlConnection = versionURL.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String inputLine = in.readLine();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
