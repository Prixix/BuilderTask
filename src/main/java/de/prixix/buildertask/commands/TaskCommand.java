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

                        if(sender instanceof Player) {
                            Player player = (Player) sender;
                            uuid = player.getUniqueId().toString();
                        }

                        if(builderTask.getServer().getWorld(args[2]) != null) {
                            try {
                                builderTask.createTask(uuid, args[1], args[2]);
                                sender.sendMessage(Messages.taskCreationSuccess);
                            } catch (SQLException e) {
                                e.printStackTrace();
                                sender.sendMessage(Messages.taskCreationFailure);
                            }
                        } else {
                            // World does not exits
                            sender.sendMessage(Messages.taskCreationWorldNotExists);
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
