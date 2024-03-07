package top.darkmine.comm.commandinvoker;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventListener implements Listener {
    String line = "skills modifier removeall ";

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent worldChange) {
        long delay = CommandInvoker.Instance.config.getInt("on-player-joined-world-commands.on-world-changed-delay");
        Player player = worldChange.getPlayer();
        for (String world : CommandInvoker.Instance.config.getStringList(
                "on-player-joined-world-commands.worlds")) {
            if (player.getWorld().getName().equals(world)) {
                for (String command : CommandInvoker.Instance.config.getStringList(
                        "on-player-joined-world-commands.worlds." + world)) {
                    String replacedCommand = command.replace("%player_Name%", player.getName());
                    CommandInvoker.Instance.InvokeCommand(replacedCommand, delay);
                }
            }
        }
    }

    @EventHandler
    public void onQuitServer(PlayerQuitEvent eventQuit) {
        Player player = eventQuit.getPlayer();
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), line + player.getName());
    }

    @EventHandler
    public void onJoinServer(PlayerJoinEvent eventJoin) {
        Player player = eventJoin.getPlayer();
        long delay = CommandInvoker.Instance.config.getInt("on-join-commands.on-join-tick-delay");
        for (String command : CommandInvoker.Instance.config.getStringList("on-join-commands.commands")) {
            String replacedCommand = command.replace("%player_Name%", player.getName());
            CommandInvoker.Instance.InvokeCommand(replacedCommand, delay);
        }
    }


}
