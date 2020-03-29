package me.Jorre.KeepInventory;

import org.bukkit.entity.Player;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class KeepInventoryAPI extends PlaceholderExpansion {

	private KeepInventory plugin;

	
	public KeepInventoryAPI(KeepInventory plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean persist() {
		return true;
	}
	@Override
	public boolean canRegister() {
		return true;		
	}
	
	

	@Override
	public String getAuthor() {
		// TODO Auto-generated method stub
		return "VoltCraft";
	}

	@Override
	public String getIdentifier() {
		// TODO Auto-generated method stub
		return "keepinventory";
	}

	@Override
	public String getVersion() {
		// TODO Auto-generated method stub
		return "1.1";
	}
	@Override
	public String onPlaceholderRequest(Player player, String identifier) {
		if(player == null) {
			return "";
		}
		String rkinv = plugin.getConfig().getString("rUsers." + player.getUniqueId() + ".state");
		if(identifier.equals("state")) {
			return plugin.getConfig().getString("state", rkinv);
			
		}
		return null;
	}
}
