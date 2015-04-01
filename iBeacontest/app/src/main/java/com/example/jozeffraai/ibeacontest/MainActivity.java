package com.example.jozeffraai.ibeacontest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import java.util.List;


public class MainActivity extends ActionBarActivity {

	private static final String ESTIMOTE_PROXIMITY_UUID = "B9407F30-F5F8-466E-AFF9-25556B57FE6D";
	private static final Region ALL_ESTIMOTE_BEACONS = new Region("regionId", ESTIMOTE_PROXIMITY_UUID, null, null);
	private static final String TAG = "mijnTag";

	private TextView tvBeacon;
	private TextView tvBeacon2;

	private BeaconManager beaconManager = new BeaconManager(this);
	BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		tvBeacon = (TextView) findViewById(R.id.rangeDing);
		tvBeacon2 = (TextView) findViewById(R.id.rangeDing2);


		//enable bluetooth
		if (!mBluetoothAdapter.isEnabled()) {
//			onCreateDialog();
			mBluetoothAdapter.enable();
			Log.e(TAG, "Bluetooth is now enabled");
		}
		;

		beaconManager.setRangingListener(new BeaconManager.RangingListener() {
			@Override
			public void onBeaconsDiscovered(Region region, List<Beacon> beacons) {
				Log.d(TAG, "Ranged beacons: " + beacons);

				tvBeacon2.setText("Er is geen Beacon binnen bereik");

//				if (beacons != null) {
				for (Beacon b : beacons) {
//						tvBeacon.setText("Beacon Rssi: " + b.getRssi() +
//										"\nBeacon Major: " + b.getMajor() +
//										"\nBeacon Minor: " + b.getMinor() +
//										"\nBeacon Name: " + b.getName() +
//										"\nBeacon MAC: " + b.getMacAddress() +
//										"\nBeacon MeasuredPower: " + b.getMeasuredPower() +
//										"\nBeacon Proximity:\n" + b.getProximityUUID() +"\n\n\n");

					if (b.getMacAddress().equals("F3:BC:3A:0D:23:24")) {
						tvBeacon2.setText("De paarse Is binnen bereik");
					} else if(b.getMacAddress().equals("E5:96:E5:68:D9:A7")){
						tvBeacon2.setText("De blauwe Is binnen bereik");
					}
				}
//				}
			}
		});
	}


	public Dialog onCreateDialog() {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.dialog_blauwe_tand_message)
				.setPositiveButton(R.string.dialog_blauwe_tand_yes, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						mBluetoothAdapter.enable();
						Log.e(TAG, "Bluetooth is now enabled");
					}
				})
				.setNegativeButton(R.string.dialog_blauwe_tand_no, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						Log.e(TAG, "Helaas");
					}
				});
		// Create the AlertDialog object and return it
		return builder.create();
	}


	@Override
	protected void onStart() {
		super.onStart();

		beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
			@Override
			public void onServiceReady() {
				try {
					beaconManager.startRanging(ALL_ESTIMOTE_BEACONS);
				} catch (RemoteException e) {
					Log.e(TAG, "Cannot start ranging", e);
				}
			}
		});
	}

	@Override
	protected void onStop() {
		super.onStop();

		try {
			beaconManager.stopRanging(ALL_ESTIMOTE_BEACONS);
		} catch (RemoteException e) {
			Log.e(TAG, "Cannot stop but it does not matter now", e);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		beaconManager.disconnect();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
