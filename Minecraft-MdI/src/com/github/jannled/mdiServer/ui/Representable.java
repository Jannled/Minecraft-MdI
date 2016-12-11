package com.github.jannled.mdiServer.ui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface Representable
{	
	/**
	 * Gets called when the representing item gets clicked by a com.github.jannled.mdiServer.player.
	 * @param The com.github.jannled.mdiServer.player who has clicked on the item
	 * @return If the interface should be closed.
	 */
	public boolean clicked(Player player);
	
	/**
	 * Gets the item that is representing this Object. Used to fill the GUI with the representing items!
	 * @return The representing item for this Object
	 */
	public ItemStack getItem();
}
