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
        ItemMeta meta = item.getItemMeta();
        if (meta.hasDisplayName()) {
            if (meta.hasItemFlag(ItemFlag.HIDE_ENCHANTS)) {
                Map<Enchantment, Integer> enchs = item.getEnchantments();
                if (enchs.containsKey(Enchantment.SILK_TOUCH)) {
                    if (enchs.get(Enchantment.SILK_TOUCH) == 43) {
                        meta.setDisplayName(addColor(Settings.chiselName));
                        item.setItemMeta(meta);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static ItemStack getChiselItem() {
        ItemStack chisel = new ItemStack(Material.STICK);
        chisel.addUnsafeEnchantment(Enchantment.SILK_TOUCH, 43);
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
                return false;
            } else {
                event.setCancelled(true);
                return true;
            }
        }
        return true;
    }

    public static byte alterData(Block block) {
        byte data = block.getData();
        if (BlockHelper.isGlazedTerracotta(block.getType())) {
            if (data < 3) {
                return (byte) (data + 1);
            } else {
                return 0;
            }
        }
        if (BlockHelper.isQuartzBlock(block.getType())) {
            if (data < 4) {
                return (byte) (data + 1);
            } else {
                return 0;
            }
        }
        if (BlockHelper.isSandstone(block.getType())) {
            if (data < 2) {
                return (byte) (data + 1);
            } else {
                return 0;
            }
        }
        if (BlockHelper.isStonebrick(block.getType())) {
            if (data < 3) {
                return (byte) (data + 1);
            } else {
                return 0;
            }
        }
        return data;
    }

    public static boolean isModifiable(Player player, Location location, Material material) {
        if (!hasPermission(player, "chisel.use")) {
            return false;
        }

        if (canBuild(player, location)) {
            if (BlockHelper.isStonebrick(material)) {
                if (Settings.allowStone) {
                    if (Settings.debug) {
                        player.sendMessage(addColor(Settings.pluginPrefix + " &d[DEBUG] &a" + material.toString() + " is allowed."));
                    }
                    return true;
                }
            }
            if (BlockHelper.isSandstone(material)) {
                if (Settings.allowSandstone) {
                    if (Settings.debug) {
                        player.sendMessage(addColor(Settings.pluginPrefix + " &d[DEBUG] &a" + material.toString() + " is allowed."));
                    }
                    return true;
                }
            }
            if (BlockHelper.isQuartzBlock(material)) {
                if (Settings.allowQuartz) {
                    if (Settings.debug) {
                        player.sendMessage(addColor(Settings.pluginPrefix + " &d[DEBUG] &a" + material.toString() + " is allowed."));
                    }
                    return true;
                }
            }
            if (BlockHelper.isGlazedTerracotta(material)) {
                if (Settings.allowTerracotta) {
                    if (Settings.debug) {
                        player.sendMessage(addColor(Settings.pluginPrefix + " &d[DEBUG] &a" + material.toString() + " is allowed."));
                    }
                    return true;
                }
            }
        }

        if (Settings.debug) {
            player.sendMessage(addColor(Settings.pluginPrefix + " &d[DEBUG] &c" + material.toString() + " is not allowed."));
        }
        return false;
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
