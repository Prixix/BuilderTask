package de.prixix.buildertask.utils;

import de.prixix.buildertask.BuilderTask;

public class Messages {

    public static String prefix = formatStringToChat(getMessageString("Prefix"));
    public static String noPermission = Messages.prefix + formatStringToChat(getMessageString("NoPermission"));
    public static String playerNotOnline = formatStringToChat(getMessageString("PlayerNotOnline"));


    public static String builderNoOption = Messages.prefix + formatStringToChat(getMessageString("BuilderNoOption"));
    public static String builderMissingArgument = Messages.prefix + formatStringToChat(getMessageString("BuilderMissingArgument"));

    public static String builderCreated = Messages.prefix + formatStringToChat(getMessageString("BuilderCreated"));
    public static String builderCreatedFailure = Messages.prefix + formatStringToChat(getMessageString("BuilderCreatedFailure"));

    public static String builderRemoved = Messages.prefix + formatStringToChat(getMessageString("BuilderRemoved"));
    public static String builderRemovedFailure = Messages.prefix + formatStringToChat(getMessageString("BuilderRemovedFailure"));
    public static String builderRemovedNotExists = Messages.prefix + formatStringToChat(getMessageString("BuilderRemovedNotExists"));



    private static String getMessageString(String name) {
        return BuilderTask.getInstance().configuration.getString("Messages." + name);
    }

    private static String formatStringToChat(String input) {
        return input.replaceAll("&", "§");
    }
}
