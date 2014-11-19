package com.gutierrez.salvador.bscaster.eneties;

import java.util.ArrayList;
import java.util.Iterator;

public class Game {

	private Player[] players = new Player[8];
	private int playerCount = 0;
	private int currentCard = 1;
	private int lastPlayer;
	private int[] lastPlay = new int[4];
	private int currentPlayer = 0;
	private ArrayList<Integer> discardDeck = new ArrayList<Integer>();
	
	

	public boolean addPlayer(String id, String name, boolean isHost) {
		if (slotAvailable()) {
			players[getAvailableSlot()] = (new Player(id, name, isHost));
			playerCount++;
			return true;
		}
		return false;

	}

	public int getCurrentCard() {
		return currentCard;
	}

	public void setCurrentCard(int currentCard) {
		this.currentCard = currentCard;
	}

	public int getSlotsAvailable() {
		int availableSlots = 0;
		for (Player i : players) {
			if (i == null) {
				availableSlots++;
			}
		}
		return availableSlots;
	}

	public boolean slotAvailable() {
		if (getSlotsAvailable() > 0) {
			return true;
		}
		return false;
	}

	public boolean slotEmpty(int index) {
		if (players[index] == null) {
			return true;
		}
		return false;
	}

	public int getAvailableSlot() {
		int availableIndex = -1;
		for (int i = 0; i < players.length; i++) {
			if (players[i] == null) {
				availableIndex = i;
				break;
			}
		}
		return availableIndex;
	}

	public void setLastPlay(int index, int value) {
		lastPlay[index] = value;
		if (value != -1) {
			discardDeck.add(value);
		}
	}

	public void setLastPlayer(String id) {
		for (int i = 0; i < players.length; i++) {
			if (players[i].getId().equals(id)) {
				lastPlayer = i;
				break;
			}
		}

	}

	public String getLastPlayer() {
		return players[lastPlayer].getId();
	}

	public String getNextPlayerID() {
		if (lastPlayer == (playerCount - 1)) {
			return players[0].getId();
		}
		return players[lastPlayer++].getId();
	}

	public void nextCard() {
		if (currentCard == 13) {
			currentCard = 1;
		} else {
			currentCard++;
		}

	}

	public Player[] getPlayers() {
		return players;
	}

	public int getPlayerCount() {
		return playerCount;
	}

	public void nextPlayer() {
		if (currentPlayer == playerCount - 1) {
			currentPlayer = 0;
		} else {
			currentPlayer++;
		}
	}

	public Player getCurrentPlayer() {
		return players[currentPlayer];
	}

	public boolean lastWasBullshit() {
		int last;
		if (currentCard == 0) {
			last = 13;
		}
		last = currentCard - 1;

		for (int i : lastPlay) {

			if (i%13 != last && i != -1) {
				return true;
			}
		}
		return false;
	}

	public int[] getDiscardDeck() {
		int[] result = new int[discardDeck.size()];
		Iterator<Integer> iterator = discardDeck.iterator();
		for (int i = 0; i < result.length; i++) {
			result[i] = iterator.next().intValue();
		}
		return result;
	}

	public void emptyDiscardDeck() {
		discardDeck.clear();

	}
	
	public int[] getLastPlay(){
		return lastPlay;
	}

	public void reset() {
		
		
		currentCard = 1;
		lastPlayer=0;
	lastPlay = new int[4];
		currentPlayer = 0;
		discardDeck = new ArrayList<Integer>();
		
	}
	
	
}
