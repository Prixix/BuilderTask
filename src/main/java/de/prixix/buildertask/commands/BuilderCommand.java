package de.prixix.buildertask.commands;

import de.prixix.buildertask.BuilderTask;
import de.prixix.buildertask.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BuilderCommand implements CommandExecutor {

    private BuilderTask builderTask = BuilderTask.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("buildertask.builder.edit")) {
            sender.sendMessage(Messages.noPermission);
            return true;
        }

        if (args.length != 0) {
            String option = args[0];

            if (option.equalsIgnoreCase("add")) {
                if (args.length != 2) {
                    sender.sendMessage(Messages.builderMissingArgument);
                    return true;
                }
                Player player = Bukkit.getPlayer(args[1]);

                if (player == null) {
                    sender.sendMessage(Messages.playerNotOnline);
                    return true;
                }
                try {
                    builderTask.createBuilder(player.getUniqueId(), player.getName());
                    sender.sendMessage(Messages.builderCreated);
                } catch (SQLException e) {
                    e.printStackTrace();
                    sender.sendMessage(Messages.builderCreatedFailure);
                }
            }

            if (option.equalsIgnoreCase("remove")) {
                if (args.length != 2) {
                    sender.sendMessage(Messages.builderMissingArgument);
                }
                try {
                    String builderUUID = builderTask.getBuilderUUIDByName(args[1]);
                    if (builderUUID == null) {
                        sender.sendMessage(Messages.builderRemovedNotExists);
                        return true;
                    }

                    builderTask.removeBuilder(builderUUID);
                    sender.sendMessage(Messages.builderRemoved);
                } catch (SQLException e) {
                    e.printStackTrace();
                    sender.sendMessage(Messages.builderRemovedFailure);
                }
            }

            if (option.equalsIgnoreCase("list")) {
                try {
                    ResultSet listResult = builderTask.getAllBuilder();
                    if (listResult.next()) {
                        sender.sendMessage(Messages.builderListHeader);
                        listResult.beforeFirst();
                        while (listResult.next()) {
                            sender.sendMessage(Messages.builderListPlayer.replace("[player]", listResult.getString("Name")));
                        }
                    } else {
                        sender.sendMessage(Messages.builderListNoPlayers);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    sender.sendMessage(Messages.builderListError);
                }
            }

        } else {
            sender.sendMessage(Messages.builderNoOption);
        }

        return false;
    }
}
