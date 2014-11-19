package com.gutierrez.salvador.bscaster.adapters;

import java.util.ArrayList;

import com.example.hexisland.R;
import com.example.hexisland.R.id;
import com.example.hexisland.R.layout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class listAdapter extends ArrayAdapter<String> {
	Context context;
	ArrayList<String> names;

	public listAdapter(Context ctx, ArrayList<String> names) {
		super(ctx, R.layout.my_simple_view, R.id.text_view_item, names);
		context = ctx;
		this.names = names;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View row = inflater.inflate(R.layout.my_simple_view, parent, false);
		TextView tv = (TextView) row.findViewById(R.id.text_view_item);
		tv.setText(names.get(position));
		return row;
	}
}
