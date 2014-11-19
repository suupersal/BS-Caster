package com.gutierrez.salvador.bscaster.eneties;

import java.util.ArrayList;
import java.util.Stack;

public class Hand {
	ArrayList<Stack<Integer>> hand = new ArrayList<Stack<Integer>>();

	public Hand() {
		for (int i = 0; i < 14; i++) {
			hand.add(new Stack<Integer>());
		}
	}

	public int getTop(int faceValue) {
		// if hand does not have any cards of requested face value
		if (hand.get(faceValue).isEmpty()) {
			return -1;
		} else {
			return hand.get(faceValue).peek();
		}
	}

	public int addCard(int card) {
		int faceValue = 0;

		// CLOVERS 1-13
		if (card >= 1 && card <= 13) {
			faceValue = card;

		}
		// SPADES
		else if (card >= 14 && card <= 26) {
			faceValue = card - 13;

		}

		// DIAMONDS
		else if (card >= 27 && card <= 39) {
			faceValue = card - 26;

		}
		// HEARTS
		else if (card >= 40 && card <= 52) {
			faceValue = card - 39;

		}
		Stack<Integer> stack = hand.get(faceValue);
		stack.push(card);
		// return the faceValue of the card, will be needed for gridview
		return faceValue;
	}

	public boolean removeTop(int faceValue) {
		if (hand.get(faceValue).isEmpty()) {
			return false;
		} else {
			hand.get(faceValue).pop();
			return true;
		}
	}

	// used to count total cards fof same suit, given the actual face value 1-13
	public int countFaceValues(int faceValue) {
		return hand.get(faceValue).size();
	}

	// used to count total cards of same suit, given actual card 1-52
	public int countOfSameSuit(int card) {
		int faceValue = 0;

		// CLOVERS 1-13
		if (card >= 1 && card <= 13) {
			faceValue = card;

		}
		// SPADES
		else if (card >= 14 && card <= 26) {
			faceValue = card - 13;

		}

		// DIAMONDS
		else if (card >= 27 && card <= 39) {
			faceValue = card - 26;

		}
		// HEARTS
		else if (card >= 40 && card <= 52) {
			faceValue = card - 39;

		}
		return countFaceValues(faceValue);
	}

	public int countTotalCards() {
		int count = 0;
		for (Stack<Integer> s : hand) {
			count += s.size();
		}
		return count;
	}
}
