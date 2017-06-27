package net.doodcraft.oshcon.bukkit.chisel;

import net.doodcraft.oshcon.bukkit.chisel.config.Settings;
import net.doodcraft.oshcon.bukkit.chisel.util.StaticMethods;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChiselCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("chisel")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (!StaticMethods.hasPermission(player, "chisel.command.chisel")) {
                    return false;
                }
                if (args.length == 0) {
                    sendValidCommands(sender);
                    return true;
                }
                if (args[0].equalsIgnoreCase("reload")) {
                    if (!StaticMethods.hasPermission(player, "chisel.command.reload")) {
                        return false;
                    }
                    boolean error = Settings.reload();
                    sendReloaded(error, sender);
                    return true;
                }
                if (args[0].equalsIgnoreCase("give")){
                    if (!StaticMethods.hasPermission(player, "chisel.command.give")) {
                        return false;
                    }
                    player.getLocation().getWorld().dropItem(player.getLocation(), StaticMethods.getChiselItem());
                    return true;
                }
                player.sendMessage(StaticMethods.addColor(Settings.pluginPrefix + "&cIncorrect subcommand."));
                sendValidCommands(sender);
                return false;
            } else {
                if (args.length == 0) {
                    sendValidCommands(sender);
                    return true;
                }
                if (args[0].equalsIgnoreCase("reload")) {
                    boolean error = Settings.reload();
                    sendReloaded(error, sender);
                    return true;
                }
                if (args[0].equalsIgnoreCase("give")) {
                    StaticMethods.log("Only players can use this command.");
                    return true;
                }
            }
        }
        return false;
    }

    public static void sendReloaded(boolean error, CommandSender sender) {
        if (!error) {
            if (sender instanceof Player) {
                sender.sendMessage(StaticMethods.addColor(Settings.pluginPrefix + " &aPlugin reloaded!"));
                StaticMethods.log("&aPlugin reloaded!");
            } else {
                StaticMethods.log("&aPlugin reloaded!");
            }
        } else {
            if (sender instanceof Player) {
                sender.sendMessage(StaticMethods.addColor(Settings.pluginPrefix + " &cError reloading plugin!"));
                StaticMethods.log("&cError reloading plugin!");
            } else {
                StaticMethods.log("&cError reloading plugin!");
            }
        }
    }

    public static void sendValidCommands(CommandSender sender) {
        if (sender instanceof Player) {
            sender.sendMessage(StaticMethods.addColor(Settings.pluginPrefix + " &3Valid Commands:"));
            sender.sendMessage(StaticMethods.addColor(Settings.pluginPrefix + " &b/chisel reload: &7Reloads the config"));
            sender.sendMessage(StaticMethods.addColor(Settings.pluginPrefix + " &b/chisel give: &7Spawn the chisel"));
        } else {
            StaticMethods.log("&3Valid Commands:");
            StaticMethods.log("&b/chisel reload: &7Reloads the config");
            StaticMethods.log("&b/chisel give: &7Spawn the chisel");
        }
    }
}