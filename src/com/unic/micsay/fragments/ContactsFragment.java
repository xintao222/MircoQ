package com.unic.micsay.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.unic.micsay.R;
import com.unic.micsay.activitys.MainActivity;
import com.unic.micsay.contentprovider.Tables;
import com.unic.micsay.datasource.ContactDataSource;
import com.unic.micsay.models.User;
import com.unic.micsay.renders.ContactRender;
import com.unic.rainbow.datasource.DataAdapter;

public class ContactsFragment extends Fragment {

	private MainActivity mainActivity = null;
	private ListView listView = null;
	private ContactDataSource dataSource;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mainActivity = (MainActivity) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		listView = (ListView) inflater
				.inflate(R.layout.contacts_fragment, null);
		return listView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		dataSource = new ContactDataSource(mainActivity);
		ContactRender render = new ContactRender(
				mainActivity.getLayoutInflater());
		DataAdapter adapter = new DataAdapter(dataSource, render);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(listener);
	}

	private AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> listview, View view,
				int position, long count) {
			User user = (User) dataSource.getItem(position);
			FragmentManager manager = mainActivity.getSupportFragmentManager();
			FragmentTransaction tn = manager.beginTransaction();
			ChatFragment chat = new ChatFragment();
			Bundle bundle = new Bundle();
			bundle.putString(Tables.Contact.Columns.EMAIL, user.getEmail());
			bundle.putString(Tables.Contact.Columns.ALIAS, user.getAlias());
			chat.setArguments(bundle);
			tn.replace(R.id.contacts, chat);
			tn.addToBackStack(null);
			tn.commit();
		}
	};

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onDetach() {
		super.onDetach();
		dataSource.close();
		mainActivity = null;
	}
}
