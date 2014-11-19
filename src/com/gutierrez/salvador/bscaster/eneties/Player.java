package com.gutierrez.salvador.bscaster.eneties;

public class Player {
	private String id;
	private String name;

	public Player(String id, String name, boolean isHost) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

}
