package com.gutierrez.salvador.bscaster.eneties;

import android.widget.ImageView;

public class PlayBox {
	private ImageView[] imageViews = new ImageView[4];
	private int[] cardValues = { -1, -1, -1, -1 };

	public ImageView getImageView(int index) {
		return imageViews[index];
	}

	public void setImageView(ImageView imageView, int index) {
		this.imageViews[index] = imageView;
	}

	public int getCardValue(int index) {
		return cardValues[index];
	}

	public void setCardValue(int index, int value) {
		this.cardValues[index] = value;
	}

	public int getSlotsAvailable() {
		int availableSlots = 0;
		for (int i : cardValues) {
			if (i == -1) {
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
		if (getCardValue(index) == -1) {
			return true;
		}
		return false;
	}

	public int getAvailableSlot() {
		int availableIndex = -2;
		for (int i = 0; i < cardValues.length; i++) {
			if (cardValues[i] == -1) {
				availableIndex = i;
				break;
			}
		}
		return availableIndex;
	}

	public boolean add(int faceValue, int card, int imageResourceId) {

		if (!slotAvailable()) {
			return false;
		}
		int availableSlot = getAvailableSlot();
		imageViews[availableSlot].setImageResource(imageResourceId);
		cardValues[availableSlot] = card;
		return true;
	}

	// return -2 if slot was already empty, else value of removed card
	public boolean remove(int index, int placeHolderImageRID) {

		if (cardValues[index] != -1) {
			imageViews[index].setImageResource(placeHolderImageRID);
			cardValues[index] = -1;
			return true;
		}
		return false;
	}
}
