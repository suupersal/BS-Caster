package com.gutierrez.salvador.bscaster;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import com.example.hexisland.R;
import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.wearable.MessageApi.SendMessageResult;
import com.google.sample.castcompanionlibrary.cast.DataCastManager;
import com.google.sample.castcompanionlibrary.cast.callbacks.DataCastConsumerImpl;
import com.google.sample.castcompanionlibrary.cast.exceptions.NoConnectionException;
import com.google.sample.castcompanionlibrary.cast.exceptions.TransientNetworkDisconnectionException;
import com.gutierrez.salvador.bscaster.adapters.listAdapter;
import com.gutierrez.salvador.bscaster.eneties.SimpleListFragment;
import com.gutierrez.salvador.bscaster.helpers.CommandBuilder;
import com.gutierrez.salvador.bscaster.helpers.Commands;
import com.gutierrez.salvador.bscaster.helpers.ViewServer;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.MediaRouteButton;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class JoinLobbyActiviy extends ActionBarActivity {

	private DataCastManager mDataCastManager;
	private MediaRouteButton mMediaRouteButton;
	private String uniqueID;
	private String playerName = "Test Name Join";
	private ArrayList<String> players = new ArrayList<String>();
	private ListView listView;
	private listAdapter adapter;
	private TextView headerText;
	private EditText playerNameET;
	private Button joinBtn;
	
	
	// Indexes of command parameters in message token array


	private final String BROADCAST_NAMESPACE = "urn:x-cast:com.salvador.gutierrez.hex.island.command.broadcast";

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// hide notification bar
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// set activity layout
		setContentView(R.layout.activity_join_game);
		 // Set content view, etc.
	      ViewServer.get(this).addWindow(this);
		// Get the cast manager
		mDataCastManager = MainActivity
				.getDataCastManager(getApplicationContext());

		// ChromeCast button setup
		mMediaRouteButton = (MediaRouteButton) findViewById(R.id.media_route_button);
		mDataCastManager.addMediaRouterButton(mMediaRouteButton);

		Context context = getApplicationContext();
		CharSequence text = "On Create!";
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
        
		players.add("Player");
		listView = (ListView) findViewById(R.id.lobby_join_LV);

		adapter = new listAdapter(this, players);
		listView.setAdapter(adapter);
		// create UUID
		uniqueID = UUID.randomUUID().toString();
		
		//header setup
		headerText=(TextView)findViewById(R.id.header_TV);
		headerText.setText("Select Chromecast");
		playerNameET = (EditText) findViewById(R.id.player_name_ET);
		joinBtn = (Button) findViewById(R.id.join_btn1);
		
		
		mDataCastManager.addDataCastConsumer(new DataCastConsumerImpl() {

			@Override
			public void onCastAvailabilityChanged(boolean castPresent) {
				mMediaRouteButton.setVisibility(castPresent ? View.VISIBLE
						: View.INVISIBLE);
			}

			@Override
			public void onMessageReceived(CastDevice castDevice,
					String namespace, String message) {
				super.onMessageReceived(castDevice, namespace, message);
				proccessMessageReceived(message);
			}
			
			@Override
		   
		    public void onConnected() {
		    	super.onConnected();
		    	playerNameET.setVisibility(EditText.VISIBLE);
		    	joinBtn.setVisibility(Button.VISIBLE);
		    	headerText.setText("Enter Your Name");
  	
		    }

		});
	}

	public void join(View view) {
		playerName = playerNameET.getText().toString();
		playerNameET.setVisibility(EditText.INVISIBLE);
		joinBtn.setText("Leave");
		listView.setVisibility(ListView.VISIBLE);
		headerText.setText("You Have Joined");
		broadCastCommand(CommandBuilder.build(Commands.REQUEST_JOIN_LOBBY,
				uniqueID, playerName));
	}

	private void proccessMessageReceived(String message) {
		String[] mTokens = message.split("@");
		String command = mTokens[0];
		// add player to lobby command received
		if (command.equals(Integer.toString(Commands.ADD_PLAYER_LOBBY))) {
			addPlayerToLobby(mTokens[2]);
		} else if (command.equals(Integer.toString(Commands.START_GAME))) {
			startGame();
		}

	}

	private void broadCastCommand(String string) {
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

	private void addPlayerToLobby(String name) {
		players.add(name);
		adapter.notifyDataSetChanged();

	}

	public void startGame() {
		Intent intent = new Intent(JoinLobbyActiviy.this,
				GameActivityGuest.class);
		
		intent.putExtra("uniqueID", uniqueID);
		intent.putExtra("playerName", playerName);
		startActivity(intent);
		finish();
	
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.join_game_activiy, menu);
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

}
