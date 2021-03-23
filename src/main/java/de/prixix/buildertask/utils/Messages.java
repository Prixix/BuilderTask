package de.prixix.buildertask.utils;

import de.prixix.buildertask.BuilderTask;

public class Messages {

    public static String prefix = formatStringToChat(getMessageString("Prefix"));
    public static String noPermission = Messages.prefix + formatStringToChat(getMessageString("NoPermission"));
    public static String playerNotOnline = Messages.prefix + formatStringToChat(getMessageString("PlayerNotOnline"));
    public static String syntaxError = Messages.prefix + formatStringToChat(getMessageString("SyntaxError"));
    public static String taskIdWrongFormat = Messages.prefix + formatStringToChat(getMessageString("TaskIdWrongFormat"));
    public static String notRegistered = Messages.prefix + formatStringToChat(getMessageString("NotRegistered"));

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
    public static String taskDoesNotExists = Messages.prefix + formatStringToChat(getMessageString("TaskDoesNotExists"));
    public static String taskCreationSuccess = Messages.prefix + formatStringToChat(getMessageString("TaskCreationSuccess"));
    public static String taskWorldNotExists = Messages.prefix + formatStringToChat(getMessageString("TaskWorldNotExists"));
    public static String taskWorldSuccess = Messages.prefix + formatStringToChat(getMessageString("TaskWorldSuccess"));
    public static String taskAssignedSuccess = Messages.prefix + formatStringToChat(getMessageString("TaskAssignedSuccess"));
    public static String taskDeletedSuccess = Messages.prefix + formatStringToChat(getMessageString("TaskDeletedSuccess"));
    public static String taskDeletedFailure = Messages.prefix + formatStringToChat(getMessageString("TaskDeletedFailure"));
    public static String taskSubmitError = Messages.prefix + formatStringToChat(getMessageString("TaskSubmitError"));
    public static String taskSubmitSuccess = Messages.prefix + formatStringToChat(getMessageString("TaskSubmitSuccess"));
    public static String taskSubmitNotAssigned = Messages.prefix + formatStringToChat(getMessageString("TaskSubmitNotAssigned"));
    public static String taskDescriptionSuccess = Messages.prefix + formatStringToChat(getMessageString("TaskDescriptionSuccess"));
    public static String taskDescriptionFailure = Messages.prefix + formatStringToChat(getMessageString("TaskDescriptionFailure"));
    public static String taskCloseSuccess = Messages.prefix + formatStringToChat(getMessageString("TaskCloseSuccess"));
    public static String taskCloseFailure = Messages.prefix + formatStringToChat(getMessageString("TaskCloseFailure"));
    public static String taskListHeader = Messages.prefix + formatStringToChat(getMessageString("TaskListHeader"));
    public static String taskListName = Messages.prefix + formatStringToChat(getMessageString("TaskListName"));
    public static String taskListWorld = Messages.prefix + formatStringToChat(getMessageString("TaskListWorld"));
    public static String taskListDescription = Messages.prefix + formatStringToChat(getMessageString("TaskListDescription"));
    public static String taskListExpiry = Messages.prefix + formatStringToChat(getMessageString("TaskListExpiryDate"));
    public static String taskListNoResult = Messages.prefix + formatStringToChat(getMessageString("TaskListNoResult"));
    public static String taskListAssignee = Messages.prefix + formatStringToChat(getMessageString("TaskListAssignee"));
    public static String taskListId = Messages.prefix + formatStringToChat(getMessageString("TaskListId"));
    public static String taskInfoHeader = Messages.prefix + formatStringToChat(getMessageString("TaskInfoHeader"));
    public static String taskInfoFailure = Messages.prefix + formatStringToChat(getMessageString("TaskInfoFailure"));


    private static String getMessageString(String name) {
        return BuilderTask.getInstance().configuration.getString("Messages." + name);
    }

    private static String formatStringToChat(String input) {
        return input.replaceAll("&", "§");
    }
}
