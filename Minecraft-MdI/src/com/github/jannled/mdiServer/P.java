package com.github.jannled.mdiServer;

import org.bukkit.ChatColor;

/**
 * Class for reusing the plugin prefix, 
 * @author Jannled
 *
 */
public class P
{
	/** The permission prefix used in plugin.yml */
	public static final String pluginPermission = "mdiServer";
	
	/** The name to prefix in chat */
	public static final String pluginName = getPref("SERVER");
	
	/** 
	 * Get the colored prefix for the given name.
	 * @param anouncerName The name will be put in square brackets and gets colorized
	 */
	public static String getPref(String anouncerName)
	{
		return ChatColor.DARK_AQUA + "[" + ChatColor.DARK_RED + anouncerName + ChatColor.DARK_AQUA + "] " + ChatColor.GOLD;
	}
	
	private P() {}
}
