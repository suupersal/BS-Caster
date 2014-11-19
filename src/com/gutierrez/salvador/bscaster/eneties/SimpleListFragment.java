package com.gutierrez.salvador.bscaster.eneties;

import java.util.ArrayList;

import com.example.hexisland.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class SimpleListFragment extends ListFragment {
	String[] data = { "dfdsf", "dsfadsa" };
	ArrayList<String> data2 = new ArrayList<String>();
	public ArrayAdapter<String> adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		adapter = new ArrayAdapter<String>(this.getActivity(),
				R.layout.my_simple_view, data2);
		setListAdapter(adapter);
	}

	public void addItem(String data) {
		data2.add(data);

	}

}
