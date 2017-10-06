package net.doodcraft.oshcon.bukkit.chisel.listeners;

import fr.neatmonster.nocheatplus.checks.CheckType;
import fr.neatmonster.nocheatplus.hooks.NCPExemptionManager;
import me.vagdedes.spartan.api.API;
import me.vagdedes.spartan.system.Enums;
import net.doodcraft.oshcon.bukkit.chisel.ChiselPlugin;
import net.doodcraft.oshcon.bukkit.chisel.ChiselUseEvent;
import net.doodcraft.oshcon.bukkit.chisel.config.Settings;
import net.doodcraft.oshcon.bukkit.chisel.util.BlockHelper;
import net.doodcraft.oshcon.bukkit.chisel.util.Compatibility;
import net.doodcraft.oshcon.bukkit.chisel.util.StaticMethods;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onUse(ChiselUseEvent event) {
        Block block = event.getBlock();
        ItemStack item = event.getChisel();
        Material material = block.getType();
        Player player = event.getPlayer();

        Long tStart = System.currentTimeMillis();

        if (Settings.debug) {
            if (player.isSneaking()) {
                player.sendMessage(StaticMethods.addColor(Settings.pluginPrefix + " &d[DEBUG] &e" + block.getState().getData().toString()));
            } else {
                //noinspection deprecation
                player.sendMessage(StaticMethods.addColor(Settings.pluginPrefix + " &d[DEBUG] &e" + block.getType().toString() + "~" + block.getData()));
            }
        }

        if (Compatibility.isHooked("NoCheatPlus")) {
            StaticMethods.debug("Cancelling NCP detection for Chisel event.");
            NCPExemptionManager.exemptPermanently(player, CheckType.ALL);
            Bukkit.getScheduler().runTaskLater(ChiselPlugin.plugin, new Runnable() {
                @Override
                public void run() {
                    NCPExemptionManager.unexempt(player, CheckType.ALL);
                }
            }, 1L);
        }

        if (Compatibility.isHooked("Spartan")) {
            StaticMethods.debug("Cancelling Spartan detection for Chisel event.");
            List<Enums.HackType> types = Arrays.asList(Enums.HackType.class.getEnumConstants());
            for (Enums.HackType type : types) {
                API.cancelCheck(player, type, 1);
            }
        }

        if (BlockHelper.isModifiable(player, block.getLocation(), material)) {
            if (BlockHelper.alterData(block)) {
                if (Settings.fakePlaceEvent) {
                    Bukkit.getScheduler().runTaskLater(ChiselPlugin.plugin, new Runnable(){
                        @Override
                        public void run() {
                            try {
                                BlockPlaceEvent placeEvent = new BlockPlaceEvent(block, block.getState(), block, item, player, true, EquipmentSlot.HAND);
                                Bukkit.getPluginManager().callEvent(placeEvent);
                            } catch (Exception ex) {
                                // It's possible another plugin, such as Essentials, did not like this event, for whatever reason.
                                if (Settings.debug) {
                                    StaticMethods.log("&cThere was an error firing a fake BlockPlaceEvent. It is probably safe to ignore.");
                                    ex.printStackTrace();
                                    StaticMethods.log("&cThis error could have been produced by another plugin.");
                                }
                            }
                        }
                    },1L);
                }
                if (!player.getGameMode().equals(GameMode.valueOf("CREATIVE"))) {
                    try {
                        if (Settings.playSoundUse) {
                            if (Compatibility.isSupported(ChiselPlugin.version, "1.9", "2.0")) {
                                block.getLocation().getWorld().playSound(block.getLocation(), Sound.BLOCK_ANVIL_USE, 0.75F, 1.8F);
                            }
                        }
                    } catch (Exception ex) {
                        if (Settings.debug) {
                            ex.printStackTrace();
                        }
                    }
                    if (item.getAmount() > 1) {
                        int dropAmount = item.getAmount() - 1;
                        ItemStack dropStack = new ItemStack(item);
                        dropStack.setAmount(dropAmount);
                        item.setAmount(1);
                        player.getLocation().getWorld().dropItem(player.getLocation(), dropStack);
                    }
                    ItemMeta meta = item.getItemMeta();
                    if (meta.hasLore()) {
                        List<String> lore = meta.getLore();
                        String[] durabilityString = lore.get(0).split("/");
                        int durability = Integer.valueOf(durabilityString[0]);
                        if (durability <= 1) {
                            player.getInventory().remove(item);
                            if (Settings.playSoundBreak) {
                                if (Compatibility.isSupported(ChiselPlugin.version, "1.9", "2.0")) {
                                    Bukkit.getScheduler().runTaskLater(ChiselPlugin.plugin, () -> block.getLocation().getWorld().playSound(block.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, 1.2F),5L);
                                }
                            }
                        } else {
                            lore.clear();
                            lore.add((durability - 1) + "/" + Settings.uses);
                            meta.setLore(lore);
                            item.setItemMeta(meta);
                        }
                    } else {
                        int max = Settings.uses;
                        int set = max - 1;
                        List<String> lore = new ArrayList<>();
                        lore.add(set + "/" + max);
                        meta.setLore(lore);
                        String[] durabilityString = lore.get(0).split("/");
                        int durability = Integer.valueOf(durabilityString[0]);
                        if (durability <= 1) {
                            player.getInventory().remove(item);
                            if (Settings.playSoundBreak) {
                                Bukkit.getScheduler().runTaskLater(ChiselPlugin.plugin, new Runnable() {
                                    @Override
                                    public void run() {
                                        if (Compatibility.isSupported(ChiselPlugin.version, "1.9", "2.0")) {
                                            block.getLocation().getWorld().playSound(block.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, 1.2F);
                                        }
                                    }
                                },5L);
                            }
                        } else {
                            lore.clear();
                            lore.add((durability - 1) + "/" + Settings.uses);
                            meta.setLore(lore);
                            item.setItemMeta(meta);
                        }
                    }
                }
            }
        }

        if (Settings.debug) {
            Long tEnd = System.currentTimeMillis();
            StaticMethods.debug("[Benchmark] Chisel use benchmark: " + (tEnd - tStart) + "ms");
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onCraft(CraftItemEvent event) {
        HumanEntity entity = event.getWhoClicked();
        if (entity instanceof Player) {
            Player player = (Player) entity;
            if (event.getRecipe().getResult().equals(StaticMethods.getChiselItem())) {
                if (!StaticMethods.hasPermission(player, "chisel.craft")) {
                    event.setCancelled(true);
                }
            }
        }
    }

    public boolean isOffHandClick(PlayerInteractEvent event) {
        if (Compatibility.isSupported(ChiselPlugin.version, "1.9", "2.0")) {
            return event.getHand().equals(EquipmentSlot.valueOf("OFF_HAND"));
        } else {
            try {
                return event.getHand().equals(EquipmentSlot.valueOf("OFF_HAND"));
            } catch (NoSuchMethodError ex) {
                return false;
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (isOffHandClick(event)) {
                return;
            }
            if (event.getItem() != null) {
                ItemStack item = event.getItem();
                if (StaticMethods.isChiselItem(item)) {
                    if (event.getClickedBlock() != null) {
                        Block block = event.getClickedBlock();
                        ChiselUseEvent chiselEvent = new ChiselUseEvent(event.getPlayer(), item, block);
                        try {
                            Bukkit.getPluginManager().callEvent(chiselEvent);
                        } catch (Exception ex) {
                            StaticMethods.log("&cThere was an error firing ChiselUseEvent:");
                            ex.printStackTrace();
                            StaticMethods.log("&cPlease report this to the plugin author, Dooder07.");
                        }
                    }
                }
            }
        }
    }
}