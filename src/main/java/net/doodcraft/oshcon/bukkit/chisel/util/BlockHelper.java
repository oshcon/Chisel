package net.doodcraft.oshcon.bukkit.chisel.util;

import net.doodcraft.oshcon.bukkit.chisel.config.Settings;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class BlockHelper {

    private static Map<String, Byte> maxValues;
    private static Map<String, Integer[]> exceptionValues;

    // group all the valid blocks
    public enum Log {
        LOG,
        LOG_2
    }

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
        YELLOW_GLAZED_TERRACOTTA
    }

    public enum Sandstone {
        SANDSTONE,
        RED_SANDSTONE
    }

    public enum StoneBrick {
        SMOOTH_BRICK,
        MONSTER_EGGS
    }

    public enum Stairs {
        WOOD_STAIRS,
        SPRUCE_WOOD_STAIRS,
        SANDSTONE_STAIRS,
        NETHER_BRICK_STAIRS,
        SMOOTH_STAIRS,
        BRICK_STAIRS,
        COBBLESTONE_STAIRS,
        BIRCH_WOOD_STAIRS,
        JUNGLE_WOOD_STAIRS,
        QUARTZ_STAIRS,
        ACACIA_STAIRS,
        DARK_OAK_STAIRS,
        RED_SANDSTONE_STAIRS,
        PURPUR_STAIRS
    }

    public enum Pumpkins {
        PUMPKIN,
        JACK_O_LANTERN
    }

    // get the possible data values for the valid blocks
    public static void addMaxValues() {
        maxValues = new HashMap<>();
        maxValues.put("BLACK_GLAZED_TERRACOTTA", (byte) 3);
        maxValues.put("BLUE_GLAZED_TERRACOTTA", (byte) 3);
        maxValues.put("BROWN_GLAZED_TERRACOTTA", (byte) 3);
        maxValues.put("CYAN_GLAZED_TERRACOTTA", (byte) 3);
        maxValues.put("GRAY_GLAZED_TERRACOTTA", (byte) 3);
        maxValues.put("GREEN_GLAZED_TERRACOTTA", (byte) 3);
        maxValues.put("LIGHT_BLUE_GLAZED_TERRACOTTA", (byte) 3);
        maxValues.put("LIME_GLAZED_TERRACOTTA", (byte) 3);
        maxValues.put("MAGENTA_GLAZED_TERRACOTTA", (byte) 3);
        maxValues.put("ORANGE_GLAZED_TERRACOTTA", (byte) 3);
        maxValues.put("PINK_GLAZED_TERRACOTTA", (byte) 3);
        maxValues.put("PURPLE_GLAZED_TERRACOTTA", (byte) 3);
        maxValues.put("RED_GLAZED_TERRACOTTA", (byte) 3);
        maxValues.put("SILVER_GLAZED_TERRACOTTA", (byte) 3);
        maxValues.put("WHITE_GLAZED_TERRACOTTA", (byte) 3);
        maxValues.put("YELLOW_GLAZED_TERRACOTTA", (byte) 3);
        maxValues.put("SMOOTH_STONE", (byte) 3);
        maxValues.put("SANDSTONE", (byte) 2);
        maxValues.put("RED_SANDSTONE", (byte) 2);
        maxValues.put("SMOOTH_BRICK", (byte) 3);
        maxValues.put("QUARTZ_BLOCK", (byte) 4);
        maxValues.put("WOOD_STAIRS", (byte) 7);
        maxValues.put("SPRUCE_WOOD_STAIRS", (byte) 7);
        maxValues.put("SANDSTONE_STAIRS", (byte) 7);
        maxValues.put("NETHER_BRICK_STAIRS", (byte) 7);
        maxValues.put("SMOOTH_STAIRS", (byte) 7);
        maxValues.put("BRICK_STAIRS", (byte) 7);
        maxValues.put("COBBLESTONE_STAIRS", (byte) 7);
        maxValues.put("BIRCH_WOOD_STAIRS", (byte) 7);
        maxValues.put("JUNGLE_WOOD_STAIRS", (byte) 7);
        maxValues.put("QUARTZ_STAIRS", (byte) 7);
        maxValues.put("ACACIA_STAIRS", (byte) 7);
        maxValues.put("DARK_OAK_STAIRS", (byte) 7);
        maxValues.put("RED_SANDSTONE_STAIRS", (byte) 7);
        maxValues.put("PURPUR_STAIRS", (byte) 7);
        maxValues.put("END_ROD", (byte) 5);
        maxValues.put("PUMPKIN", (byte) 3);
        maxValues.put("JACK_O_LANTERN", (byte) 3);
    }

    public static void addExceptionValues() {
        exceptionValues = new HashMap<>();
        exceptionValues.put("MONSTER_EGGS", new Integer[] {2,3,4,5});
    }

    public static boolean isGlazedTerracotta(Material material) {
        for (GlazedTerracotta value : GlazedTerracotta.values()) {
            if (material.toString().equals(value.name())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSandstone(Material material) {
        for (Sandstone value : Sandstone.values()) {
            if (material.toString().equals(value.name())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isStonebrick(Material material) {
        for (StoneBrick value : StoneBrick.values()) {
            if (material.toString().equals(value.name())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isQuartzBlock(Material material) {
        return material.equals(Material.valueOf("QUARTZ_BLOCK"));
    }

    public static boolean isPurpurBlock(Material material) {
        return material.equals(Material.valueOf("PURPUR_PILLAR"));
    }

    public static boolean isLogBlock(Material material) {
        for (Log value : Log.values()) {
            if (material.toString().equals(value.name())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isStairBlock(Material material) {
        for (Stairs value : Stairs.values()) {
            if (material.toString().equals(value.name())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isEndRod(Material material) {
        return material.equals(Material.valueOf("END_ROD"));
    }

    public static boolean isPumpkin(Material material) {
        for (Pumpkins value : Pumpkins.values()) {
            if (material.toString().equals(value.name())) {
                return true;
            }
        }
        return false;
    }

    public static Boolean isHayBale(Material material) {
        return material.equals(Material.valueOf("HAY_BLOCK"));
    }

    public static Boolean isBoneBlock(Material material) {
        return material.equals(Material.valueOf("BONE_BLOCK"));
    }

    public static boolean isModifiable(Player player, Location location, Material material) {
        if (!StaticMethods.hasPermission(player, "chisel.use")) {
            return false;
        }
        if (BlockHelper.isStonebrick(material)) {
            if (Settings.allowStone) {
                return StaticMethods.canBuild(player, location);
            }
        }
        if (BlockHelper.isSandstone(material)) {
            if (Settings.allowSandstone) {
                return StaticMethods.canBuild(player, location);
            }
        }
        if (BlockHelper.isQuartzBlock(material)) {
            if (Settings.allowQuartz) {
                return StaticMethods.canBuild(player, location);
            }
        }
        if (BlockHelper.isGlazedTerracotta(material)) {
            if (Settings.allowTerracotta) {
                return StaticMethods.canBuild(player, location);
            }
        }
        if (BlockHelper.isPurpurBlock(material)) {
            if (Settings.allowPurpurPillar) {
                return StaticMethods.canBuild(player, location);
            }
        }
        if (BlockHelper.isLogBlock(material)) {
            if (Settings.allowLogs) {
                return StaticMethods.canBuild(player, location);
            }
        }
        if (BlockHelper.isStairBlock(material)) {
            if (Settings.allowStairs) {
                return StaticMethods.canBuild(player, location);
            }
        }
        if (BlockHelper.isEndRod(material)) {
            if (Settings.allowEndRods) {
                return StaticMethods.canBuild(player, location);
            }
        }
        if (BlockHelper.isPumpkin(material)) {
            if (Settings.allowPumpkins) {
                return StaticMethods.canBuild(player, location);
            }
        }
        if (BlockHelper.isHayBale(material)) {
            if (Settings.allowHayBales) {
                return StaticMethods.canBuild(player, location);
            }
        }
        if (BlockHelper.isBoneBlock(material)) {
            if (Settings.allowBoneBlocks) {
                return StaticMethods.canBuild(player, location);
            }
        }
        return false;
    }

    // modify the block
    public static boolean alterData(Block block) {
        byte data = block.getData();
        if (maxValues.containsKey(block.getType().toString())) {
            byte max = maxValues.get(block.getType().toString());
            if (data < max) {
                block.setData((byte) (data + 1));
                return true;
            } else {
                block.setData((byte) 0);
                return true;
            }
        } else {
            if (exceptionValues.containsKey(block.getType().toString())) {
                Integer[] ints = exceptionValues.get(block.getType().toString());
                for (int integer : ints) {
                    if ((byte) integer == data) {
                        int min = getMin(ints);
                        int max = getMax(ints);
                        if (data < max) {
                            block.setData((byte) (data + 1));
                            return true;
                        } else {
                            block.setData((byte) min);
                            return true;
                        }
                    }
                }
            }
        }
        if (isPurpurBlock(block.getType()) || isHayBale(block.getType()) || isBoneBlock(block.getType())) {
            if (data == 0) {
                block.setData((byte) 4);
                return true;
            }
            if (data == 4) {
                block.setData((byte) 8);
                return true;
            }
            if (data == 8) {
                block.setData((byte) 0);
                return true;
            }
        }
        if (isLogBlock(block.getType())) {
            if (data == 0) {
                block.setData((byte) 4);
                return true;
            }
            if (data == 1) {
                block.setData((byte) 5);
                return true;
            }
            if (data == 2) {
                block.setData((byte) 6);
                return true;
            }
            if (data == 3) {
                block.setData((byte) 7);
                return true;
            }
            if (data == 4) {
                block.setData((byte) 8);
                return true;
            }
            if (data == 5) {
                block.setData((byte) 9);
                return true;
            }
            if (data == 6) {
                block.setData((byte) 10);
                return true;
            }
            if (data == 7) {
                block.setData((byte) 11);
                return true;
            }
            if (data == 8) {
                block.setData((byte) 0);
                return true;
            }
            if (data == 9) {
                block.setData((byte) 1);
                return true;
            }
            if (data == 10) {
                block.setData((byte) 2);
                return true;
            }
            if (data == 11) {
                block.setData((byte) 3);
                return true;
            }
        }
        return false;
    }

    public static int getMin(Integer[] inputArray){
        int minValue = inputArray[0];
        for(int i=1;i<inputArray.length;i++){
            if(inputArray[i] < minValue){
                minValue = inputArray[i];
            }
        }
        return minValue;
    }

    public static int getMax(Integer[] inputArray){
        int maxValue = inputArray[0];
        for(int i=1;i < inputArray.length;i++){
            if(inputArray[i] > maxValue){
                maxValue = inputArray[i];
            }
        }
        return maxValue;
    }
}