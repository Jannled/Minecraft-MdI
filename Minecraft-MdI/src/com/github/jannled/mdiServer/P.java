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
	public static final String pluginName = ChatColor.DARK_AQUA + "[" + ChatColor.DARK_RED + "SERVER" + ChatColor.DARK_AQUA + "] " + ChatColor.GOLD;;
	
	private P() {}
}
