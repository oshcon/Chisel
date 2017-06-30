package net.doodcraft.oshcon.bukkit.chisel;

import net.doodcraft.oshcon.bukkit.chisel.config.Settings;
import net.doodcraft.oshcon.bukkit.chisel.listeners.PlayerListener;
import net.doodcraft.oshcon.bukkit.chisel.util.BlockHelper;
import net.doodcraft.oshcon.bukkit.chisel.util.StaticMethods;
import org.bstats.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
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
        setupRecipe();
        BlockHelper.addMaxValues();
        BlockHelper.addExceptionValues();
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

    public static void setupRecipe() {
        Boolean reloadWarning = false;
        if (chiselRecipe != null) {
            if (chiselRecipe.getIngredientList() != null) {
                for (ItemStack item : chiselRecipe.getIngredientList()) {
                    if (!item.getType().toString().equals(Settings.craftMaterialHandle.toUpperCase())) {
                        reloadWarning = true;
                    }
                    if (!item.getType().toString().equals(Settings.craftMaterialTip.toUpperCase())) {
                        reloadWarning = true;
                    }
                }
                if (!chiselRecipe.getResult().getType().toString().equals(Settings.chiselMaterial.toUpperCase())) {
                    reloadWarning = true;
                }
            }
        }
        if (reloadWarning) {
            StaticMethods.log("&eIf the chisel recipe has changed, it is strongly recommended to restart your server asap.");
        }
        ItemStack chisel = StaticMethods.getChiselItem();
        // deprecated, find a way to use NamespacedKey soon.
        chiselRecipe = new ShapelessRecipe(chisel);
        for (ItemStack item : chiselRecipe.getIngredientList()) {
            chiselRecipe.removeIngredient(1, item.getType());
        }
        Material handle = Material.valueOf(Settings.craftMaterialHandle.toUpperCase());
        Material tip = Material.valueOf(Settings.craftMaterialTip.toUpperCase());
        try {
            chiselRecipe.addIngredient(1, handle);
            chiselRecipe.addIngredient(1, tip);
        } catch (Exception ex) {
            StaticMethods.log("&cThere was an error setting the chisel recipe.");
            StaticMethods.log("&cDefaulting to STICK (handle) and IRON_INGOT (tip)");
            chiselRecipe.addIngredient(1, Material.STICK);
            chiselRecipe.addIngredient(1, Material.IRON_INGOT);
        }
        Bukkit.getServer().addRecipe(chiselRecipe);
    }

    public void setExecutors() {
        getCommand("chisel").setExecutor(new ChiselCommand());
    }
}
