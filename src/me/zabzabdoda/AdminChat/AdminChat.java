package me.zabzabdoda.AdminChat;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/*
 * Written by Tyler Massey aka zabzabdoda
 */

public class AdminChat extends JavaPlugin implements Listener {

	private static Server server;
	private static HashMap<Player,Boolean> playerMap;
	
	static {
		server = Bukkit.getServer();
	}

	public void onEnable() {
		System.out.println("Enabling Admin Chat Plugin by zabzabdoda");
		AdminChat.server.getPluginManager().registerEvents((Listener) this, (Plugin) this);
		playerMap = new HashMap<Player,Boolean>();
		for(Player p : server.getOnlinePlayers()) {
			playerMap.put(p, false);
		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (cmd.getName().equalsIgnoreCase("chat")) {
				if (args.length == 1) {
					if (args[0].equalsIgnoreCase("admin")) {
						if (player.hasPermission("chat.admin")) {
							playerMap.put(player, true);
							player.sendMessage("You are now in " + ChatColor.RED + "ADMIN" + ChatColor.WHITE + " chat");
							return true;
						}
					} else if (args[0].equalsIgnoreCase("global")) {
						if (player.hasPermission("chat.global")) {
							playerMap.put(player, false);
							player.sendMessage(
									"You are now in " + ChatColor.BLUE + "GLOBAL" + ChatColor.WHITE + " chat");
							return true;
						}
					} else if (args[0].equalsIgnoreCase("help")) {
						player.sendMessage("Help for AdminChat: ");
						player.sendMessage("/chat <admin|global>");
						player.sendMessage("admin - sends messages only OPs can see");
						player.sendMessage("global - sends messages everyone can see");
						return true;
					} else if (args[0].equalsIgnoreCase("status")) {
						if (playerMap.get(player)) {
							player.sendMessage(
									"You are currently in " + ChatColor.RED + "Admin" + ChatColor.WHITE + " Chat");
						} else {
							player.sendMessage(
									"You are currently in " + ChatColor.BLUE + "Global" + ChatColor.WHITE + " Chat");
						}
						return true;
					}
				} else {
					player.sendMessage(ChatColor.RED + "Incorrect Usage, type /chat help for help");
					return false;
				}
			} else if (cmd.getName().equalsIgnoreCase("broadcast")) {
				if(player.hasPermission("chat.broadcast")) {
					String message = ChatColor.BLUE + "[" + ChatColor.RED + "Broadcast" + ChatColor.BLUE + "]" + ChatColor.LIGHT_PURPLE;
					for(String s : args) {
						message += " " + s;
					}
					server.broadcastMessage(message);
					return true;
				}
				player.sendMessage(ChatColor.RED + "You do ");
				return true;
			}
			player.sendMessage(ChatColor.RED + "Incorrect Usage, type /chat help for help");
			return false;
		}
		sender.sendMessage("Only Players can run this command.");
		return false;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		playerMap.put(e.getPlayer(), false);
	}
	
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		if(playerMap.get(e.getPlayer())) {
			e.setCancelled(true);
			for(Player p : server.getOnlinePlayers()) {
				if(p.isOp()) {
					p.sendMessage(ChatColor.GOLD + "[AdminChat] " + ChatColor.WHITE + e.getPlayer().getName() + ": "+e.getMessage());
				}
			}
		}
	}
	
}
