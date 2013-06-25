package io.github.LilParker.RadioPlugin;

import java.util.HashMap;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SendTag implements CommandExecutor 
{
	RadioPlugin plugin;
	
	public SendTag (RadioPlugin actPlugin){
		plugin = actPlugin;
	}
	
	public static HashMap<String, String> tag = new HashMap<String, String>();
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("settag")){
			if (sender instanceof Player){
				if (args.length==1){
					if(((Player) sender).getItemInHand().getTypeId() == plugin.getConfig().getInt("radioitemid")){
					if(args[0].equals("clear") == false){
						tag.put(sender.getName(), (args[0]));
						sender.sendMessage("Your radio tag is now " + tag.get(sender.getName()));
						return true;
					}else{
						tag.put(sender.getName(), null);
						sender.sendMessage("Your tag has been cleared");
						return true;
					}
				}else{
					sender.sendMessage("You must have a radio in hand to set your tag");
					return true;
				}
				}else{
					sender.sendMessage("Need 1 argument!");
					return false;
					}
			}else{
					sender.sendMessage("You must be a player to use this command");
					return true;
				}
			}
		if(cmd.getName().equalsIgnoreCase("gettag")){
			if(sender instanceof Player){
				if(((Player) sender).getItemInHand().getTypeId() == plugin.getConfig().getInt("radioitemid")){
					if(tag.get(sender.getName()) != null){
						sender.sendMessage("Your tag is " + tag.get(sender.getName()));
						return true;
					}else{
						sender.sendMessage("You don't have a tag right now");
						return true;
					}
				}else{
					sender.sendMessage("You must have a radio in your hand to check your tag");
					return true;
				}
			}else{
				sender.sendMessage("You must be a player to use this command");
				return true;
			}
		}
		return false;
		}
	}