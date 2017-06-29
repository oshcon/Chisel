package net.doodcraft.oshcon.bukkit.chisel.listeners;

import net.doodcraft.oshcon.bukkit.chisel.ChiselPlugin;
import net.doodcraft.oshcon.bukkit.chisel.config.Settings;
import net.doodcraft.oshcon.bukkit.chisel.util.BlockHelper;
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
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class PlayerListener implements Listener {

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

    @EventHandler(ignoreCancelled = true)
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (event.getHand().equals(EquipmentSlot.OFF_HAND)) {
                return;
            }
            if (event.getItem() != null) {
                ItemStack item = event.getItem();
                if (StaticMethods.isChiselItem(item)) {
                    if (event.getClickedBlock() != null) {
                        Block block = event.getClickedBlock();
                        Material material = block.getType();
                        if (Settings.debug) {
                            if (event.getPlayer().isSneaking()) {
                                event.getPlayer().sendMessage(StaticMethods.addColor(Settings.pluginPrefix + " &d[DEBUG] &e" + block.getState().getData().toString()));
                            } else {
                                event.getPlayer().sendMessage(StaticMethods.addColor(Settings.pluginPrefix + " &d[DEBUG] &e" + block.getType().toString() + "~" + block.getData()));
                            }
                        }
                        if (BlockHelper.isModifiable(event.getPlayer(), block.getLocation(), material)) {
                            if (BlockHelper.alterData(block)) {
                                if (!event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
                                    try {
                                        if (Settings.playSoundUse) {
                                            block.getLocation().getWorld().playSound(block.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.75F, 2.0F);
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
                                        event.getPlayer().getLocation().getWorld().dropItem(event.getPlayer().getLocation(), dropStack);
                                    }
                                    ItemMeta meta = item.getItemMeta();
                                    List<String> lore = meta.getLore();
                                    String[] durabilityString = lore.get(0).split("/");
                                    int durability = Integer.valueOf(durabilityString[0]);
                                    if (durability <= 1) {
                                        event.getPlayer().getInventory().remove(item);
                                        if (Settings.playSoundBreak) {
                                            Bukkit.getScheduler().runTaskLater(ChiselPlugin.plugin, () -> block.getLocation().getWorld().playSound(block.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, 1.2F),5L);
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
                }
            }
        }
    }
}
