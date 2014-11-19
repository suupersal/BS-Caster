package com.gutierrez.salvador.bscaster;

import com.example.hexisland.R;
import com.google.sample.castcompanionlibrary.cast.DataCastManager;
import com.google.sample.castcompanionlibrary.cast.callbacks.DataCastConsumerImpl;
import com.gutierrez.salvador.bscaster.eneties.Game;
import com.gutierrez.salvador.bscaster.helpers.ViewServer;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {
	static DataCastManager mCastMgr;
	static Game activeGame;
	static DataCastConsumerImpl consumer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);

		 // Set content view, etc.
	      ViewServer.get(this).addWindow(this);
		Button newGameBtn = (Button) findViewById(R.id.activity_main_btn1);
		newGameBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						HostLobbyActivity.class);
		
				startActivity(intent);
			}
		});

		Button joinGameBtn = (Button) findViewById(R.id.activity_main_btn2);
		joinGameBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						JoinLobbyActiviy.class);
				
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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

	public static DataCastManager getDataCastManager(Context ctx) {
		if (null == mCastMgr) {
			mCastMgr = DataCastManager
					.initialize(ctx, "CHROME CAST APP ID GOES HERE",
							"urn:x-cast:com.salvador.gutierrez.hex.island.command.broadcast");
			mCastMgr.enableFeatures(DataCastManager.FEATURE_NOTIFICATION
					| DataCastManager.FEATURE_LOCKSCREEN
					| DataCastManager.FEATURE_WIFI_RECONNECT |

					DataCastManager.FEATURE_CAPTIONS_PREFERENCE
					| DataCastManager.FEATURE_DEBUGGING);
		}
		mCastMgr.setContext(ctx);
		return mCastMgr;
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
