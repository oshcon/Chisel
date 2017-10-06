package net.doodcraft.oshcon.bukkit.chisel.util;

import net.doodcraft.oshcon.bukkit.chisel.ChiselPlugin;
import net.doodcraft.oshcon.bukkit.chisel.config.Settings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StaticMethods {

    public static boolean isChiselItem(ItemStack item) {
        if (item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            if (meta.hasDisplayName()) {
                if (meta.hasItemFlag(ItemFlag.HIDE_ENCHANTS)) {
                    Map<Enchantment, Integer> enchs = item.getEnchantments();
                    if (enchs.containsKey(Enchantment.ARROW_INFINITE)) {
                        if (enchs.get(Enchantment.ARROW_INFINITE) == 42) {
                            meta.setDisplayName(addColor(Settings.chiselName));
                            item.setItemMeta(meta);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static ItemStack getChiselItem() {
        String material = Settings.chiselMaterial.toUpperCase();
        ItemStack chisel;
        try {
            chisel = new ItemStack(Material.valueOf(material));
        } catch (Exception ex) {
            StaticMethods.log("&cThere was an error setting the material value of the Chisel.");
            StaticMethods.log("&c" + material + " may be invalid. Check your configuration.");
            StaticMethods.log("&cDefaulting to STICK for now.");
            chisel = new ItemStack(Material.valueOf("STICK"));
        }

        chisel.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 42);
        ItemMeta meta = chisel.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setDisplayName(StaticMethods.addColor(Settings.chiselName));
        List<String> lore = new ArrayList<>();
        lore.add(Settings.uses + "/" + Settings.uses);
        meta.setLore(lore);
        chisel.setItemMeta(meta);
        return chisel;
    }

    public static Boolean canBuild(Player player, Location location) {
        if (location.getBlock() != null) {
            Block block = location.getBlock();
            BlockBreakEvent event = new BlockBreakEvent(block, player);
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled()) {
                event.setCancelled(true);
                return false;
            } else {
                event.setCancelled(true);
                return true;
            }
        }
        return true;
    }

    public static Boolean hasPermission(Player player, String node) {
        if (player.isOp()) {
            return true;
        }
        if (player.hasPermission(ChiselPlugin.plugin.getName().toLowerCase() + ".*")) {
            return true;
        }
        if (player.hasPermission(node)) {
            return true;
        } else {
            player.sendMessage(addColor(Settings.pluginPrefix + " &r" + Settings.noPermission));
            return false;
        }
    }

    public static void log(String message) {
        message = Settings.pluginPrefix + " &r" + message;
        sendConsole(message);
    }

    public static void debug(String message) {
        if (Settings.debug) {
            message = "&8[&dDEBUG&8] &e" + message;
            log(message);
        }
    }

    private static void sendConsole(String message) {
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        try {
            if (Settings.colorfulLogging) {
                console.sendMessage(addColor(message));
            } else {
                console.sendMessage(removeColor(addColor(message)));
            }
        } catch (Exception ignored) {
            console.sendMessage(removeColor(addColor(message)));
        }
    }

    public static String addColor(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    private static String removeColor(String message) {
        message = addColor(message);
        return ChatColor.stripColor(message);
    }
}