package de.prixix.buildertask.commands;

import de.prixix.buildertask.BuilderTask;
import de.prixix.buildertask.utils.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class TaskCommand implements CommandExecutor {

    private BuilderTask builderTask = BuilderTask.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(args.length != 0) {

            String option = args[0];

            if(option.equalsIgnoreCase("create")) {
                if(sender.hasPermission("buildertask.task.create")) {
                    if (args.length == 3) {
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
        } else {
            sender.sendMessage(Messages.syntaxError);
        }

        return false;
    }
}