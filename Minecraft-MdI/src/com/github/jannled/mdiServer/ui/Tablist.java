package com.github.jannled.mdiServer.ui;

import java.lang.reflect.Field;

import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_11_R1.IChatBaseComponent;
import net.minecraft.server.v1_11_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_11_R1.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_11_R1.PlayerConnection;

/**
 * Util class for the tab list
 * @author Jannled
 * @author maffkev
 * Source: <a href="https://www.spigotmc.org/threads/new-1-8-tab-list-headers-and-footers.30756/">Spigot Forum</a>
 */
public class Tablist
{
	public static void setPlayerListHeader(Player player,String header,String footer){
		CraftPlayer cplayer = (CraftPlayer) player;
		PlayerConnection connection = cplayer.getHandle().playerConnection;
		IChatBaseComponent hj = ChatSerializer.a("{'text':'"+header+"'}");
		PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
		try
		{
			Field headerField = packet.getClass().getDeclaredField("a");
			headerField.setAccessible(true);
			headerField.set(packet, hj);
			headerField.setAccessible(!headerField.isAccessible()); 
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		connection.sendPacket(packet);
	}
  
  
	public static void setPlayerListFooter(Player player,String footer){
		CraftPlayer cp = (CraftPlayer) player;
		PlayerConnection con = cp.getHandle().playerConnection;
		IChatBaseComponent fj = ChatSerializer.a("{'text':'"+footer+"'}");
		PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
		try
		{
			Field footerField = packet.getClass().getDeclaredField("b");
			footerField.setAccessible(true);
			footerField.set(packet, fj);
			footerField.setAccessible(!footerField.isAccessible());
		} catch(Exception e)
		{
			e.printStackTrace();
		}
		con.sendPacket(packet);
	}
	public Tablist()
	{
		// TODO Auto-generated constructor stub
	}

}
