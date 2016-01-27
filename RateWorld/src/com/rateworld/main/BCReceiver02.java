package com.rateworld.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import com.rateworld.database.DatabaseActivity;

public class BCReceiver02 extends BroadcastReceiver{

	  	JSONParser03 jsonParser = new JSONParser03();
	  	Context context1;
		static String stringCategory;
		HashMap<String, String> queryValues;
		static ArrayList<HashMap<String, String>> usersList1 = new ArrayList<HashMap <String, String>>();
		ArrayList<HashMap<String, String>> usersList2 = new ArrayList<HashMap <String, String>>();
		static List<String> list;
		static List<String> list1;
		DatabaseActivity db;
		ProgressDialog prgDialog;
		static String ofCurrencyShort;
		static String ofCurrencyFull;
		double value;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		
		context1 = context;
		usersList1.clear();
        db = new DatabaseActivity(context);
		usersList1 = db.getAllCurrency01();
		ofCurrencyShort = usersList1.get(0).get("intoCurrencyCode");
		ofCurrencyFull = usersList1.get(0).get("intoCurrencyFullName");
		value = Double.parseDouble(usersList1.get(0).get("intoCurrentRate"));
		list = db.getAllIntoCurrencyCode(ofCurrencyShort);
		list1 = db.getAllIntoCurrencyFullName(ofCurrencyFull);
		StringBuilder sb = new StringBuilder();
		for(String n : list){
			if(sb.length()>0)
				sb.append(',');
			sb.append("%22").append(ofCurrencyShort).append(n).append("%22");
		}

		stringCategory = sb.toString();
		
		ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		
		if (networkInfo != null && networkInfo.isConnected()) {
		
		new NetworkThread3().execute();
		}
	}
	
	public class NetworkThread3 extends AsyncTask<String, String, String>{

		/*@Override
		protected void onPreExecute() {
			super.onPreExecute();
			prgDialog = new ProgressDialog(context);
			prgDialog.setMessage("Loading..");
			prgDialog.setIndeterminate(false);
			prgDialog.setCancelable(false);
			prgDialog.show();

		}*/

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub

			String link = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.xchange%20where%20pair%20in%20%28"+stringCategory+"%29&env=store://datatables.org/alltableswithkeys&format=json";
			
			
			JSONObject json = jsonParser.getJSONFromUrl(link);

			db.update();
			
			usersList1.clear();

			
			try {

				JSONObject arr = (JSONObject) json.get("query");
				JSONObject arr1 = (JSONObject) arr.get("results");
				JSONArray arrOffer = arr1.getJSONArray("rate");

				for(int i = 0; i<arrOffer.length(); i++){
					/*if(i==-1){

						queryValues = new HashMap<String, String>();

						
						queryValues.put("ofCurrencyCode", ofCurrencyShort);
						queryValues.put("intoCurrencyCode", ofCurrencyShort);
						queryValues.put("ofCurrencyFullName", ofCurrencyFull);
						queryValues.put("intoCurrencyFullName", ofCurrencyFull);
						queryValues.put("ofRate", "1");
						queryValues.put("intoPreviousRate", "1");
						queryValues.put("intoCurrentRate", "1");

						//db.insertCurrency(queryValues);

					}else{*/
						JSONObject obj = (JSONObject) arrOffer.get(i);
						queryValues = new HashMap<String, String>();

						
						db.updateCurrencyRates(list.get(i), obj.get("Rate").toString());
						//queryValues.put("ofCurrencyCode", ofCurrencyShort);
						//queryValues.put("intoCurrencyCode", list.get(i));
						//queryValues.put("ofCurrencyFullName", ofCurrencyFull);
						//queryValues.put("intoCurrencyFullName", list1.get(i));
						//queryValues.put("ofRate", "1");
						//queryValues.put("intoPreviousRate", obj.get("Rate").toString());
						//queryValues.put("intoCurrentRate", obj.get("Rate").toString());

						//db.insertCurrency(queryValues);
						
					//}


				}
				
		
				
				//

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(String file_url){
			
			MainActivity.testing02();
			//prgDialog.dismiss();
			//System.out.println(db.getAllCurrency());
			//MainActivity.testing(getContext());

		}

	}

}
