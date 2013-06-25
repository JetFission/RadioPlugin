package io.github.LilParker.RadioPlugin;

import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class RadioPluginCommandExecuter implements CommandExecutor {
	
	RadioPlugin plugin;
	public static HashMap<String, Float> playerFreqs = new HashMap<String, Float>();
	public static HashMap<String, String> eKey = new HashMap<String, String>();
	private String[] alphanumeric = new String[]{"a","b","c","d","e","f","g","h","i","k","j","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
			"1","2","3","4","5","6","7","8","9","0",
			"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	
	public RadioPluginCommandExecuter (RadioPlugin actPlugin) {
		plugin = actPlugin;

	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("setfreq")){
			if(sender instanceof Player){
				if(args.length == 1){
					if(((Player) sender).getItemInHand().getTypeId() == plugin.getConfig().getInt("radioitemid")){
						if(testParse(args[0])){
							playerFreqs.put(sender.getName(), Float.parseFloat(args[0]));
							sender.sendMessage("Your frequency is now " + playerFreqs.get(sender.getName()));
							return true;
						}else{
							sender.sendMessage("Invalid frequency");
							return false;
						}
					}else{
						sender.sendMessage("You must have a radio in hand to tune it");
						return true;
					}
				}else{
					sender.sendMessage("Need 1 argument");
					return false;
				}
				
			}else{
				sender.sendMessage("You must be a player");
				return false;
			}
		}
		if(cmd.getName().equalsIgnoreCase("getfreq")){
			if(sender instanceof Player){
				if(((Player) sender).getItemInHand().getTypeId() == plugin.getConfig().getInt("radioitemid")){
					if(playerFreqs.get(sender.getName()) != null){
						sender.sendMessage("Your current frequency is " + playerFreqs.get(sender.getName()));
						return true;
					}else{
						sender.sendMessage("Your radio isn't tuned to a frequency");
						return true;
					}
				}else{
					sender.sendMessage("You must have a radio in your hand to check it's frequency");
					return true;
				}
			}else{
				sender.sendMessage("You must be a player");
				return false;
			}
		}
		if (cmd.getName().equalsIgnoreCase("setekey")){
			if (sender instanceof Player){
				if (args.length==1){
					if(((Player) sender).getItemInHand().getTypeId() == plugin.getConfig().getInt("radioitemid")){
						if(args[0].equals("clear") == false){
							eKey.put(sender.getName(), (args[0]));
							sender.sendMessage("Your encryption key is now " + eKey.get(sender.getName()));
							return true;
							}else{
							eKey.put(sender.getName(), null);
							sender.sendMessage("Your encryption key has been cleared");
							return true;
							}
						}else{
						sender.sendMessage("You must have a radio in your hand to do this");
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
		if(cmd.getName().equalsIgnoreCase("getekey")){
			if (sender instanceof Player){
				if(((Player) sender).getItemInHand().getTypeId() == plugin.getConfig().getInt("radioitemid")){
					if(eKey.get(sender.getName()) != null){
						sender.sendMessage("Your radio encryption key is " + eKey.get(sender.getName()));
						return true;
					}else{
						sender.sendMessage("You don't have an encryption key right now");
						return true;
					}
				}else{
					sender.sendMessage("You must have a radio in your hand to check it's encryption key");
					return true;
				}
			}else{
				sender.sendMessage("You must be a player to use this command");
				return true;
			}
		}
		if(cmd.getName().equalsIgnoreCase("radio")){
			if(sender instanceof Player){
				if(((Player) sender).getItemInHand().getTypeId() == plugin.getConfig().getInt("radioitemid")){
				if(playerFreqs.get(sender.getName()) != null){
					if(SendTag.tag.get(sender.getName()) != null){
					String message = "[FREQ: " + playerFreqs.get(sender.getName()) + "] " + SendTag.tag.get(sender.getName()) + ": ";
					for(String messagePart : args){
						message = message + " " + messagePart;
					}
					Player[] playerList = Bukkit.getOnlinePlayers();
					for(Player player : playerList){
						if(playerFreqs.get(player.getName()) != null && player.getInventory().contains(plugin.getConfig().getInt("radioitemid"))){
							float playerFreq = playerFreqs.get(player.getName());
							String enKey = eKey.get(player.getName());
							if(playerFreq == playerFreqs.get(sender.getName())){
									if(eKey.get(sender.getName()) != null){
										if(enKey != null && enKey.equals(eKey.get(sender.getName()))){
											message = plugin.getConfig().getString("radioencryptedcolor") + "[E]" + message;
											player.sendMessage(message);
									}else if(enKey != eKey.get(sender.getName())){
										Random randGen = new Random();
										String msg = "";
										for(String messagePart : args){
											msg = msg + " " + messagePart;
										}
										String scrambledMessage = plugin.getConfig().getString("radioencryptedcolor") + "[E]" + "[FREQ: " + playerFreqs.get(sender.getName()) + "] " + SendTag.tag.get(sender.getName()) + ": ";
										for(char ch : msg.toCharArray()){
											if(ch != ' '){
											int randInt = randGen.nextInt(alphanumeric.length);
											String str = alphanumeric[randInt];
											scrambledMessage = scrambledMessage + str;
											}else{
											scrambledMessage = scrambledMessage + " ";
											}
										}
										player.sendMessage(scrambledMessage);
									}
								}else{
									message = plugin.getConfig().getString("radiocolor") + message;
									player.sendMessage(message);
								}
							}
						}
					}
					return true;
					}else{
						sender.sendMessage("You must have a tag to talk on the radio");
						return true;
					}
				}else{
					sender.sendMessage("Your radio isn't tuned to a frequency");
					return true;
				}
			}else{
				sender.sendMessage("You must have a radio in your hand to use it");
				return true;
			}
			}else{
				sender.sendMessage("You must be a player");
				return false;
			}
		}
		return false;
	}
	private boolean testParse (String str) {
		try{
			Float.parseFloat(str);
			return true;
		}catch(NumberFormatException nfe){
			return false;
		}
	}
}
