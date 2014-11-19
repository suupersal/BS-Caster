package com.gutierrez.salvador.bscaster;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import com.example.hexisland.R;
import com.google.android.gms.cast.CastDevice;
import com.google.sample.castcompanionlibrary.cast.DataCastManager;
import com.google.sample.castcompanionlibrary.cast.callbacks.DataCastConsumerImpl;
import com.google.sample.castcompanionlibrary.cast.exceptions.NoConnectionException;
import com.google.sample.castcompanionlibrary.cast.exceptions.TransientNetworkDisconnectionException;
import com.gutierrez.salvador.bscaster.adapters.listAdapter;
import com.gutierrez.salvador.bscaster.eneties.Game;
import com.gutierrez.salvador.bscaster.helpers.Commands;
import com.gutierrez.salvador.bscaster.helpers.ViewServer;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.MediaRouteButton;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.Toast;

public class HostLobbyActivity extends ActionBarActivity {

	private MediaRouteButton mMediaRouteButton;
	private DataCastManager mDataCastManager;
	private String uniqueID;
	private String playerName = "Test Host";
	private ArrayList<String> players = new ArrayList<>();
	private ListView l;
	listAdapter adapter;

	private final String BROADCAST_NAMESPACE = "urn:x-cast:com.salvador.gutierrez.hex.island.command.broadcast";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// hide notification bar
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// set activity layout
		setContentView(R.layout.activity_host_game);
		 // Set content view, etc.
	      ViewServer.get(this).addWindow(this);
		// Get the cast manager
		mDataCastManager = MainActivity.getDataCastManager(this);
		
		Context context = getApplicationContext();
		CharSequence text = "On Create!";
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();

		// ChromeCast button setup
		mMediaRouteButton = (MediaRouteButton) findViewById(R.id.media_route_button);
		mDataCastManager.addMediaRouterButton(mMediaRouteButton);

		// listFragment.addItem("Joiner-initial");
		players.add("HOST!");
		l = (ListView) findViewById(R.id.lobby_join_LV);

		// ArrayAdapter<String> adapter = new
		// ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,players);
		adapter = new listAdapter(this, players);
		l.setAdapter(adapter);
		// Create the new game
		MainActivity.activeGame = new Game();

		// create UUID
		uniqueID = UUID.randomUUID().toString();
		// This Activity should only be running on host device
		MainActivity.activeGame.addPlayer(uniqueID, "HOST NAME", true);
        
		mDataCastManager.removeDataCastConsumer(MainActivity.consumer);
		MainActivity.consumer =new DataCastConsumerImpl() {
			
			@Override
			public void onCastAvailabilityChanged(boolean castPresent) {
				mMediaRouteButton.setVisibility(castPresent ? View.VISIBLE
						: View.INVISIBLE);
			}

			@Override
			public void onMessageReceived(CastDevice castDevice,
					String namespace, String message) {
				// TODO Auto-generated method stub
				super.onMessageReceived(castDevice, namespace, message);
				processMessageReceived(message);
			}
		};
		
		mDataCastManager.addDataCastConsumer(MainActivity.consumer);

	}

	// all messages received should be processed here and appropriate methods
	// called according to command
	private void processMessageReceived(String message) {
		String[] mTokens = message.split("@");
		String command = mTokens[0];
		if (command.equals(Integer.toString(Commands.REQUEST_JOIN_LOBBY))) {
			addNewPlayer(mTokens[1], mTokens[3]);
			broadCastCommand(Commands.ADD_PLAYER_LOBBY + "@" + mTokens[1] + "@"
					+ mTokens[3]);
		} else if (command.equals(Integer.toString(Commands.START_GAME))) {
			startGame();
		} else {
			// no command match
		}

	}

	// start game button clicked
	public void startGame(View view) {
		String command = Integer.toString(Commands.START_GAME) + "@" + "NULL";
		broadCastCommand(command);

	}

	public void startGame() {
		Intent intent = new Intent(HostLobbyActivity.this,
				GameActivityHost.class);

		intent.putExtra("uniqueID", uniqueID);
		intent.putExtra("playerName", playerName);
		startActivity(intent);
		finish();
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

	private void addNewPlayer(String id, String name) {
		MainActivity.activeGame.addPlayer(id, name, false);
		players.add(name);
		adapter.notifyDataSetChanged();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.select_cast, menu);
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
