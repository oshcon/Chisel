package net.doodcraft.oshcon.bukkit.chisel.util;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;

public class Compatibility {

    public static HashMap<String, Plugin> hooked;
    public static ArrayList<String> warned;

    public static void checkHooks() {
        hooked = new HashMap<>();
    }

    public static boolean isHooked(String name) {
        if (hooked.get(name) != null) {
            if (getPlugin(name) != null && getPlugin(name).isEnabled()) {
                return true;
            } else {
                if (!warned.contains(name)) {
                    warned.add(name);
                    StaticMethods.log("&c" + name + " is hooked, but not enabled anymore.");
                }
                return false;
            }
        }
        return false;
    }

    public static Plugin getPlugin(String name) {
        return hooked.get(name);
    }

    public static boolean hookPlugin(String name, String min, String max) {
        Plugin hook = Bukkit.getPluginManager().getPlugin(name);
        if (hook != null) {
            String rawVersion = hook.getDescription().getVersion();
            String[] versionPart = rawVersion.split("\\-");
            String version = versionPart[0];
            if (isSupported(version, min, max)) {
                if (!hooked.containsKey(name)) {
                    hooked.put(name, hook);
                    return true;
                } else {
                    return false;
                }
            } else {
                StaticMethods.log("&c" + name + " v" + version + " is unknown or unsupported.");
                StaticMethods.log("&cAttempting to hook anyway. There may be errors.");
                try {
                    if (!hooked.containsKey(name)) {
                        hooked.put(name, hook);
                        return true;
                    } else {
                        return false;
                    }
                } catch (Exception ex) {
                    StaticMethods.log("&cCould not hook into " + name + " v" + version);
                    StaticMethods.log("&cThe following stack trace may reveal why:");
                    StaticMethods.log(" ");
                    ex.printStackTrace();
                    StaticMethods.log(" ");
                    return false;
                }
            }
        }
        return false;
    }

    public static boolean isSupported(String version, String min, String max) {
        try {
            return compareVersions(version, min) >= 0 && compareVersions(version, max) <= 0;
        } catch (Exception ignored) {
            return false;
        }
    }

    public static Integer compareVersions(String version, String compareTo) {
        String[] versionString = version.split("\\.");
        String[] compareToString = compareTo.split("\\.");
        int i = 0;
        while (i < versionString.length && i < compareToString.length && versionString[i].equals(compareToString[i])) {
            i++;
        }
        if (i < versionString.length && i < compareToString.length) {
            int diff = Integer.valueOf(versionString[i]).compareTo(Integer.valueOf(compareToString[i]));
            return Integer.signum(diff);
        } else {
            return Integer.signum(versionString.length - compareToString.length);
        }
    }
}
