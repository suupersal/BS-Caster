package com.gutierrez.salvador.bscaster.helpers;

import com.example.hexisland.R;

public class ImageResource {

	public static int getId(int card) {
		int imageResource;
		switch (card) {
		case 0:
			imageResource = R.drawable.joker;
			break;

		// CLOVERS
		case 1:
			imageResource = R.drawable.card_ac;
			break;
		case 2:
			imageResource = R.drawable.card_2c;
			break;
		case 3:
			imageResource = R.drawable.card_3c;
			break;
		case 4:
			imageResource = R.drawable.card_4c;
			break;
		case 5:
			imageResource = R.drawable.card_5c;
			break;
		case 6:
			imageResource = R.drawable.card_6c;
			break;
		case 7:
			imageResource = R.drawable.card_7c;
			break;
		case 8:
			imageResource = R.drawable.card_8c;
			break;
		case 9:
			imageResource = R.drawable.card_9c;
			break;
		case 10:
			imageResource = R.drawable.card_10c;
			break;
		case 11:
			imageResource = R.drawable.card_jc;
			break;
		case 12:
			imageResource = R.drawable.card_qc;
			break;
		case 13:
			imageResource = R.drawable.card_kc;
			break;

		// SPADES
		case 14:
			imageResource = R.drawable.card_as;
			break;
		case 15:
			imageResource = R.drawable.card_2s;
			break;
		case 16:
			imageResource = R.drawable.card_3s;
			break;
		case 17:
			imageResource = R.drawable.card_4s;
			break;
		case 18:
			imageResource = R.drawable.card_5s;
			break;
		case 19:
			imageResource = R.drawable.card_6s;
			break;
		case 20:
			imageResource = R.drawable.card_7s;
			break;
		case 21:
			imageResource = R.drawable.card_8s;
			break;
		case 22:
			imageResource = R.drawable.card_9s;
			break;
		case 23:
			imageResource = R.drawable.card_10s;
			break;
		case 24:
			imageResource = R.drawable.card_js;
			break;
		case 25:
			imageResource = R.drawable.card_qs;
			break;
		case 26:
			imageResource = R.drawable.card_ks;
			break;

		// DIAMONDS
		case 27:
			imageResource = R.drawable.card_ad;
			break;
		case 28:
			imageResource = R.drawable.card_2d;
			break;
		case 29:
			imageResource = R.drawable.card_3d;
			break;
		case 30:
			imageResource = R.drawable.card_4d;
			break;
		case 31:
			imageResource = R.drawable.card_5d;
			break;
		case 32:
			imageResource = R.drawable.card_6d;
			break;
		case 33:
			imageResource = R.drawable.card_7d;
			break;
		case 34:
			imageResource = R.drawable.card_8d;
			break;
		case 35:
			imageResource = R.drawable.card_9d;
			break;
		case 36:
			imageResource = R.drawable.card_10d;
			break;
		case 37:
			imageResource = R.drawable.card_jd;
			break;
		case 38:
			imageResource = R.drawable.card_qd;
			break;
		case 39:
			imageResource = R.drawable.card_kd;
			break;

		// HEARTS
		case 40:
			imageResource = R.drawable.card_ah;
			break;
		case 41:
			imageResource = R.drawable.card_2h;
			break;
		case 42:
			imageResource = R.drawable.card_3h;
			break;
		case 43:
			imageResource = R.drawable.card_4h;
			break;
		case 44:
			imageResource = R.drawable.card_5h;
			break;
		case 45:
			imageResource = R.drawable.card_6h;
			break;
		case 46:
			imageResource = R.drawable.card_7h;
			break;
		case 47:
			imageResource = R.drawable.card_8h;
			break;
		case 48:
			imageResource = R.drawable.card_9h;
			break;
		case 49:
			imageResource = R.drawable.card_10h;
			break;
		case 50:
			imageResource = R.drawable.card_jh;
			break;
		case 51:
			imageResource = R.drawable.card_qh;
			break;
		case 52:
			imageResource = R.drawable.card_kh;
			break;
		default:
			imageResource = R.drawable.card_placeholder;
			break;
		}
		return imageResource;
	}
	
	public static int getIdUpcoming(int current,int upcoming){
		int result=(current+upcoming)%13;
		//0 = joker, dont want jokers
		if (result==0){
			result= 13;
		}
		return getId(result);
	}

}
