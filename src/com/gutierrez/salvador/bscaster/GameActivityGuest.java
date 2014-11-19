package com.gutierrez.salvador.bscaster;
import android.os.Bundle;
import android.widget.Button;


public class GameActivityGuest extends GameActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//hide the deal button, guest player does not need it
		
		dealBtn.setVisibility(Button.INVISIBLE);

	}
	

	
	
	
	

}
