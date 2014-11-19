package com.gutierrez.salvador.bscaster;

import java.util.Random;

import com.gutierrez.salvador.bscaster.eneties.Game;
import com.gutierrez.salvador.bscaster.eneties.Player;
import com.gutierrez.salvador.bscaster.helpers.CommandBuilder;
import com.gutierrez.salvador.bscaster.helpers.Commands;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GameActivityHost extends GameActivity {
	private Game activeGame;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		activeGame = MainActivity.activeGame;
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void processMessageReceived(String message) {
		String[] mTokens = message.split("@");
		String command = mTokens[0];

		// Commands to start new turn
		if (command.equals(strConvert(Commands.NEW_TURN))) {
			setCurrentCard(intConvert(mTokens[3]));
			if (emptyHand) {
				if (nextTurnWin) {
					declareWin();
				}
				nextTurnWin = true;
			}
			if (mTokens[1].equals(uniqueId)) {
				myTurn();
			}
		}

		// Request from player to play his/her cards
		else if ((command.equals(strConvert(Commands.PLAY_CARDS)))
				&& activeGame.getCurrentPlayer().getId().equals(mTokens[1])) {
			activeGame.setLastPlay(0, intConvert(mTokens[3]));
			activeGame.setLastPlay(1, intConvert(mTokens[4]));
			activeGame.setLastPlay(2, intConvert(mTokens[5]));
			activeGame.setLastPlay(3, intConvert(mTokens[6]));
			activeGame.setLastPlayer(mTokens[1]);
			activeGame.nextCard();
			activeGame.nextPlayer();

			// send new command to move on to next player;
			String commandToSend = CommandBuilder.build(Commands.NEW_TURN,
					activeGame.getCurrentPlayer().getId(),
					activeGame.getCurrentCard());
			broadCastCommand(commandToSend);

			// Command is to add cards to hand
		} else if (command.equals(strConvert(Commands.ADD_CARD_TO_HAND))
				&& mTokens[1].equals(uniqueId)) {
			for (int i = 3; i < mTokens.length; i++) {
				addCardToHand(intConvert(mTokens[i]));
			}

			// Player has called bullshit
		} else if (command.equals(strConvert(Commands.CALL_BULLSHIT))) {
            int[] discardDeck=activeGame.getDiscardDeck();
			if (activeGame.lastWasBullshit()) {
				
					broadCastCommand(CommandBuilder.build(
							Commands.ADD_CARD_TO_HAND,activeGame.getLastPlayer(),discardDeck));
				
			} else {
				broadCastCommand(CommandBuilder.build(
						Commands.ADD_CARD_TO_HAND,mTokens[1],discardDeck));
			}
			
			int[] lastPlay = activeGame.getLastPlay();
			broadCastCommand(CommandBuilder.build(Commands.REVEAL_LAST_PLAY, uniqueId,lastPlay));
			activeGame.emptyDiscardDeck();
		}

	
		// Player declared win
		else if (command.equals(strConvert(Commands.DECLARE_WIN))) {
			// Broadcast to others that game has been won, and by whom.
			broadCastCommand(CommandBuilder.build(Commands.GAME_WON, uniqueId));
		}
		// Game has been won
		else if (command.equals(strConvert(Commands.GAME_WON))) {
			if (mTokens[1].equals(uniqueId)) {
				gameOver(true);
			} else {
				gameOver(false);
				
			}
		}
	}
@Override
public void newGame(View view){
	super.newGame(null);
	activeGame.reset();
	dealBtn.setVisibility(Button.VISIBLE);
	broadCastCommand(CommandBuilder.build(Commands.RESET_GAME,uniqueId));
	
}


@Override
protected void gameOver(boolean won){
	super.gameOver(won);
	quitBtn.setVisibility(Button.VISIBLE);
	resetBtn.setVisibility(Button.VISIBLE);
}

public void quit(View view) {
	broadCastCommand(CommandBuilder.build(Commands.END_GAME,uniqueId));
	Intent intent = new Intent(GameActivityHost.this,MainActivity.class);
	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	startActivity(intent);
	
}
	
	public void deal(View view) {
		
		int[] cards = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
				16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31,
				32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47,
				48, 49, 50, 51, 52, 0, 0, 0 };
		shuffle(cards);
		// shuffle twice just for the hell of it. why not.
		shuffle(cards);
		Player[] players = activeGame.getPlayers();
		int playerCount = activeGame.getPlayerCount();
		int dealIndex = 0;

		// Build hand for each player, then just send one single command with
		// all cards belenging to them
		// less overhead compared to sending message per card dealt
		String[] tempHands = new String[playerCount];
		for (int i = 0; i < tempHands.length; i++) {
			tempHands[i] = "";
		}

		int[] tempCardCount = new int[tempHands.length];
		for (int card : cards) {
			tempHands[dealIndex] = tempHands[dealIndex] + "@"
					+ strConvert(card);
			tempCardCount[dealIndex]++;
			if (dealIndex == playerCount - 1) {
				dealIndex = 0;
			} else {
				dealIndex++;
			}
		}
		//note to self, should change loop to stop at number of players, not 2
		for (int i = 0; i < activeGame.getPlayerCount(); i++) {
			broadCastCommand(CommandBuilder.build(Commands.ADD_CARD_TO_HAND,
					players[i].getId(), tempCardCount[i], tempHands[i]));
		}

		view.setVisibility(View.INVISIBLE);
		startFirstTurn();
		
	}

	private void startFirstTurn() {
		String playerId = activeGame.getCurrentPlayer().getId();
		String card = strConvert(activeGame.getCurrentCard());
		broadCastCommand(CommandBuilder
				.build(Commands.NEW_TURN, playerId, card));
	}

	//
	// Below are just helper methods, may put them in their own class if they
	// get large enough
	//
	private void shuffle(int[] array) {
		Random rnd = new Random();
		for (int i = array.length - 1; i > 0; i--) {
			int index = rnd.nextInt(i + 1);
			// swap places,
			int a = array[index];
			array[index] = array[i];
			array[i] = a;
		}
	}

}
