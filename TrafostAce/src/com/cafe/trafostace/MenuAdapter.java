package com.cafe.trafostace;

import java.util.ArrayList;
import com.cafe.trafostace.ClassMenu;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MenuAdapter extends BaseAdapter {
	
	private Activity activity;
	private ArrayList<ClassMenu> data_menu = new ArrayList<ClassMenu>();
	
	private static LayoutInflater inflater = null;
	
	public MenuAdapter(Activity a, ArrayList<ClassMenu> d) {
		activity = a;
		data_menu = d;
		inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public int getCount() {
		return data_menu.size();
	}
	
	public Object getItem(int position) {
		return data_menu.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null) 
			vi = inflater.inflate(R.layout.list_item_menu, null);
		TextView id_menu = (TextView) vi.findViewById(R.id.id_menu);
		TextView nama_menu = (TextView) vi.findViewById(R.id.nama_menu);
		TextView harga = (TextView) vi.findViewById(R.id.harga);
		TextView status = (TextView) vi.findViewById(R.id.status);
		
		ClassMenu daftar_menu = data_menu.get(position);
		id_menu.setText(daftar_menu.getMenuId());
		nama_menu.setText(daftar_menu.getMenuName());
		harga.setText(daftar_menu.getHarga());
		status.setText(daftar_menu.getStatus());
		
		return vi;
	}
}
