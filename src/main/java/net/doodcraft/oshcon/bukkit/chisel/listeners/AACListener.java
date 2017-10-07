package net.doodcraft.oshcon.bukkit.chisel.listeners;

import me.konsolas.aac.api.PlayerViolationEvent;
import net.doodcraft.oshcon.bukkit.chisel.util.StaticMethods;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.Map;

public class AACListener implements Listener {

    public static Map<Player, Long> lastAction = new HashMap<>();

    @EventHandler
    public void onPlayerViolation(PlayerViolationEvent event) {
        Player player = event.getPlayer();
        if (lastAction.containsKey(player)) {
            Long c = System.currentTimeMillis();
            Long l = lastAction.get(player);
            if ((c - l) <= 250) {
                StaticMethods.debug("Cancelling AAC hack detection for Chisel event.");
                event.setCancelled(true);
            }
        }
    }
}