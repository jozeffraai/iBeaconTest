package com.example.jozeffraai.ibeacontest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.estimote.sdk.Beacon;
import com.example.jozeffraai.ibeacontest.R;

public class CustomAdapter extends ArrayAdapter<Beacon> {

	private Beacon mData[] = null;

	public CustomAdapter(Context context, Beacon[] pData) {
		super(context, R.layout.activity_main,pData);
		mData = pData;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater customInflater = LayoutInflater.from(getContext());
		View customView = customInflater.inflate(R.layout.activity_main, parent, false);

		TextView customRowText = (TextView)customView.findViewById(R.id.rangeDing);

		Beacon beacons = mData[position];
		customRowText.setText(beacons.getRssi());
		return customView;
	}
}
