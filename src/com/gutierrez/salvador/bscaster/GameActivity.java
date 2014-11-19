package com.gutierrez.salvador.bscaster;

import java.io.IOException;

import com.example.hexisland.R;
import com.google.android.gms.cast.CastDevice;
import com.google.sample.castcompanionlibrary.cast.DataCastManager;
import com.google.sample.castcompanionlibrary.cast.callbacks.DataCastConsumerImpl;
import com.google.sample.castcompanionlibrary.cast.exceptions.NoConnectionException;
import com.google.sample.castcompanionlibrary.cast.exceptions.TransientNetworkDisconnectionException;
import com.gutierrez.salvador.bscaster.adapters.CardGridAdapter;
import com.gutierrez.salvador.bscaster.eneties.Hand;
import com.gutierrez.salvador.bscaster.eneties.PlayBox;
import com.gutierrez.salvador.bscaster.helpers.CommandBuilder;
import com.gutierrez.salvador.bscaster.helpers.Commands;
import com.gutierrez.salvador.bscaster.helpers.ImageResource;
import com.gutierrez.salvador.bscaster.helpers.ViewServer;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class GameActivity extends ActionBarActivity implements
		OnItemClickListener {

	protected boolean isTurn = false;
	protected String uniqueId;
	protected String playerName;
	protected GridView cardGrid;
	protected PlayBox playBox = new PlayBox();
	protected CardGridAdapter gridAdapter;
	protected DataCastManager mDataCastManager;
	protected final String BROADCAST_NAMESPACE = "urn:x-cast:com.salvador.gutierrez.hex.island.command.broadcast";
	protected int currentCard = 0;
	protected Hand myHand;
	protected String[] cardNames;
	protected boolean emptyHand = false;
	protected boolean nextTurnWin = false;
	protected Button bullshitBtn;
	protected Button dealBtn; 
	protected Button playBtn;
	protected RelativeLayout gameEndRL;
	protected TextView gameEndTV;
	protected TextView playboxDialogTV;
	protected TextView currentCardTV;
	protected ImageView topCard1;
	protected ImageView topCard2;
	protected ImageView topCard3;
	protected ImageView topCard4;
	protected Button quitBtn;
	protected Button resetBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// hide notification bar
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		// set the layout
		setContentView(R.layout.game_activity);

		
		 // Set content view, etc.
	      ViewServer.get(this).addWindow(this);
		// grid view
		cardGrid = (GridView) findViewById(R.id.gridView1);
		gridAdapter = new CardGridAdapter(this);
		cardGrid.setAdapter(gridAdapter);
		cardGrid.setOnItemClickListener(this);
		
		Context context = getApplicationContext();
		CharSequence text = "On Create!";
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();

		// playBox setup
		playBox.setImageView((ImageView) findViewById(R.id.card_holder_1), 0);
		playBox.setImageView((ImageView) findViewById(R.id.card_holder_2), 1);
		playBox.setImageView((ImageView) findViewById(R.id.card_holder_3), 2);
		playBox.setImageView((ImageView) findViewById(R.id.card_holder_4), 3);

		// get card names
		cardNames = this.getResources().getStringArray(R.array.card_names);

		// Chromecast setup
		mDataCastManager = MainActivity.mCastMgr;

		// get extras
		
		uniqueId = getIntent().getStringExtra("uniqueID");
		playerName = getIntent().getStringExtra("playerName");
		
		//initialize buttons
		bullshitBtn =(Button)findViewById(R.id.bullshit_BTN);
		dealBtn = (Button)findViewById(R.id.deal_BTN);
		playBtn = (Button)findViewById(R.id.play_BTN);
		playBtn.setEnabled(false);
		
		gameEndRL=(RelativeLayout) findViewById(R.id.game_end_RL);
		gameEndTV=(TextView)findViewById(R.id.game_end_TV);
		playboxDialogTV=(TextView)findViewById(R.id.playbox_dialog_TV);
		
		topCard1=(ImageView)findViewById(R.id.top_card_holder_1);
		topCard2=(ImageView)findViewById(R.id.top_card_holder_2);
		topCard3=(ImageView)findViewById(R.id.top_card_holder_3);
		topCard4=(ImageView)findViewById(R.id.top_card_holder_4);
		
		quitBtn=(Button)findViewById(R.id.game_end_quit_btn);
		resetBtn=(Button)findViewById(R.id.game_end_reset_btn);
		myHand= new Hand();
		mDataCastManager.removeDataCastConsumer(MainActivity.consumer);
		MainActivity.consumer =new DataCastConsumerImpl() {

			@Override
			public void onMessageReceived(CastDevice castDevice,
					String namespace, String message) {
				super.onMessageReceived(castDevice, namespace, message);
				processMessageReceived(message);
			}
		};
		mDataCastManager.addDataCastConsumer(MainActivity.consumer);
	}
	@Override
    public void onNewIntent (Intent intent)
    {
        super.onNewIntent(intent);
        setIntent(intent);
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	protected void setCurrentCard(int card) {
		gridAdapter.arrayList.get(currentCard).setActive(false);
		gridAdapter.arrayList.get(card).setActive(true);
		currentCard = card;
		currentCardTV = (TextView) findViewById(R.id.current_card_TV);
		currentCardTV.setText(cardNames[card]);
		gridAdapter.notifyDataSetChanged();
		
		//display currentcard and upcoming cards
		
		
		topCard1.setImageResource(ImageResource.getIdUpcoming(card, 0));
		topCard2.setImageResource(ImageResource.getIdUpcoming(card, 1));
		topCard3.setImageResource(ImageResource.getIdUpcoming(card, 2));
		topCard4.setImageResource(ImageResource.getIdUpcoming(card, 3));
	}

	protected void addCardToHand(int card) {
		
		nextTurnWin = false;
		emptyHand = false;
		int faceValue = myHand.addCard(card);

		int imageResource = ImageResource.getId(card);
		gridAdapter.arrayList.get(faceValue).setImageId(imageResource);
		gridAdapter.arrayList.get(faceValue).setCount(
				myHand.countOfSameSuit(card));
		gridAdapter.notifyDataSetChanged();
	}

	// (Only looks for facevalue, removes first suit of matching facevalue from
	// stack)
	protected boolean removeCardFromHand(int faceValue) {
		boolean result = myHand.removeTop(faceValue);
		if (!result) {
			return false;
		}
		int remaining = myHand.countFaceValues(faceValue);
		gridAdapter.arrayList.get(faceValue).setCount(remaining);
		// if player does not have any more cards of given type, show empty
		// placeholder image
		if (remaining == 0) {
			gridAdapter.arrayList.get(faceValue).setImageId(
					ImageResource.getId(-1));
		}
		// if there is cards, show the one below the one that was removed
		else {
			gridAdapter.arrayList.get(faceValue).setImageId(
					ImageResource.getId(myHand.getTop(faceValue)));
		}
		gridAdapter.notifyDataSetChanged();
		return true;
	}

	public void playCards(View view) {
		if (isTurn) {
			
			int card1 = playBox.getCardValue(0);
			int card2 = playBox.getCardValue(1);
			int card3 = playBox.getCardValue(2);
			int card4 = playBox.getCardValue(3);
			if(card1==-1&&card2==-1&&card3==-1&&card4==-1){
				playboxDialogTV.setText("Play Atleast 1 Card!");
				playboxDialogTV.setVisibility(TextView.VISIBLE);
				return;
			}
			if (countCards() == 0) {
				emptyHand = true;
			}
			broadCastCommand(CommandBuilder.build(Commands.PLAY_CARDS,
					uniqueId, card1, card2, card3, card4));
			playBox.remove(0, R.drawable.card_placeholder);
			playBox.remove(1, R.drawable.card_placeholder);
			playBox.remove(2, R.drawable.card_placeholder);
			playBox.remove(3, R.drawable.card_placeholder);
			TextView messageView = (TextView) findViewById(R.id.message_TV);
			messageView.setText("BULL!");
			playboxDialogTV.setVisibility(TextView.INVISIBLE);
			playBtn.setEnabled(false);
			isTurn = false;
		}
		
	}

	public void callBullshit(View view) {
		String command = Integer.toString(Commands.CALL_BULLSHIT);
		broadCastCommand(command + "@" + uniqueId);
	}

	public void playBoxCardClicked(View view) {
		switch (view.getId()) {
		case R.id.card_holder_1:
			if (!playBox.slotEmpty(0)) {
				addCardToHand(playBox.getCardValue(0));
				playBox.remove(0, R.drawable.card_placeholder);
			}
			break;
		case R.id.card_holder_2:
			if (!playBox.slotEmpty(1)) {
				addCardToHand(playBox.getCardValue(1));
				playBox.remove(1, R.drawable.card_placeholder);
			}
			break;
		case R.id.card_holder_3:
			if (!playBox.slotEmpty(2)) {
				addCardToHand(playBox.getCardValue(2));
				playBox.remove(2, R.drawable.card_placeholder);
			}
			break;
		case R.id.card_holder_4:
			if (!playBox.slotEmpty(3)) {
				addCardToHand(playBox.getCardValue(3));
				playBox.remove(3, R.drawable.card_placeholder);
			}
			break;
		default:
			break;
		}

	}

	// Process incoming messages.
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
			// Command to reset Game
		} else if (command.equals(strConvert(Commands.RESET_GAME))) {
			newGame(null);

			// Command to add cards to hand
		} else if (command.equals(strConvert(Commands.ADD_CARD_TO_HAND))
				&& mTokens[1].equals(uniqueId)) {
			for (int i = 3; i < mTokens.length; i++) {
				addCardToHand(intConvert(mTokens[i]));
			}
		}
		// Game has been won
		else if (command.equals(strConvert(Commands.GAME_WON))) {
			if (mTokens[1].equals(uniqueId)) {
				gameOver(true);
			} else {
				gameOver(false);
			}
		}
		
		//command to quit game received
		else if (command.equals(strConvert(Commands.END_GAME))){
			quit(null);
			
		}
		
	}

	public void quit(View view) {
		//	broadCastCommand(CommandBuilder.build(Commands.END_GAME,uniqueId));
			
			Intent intent = new Intent(GameActivity.this,MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}

	protected void myTurn() {
       
        playBtn.setEnabled(true);
		TextView messageView = (TextView) findViewById(R.id.message_TV);
		messageView.setText("Its Your Turn!");
		isTurn = true;
	}
     
	public void newGame(View view) {
		nextTurnWin=false;
		emptyHand=false;
		//Re initialize grid adapter, have garbage collection deal with it.
		gridAdapter = new CardGridAdapter(this);
		cardGrid.setAdapter(gridAdapter);
		gridAdapter.notifyDataSetChanged();
		gameEndRL.setVisibility(RelativeLayout.INVISIBLE);
		playBox.remove(0, R.drawable.card_placeholder);
		playBox.remove(1, R.drawable.card_placeholder);
		playBox.remove(2, R.drawable.card_placeholder);
		playBox.remove(3, R.drawable.card_placeholder);
		topCard1.setImageResource(R.drawable.card_placeholder);
		topCard2.setImageResource(R.drawable.card_placeholder);
		topCard3.setImageResource(R.drawable.card_placeholder);
		topCard4.setImageResource(R.drawable.card_placeholder);
		myHand= new Hand();
		playboxDialogTV.setVisibility(TextView.INVISIBLE);
		playBtn.setEnabled(false);
	}

	protected void declareWin() {
		broadCastCommand(CommandBuilder.build(Commands.DECLARE_WIN, uniqueId));
	}

	protected void gameOver(boolean won) {
		gameEndRL.setVisibility(RelativeLayout.VISIBLE);
		if (won) {
			
			gameEndTV.setText("Winner! Bullshit Pro!");
			return;
		}
		
		gameEndTV.setText("YOU WIN!...JK you LOST.");
	}

	protected void broadCastCommand(String string) {
		try {
			mDataCastManager.sendDataMessage(string, BROADCAST_NAMESPACE);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransientNetworkDisconnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int faceValue,
			long arg3) {
		if (playBox.slotAvailable()) {
			int topValue = myHand.getTop(faceValue);
			boolean result = removeCardFromHand(faceValue);
			if (result) {
				playboxDialogTV.setVisibility(TextView.INVISIBLE);
				playBox.add(faceValue, topValue, ImageResource.getId(topValue));
			} else {
				
			}
		} else {
			playboxDialogTV.setText("Max 4 Cards!");
			playboxDialogTV.setVisibility(TextView.VISIBLE);
		}
	}

	protected String strConvert(int i) {
		return Integer.toString(i);
	}

	protected int intConvert(String s) {
		return Integer.parseInt(s);
	}

	protected int countCards() {
		return myHand.countTotalCards();
	}
	@Override
	public void onDestroy() {
		         super.onDestroy();
		         ViewServer.get(this).removeWindow(this);
		     }
		    @Override
		     public void onResume() {
		        super.onResume();
		          ViewServer.get(this).setFocusedWindow(this);
		      }
		    
		    @Override
		   
		    public void onBackPressed() {
		    	mDataCastManager.disconnect();
		    	super.onBackPressed();
		    }
		    
		    
}
