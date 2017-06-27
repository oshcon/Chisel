package net.doodcraft.oshcon.bukkit.chisel;

import net.doodcraft.oshcon.bukkit.chisel.config.Settings;
import net.doodcraft.oshcon.bukkit.chisel.listeners.PlayerListener;
import net.doodcraft.oshcon.bukkit.chisel.util.StaticMethods;
import org.bstats.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class ChiselPlugin extends JavaPlugin {

    public static Plugin plugin;
    public static ShapelessRecipe chiselRecipe;
    public static Metrics metrics;

    @Override
    public void onEnable() {
        long start = System.currentTimeMillis();
        plugin = this;
        metrics = new Metrics(this);
        registerListeners();
        setExecutors();
        Settings.setupDefaults();
        ItemStack chisel = StaticMethods.getChiselItem();
        NamespacedKey keySpace = new NamespacedKey(plugin, "CHISEL");
        chiselRecipe = new ShapelessRecipe(keySpace, chisel);
        chiselRecipe.addIngredient(1, Material.STICK);
        chiselRecipe.addIngredient(1, Material.DIAMOND);
        Bukkit.getServer().addRecipe(chiselRecipe);
        long finish = System.currentTimeMillis();
        StaticMethods.log("&aChisel v" + plugin.getDescription().getVersion() + " is now loaded. &e(" + (finish - start) + "ms)");
    }

    public void registerListeners() {
        registerEvents(plugin, new PlayerListener());
    }

    public void registerEvents(Plugin plugin, Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
        }
    }

    public void setExecutors() {
        getCommand("chisel").setExecutor(new ChiselCommand());
    }
}
