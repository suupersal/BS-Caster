package com.gutierrez.salvador.bscaster.adapters;

public class SingleCardItem {
	int imageId;
	String cardName;
	boolean isActive = false;
	private int count = 0;

	public SingleCardItem(int imageId, String cardName) {
		this.imageId = imageId;
		this.cardName = cardName;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	};

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public void setCount(int count) {
		this.count = count;

	}

	public int getCount() {

		return count;
	}

}
