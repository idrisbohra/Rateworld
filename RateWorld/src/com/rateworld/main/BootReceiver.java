package com.rateworld.main;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.rateworld.database.DatabaseActivity;

public class BootReceiver extends BroadcastReceiver{

	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		DatabaseActivity db = new DatabaseActivity(context);
		
		if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
			
			if(db.getNotificationStatus().equals("true")){
				
				Calendar timeOff9 = Calendar.getInstance();

				timeOff9.setTimeInMillis(System.currentTimeMillis());

				timeOff9.set(Calendar.HOUR_OF_DAY, 10);

				timeOff9.set(Calendar.MINUTE, 00);

				timeOff9.set(Calendar.SECOND, 00);

				AlarmManager alarmMgr01 = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

				Intent intent02 = new Intent(context, BCReceiver03.class);

				PendingIntent alarmIntent02 = PendingIntent.getBroadcast(context, 0, intent02, PendingIntent.FLAG_UPDATE_CURRENT);

				alarmMgr01.setRepeating(AlarmManager.RTC_WAKEUP,
						timeOff9.getTimeInMillis(),
						AlarmManager.INTERVAL_HALF_DAY, alarmIntent02);

			}
		}
	}

}
