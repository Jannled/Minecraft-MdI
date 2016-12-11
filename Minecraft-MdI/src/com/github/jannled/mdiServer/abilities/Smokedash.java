package com.github.jannled.mdiServer.abilities;

import org.bukkit.Material;
import org.bukkit.entity.Snowball;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Smokedash extends Abilitie
{
	
	public Smokedash()
	{
		item = new ItemStack(Material.COAL);
		ItemMeta itm = item.getItemMeta();
		itm.setDisplayName("Smokedash");
		item.setItemMeta(itm);
	}

	@Override
	public void playerInteract(PlayerInteractEvent e)
	{
		Snowball dash = e.getPlayer().launchProjectile(Snowball.class);
		
		dash.setPassenger(e.getPlayer());
	}

	@Override
	public void playerEntityInteract(PlayerInteractAtEntityEvent e)
	{
		
	}
}
