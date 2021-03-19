package de.prixix.buildertask.commands;

import de.prixix.buildertask.BuilderTask;
import de.prixix.buildertask.utils.Messages;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.Objects;

public class TaskCommand implements CommandExecutor {

    private BuilderTask builderTask = BuilderTask.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(args.length != 0) {

            String option = args[0];

            if(option.equalsIgnoreCase("create")) {
                if(sender.hasPermission("buildertask.task.create")) {
                    if (args.length >= 2) {
                        String uuid = "Console";

                        if (sender instanceof Player) {
                            Player player = (Player) sender;
                            uuid = player.getUniqueId().toString();
                        }

                        try {
                            StringBuilder name = new StringBuilder(args[1]);
                            for (int i = 2; i < args.length; i++) {
                                if (!args[i].isEmpty()) {
                                    name.append(" ").append(args[i]);
                                }
                            }

                            sender.sendMessage(Messages.taskCreationSuccess.replace("[id]", String.valueOf(builderTask.createTask(uuid, name))));
                        } catch (SQLException e) {
                            e.printStackTrace();
                            sender.sendMessage(Messages.taskCreationFailure);
                        }
                    } else {
                        sender.sendMessage(Messages.syntaxError);
                    }

                } else {
                    sender.sendMessage(Messages.noPermission);
                }
            }

            if(option.equalsIgnoreCase("assign")) {
                if(args.length == 3) {
                    if(sender.hasPermission("buildertask.task.assign")) {
                        try {
                            String uuid = builderTask.getBuilderUUIDByName(args[1]);

                            if(uuid != null) {
                                builderTask.assignTask(uuid, Integer.parseInt(args[2]));
                                sender.sendMessage(Messages.taskAssignedSuccess.replace("[player]", Objects.requireNonNull(builderTask.getBuilderNameByUUID(uuid))));

                            } else {
                                sender.sendMessage(Messages.builderDoesNotExists);
                            }

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    sender.sendMessage(Messages.syntaxError);
                }
            }

            if(option.equalsIgnoreCase("setworld")) {
                if(args.length == 3) {
                    if(sender.hasPermission("buildertask.task.setworld")) {
                        try {
                            World world = builderTask.getServer().getWorld(args[1]);
                            int taskId = Integer.parseInt(args[2]);

                            if(world != null) {
                                if(builderTask.doesTaskExist(taskId)) {
                                    builderTask.setWorldTask(world.getName(), taskId);
                                    sender.sendMessage(Messages.taskWorldSuccess);

                                } else {
                                    sender.sendMessage(Messages.taskDoesNotExists);
                                }

                            } else {
                                sender.sendMessage(Messages.taskWorldNotExists);
                            }

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } else {
            sender.sendMessage(Messages.syntaxError);
        }

        return false;
    }
}