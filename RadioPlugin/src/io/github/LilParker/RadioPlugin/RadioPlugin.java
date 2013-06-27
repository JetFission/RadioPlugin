package io.github.LilParker.RadioPlugin;

import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.plugin.java.JavaPlugin;

public class RadioPlugin extends JavaPlugin {
	
	@Override
	public void onEnable () {
		
		if(getConfig().getConfigurationSection("playerFreqs") != null){
		Map<String, Object> configmap = getConfig().getConfigurationSection("playerFreqs").getValues(false);
		for(Entry<String, Object> entry : configmap.entrySet()){
			Double val = (Double)entry.getValue();
			RadioPluginCommandExecuter.playerFreqs.put((String) entry.getKey(), val.floatValue());
			}
		}
		
		if(getConfig().getConfigurationSection("eKey") != null){
		Map<String, Object> eKeymap = getConfig().getConfigurationSection("eKey").getValues(false);
		for(Entry<String, Object> entry : eKeymap.entrySet()){
			RadioPluginCommandExecuter.eKey.put((String) entry.getKey(), (String) entry.getValue());
			}
		}
		
		if(getConfig().getConfigurationSection("tags") != null){
		Map<String, Object> tagMap = getConfig().getConfigurationSection("tags").getValues(false);
		for(Entry<String, Object> entry : tagMap.entrySet()){
			SendTag.tag.put((String) entry.getKey(), (String) entry.getValue());
			}
		}
		
		RadioPluginCommandExecuter executer = new RadioPluginCommandExecuter(this);
		EssentialsUtils eutils = new EssentialsUtils(this);
		SendTag tagsender = new SendTag(this);
		
		getCommand("getfreq").setExecutor(executer);
		getCommand("setfreq").setExecutor(executer);
		getCommand("radio").setExecutor(executer);
		getCommand("setekey").setExecutor(executer);
		getCommand("getekey").setExecutor(executer);
		getCommand("settag").setExecutor(tagsender);
		getCommand("gettag").setExecutor(tagsender);
	
	}

	@Override	
	public void onDisable () {
		getConfig().createSection("playerFreqs", RadioPluginCommandExecuter.playerFreqs);
		getConfig().createSection("tags", SendTag.tag);
		getConfig().createSection("eKey", RadioPluginCommandExecuter.eKey);
		saveConfig();
	}
}
