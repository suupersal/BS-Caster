package com.gutierrez.salvador.bscaster.helpers;

public class Commands {
	// Player specific commands should be in the form of
	// [Command][PlayerID][Values...]
	public static final int START_GAME = 501;
	public static final int RESET_GAME = 503;

	// joining and hosting commands
	public static final int REQUEST_JOIN_LOBBY = 505;
	public static final int REQUEST_LEAVE_LOBBY = 507;
	public static final int REQUEST_LEAVE_GAME = 513;
	public static final int REQUEST_JOIN_GAME = 515;
	public static final int END_GAME = 521;
	public static final int KICK_PLAYER = 539;
	public static final int GAME_WON = 529;
	public static final int ADD_PLAYER_LOBBY = 527;
	public static final int REOMVE_PLAYER_LOBBY = 535;
	public static final int DECLARE_WIN = 533;

	// in game move commands
	public static final int ADD_CARD_TO_HAND = 509;
	public static final int REQUEST_CARD_COUNT = 511;
	public static final int NEW_TURN = 517;
	public static final int PLAY_CARDS = 519;
	public static final int REQUEST_TRADE = 523;
	public static final int TRADE_ACCEPTED = 525;
	public static final int CALL_BULLSHIT = 531;
	public static final int REVEAL_LAST_PLAY=537;

}
