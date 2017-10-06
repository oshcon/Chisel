package net.doodcraft.oshcon.bukkit.chisel;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class ChiselUseEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private Player player;
    private ItemStack chisel;
    private Block block;
    private boolean cancelled;

    public ChiselUseEvent(Player player, ItemStack chisel, Block block) {
        this.player = player;
        this.chisel = chisel;
        this.block = block;
    }

    public Player getPlayer() {
        return this.player;
    }

    public ItemStack getChisel() {
        return this.chisel;
    }

    public Block getBlock() {
        return this.block;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean bln) {
        this.cancelled = bln;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}