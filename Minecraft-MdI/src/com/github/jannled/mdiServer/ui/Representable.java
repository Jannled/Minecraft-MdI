package com.github.jannled.mdiServer.ui;

import org.bukkit.entity.Player;

public interface Representable
{
	/**
	 * Gets called when the representing item gets clicked by a player.
	 * @param The player who has clicked on the item
	 * @return If the interface should be closed.
	 */
	public boolean clicked(Player player);
}
