package com.unic.micsay.renders;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.unic.micsay.R;
import com.unic.micsay.models.User;
import com.unic.rainbow.datasource.Render;

public class ContactRender implements Render {

	public ContactRender(LayoutInflater inflater) {
		this.inflater = inflater;
	}

	private LayoutInflater inflater = null;

	private static class ViewHolder {
		public TextView name = null;
		private TextView email = null;
	}

	@Override
	public View create(int position, Object data) {
		View view = inflater.inflate(R.layout.list_contact_item, null);
		ViewHolder holder = new ViewHolder();
		view.setTag(holder);
		holder.email = (TextView) view.findViewById(R.id.email);
		holder.name = (TextView) view.findViewById(R.id.name);
		return view;
	}

	@Override
	public void render(View view, Object data) {
		ViewHolder holder = (ViewHolder) view.getTag();
		User user = (User) data;
		holder.name.setText(user.getAlias());
		holder.email.setText(user.getEmail());

	}

}
