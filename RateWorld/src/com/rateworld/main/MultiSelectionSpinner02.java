package com.rateworld.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.rateworld.R;
import com.rateworld.database.DatabaseActivity;

public class MultiSelectionSpinner02 extends Spinner implements
        OnMultiChoiceClickListener {
    String[] _items = null;
    boolean[] mSelection = null;
    boolean[] mSelectionAtStart = null;
    String _itemsAtStart = null;
    JSONParser03 jsonParser = new JSONParser03();
    JSONParser04 jsonParser04 = new JSONParser04();
	static String stringCategory;
	HashMap<String, String> queryValues;
	static ArrayList<HashMap<String, String>> usersList1 = new ArrayList<HashMap <String, String>>();
	ArrayList<HashMap<String, String>> usersList2 = new ArrayList<HashMap <String, String>>();
	static List<String> list = new ArrayList<String>();
	static List<String> list1  = new ArrayList<String>();
	static List<String> list2  = new ArrayList<String>();
	DatabaseActivity db;
	ProgressDialog prgDialog;
	static String ofCurrencyShort;
	static String ofCurrencyFull;
	double value;
	Context context;
	static String notificationFlag = "false";

    ArrayAdapter<String> simple_adapter;

    public MultiSelectionSpinner02(Context context) {
        super(context);

        this.context = context;
        simple_adapter = new ArrayAdapter<>(context,
                R.layout.addcurrency_spinner);
        super.setAdapter(simple_adapter);
    }

    public MultiSelectionSpinner02(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        simple_adapter = new ArrayAdapter<>(context,
                R.layout.addcurrency_spinner);
        super.setAdapter(simple_adapter);
    }

    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
        if (mSelection != null && which < mSelection.length) {
            mSelection[which] = isChecked;
            simple_adapter.clear();
            simple_adapter.add(buildSelectedItemString());
            //System.arraycopy(mSelection, 0, mSelectionAtStart, 0, mSelection.length);
        } else {
            throw new IllegalArgumentException(
                    "Argument 'which' is out of bounds.");
        }
    }

    @Override
    public boolean performClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMultiChoiceItems(_items, mSelection, this);
        _itemsAtStart = getSelectedItemsAsString();
        
       /* list1.clear();
        list1 = getSelectedStrings();
        if(list1.size()>1){
		*/
		
        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            	
            	ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

				NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
				
				if (networkInfo != null && networkInfo.isConnected()) {
                
				System.arraycopy(mSelection, 0, mSelectionAtStart, 0, mSelection.length);
                
                usersList1.clear();
                db = new DatabaseActivity(getContext());
        		usersList1 = db.getAllCurrency01();
        		ofCurrencyShort = usersList1.get(0).get("intoCurrencyCode");
        		ofCurrencyFull = usersList1.get(0).get("intoCurrencyFullName");
        		value = Double.parseDouble(usersList1.get(0).get("intoCurrentRate"));
        		
        		list = getSelectedStrings1();
        		list1 = getSelectedStrings();
        		StringBuilder sb = new StringBuilder();
        		for(String n : list){
        			if(sb.length()>0)
        				sb.append(',');
        			sb.append("%22").append(ofCurrencyShort).append(n).append("%22");
        		}

        		stringCategory = sb.toString();
        		
        		
        		
        			new NetworkThread3().execute();
        		
				}else{
					Toast.makeText(context, "No internet connection", Toast.LENGTH_LONG).show();
				}
        		
           // }
            }
        });
       /* }else{
			Toast.makeText(context, "Please select atleast two currencies", Toast.LENGTH_LONG).show();
		}*/
        
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                simple_adapter.clear();
                simple_adapter.add(_itemsAtStart);
                System.arraycopy(mSelectionAtStart, 0, mSelection, 0, mSelectionAtStart.length);
            }
        });
        builder.show();
        return true;
    }

    @Override
    public void setAdapter(SpinnerAdapter adapter) {
        throw new RuntimeException(
                "setAdapter is not supported by MultiSelectSpinner.");
    }

    public void setItems(String[] items) {
        _items = items;
        mSelection = new boolean[_items.length];
        mSelectionAtStart = new boolean[_items.length];
        simple_adapter.clear();
        simple_adapter.add(_items[0]);
        Arrays.fill(mSelection, false);
        mSelection[0] = true;
        mSelectionAtStart[0] = true;
    }

    public void setItems(List<String> items) {
        _items = items.toArray(new String[items.size()]);
        mSelection = new boolean[_items.length];
        mSelectionAtStart  = new boolean[_items.length];
        simple_adapter.clear();
        simple_adapter.add(_items[0]);
        Arrays.fill(mSelection, false);
        mSelection[0] = true;
    }

    public void setSelection(String[] selection) {
       //System.out.println(selection[0]);
       //System.out.println(selection[1]);
    	for (String cell : selection) {
            for (int j = 0; j < _items.length; ++j) {
                if (_items[j].equals(cell)) {
                    mSelection[j] = true;
                    mSelectionAtStart[j] = true;
                }
            }
        }
    }

    public void setSelection(List<String> selection) {
        for (int i = 0; i < mSelection.length; i++) {
            mSelection[i] = false;
            mSelectionAtStart[i] = false;
        }
        for (String sel : selection) {
            for (int j = 0; j < _items.length; ++j) {
                if (_items[j].contains(sel)) {
                    mSelection[j] = true;
                    mSelectionAtStart[j] = true;
                }
            }
        }
        simple_adapter.clear();
        simple_adapter.add(buildSelectedItemString());
    }
    
    

    public void setSelection(int index) {
        for (int i = 0; i < mSelection.length; i++) {
            mSelection[i] = false;
            mSelectionAtStart[i] = false;
        }
        if (index >= 0 && index < mSelection.length) {
            mSelection[index] = true;
            mSelectionAtStart[index] = true;
        } else {
            throw new IllegalArgumentException("Index " + index
                    + " is out of bounds.");
        }
        simple_adapter.clear();
        simple_adapter.add(buildSelectedItemString());
    }

    public void setSelection(int[] selectedIndices) {
        for (int i = 0; i < mSelection.length; i++) {
            mSelection[i] = false;
            mSelectionAtStart[i] = false;
        }
        for (int index : selectedIndices) {
            if (index >= 0 && index < mSelection.length) {
                mSelection[index] = true;
                mSelectionAtStart[index] = true;
            } else {
                throw new IllegalArgumentException("Index " + index
                        + " is out of bounds.");
            }
        }
        simple_adapter.clear();
        simple_adapter.add(buildSelectedItemString());
    }

    public List<String> getSelectedStrings() {
        List<String> selection = new LinkedList<>();
        for (int i = 0; i < _items.length; ++i) {
            if (mSelection[i]) {
                selection.add(_items[i]);
            }
        }
        return selection;
    }
    
    public List<String> getSelectedStrings1() {
        List<String> selection = new LinkedList<>();
        String item = null;
        for (int i = 0; i < _items.length; ++i) {
            if (mSelection[i]) {
            	int spaceIndex = _items[i].indexOf(" ");
            	if (spaceIndex != -1)
            	{
            		item = _items[i].substring(0, spaceIndex);
            	}
            	selection.add(item);
            }
        }
        return selection;
    }
    

    public List<Integer> getSelectedIndices() {
        List<Integer> selection = new LinkedList<>();
        for (int i = 0; i < _items.length; ++i) {
            if (mSelection[i]) {
                selection.add(i);
            }
        }
        return selection;
    }

    private String buildSelectedItemString() {
        StringBuilder sb = new StringBuilder();
        boolean foundOne = false;

        for (int i = 0; i < _items.length; ++i) {
            if (mSelection[i]) {
                if (foundOne) {
                    sb.append(", ");
                }
                foundOne = true;

                sb.append(_items[i]);
            }
        }
        return sb.toString();
    }

    public String getSelectedItemsAsString() {
        StringBuilder sb = new StringBuilder();
        boolean foundOne = false;

        for (int i = 0; i < _items.length; ++i) {
            if (mSelection[i]) {
                if (foundOne) {
                    sb.append(", ");
                }
                foundOne = true;
                sb.append(_items[i]);
            }
        }
        return sb.toString();
    }
    
    public class NetworkThread3 extends AsyncTask<String, String, String>{

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			prgDialog = new ProgressDialog(getContext());
			prgDialog.setMessage("Loading..");
			prgDialog.setIndeterminate(false);
			prgDialog.setCancelable(false);
			prgDialog.show();

		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub

			list2.clear();
			for(int i = 0; i<list.size(); i++){
				
				String link1 = "http://www.oorsprong.org/websamples.countryinfo/CountryInfoService.wso/CountryFlag/JSON/debug?sCountryISOCode="+list.get(i);
			
				String intoCurrencyFLag =  jsonParser04.getJSONFromUrl(link1).toString();
				
				intoCurrencyFLag = intoCurrencyFLag.replace("\"", "");
				
				
				list2.add(intoCurrencyFLag);
			}
			
			String link = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.xchange%20where%20pair%20in%20%28"+stringCategory+"%29&env=store://datatables.org/alltableswithkeys&format=json";			
			
			JSONObject json = jsonParser.getJSONFromUrl(link);

			notificationFlag = db.getNotificationStatus();
			db.resetTables();
			usersList1.clear();

			try {

				JSONObject arr = (JSONObject) json.get("query");
				JSONObject arr1 = (JSONObject) arr.get("results");
				JSONArray arrOffer = arr1.getJSONArray("rate");

				for(int i = -1; i<arrOffer.length(); i++){
					if(i==-1){

						queryValues = new HashMap<String, String>();

						
						queryValues.put("ofCurrencyCode", ofCurrencyShort);
						queryValues.put("intoCurrencyCode", ofCurrencyShort);
						queryValues.put("ofCurrencyFullName", ofCurrencyFull);
						queryValues.put("intoCurrencyFullName", ofCurrencyFull);
						queryValues.put("ofRate", "1.00");
						queryValues.put("intoPreviousRate", "1.00");
						queryValues.put("intoCurrentRate", "1.00");
						queryValues.put("intoCurrencyFlag", "");
						queryValues.put("status", notificationFlag);
						db.insertCurrency(queryValues);

					}else{
						JSONObject obj = (JSONObject) arrOffer.get(i);
						queryValues = new HashMap<String, String>();

						//String rates = obj.get("Rate").toString();
						queryValues.put("ofCurrencyCode", ofCurrencyShort);
						queryValues.put("intoCurrencyCode", list.get(i));
						queryValues.put("ofCurrencyFullName", ofCurrencyFull);
						queryValues.put("intoCurrencyFullName", list1.get(i));
						queryValues.put("ofRate", "1");
						queryValues.put("intoPreviousRate", obj.get("Rate").toString());
						queryValues.put("intoCurrentRate", obj.get("Rate").toString());
						queryValues.put("intoCurrencyFlag", list2.get(i));
						queryValues.put("status", notificationFlag);
						db.insertCurrency(queryValues);
						
					}


				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(String file_url){

			prgDialog.dismiss();
			MainActivity02.testing(getContext());

		}

	}

}
