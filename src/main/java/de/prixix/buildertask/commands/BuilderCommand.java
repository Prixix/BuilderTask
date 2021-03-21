package de.prixix.buildertask.commands;

import de.prixix.buildertask.BuilderTask;
import de.prixix.buildertask.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BuilderCommand implements CommandExecutor, TabExecutor {

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
                    return false;
                } catch (SQLException e) {
                    e.printStackTrace();
                    sender.sendMessage(Messages.builderCreatedFailure);
                    return true;
                }
            }

            if (option.equalsIgnoreCase("remove")) {
                if (args.length != 2) {
                    sender.sendMessage(Messages.builderMissingArgument);
                    return true;
                }
                try {
                    String builderUUID = builderTask.getBuilderUUIDByName(args[1]);
                    if (builderUUID == null) {
                        sender.sendMessage(Messages.builderRemovedNotExists);
                        return true;
                    }

                    builderTask.removeBuilder(builderUUID);
                    sender.sendMessage(Messages.builderRemoved);
                    return false;
                } catch (SQLException e) {
                    e.printStackTrace();
                    sender.sendMessage(Messages.builderRemovedFailure);
                    return true;
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
                        return false;
                    } else {
                        sender.sendMessage(Messages.builderListNoPlayers);
                        return true;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    sender.sendMessage(Messages.builderListError);
                    return true;
                }
            }

        } else {
            sender.sendMessage(Messages.builderNoOption);
            return true;
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        String option = args[0];
        ArrayList<String> arguments = new ArrayList<>();

        if(args.length == 1) {
            arguments.add("add");
            arguments.add("remove");
            arguments.add("list");

            return arguments;
        }

        if(args.length == 2) {
            if(option.equalsIgnoreCase("list")) {
                return null;
            }

            if(option.equalsIgnoreCase("add")) {
                for(Player player : Bukkit.getOnlinePlayers()) {
                    arguments.add(player.getName());
                }

                return arguments;
            }
        }

        return null;
    }
}
