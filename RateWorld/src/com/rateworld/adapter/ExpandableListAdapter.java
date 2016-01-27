package com.rateworld.adapter;

import java.util.Calendar;
import java.util.List;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.rateworld.R;
import com.rateworld.database.DatabaseActivity;
import com.rateworld.main.BCReceiver03;
import com.rateworld.switchbutton.SwitchButton;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

	private Context _context;
	private List<String> _listDataHeader; 
	ImageView notifi, notifi2;
	DatabaseActivity db;
	PendingIntent pendingIntent;
	static boolean flag = false; 

	public ExpandableListAdapter(Context context, List<String> listDataHeader) {
		this._context = context;
		this._listDataHeader = listDataHeader;

	}



	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return this._listDataHeader.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return this._listDataHeader.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		//String headerTitle = (String) getGroup(groupPosition);


		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.setting_tab, null);
		}
		db = new DatabaseActivity(_context);
		Intent intent02 = new Intent(_context, BCReceiver03.class);
		pendingIntent = PendingIntent.getBroadcast(_context, 0, intent02, 0);
		final ImageView notifi = (ImageView)convertView.findViewById(R.id.imageView3);
		final ImageView notifi2 = (ImageView)convertView.findViewById(R.id.ImageView01);
		SwitchButton notification = (SwitchButton) convertView.findViewById(R.id.sb_md);

		if(db.getNotificationStatus().equals("true")){
			notification.setChecked(Boolean.parseBoolean(db.getNotificationStatus()));
			notifi.setVisibility(View.VISIBLE);
			notifi2.setVisibility(View.INVISIBLE);
		}else{
			notification.setChecked(Boolean.parseBoolean(db.getNotificationStatus()));
			notifi.setVisibility(View.INVISIBLE);
			notifi2.setVisibility(View.VISIBLE);

		}

		notification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

				if(isChecked){

					if(!flag){
						
						notifi.setVisibility(View.VISIBLE);
						notifi2.setVisibility(View.INVISIBLE);
						db.updateNotificationStatus(String.valueOf(isChecked));
						//Toast.makeText(_context, "checked", Toast.LENGTH_LONG).show();
						//System.out.println("checked");
						Calendar timeOff9 = Calendar.getInstance();

						timeOff9.setTimeInMillis(System.currentTimeMillis());

						timeOff9.set(Calendar.HOUR_OF_DAY, 10);

						timeOff9.set(Calendar.MINUTE, 00);

						timeOff9.set(Calendar.SECOND, 00);

						AlarmManager alarmMgr01 = (AlarmManager)_context.getSystemService(Context.ALARM_SERVICE);

						alarmMgr01.setRepeating(AlarmManager.RTC_WAKEUP,
								timeOff9.getTimeInMillis(),
								AlarmManager.INTERVAL_HALF_DAY, pendingIntent);
						flag = true;
					}
					
				}else{
					notifi.setVisibility(View.INVISIBLE);
					notifi2.setVisibility(View.VISIBLE);
					//Toast.makeText(_context, "unchecked", Toast.LENGTH_LONG).show();
					AlarmManager alarmMgr01 = (AlarmManager)_context.getSystemService(Context.ALARM_SERVICE);
					alarmMgr01.cancel(pendingIntent);
					db.updateNotificationStatus02(String.valueOf(isChecked));
					flag = false;
				}

			}
		});

		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.setting_tab_child, null);
		}



		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

}
