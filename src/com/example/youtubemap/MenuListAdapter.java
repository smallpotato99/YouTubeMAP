package com.example.youtubemap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuListAdapter extends BaseAdapter {

	Context context;
	String[] mTitle;
	String[] mSubTitle;
	int[] mIcon;
	LayoutInflater inflater;
	
	public MenuListAdapter(Context context, String[] title, String[] subtitle, int[] icon) {
		this.context = context;
		this.mTitle = title;
		this.mSubTitle = subtitle;
		this.mIcon = icon;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mTitle.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mTitle[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView txtTitle;
		TextView txtSubTitle;
		ImageView imgIcon;
		
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View itemView = inflater.inflate(R.layout.menu_list_item, parent, false);
		
		txtTitle = (TextView) itemView.findViewById(R.id.title);
		txtSubTitle = (TextView) itemView.findViewById(R.id.subtitle);
		imgIcon = (ImageView) itemView.findViewById(R.id.icon);
		
		txtTitle.setText(mTitle[position]);
		txtSubTitle.setText(mSubTitle[position]);
		imgIcon.setImageResource(mIcon[position]);
		
		return itemView;
	}

}
