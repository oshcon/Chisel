package net.doodcraft.oshcon.bukkit.chisel.config;

import net.doodcraft.oshcon.bukkit.chisel.ChiselPlugin;

import java.io.File;

public class Settings {
    // CONFIG
    public static Boolean colorfulLogging;
    public static Boolean debug;
    public static String craftMaterialTip;
    public static String craftMaterialHandle;
    public static String chiselMaterial;
    public static Boolean allowQuartz;
    public static Boolean allowStone;
    public static Boolean allowSandstone;
    public static Boolean allowTerracotta;
    public static Boolean allowPurpurPillar;
    public static Boolean allowLogs;
    public static Boolean allowStairs;
    public static Boolean allowEndRods;
    public static Boolean allowPumpkins;
    public static Boolean allowHayBales;
    public static Boolean allowBoneBlocks;
    public static String chiselName;
    public static Boolean playSoundUse;
    public static Boolean playSoundBreak;
    public static int uses;
    public static Boolean fakePlaceEvent;
    // LOCALE
    public static String pluginPrefix;
    public static String noPermission;

    public static void setupDefaults() {
        // CONFIG
        colorfulLogging = true;
        debug = false;
        craftMaterialTip = "IRON_INGOT";
        craftMaterialHandle = "STICK";
        chiselMaterial = "STICK";
        allowQuartz = true;
        allowStone = true;
        allowSandstone = true;
        allowTerracotta = true;
        allowPurpurPillar = true;
        allowLogs = true;
        allowStairs = true;
        allowEndRods = true;
        allowPumpkins = true;
        allowHayBales = true;
        allowBoneBlocks = true;
        playSoundUse = true;
        playSoundBreak = true;
        uses = 64;
        fakePlaceEvent = true;
        // LOCALE
        pluginPrefix = "&8[&6Chisel&8]&r";
        noPermission = "&cYou do not have permission.";
        chiselName = "&eChisel";

        Configuration config = new Configuration(ChiselPlugin.plugin.getDataFolder() + File.separator + "config.yml");
        Configuration locale = new Configuration(ChiselPlugin.plugin.getDataFolder() + File.separator + "locale.yml");
        config.add("General.ColorfulLogging", colorfulLogging);
        config.add("General.DebugMessages", debug);
        config.add("CraftingMaterials.Tip", craftMaterialTip);
        config.add("CraftingMaterials.Handle", craftMaterialHandle);
        config.add("ChiselMaterial", chiselMaterial);
        config.add("Allowed.QUARTZ", allowQuartz);
        config.add("Allowed.STONEBRICK", allowStone);
        config.add("Allowed.SANDSTONE", allowSandstone);
        config.add("Allowed.TERRACOTTA", allowTerracotta);
        config.add("Allowed.PURPURPILLAR", allowPurpurPillar);
        config.add("Allowed.LOGS", allowLogs);
        config.add("Allowed.STAIRS", allowStairs);
        config.add("Allowed.ENDRODS", allowEndRods);
        config.add("Allowed.PUMPKINS", allowPumpkins);
        config.add("Allowed.HAYBALES", allowHayBales);
        config.add("Allowed.BONEBLOCKS", allowBoneBlocks);
        config.add("Sounds.Use", playSoundUse);
        config.add("Sounds.Break", playSoundBreak);
        config.add("DefaultUses", uses);
        config.add("FakePlaceEvent", fakePlaceEvent);
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
        craftMaterialTip = config.getString("CraftingMaterials.Tip");
        craftMaterialHandle = config.getString("CraftingMaterials.Handle");
        chiselMaterial = config.getString("ChiselMaterial");
        allowTerracotta = config.getBoolean("Allowed.TERRACOTTA");
        allowQuartz = config.getBoolean("Allowed.QUARTZ");
        allowSandstone = config.getBoolean("Allowed.SANDSTONE");
        allowStone = config.getBoolean("Allowed.STONEBRICK");
        allowPurpurPillar = config.getBoolean("Allowed.PURPURPILLAR");
        allowLogs = config.getBoolean("Allowed.LOGS");
        allowStairs = config.getBoolean("Allowed.STAIRS");
        allowEndRods = config.getBoolean("Allowed.ENDRODS");
        allowPumpkins = config.getBoolean("Allowed.PUMPKINS");
        allowHayBales = config.getBoolean("Allowed.HAYBALES");
        allowBoneBlocks = config.getBoolean("Allowed.BONEBLOCKS");
        playSoundUse = config.getBoolean("Sounds.Use");
        playSoundBreak = config.getBoolean("Sounds.Break");
        uses = config.getInteger("DefaultUses");
        fakePlaceEvent = config.getBoolean("FakePlaceEvent");
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
            setupDefaults();
            ChiselPlugin.setupRecipe();
            setNewConfigValues(config);
            setNewLocaleValues(locale);
            return false;
        } catch (Exception ex) {
            ex.printStackTrace();
            return true;
        }
    }
}
