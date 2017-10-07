package net.doodcraft.oshcon.bukkit.chisel.listeners;

import DAKATA.PlayerCheatEvent;
import net.doodcraft.oshcon.bukkit.chisel.util.StaticMethods;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.Map;

public class DakataListener implements Listener {

    public static Map<Player, Long> lastAction = new HashMap<>();

    @EventHandler
    public void onCheat(PlayerCheatEvent event) {
        Player player = event.getPlayer();
        if (lastAction.containsKey(player)) {
            Long c = System.currentTimeMillis();
            Long l = lastAction.get(player);
            if ((c - l) <= 250) {
                StaticMethods.debug("Cancelling Dakata hack detection for Chisel event.");
                event.setCancelled(true);
            }
        }
    }
}