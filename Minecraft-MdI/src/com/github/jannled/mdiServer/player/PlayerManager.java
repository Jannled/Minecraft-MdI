package com.github.jannled.mdiServer.player;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.github.jannled.mdiServer.MdIServer;
import com.github.jannled.mdiServer.lobby.Lobby;

public class PlayerManager implements Listener
{
	MdIServer main;
	HashMap<Player, ServerPlayer> players = new HashMap<Player, ServerPlayer>();
	
	public PlayerManager(MdIServer main)
	{
		this.main = main;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e)
	{
		ServerPlayer player = players.get(e.getPlayer());
		if(player == null)
		{
			players.put(e.getPlayer(), new ServerPlayer(e.getPlayer(), main.getLobbyManager().reconnectLobby(e.getPlayer())));
		}
		else
		{
			main.getLobbyManager().reconnectLobby(e.getPlayer());
		}
	}
	
	public void setLobby(Player player, Lobby lobby)
	{
		ServerPlayer splayer = players.get(player);
		setLobby(splayer, lobby);
	}
	
	public void setLobby(ServerPlayer player, Lobby lobby)
	{
		player.setLobby(lobby);
	}
}
