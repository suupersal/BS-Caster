package com.gutierrez.salvador.bscaster.adapters;

import java.util.ArrayList;

import com.example.hexisland.R;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CardGridAdapter extends BaseAdapter {
	public ArrayList<SingleCardItem> arrayList;
	Context ctx;

	public CardGridAdapter(Context ctx) {
		this.ctx = ctx;
		arrayList = new ArrayList<SingleCardItem>();
		Resources res = ctx.getResources();
		String[] tempNames = res.getStringArray(R.array.card_names);

		for (int i = 0; i < 14; i++) {
			// create objects and add them to list
			arrayList.add(new SingleCardItem(R.drawable.card_placeholder,
					tempNames[i]));
		}
	}

	@Override
	public int getCount() {
		return arrayList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return arrayList.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	// ViewHolder defined below

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder vHolder = null;
		View row = convertView;

		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) ctx
					.getSystemService(ctx.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.single_card_layout, parent, false);
			vHolder = new ViewHolder(row);
			SingleCardItem temp = arrayList.get(position);
			if (temp.isActive) {
				vHolder.activeCardView.setVisibility(View.VISIBLE);
			} else {
				vHolder.activeCardView.setVisibility(View.INVISIBLE);
			}
			row.setTag(vHolder);

		} else {
			vHolder = (ViewHolder) row.getTag();
		}
		SingleCardItem temp = arrayList.get(position);
		vHolder.cardImage.setImageResource(temp.imageId);
		if (temp.isActive) {
			vHolder.activeCardView.setVisibility(View.VISIBLE);
		} else {
			vHolder.activeCardView.setVisibility(View.INVISIBLE);
		}
		vHolder.cardCountView.setText("x" + Integer.toString(temp.getCount()));
		return row;
	}

	class ViewHolder {
		ImageView cardImage;
		ImageView activeCardView;
		TextView cardCountView;

		public ViewHolder(View view) {
			cardImage = (ImageView) view.findViewById(R.id.imageView1);
			activeCardView = (ImageView) view.findViewById(R.id.imageView2);
			cardCountView = (TextView) view.findViewById(R.id.card_count_TV);

		}
	}

}
