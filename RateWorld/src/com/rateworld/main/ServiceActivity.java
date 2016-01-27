package com.rateworld.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.rateworld.R;
import com.rateworld.database.DatabaseActivity;

public class ServiceActivity extends Service {

	private NotificationManager mNotificationManager;
	DatabaseActivity db = new DatabaseActivity(this);
	ArrayList<HashMap<String, String>> usersList = new ArrayList<HashMap <String, String>>();
	ArrayList<HashMap<String, String>> usersList02 = new ArrayList<HashMap <String, String>>();
	List<String> list;
	StringBuilder sb;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public void onCreate() {
		super.onCreate();

	}

	@Override
	public void onStart(Intent intent, int startId) {
		
		usersList = db.getAllCurrency01();
		
		for(int i =1; i<usersList.size(); i++){
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("ofCurrencyCode", usersList.get(i).get("ofCurrencyCode"));
        	map.put("intoCurrencyCode", usersList.get(i).get("intoCurrencyCode"));
        	map.put("ofCurrencyFullName", usersList.get(i).get("ofCurrencyFullName"));
        	map.put("intoCurrencyFullName", usersList.get(i).get("intoCurrencyFullName"));
        	map.put("intoCurrencyFlag", usersList.get(i).get("intoCurrencyFlag"));
        	map.put("ofRate", usersList.get(i).get("ofRate"));
        	map.put("intoPreviousRate", usersList.get(i).get("intoPreviousRate"));
        	map.put("intoCurrentRate", usersList.get(i).get("intoCurrentRate"));
        	usersList02.add(map);
		}
		
		StringBuilder sb = new StringBuilder();
		
			for(HashMap<String, String> hashmap : usersList02){
				if(sb.length()>0)
					sb.append('\n');
				sb.append(usersList.get(0).get("ofCurrencyCode")+" to "+hashmap.get("intoCurrencyCode")+" : ")
				.append("Today's - "+hashmap.get("intoCurrentRate")+", Prev - "+hashmap.get("intoPreviousRate"));
				
			}
		
			String stringCategory = sb.toString();
					
		Intent resultIntent = new Intent(this, MainActivity02.class);
		//resultIntent.putExtra("methodName","pageRefresh");

		PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0,
				resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		android.app.Notification.Builder mNotifyBuilder = new Notification.Builder(
	            this);

		mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		// Sets an ID for the notification, so it can be updated for the next time for new message
		int notifyID = 9001;

		mNotifyBuilder
		.setContentTitle("Rateworld\n")
		.setContentText("Check out your today's currency rate")
		.setSmallIcon(R.drawable.ic_launcher);

		// Set pending intent
		mNotifyBuilder.setContentIntent(resultPendingIntent);

		// Set Vibrate, Sound and Light
		int defaults = 0;
		defaults = defaults | Notification.DEFAULT_LIGHTS;
		defaults = defaults | Notification.DEFAULT_VIBRATE;
		defaults = defaults | Notification.DEFAULT_SOUND;
		mNotifyBuilder.setDefaults(defaults);

		// Set the content for Notification 
		//mNotifyBuilder.setContentText(getString(R.string.weather_text));
		// Set autocancel
		mNotifyBuilder.setAutoCancel(true);
		// Post a notification
		
		Notification notification = new Notification.BigTextStyle(mNotifyBuilder)
        .bigText(stringCategory).build();
		
		mNotificationManager.notify(notifyID, notification);

		this.stopSelf();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

	}
}
