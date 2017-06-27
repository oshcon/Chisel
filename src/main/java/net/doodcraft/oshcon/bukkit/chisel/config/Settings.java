package net.doodcraft.oshcon.bukkit.chisel.config;

import net.doodcraft.oshcon.bukkit.chisel.ChiselPlugin;

import java.io.File;

public class Settings {
    // CONFIG
    public static Boolean colorfulLogging;
    public static Boolean debug;
    public static Boolean allowQuartz;
    public static Boolean allowStone;
    public static Boolean allowSandstone;
    public static Boolean allowTerracotta;
    public static String chiselName;
    public static Boolean playSoundUse;
    public static Boolean playSoundBreak;
    public static int uses;
    // LOCALE
    public static String pluginPrefix;
    public static String noPermission;

    public static void setupDefaults() {
        // CONFIG
        colorfulLogging = true;
        debug = false;
        allowQuartz = true;
        allowStone = true;
        allowSandstone = true;
        allowTerracotta = true;
        playSoundUse = true;
        playSoundBreak = true;
        uses = 64;
        // LOCALE
        pluginPrefix = "&8[&6Chisel&8]&r";
        noPermission = "&cYou do not have permission.";
        chiselName = "&eChisel";

        Configuration config = new Configuration(ChiselPlugin.plugin.getDataFolder() + File.separator + "config.yml");
        Configuration locale = new Configuration(ChiselPlugin.plugin.getDataFolder() + File.separator + "locale.yml");
        config.add("General.ColorfulLogging", colorfulLogging);
        config.add("General.DebugMessages", debug);
        config.add("Allowed.QUARTZ", allowQuartz);
        config.add("Allowed.STONEBRICK", allowStone);
        config.add("Allowed.SANDSTONE", allowSandstone);
        config.add("Allowed.TERRACOTTA", allowTerracotta);
        config.add("Sounds.Use", playSoundUse);
        config.add("Sounds.Break", playSoundBreak);
        config.add("DefaultUses", uses);
        locale.add("General.PluginPrefix", pluginPrefix);
        locale.add("General.NoPermission", noPermission);
        locale.add("ChiselName", chiselName);
        config.save();
        locale.save();
        setNewConfigValues(config);
        setNewLocaleValues(locale);
    }

    public static void setNewConfigValues(Configuration config) {
        colorfulLogging = config.getBoolean("General.ColorfulLogging");
        debug = config.getBoolean("General.DebugMessages");
        allowQuartz = config.getBoolean("Allowed.QUARTZ");
        allowStone = config.getBoolean("Allowed.STONEBRICK");
        allowSandstone = config.getBoolean("Allowed.SANDSTONE");
        allowTerracotta = config.getBoolean("Allowed.TERRACOTTA");
        playSoundUse = config.getBoolean("Sounds.Use");
        playSoundBreak = config.getBoolean("Sounds.Break");
        uses = config.getInteger("DefaultUses");
    }

    public static void setNewLocaleValues(Configuration locale) {
        pluginPrefix = locale.getString("General.PluginPrefix");
        noPermission = locale.getString("General.NoPermission");
        chiselName = locale.getString("ChiselName");
    }

    public static boolean reload() {
        try {
            Configuration config = new Configuration(ChiselPlugin.plugin.getDataFolder() + File.separator + "config.yml");
            Configuration locale = new Configuration(ChiselPlugin.plugin.getDataFolder() + File.separator + "locale.yml");
            setNewConfigValues(config);
            setNewLocaleValues(locale);
            return false;
        } catch (Exception ex) {
            ex.printStackTrace();
            return true;
        }
    }
}
