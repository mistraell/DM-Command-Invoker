package top.darkmine.comm.commandinvoker;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class CommandInvoker extends JavaPlugin {
    public static CommandInvoker Instance;
    public FileConfiguration config;

    public @NotNull FileConfiguration getConfig() {
        return config;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("cinvoker") && args.length > 0 &&
                args[0].equalsIgnoreCase("reload")) {
            LoadConfig();
            sender.sendMessage("Конфигурация успешно перезагружена.");
            return true;
        }
        return false;
    }
    private  void LoadConfig(){
        java.io.File configFile = new java.io.File(getDataFolder(), "config.yml");
        config = YamlConfiguration.loadConfiguration(configFile);

    }
    @Override
    public void onEnable() {
        if (Instance == null) {
            Instance = this;
        }
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
            saveDefaultConfig();
        }
        java.io.File configFile = new java.io.File(getDataFolder(), "config.yml");
        config = YamlConfiguration.loadConfiguration(configFile);

        getLogger().info(config.getString("config-load-message"));

        Bukkit.getPluginManager().registerEvents(new EventListener(), (Plugin) this);
    }
    public void InvokeCommand(String command, long delay){
        getLogger().info("!!!!delay = " + delay + " !!!! command " + command);
        if(delay > 0){
            Bukkit.getScheduler().runTaskLater(this, () -> {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
            }, delay);
            return;
        }
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
