package me.Jorre.KeepInventory;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;


public class KeepInventory extends JavaPlugin implements CommandExecutor, Listener {
	
	public static KeepInventory plugin;
	String VTprefix ="&c&lVoltcraft &8>>";
	
	private final boolean hasMethod = hasMethod("setKeepInventory");
	
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
		plugin = this;
		this.getConfig().options().copyDefaults();
		this.saveConfig();

		
	}
	public void onDisable() {
		this.saveConfig();
	}
	
	@EventHandler
	public void onEntityDeath(PlayerDeathEvent event) {
		Player player = (Player) event.getEntity();

		if (player.hasPermission("keepinventory.keep")) {
			if (!hasMethod) {
				InventoryHandler.getInstance().saveInventoryAndArmor(player);
				event.getDrops().clear();
			} else {
				event.setKeepInventory(true);
				event.getDrops().clear();
			}
		}

		if (player.hasPermission("keepinventory.keepxp")) {
			event.setKeepLevel(true);
			event.setDroppedExp(0);
		}
	}
	
	private boolean hasMethod(String string) {
		boolean hasMethod = false;
		Method[] methods = PlayerDeathEvent.class.getMethods();
		for (Method m : methods) {
			if (m.getName().equals(string)) {
				hasMethod = true;
				break;
			}
		}
		return hasMethod;
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();

		if (player.hasPermission("keepinventory.keep")) {
			InventoryHandler ih = InventoryHandler.getInstance();

			if ((ih.hasInventorySaved(player)) && (ih.hasArmorSaved(player))) {
				player.getInventory().setContents(ih.loadInventory(player));
				player.getInventory().setArmorContents(ih.loadArmor(player));
				ih.removeInventoryAndArmor(player);
			}
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		String Kinv = plugin.getConfig().getString("Users." + player.getUniqueId() + ".KeepInventory");
		PermissionAttachment attachment = player.addAttachment(this);
		playerPermissions.put(player.getUniqueId(), attachment);
//
		if (Kinv == ("On"))  {
//		
		player.sendMessage(ChatColor.BLUE + "Test");
		PermissionAttachment pperms = playerPermissions.get(player.getUniqueId());
		pperms.setPermission("keepinventory.keep", true);
		pperms.setPermission("keepinventory.keepxp", true);
		}else {
			player.sendMessage(ChatColor.RED + "test");
		
		}
//		
		if (player.hasPermission("keepinventory.keep") == true) {
			player.sendMessage(VTprefix + ChatColor.GOLD + "Keepinventory is enabled!");
		}else { player.sendMessage(VTprefix + ChatColor.GOLD + "Keepinventory is disabled!");
		} 
		
	}
	
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if (player.hasPermission("keepinventory.keep") == true) {
			plugin.getConfig().set("Users." + player.getUniqueId() + ".KeepInventory" , "On");
			
		}else {
			plugin.getConfig().set("Users." + player.getUniqueId() + ".KeepInventory", "Off");
		}
		
		
		saveConfig();
		
	}
	

	public HashMap<UUID, PermissionAttachment> playerPermissions = new HashMap<UUID, PermissionAttachment>();

	public String cmd1 = "keepinventory";
	public String cmd2 = "ki";
	public String cmd3 = "discord";
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel,
			String[] args) {
		Player player = (Player) sender;
		PermissionAttachment pperms = playerPermissions.get(player.getUniqueId());
		PermissionAttachment attachment = player.addAttachment(this);
		playerPermissions.put(player.getUniqueId(), attachment);
		
		if (cmd.getName().equalsIgnoreCase(cmd1)){
			if (!player.hasPermission("keepinventory.keep") == true) {
			player.sendMessage("&c&lVoltcraft &8>> &7You have &aEnabled &7KeepInventory");
			pperms.setPermission("keepinventory.keep", true);
			pperms.setPermission("keepinventory.keepxp", true);
			
			
			
		}else {
			player.sendMessage("&c&lVoltcraft &8>> &7You need to &4relog &7to &4disable &7KeepInventory");
			pperms.unsetPermission("keepinventory.keep");
			pperms.unsetPermission("keepinventory.keepxp");
		return true;
	}
		return true;}else if (cmd.getName().equalsIgnoreCase(cmd3)) {
			player.sendMessage(VTprefix + ChatColor.AQUA + "https://discord.gg/8h24t6p");

		return true;}
		return true;
	}
	

}
	
	


