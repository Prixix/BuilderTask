package de.prixix.buildertask.commands;

import de.prixix.buildertask.BuilderTask;
import de.prixix.buildertask.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class BuilderCommand implements CommandExecutor {

    private BuilderTask builderTask = BuilderTask.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission("buildertask.edit")) {
            if(args.length != 0) {
                String option = args[0];

                if(option.equalsIgnoreCase("add")) {
                    if(args.length == 2) {
                        Player player = Bukkit.getPlayer(args[1]);

                        if(player != null) {
                            try {
                                builderTask.createBuilder(player.getUniqueId());
                                sender.sendMessage(Messages.builderCreated);
                            } catch (SQLException e) {
                                e.printStackTrace();
                                sender.sendMessage(Messages.builderCreatedFailure);
                            }
                        } else {
                            //Target not online
                            sender.sendMessage(Messages.playerNotOnline);
                        }
                    }
                }

            } else {
                //No argument
                sender.sendMessage(Messages.builderNoOption);
            }
        } else {
            //No Permission
            sender.sendMessage(Messages.noPermission);
        }

        return false;
    }
}
