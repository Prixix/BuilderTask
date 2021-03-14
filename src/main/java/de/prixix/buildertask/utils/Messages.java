package de.prixix.buildertask.utils;

import de.prixix.buildertask.BuilderTask;

public class Messages {

    public static String prefix = formatStringToChat(getMessageString("Prefix"));
    public static String noPermission = Messages.prefix + formatStringToChat(getMessageString("NoPermission"));
    public static String playerNotOnline = formatStringToChat(getMessageString("PlayerNotOnline"));

    public static String builderCreated = Messages.prefix + formatStringToChat(getMessageString("BuilderCreated"));
    public static String builderCreatedFailure = Messages.prefix + formatStringToChat(getMessageString("BuilderCreatedFailure"));
    public static String builderNoOption = Messages.prefix + formatStringToChat(getMessageString("BuilderNoOption"));


    private static String getMessageString(String name) {
        return BuilderTask.getInstance().configuration.getString("Messages." + name);
    }

    private static String formatStringToChat(String input) {
        return input.replaceAll("&", "§");
    }
}
