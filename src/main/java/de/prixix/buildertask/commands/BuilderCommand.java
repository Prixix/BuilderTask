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
        if(sender.hasPermission("buildertask.builder.edit")) {
            if(args.length != 0) {
                String option = args[0];

                if(option.equalsIgnoreCase("add")) {
                    if(args.length == 2) {
                        Player player = Bukkit.getPlayer(args[1]);

                        if(player != null) {
                            try {
                                builderTask.createBuilder(player.getUniqueId(), player.getName());
                                sender.sendMessage(Messages.builderCreated);
                            } catch (SQLException e) {
                                e.printStackTrace();
                                sender.sendMessage(Messages.builderCreatedFailure);
                            }
                        } else {
                            //Target not online
                            sender.sendMessage(Messages.playerNotOnline);
                        }
                    } else {
                        sender.sendMessage(Messages.builderMissingArgument);
                    }
                }

                if(option.equalsIgnoreCase("remove")) {
                    if(args.length == 2) {
                        try {
                            String builderUUID = builderTask.getBuilderUUIDByName(args[1]);
                            if(builderUUID != null) {
                                builderTask.removeBuilder(builderUUID);
                                sender.sendMessage(Messages.builderRemoved);
                            } else {
                                sender.sendMessage(Messages.builderRemovedNotExists);
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                            sender.sendMessage(Messages.builderRemovedFailure);
                        }
                    } else {
                        sender.sendMessage(Messages.builderMissingArgument);
                    }
                }

                if(option.equalsIgnoreCase("list")) {
                    try {
                        ResultSet listResult = builderTask.getAllBuilder();
                        if(listResult.next()) {
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
