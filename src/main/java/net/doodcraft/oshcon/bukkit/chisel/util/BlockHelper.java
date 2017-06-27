package net.doodcraft.oshcon.bukkit.chisel.util;

import org.apache.commons.lang3.EnumUtils;
import org.bukkit.Material;

public class BlockHelper {

    public enum GlazedTerracotta {
        BLACK_GLAZED_TERRACOTTA,
        BLUE_GLAZED_TERRACOTTA,
        BROWN_GLAZED_TERRACOTTA,
        CYAN_GLAZED_TERRACOTTA,
        GRAY_GLAZED_TERRACOTTA,
        GREEN_GLAZED_TERRACOTTA,
        LIGHT_BLUE_GLAZED_TERRACOTTA,
        LIME_GLAZED_TERRACOTTA,
        MAGENTA_GLAZED_TERRACOTTA,
        ORANGE_GLAZED_TERRACOTTA,
        PINK_GLAZED_TERRACOTTA,
        PURPLE_GLAZED_TERRACOTTA,
        RED_GLAZED_TERRACOTTA,
        SILVER_GLAZED_TERRACOTTA,
        WHITE_GLAZED_TERRACOTTA,
        YELLOW_GLAZED_TERRACOTTA;
    }

    public enum Sandstone {
        SANDSTONE,
        RED_SANDSTONE;
    }

    public static boolean isGlazedTerracotta(Material material) {
        return EnumUtils.isValidEnum(GlazedTerracotta.class, material.toString());
    }

    public static boolean isSandstone(Material material) {
        return EnumUtils.isValidEnum(Sandstone.class, material.toString());
    }

    public static boolean isQuartzBlock(Material material) {
        return material.equals(Material.QUARTZ_BLOCK);
    }

    public static boolean isStonebrick(Material material) {
        return material.equals(Material.SMOOTH_BRICK);
    }
}
