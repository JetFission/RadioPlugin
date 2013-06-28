package io.github.LilParker.RadioPlugin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RadioVolume implements CommandExecutor {

	RadioPlugin plugin;
	int volume = 0;
	
	public RadioVolume (RadioPlugin actPlugin) {
		plugin = actPlugin;
	}
	
	public void RadioSendHear (String sentMessage, Player sender) {
		String message = EssentialsUtils.getNickname(sender.getName()) + ": " + sentMessage;
		for(Player player : Bukkit.getOnlinePlayers()){
			if(sender.getLocation().distance(player.getLocation()) <= plugin.getConfig().getInt("radiohearrange")){
				player.sendMessage(message);
			}
		}
	}
	public void RadioRecieveRange (String sentMessage, Player reciever, Player sender) {
		String message = "[RADIO]" + SendTag.tag.get(sender.getName()) + ": " + sentMessage;
		for(Player player : Bukkit.getOnlinePlayers()){
			if(reciever.getLocation().distance(player.getLocation()) >= volume){
				player.sendMessage(message);
			}
		}
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("setvol")){
			if(sender instanceof Player){
				if(((Player) sender).getItemInHand().getTypeId() == plugin.getConfig().getInt("radioitemid")){
					if(args.length == 1){
						if(testParse(args[0])){
							int argu = Integer.parseInt(args[0]); 
							if ((argu <= plugin.getConfig().getInt("radiomaxvol"))){
								volume = argu;
								sender.sendMessage("Your volume is now " + volume);
								return true;
							}else{
								sender.sendMessage("Maximium volume limit has been reached");
								return false;
							}
						}else{
							sender.sendMessage("Volume must be an integer");
							return false;
						}
					}else{
						sender.sendMessage("Need 1 argument!");
						return false;
					}
				}else{
					sender.sendMessage("You must have a radio in your hand to set it's volume");
					return true;
				}
			}else{
				sender.sendMessage("You must be a player");
				return true;
			}
		}
		if(cmd.getName().equalsIgnoreCase("getvol")){
			if(sender instanceof Player){
				if(((Player) sender).getItemInHand().getTypeId() == plugin.getConfig().getInt("radioitemid")){
					if(volume == 0){
						sender.sendMessage("Your radio is only audible to you right now");
						return true;
					}else{
						sender.sendMessage("Your radio's volume is " + volume);
						return true;
					}
				}else{
					sender.sendMessage("You must have a radio in your hand to check it's volume");
					return true;
				}
			}else{
				sender.sendMessage("You must be a player");
				return true; 
			}
		}
		return false;
	}
	private boolean testParse (String testString) {
		try{
			Integer.parseInt(testString);
			return true;
		}catch(NumberFormatException nfe){
			return false;
		}
	}
}
