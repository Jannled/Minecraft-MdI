package com.github.jannled.mdiServer.player;

import org.bukkit.entity.Player;

import com.github.jannled.mdiServer.lobby.Lobby;

public class ServerPlayer
{
	Player player;
	Lobby lobby;
	
	public ServerPlayer(Player player, Lobby lobby)
	{
		this.player = player;
		this.lobby = lobby;
	}
	
	public Player getPlayer()
	{
		return player;
	}
	
	public void setLobby(Lobby lobby)
	{
		this.lobby = lobby;
	}
}