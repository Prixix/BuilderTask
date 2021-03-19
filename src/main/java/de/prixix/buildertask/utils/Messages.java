package de.prixix.buildertask.utils;

import de.prixix.buildertask.BuilderTask;

public class Messages {

    public static String prefix = formatStringToChat(getMessageString("Prefix"));
    public static String noPermission = Messages.prefix + formatStringToChat(getMessageString("NoPermission"));
    public static String playerNotOnline = formatStringToChat(getMessageString("PlayerNotOnline"));
    public static String syntaxError = formatStringToChat(getMessageString("SyntaxError"));

    public static String builderNoOption = Messages.prefix + formatStringToChat(getMessageString("BuilderNoOption"));
    public static String builderMissingArgument = Messages.prefix + formatStringToChat(getMessageString("BuilderMissingArgument"));
    public static String builderDoesNotExists = Messages.prefix + formatStringToChat(getMessageString("BuilderDoesNotExists"));

    public static String builderCreated = Messages.prefix + formatStringToChat(getMessageString("BuilderCreated"));
    public static String builderCreatedFailure = Messages.prefix + formatStringToChat(getMessageString("BuilderCreatedFailure"));

    public static String builderRemoved = Messages.prefix + formatStringToChat(getMessageString("BuilderRemoved"));
    public static String builderRemovedFailure = Messages.prefix + formatStringToChat(getMessageString("BuilderRemovedFailure"));
    public static String builderRemovedNotExists = Messages.prefix + formatStringToChat(getMessageString("BuilderRemovedNotExists"));

    public static String builderListHeader = Messages.prefix + formatStringToChat(getMessageString("BuilderListHeader"));
    public static String builderListPlayer = Messages.prefix + formatStringToChat(getMessageString("BuilderListPlayer"));
    public static String builderListError = Messages.prefix + formatStringToChat(getMessageString("BuilderListError"));
    public static String builderListNoPlayers = Messages.prefix + formatStringToChat(getMessageString("BuilderListNoPlayers"));

    public static String taskCreationFailure = Messages.prefix + formatStringToChat(getMessageString("TaskCreationFailure"));
    public static String taskCreationWorldNotExists = Messages.prefix + formatStringToChat(getMessageString("TaskCreationWorldNotExists"));
    public static String taskCreationSuccess = Messages.prefix + formatStringToChat(getMessageString("TaskCreationSuccess"));
    public static String taskAssignedSuccess = Messages.prefix + formatStringToChat(getMessageString("TaskAssignedSuccess"));



    private static String getMessageString(String name) {
        return BuilderTask.getInstance().configuration.getString("Messages." + name);
    }

    private static String formatStringToChat(String input) {
        return input.replaceAll("&", "§");
    }
}
