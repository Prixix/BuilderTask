package de.prixix.buildertask.commands;

import de.prixix.buildertask.BuilderTask;
import de.prixix.buildertask.utils.Messages;
import de.prixix.buildertask.utils.TASKSTATES;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TaskCommand implements CommandExecutor, TabExecutor {

    private BuilderTask builderTask = BuilderTask.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String uuid = "Console";

        if (sender instanceof Player) {
            Player player = (Player) sender;
            uuid = player.getUniqueId().toString();
        }

        if (args.length != 0) {

            String option = args[0];

            if (option.equalsIgnoreCase("create")) {
                if (!sender.hasPermission("buildertask.task.create")) {
                    sender.sendMessage(Messages.noPermission);
                    return true;
                }

                if (args.length >= 2) {
                    try {
                        StringBuilder name = new StringBuilder(args[1]);
                        for (int i = 2; i < args.length; i++) {
                            if (!args[i].isEmpty()) {
                                name.append(" ").append(args[i]);
                            }
                        }

                        sender.sendMessage(Messages.taskCreationSuccess.replace("[id]", String.valueOf(builderTask.createTask(uuid, name))));
                        return false;
                    } catch (SQLException e) {
                        e.printStackTrace();
                        sender.sendMessage(Messages.taskCreationFailure);
                        return true;
                    }
                } else {
                    sender.sendMessage(Messages.syntaxError);
                    return true;
                }
            }

            if (option.equalsIgnoreCase("assign")) {
                if (args.length != 3) {
                    sender.sendMessage(Messages.syntaxError);
                    return true;
                }

                if (!sender.hasPermission("buildertask.task.assign")) {
                    sender.sendMessage(Messages.noPermission);
                    return true;
                }

                try {
                    String uuidBuilder = builderTask.getBuilderUUIDByName(args[1]);

                    if (uuidBuilder == null) {
                        sender.sendMessage(Messages.builderDoesNotExists);
                        return true;
                    }
                    builderTask.assignTask(uuidBuilder, Integer.parseInt(args[2]));
                    sender.sendMessage(Messages.taskAssignedSuccess.replace("[player]", Objects.requireNonNull(builderTask.getBuilderNameByUUID(uuidBuilder))));
                    return false;

                } catch (SQLException e) {
                    e.printStackTrace();
                    return true;
                } catch (NumberFormatException e) {
                    sender.sendMessage(Messages.taskIdWrongFormat);

                    return true;
                }
            }

            if (option.equalsIgnoreCase("setworld")) {
                if (args.length != 3) {
                    sender.sendMessage(Messages.syntaxError);
                    return true;
                }

                if (!sender.hasPermission("buildertask.task.setworld")) {
                    sender.sendMessage(Messages.noPermission);
                    return true;
                }

                try {
                    World world = builderTask.getServer().getWorld(args[1]);
                    int taskId = Integer.parseInt(args[2]);

                    if (world == null) {
                        sender.sendMessage(Messages.taskWorldNotExists);
                        return true;
                    }

                    if (!builderTask.doesTaskExist(taskId)) {
                        sender.sendMessage(Messages.taskDoesNotExists);
                        return true;
                    }

                    builderTask.setWorldTask(world.getName(), taskId);
                    sender.sendMessage(Messages.taskWorldSuccess);
                    return false;


                } catch (SQLException e) {
                    e.printStackTrace();
                    return true;
                } catch (NumberFormatException e) {
                    sender.sendMessage(Messages.taskIdWrongFormat);

                    return true;
                }
            }

            if (option.equalsIgnoreCase("delete")) {
                if (args.length != 2) {
                    sender.sendMessage(Messages.syntaxError);
                    return true;
                }

                if (!sender.hasPermission("buildertask.task.delete")) {
                    sender.sendMessage(Messages.noPermission);
                    return true;
                }

                try {
                    int taskId = Integer.parseInt(args[1]);

                    if (!builderTask.doesTaskExist(taskId)) {
                        sender.sendMessage(Messages.taskDoesNotExists);
                        return true;
                    }

                    builderTask.deleteTask(taskId);
                    sender.sendMessage(Messages.taskDeletedSuccess);
                    return false;
                } catch (SQLException e) {
                    e.printStackTrace();
                    sender.sendMessage(Messages.taskDeletedFailure);
                    return true;
                } catch (NumberFormatException e) {
                    sender.sendMessage(Messages.taskIdWrongFormat);

                    return true;
                }
            }

            if (option.equalsIgnoreCase("submit")) {
                try {
                    if (!builderTask.doesBuilderExist(uuid)) {
                        sender.sendMessage("You're not registered");
                        return true;
                    }
                    if (args.length != 2) {
                        sender.sendMessage(Messages.syntaxError);
                        return true;
                    }

                    int taskId = Integer.parseInt(args[1]);

                    if (!builderTask.doesTaskExist(taskId)) {
                        sender.sendMessage(Messages.taskDoesNotExists);
                        return true;
                    }

                    if (builderTask.getTaskAssignee(taskId) == null || !builderTask.getTaskAssignee(taskId).equals(uuid)) {
                        sender.sendMessage(Messages.taskSubmitNotAssigned);
                        return true;
                    }

                    builderTask.setTaskState(taskId, TASKSTATES.closed);
                    sender.sendMessage(Messages.taskSubmitSuccess);
                    return false;

                } catch (SQLException e) {
                    e.printStackTrace();
                    sender.sendMessage(Messages.taskSubmitError);

                    return true;
                } catch (NumberFormatException e) {
                    sender.sendMessage(Messages.taskIdWrongFormat);

                    return true;
                }
            }

            if(option.equalsIgnoreCase("description")) {
                if(args.length <= 2) {
                    sender.sendMessage(Messages.syntaxError);
                    return true;
                }

                if(!sender.hasPermission("builder.task.description")) {
                    sender.sendMessage(Messages.noPermission);
                    return true;
                }

                try {
                    int taskId = Integer.parseInt(args[1]);

                    if(!builderTask.doesTaskExist(taskId)) {
                        sender.sendMessage(Messages.taskDoesNotExists);
                        return true;
                    }

                    StringBuilder description = new StringBuilder(args[2]);

                    for(int i = 3; i < args.length; i++) {
                        if(!args[i].isEmpty()) {
                            description.append(" ").append(args[i]);
                        }
                    }

                    builderTask.setTaskDescription(taskId, description.toString());
                    sender.sendMessage(Messages.taskDescriptionSuccess);
                    return false;

                } catch (SQLException e) {
                    e.printStackTrace();
                    sender.sendMessage(Messages.taskDescriptionFailure);
                    return true;
                } catch (NumberFormatException e) {
                    sender.sendMessage(Messages.taskIdWrongFormat);
                    return true;
                }
            }

        } else {
            sender.sendMessage(Messages.syntaxError);
            return true;
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        ArrayList<String> arguments = new ArrayList<>();

        if (args.length == 1) {
            arguments.add("create");
            arguments.add("assign");
            arguments.add("setworld");
            arguments.add("delete");
            arguments.add("submit");
            arguments.add("description");

            return arguments;
        }

        return null;
    }
}