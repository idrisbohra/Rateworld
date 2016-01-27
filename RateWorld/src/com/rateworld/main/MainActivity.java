package com.rateworld.main;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rateworld.R;
import com.rateworld.adapter.ExpandableListAdapter;
import com.rateworld.adapter.ImageSlideAdapter;
import com.rateworld.adapter.NothingSelectedSpinnerAdapter;
import com.rateworld.database.DatabaseActivity;
import com.rateworld.slidingimages.CirclePageIndicator;
import com.rateworld.slidingimages.PageIndicator;
import com.rateworld.swipemenulistview.SwipeMenu;
import com.rateworld.swipemenulistview.SwipeMenuCreator;
import com.rateworld.swipemenulistview.SwipeMenuItem;
import com.rateworld.swipemenulistview.SwipeMenuListView;
import com.squareup.picasso.Picasso;


public class MainActivity extends Activity{

	View rootView;
	Handler handler02, handler03;
	Runnable runnable01, runnable02, runnable03, runnable04;
	private ViewPager mViewPager;
	PageIndicator mIndicator;
	private Runnable animateViewPager;
	private Handler handler;
	boolean stopSliding = false;
	ArrayList<String> products;
	Animation leftIn, rightIn, rightIn02, leftOut, rightOut, topIn, topIn02, topOut, topOut02, topOut03, downIn, downOut,  
	fadeOut, fadeOut02, fadeIn;
	static Animation rotationrefresh;
	//ScaleAnimation scale;
	ImageView Logo, App_name, upArrow, downArrow;
	ProgressBar progressBar;
	TextView loading, fromCurrency, intoCurrency;
	View view1;
	ImageView submit;
	static DatabaseActivity db;
	MultiSelectionSpinner into;
	Spinner of;
	static Context context;
	static String ofCurrencyShort;
	static String ofCurrencyFull;
	GPSTracker gps;
	protected LocationManager locationManager;
	static MultiSelectionSpinner03 addCurrency;
	static int count = 1;
	ImageView CalculationButton;
	JSONParser03 jsonParser;
	JSONParser04 jsonParser04;
	static String stringCategory;
	static HashMap<String, String> queryValues;
	static ArrayList<HashMap<String, String>> usersList1 = new ArrayList<HashMap <String, String>>();
	static ArrayList<HashMap<String, String>> usersList2 = new ArrayList<HashMap <String, String>>();
	static ArrayList<HashMap<String, String>> usersList3 = new ArrayList<HashMap <String, String>>();
	static List<String> list;
	static List<String> list1;
	static List<String> list2;
	static ListView listview;
	static OfferCustomAdapter adapter;
	static OfferCustomAdapter02 adapter02;
	Dialog dialog;
	//static Context context;
	static EditText ofRateEditText;
	static TextView ofTextView;
	ProgressDialog prgDialog;
	static double value;
	List<String> Categories = new ArrayList<String>();
	static ImageView refresh, notifi, easyT, notifi2, easyT2;
	static TextView updatelabel;
	double latitude, longitude;
	static TextView title; 
	TextView intoHint, toshortform, near_by;
	View View02, View01, view01, view03;
	ImageView setting, back, nearby_imageview;
	String intoCurrencyFLag;
	PendingIntent alarmIntent;
	PendingIntent pendingIntent;
	static boolean isActive = false;
	static boolean flag = false;
	static String notificationFlag = "false";

	public static final String ARG_ITEM_ID = "home_fragment";
	private static final long ANIM_VIEWPAGER_DELAY = 10000;
	private static final long ANIM_VIEWPAGER_DELAY_USER_VIEW = 10000;

	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	List<String> listDataHeader;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		context = this;
		products = new ArrayList<String>();

		leftIn = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.leftin);
		rightIn = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.rightin);
		rightIn02 = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.rightin02);
		leftOut = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.leftout);
		rightOut = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.rightout);
		topIn = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.topin);
		topIn02 = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.topin02);
		topOut = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.topout);
		downIn = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.downin);
		downOut = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.downout);
		topOut02 = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.topout02);
		topOut03 = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.topout03);
		fadeIn = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.fadein);
		fadeOut = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.fadeout);
		fadeOut02 = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.fadeout02);
		rotationrefresh = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);


		//scale = new ScaleAnimation((float)1.0, (float)1.0, (float)1.0, (float)2.5);
		//scale.setFillAfter(true);
		//scale.setDuration(500);

		Intent intent02 = new Intent(MainActivity.this, BCReceiver03.class);
		pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent02, 0);

		db = new DatabaseActivity(getApplicationContext());
		Logo = (ImageView) findViewById(R.id.logoImageView);
		App_name = (ImageView) findViewById(R.id.appNameImageView);
		progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		loading = (TextView) findViewById(R.id.loadingTextView);
		title = (TextView) findViewById(R.id.textView1);
		intoHint = (TextView) findViewById(R.id.intoCorrencyHint);
		view1 = (View) findViewById(R.id.View00);
		View02 = (View) findViewById(R.id.View02);
		view03 = (View) findViewById(R.id.view03);
		toshortform = (TextView) findViewById(R.id.TextView02);
		near_by = (TextView) findViewById(R.id.TextView04);
		of = (Spinner) findViewById(R.id.spinner1);
		into = (MultiSelectionSpinner) findViewById(R.id.spinner2);
		gps = new GPSTracker(getApplicationContext());
		locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
		submit = (ImageView) findViewById(R.id.submitButton);


		updatelabel = (TextView)findViewById(R.id.textView14);
		jsonParser = new JSONParser03();
		jsonParser04 = new JSONParser04();
		refresh = (ImageView) findViewById(R.id.ImageView01);
		list = new ArrayList<String>();
		list1 = new ArrayList<String>();
		list2 = new ArrayList<String>();
		ofRateEditText = (EditText)findViewById(R.id.editText1);
		ofTextView = (TextView)findViewById(R.id.ofTextView);
		listview = (SwipeMenuListView) findViewById(android.R.id.list);
		CalculationButton = (ImageView) findViewById(R.id.calcuButton);
		addCurrency = (MultiSelectionSpinner03) findViewById(R.id.spinner3);
		setting = (ImageView) findViewById(R.id.settingImageView);
		back = (ImageView) findViewById(R.id.backImageView);
		nearby_imageview = (ImageView) findViewById(R.id.imageView1);


		mViewPager = (ViewPager) findViewById(R.id.view_pager);
		mIndicator = (CirclePageIndicator)findViewById(R.id.indicator);
		//imgNameTxt = (TextView) getView().findViewById(R.id.img_name);
		//mIndicator.setOnPageChangeListener(new PageChangeListener());
		//mViewPager.setOnPageChangeListener(new PageChangeListener());
		//stopSliding = false;

		products.add(0, "drawable://"+ R.drawable.picturethird);
		products.add(1, "drawable://"+ R.drawable.picturesecond);
		products.add(2, "drawable://"+ R.drawable.pictureone);
		mViewPager.setAdapter(new ImageSlideAdapter(getApplicationContext(), products));

		runnable(products.size());
		handler.postDelayed(animateViewPager, ANIM_VIEWPAGER_DELAY_USER_VIEW);

		mIndicator.setViewPager(mViewPager);

		Logo.startAnimation(rightIn);
		App_name.startAnimation(leftIn);

		rightIn.setAnimationListener(new AnimationListener(){

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub

				progressBar.setVisibility(View.VISIBLE);
				loading.setVisibility(View.VISIBLE);

				if(db.getAllCurrency().size()!=0){

					Intent i = new Intent(getApplicationContext(), MainActivity02.class);
					startActivity(i);
					finish();

				} else {

					handler02 = new Handler(); // create Handler object

					runnable01 = new Runnable() {
						@Override
						public void run() {

							ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

							NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

							if (networkInfo != null && networkInfo.isConnected()) {


								//progressBar.startAnimation(fadeOut);
								//loading.startAnimation(fadeOut);
								progressBar.setVisibility(View.INVISIBLE);
								loading.setVisibility(View.INVISIBLE);
								Logo.startAnimation(topIn);
								App_name.startAnimation(topIn);

								topIn.setAnimationListener(new AnimationListener(){

									@Override
									public void onAnimationEnd(Animation animation) {
										// TODO Auto-generated method stub
										Logo.startAnimation(topOut02);
										App_name.startAnimation(topOut);

										topOut.setAnimationListener(new AnimationListener(){

											@Override
											public void onAnimationEnd(Animation animation) {
												// TODO Auto-generated method stub
												//fromCurrency.setVisibility(View.VISIBLE);
												//intoCurrency.setVisibility(View.VISIBLE);
												//upArrow.setVisibility(View.VISIBLE);
												//downArrow.setVisibility(View.VISIBLE);
												view1.setVisibility(View.VISIBLE);
												//view2.setVisibility(View.VISIBLE);
												submit.setVisibility(View.VISIBLE);
												of.setVisibility(View.VISIBLE);
												into.setVisibility(View.VISIBLE);

												//fromCurrency.startAnimation(fadeIn);
												//intoCurrency.startAnimation(fadeIn);
												//upArrow.startAnimation(leftIn);
												//downArrow.startAnimation(leftIn);
												of.startAnimation(rightIn02);
												into.startAnimation(leftIn);
												//intoHint.startAnimation(fadeIn);
												view1.startAnimation(fadeIn);
												//view2.startAnimation(fadeIn);
												submit.startAnimation(downIn);

												downIn.setAnimationListener(new AnimationListener(){

													@Override
													public void onAnimationEnd(Animation animation) {
														// TODO Auto-generated method stub

														ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

														NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

														if (networkInfo != null && networkInfo.isConnected()) {

															//
															new NetworkThread2().execute();

														}else{
															Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_LONG).show();

														}


													}

													@Override
													public void onAnimationRepeat(Animation animation) {
														// TODO Auto-generated method stub

													}

													@Override
													public void onAnimationStart(Animation animation) {
														// TODO Auto-generated method stub

													}

												});

											}

											@Override
											public void onAnimationRepeat(Animation animation) {
												// TODO Auto-generated method stub

											}

											@Override
											public void onAnimationStart(Animation animation) {
												// TODO Auto-generated method stub

											}

										});

									}

									@Override
									public void onAnimationRepeat(Animation animation) {
										// TODO Auto-generated method stub

									}

									@Override
									public void onAnimationStart(Animation animation) {
										// TODO Auto-generated method stub

									}

								});
							}else{
								Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_SHORT).show();
								loading.setText("Searching...");
								handler02.postDelayed(runnable02, 2500);
							}


						}
					};

					handler02.postDelayed(runnable01, 100);

					runnable02 = new Runnable() { // runnable to start settings anim
						public void run() {

							new Handler().postDelayed(runnable01, 2000);
						}
					};

				}

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

		});

		if(locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER)){

			if(gps.canGetLocation()){

				latitude = gps.getLatitude();
				longitude = gps.getLongitude();

			}
		}

		submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				list = into.getSelectedStrings1();
				list1 = into.getSelectedStrings();
				if(ofCurrencyFull != null){

					if(list.size()>1){

						ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

						NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

						if (networkInfo != null && networkInfo.isConnected()) {


							App_name.startAnimation(topOut03);
							title.setVisibility(View.VISIBLE);
							title.startAnimation(topIn02);
							of.startAnimation(fadeOut);
							into.startAnimation(fadeOut);
							intoHint.startAnimation(fadeOut);
							view1.startAnimation(fadeOut);
							submit.startAnimation(fadeOut02);

							view1.setEnabled(false);
							intoHint.setEnabled(false);
							of.setEnabled(false);
							into.setEnabled(false);

							//submit.setVisibility(View.INVISIBLE);


							fadeOut02.setAnimationListener(new AnimationListener(){

								@Override
								public void onAnimationEnd(Animation animation) {
									// TODO Auto-generated method stub


									submit.setEnabled(false);
									setting.setVisibility(View.VISIBLE);
									back.setVisibility(View.VISIBLE);
									setting.setAnimation(fadeIn);
									back.setAnimation(fadeIn);


									StringBuilder sb = new StringBuilder();
									for(String n : list){
										if(sb.length()>0)
											sb.append(',');
										sb.append("%22").append(ofCurrencyShort).append(n).append("%22");
									}

									stringCategory = sb.toString();


									new NetworkThread1().execute();




								}

								@Override
								public void onAnimationRepeat(Animation animation) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onAnimationStart(Animation animation) {
									// TODO Auto-generated method stub

								}

							});

						}else{
							Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_LONG).show();

						}


					}else{

						Toast.makeText(getApplicationContext(), "Please select atleast two convert-into currencies", Toast.LENGTH_LONG).show();
					}

				}else{

					Toast.makeText(getApplicationContext(), "Please select currency", Toast.LENGTH_LONG).show();
				}
			}
		});

		CalculationButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				usersList2.clear();
				value = Double.parseDouble(ofRateEditText.getText().toString());
				HashMap<String, String> queryValues;
				usersList1 = db.getAllCurrency();
				for(int i = 0; i<usersList1.size(); i++){

					double value1 = (Double.parseDouble(usersList1.get(i).get("intoCurrentRate")))*value;
					double value2 = (Double.parseDouble(usersList1.get(i).get("intoCurrentRate")))*value;
					value1 = Double.parseDouble(new DecimalFormat("##.#####").format(value1));
					value2 = Double.parseDouble(new DecimalFormat("##.#####").format(value2));

					queryValues = new HashMap<String, String>();
					queryValues.put("ofCurrencyCode", usersList1.get(i).get("ofCurrencyCode"));
					queryValues.put("intoCurrencyCode", usersList1.get(i).get("intoCurrencyCode"));
					queryValues.put("ofCurrencyFullName", usersList1.get(i).get("ofCurrencyFullName"));
					queryValues.put("intoCurrencyFullName", usersList1.get(i).get("intoCurrencyFullName"));
					queryValues.put("intoCurrencyFlag", usersList1.get(i).get("intoCurrencyFlag"));
					queryValues.put("ofRate", ofRateEditText.getText().toString());
					queryValues.put("intoPreviousRate", Double.toString(value2));
					queryValues.put("intoCurrentRate", Double.toString(value1));
					usersList2.add(queryValues);
				}

				usersList1.clear();
				usersList1.addAll(usersList2);
				adapter =  new OfferCustomAdapter(context, usersList1);	
				listview.setAdapter(adapter);

			}
		});


		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				//addCurrency.setSelection(db.getAllIntoCurrencyCode(usersList1.get(position).get("ofCurrencyCode")));
				ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

				NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

				if (networkInfo != null && networkInfo.isConnected()) {

					ofCurrencyShort = usersList1.get(position).get("intoCurrencyCode");
					ofCurrencyFull = usersList1.get(position).get("intoCurrencyFullName");
					value = Double.parseDouble(usersList1.get(position).get("intoCurrentRate"));
					list = db.getAllIntoCurrencyCode(ofCurrencyShort);
					list1 = db.getAllIntoCurrencyFullName(ofCurrencyFull);

					StringBuilder sb = new StringBuilder();
					for(String n : list){
						if(sb.length()>0)
							sb.append(',');
						sb.append("%22").append(ofCurrencyShort).append(n).append("%22");
					}

					stringCategory = sb.toString();

					new NetworkThread3().execute();
				}else{
					Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_SHORT).show();
				}
			}
		});

		near_by.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ConnectivityManager mgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo networkInfo = mgr.getActiveNetworkInfo();

				if(networkInfo != null && networkInfo.isConnected()){

					if(locationManager
							.isProviderEnabled(LocationManager.GPS_PROVIDER)){

						if(latitude!=0.00){

							new NetworkThread4().execute();

						}else{

							prgDialog = new ProgressDialog(context);
							prgDialog.setMessage("Enabling GPS...");
							prgDialog.setIndeterminate(false);
							prgDialog.setCancelable(false);
							prgDialog.show();

							count=1;
							handler03 = new Handler(); // create Handler object

							runnable03 = new Runnable() {
								@Override
								public void run() {

									gps = new GPSTracker(getApplicationContext());
									if(gps.canGetLocation()){

										latitude = gps.getLatitude();
										longitude = gps.getLongitude();
										//latitude = 0.000;
										//longitude = 0.000;
										//prgDialog.dismiss();
										//flag = false;
									}
									if(latitude!=0.00 &&longitude!=0.00){
										prgDialog.dismiss();
										new NetworkThread4().execute();

									}else if(count<5){

										handler03.postDelayed(runnable04, 2000);
										count++;
									}else{
										Toast.makeText(getApplicationContext(), "Your GPS is still not working. Try again later", Toast.LENGTH_SHORT).show();
										prgDialog.dismiss();
									}



								}
							};

							handler03.postDelayed(runnable03, 100);

							runnable04 = new Runnable() { // runnable to start settings anim
								public void run() {

									new Handler().postDelayed(runnable03, 2500);
								}
							};

						}

					}else{

						startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
						Toast.makeText(getApplicationContext(), "GPS is off. Enable your GPS", Toast.LENGTH_LONG).show();
						flag = true;
					}

				}else{
					Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_SHORT).show();
				}
			}
		});

		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		setting.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog = new Dialog(context);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.activity_setting02);
				TextView about = (TextView) dialog.findViewById(R.id.TextView01);
				TextView instruction = (TextView) dialog.findViewById(R.id.TextView02);
				instruction.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent i = new Intent(getApplicationContext(), InstructionActivity.class);
						startActivity(i);
						dialog.dismiss();
					}
				});
				about.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent i = new Intent(getApplicationContext(), AboutActivity.class);
						startActivity(i);
						dialog.dismiss();
					}
				});

				expListView = (ExpandableListView) dialog.findViewById(R.id.lvExp);

				// preparing list data
				prepareListData();

				listAdapter = new ExpandableListAdapter (getApplicationContext(), listDataHeader);

				expListView.setAdapter(listAdapter);


				expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

					int previousGroup = -1;

					@Override
					public void onGroupExpand(int groupPosition) {
						if(groupPosition != previousGroup)
							expListView.collapseGroup(previousGroup);
						previousGroup = groupPosition;
					}
				});

				/*notifi = (ImageView)dialog.findViewById(R.id.imageView3);
				easyT = (ImageView)dialog.findViewById(R.id.imageView2);
				notifi2 = (ImageView)dialog.findViewById(R.id.ImageView01);
				easyT2 = (ImageView)dialog.findViewById(R.id.ImageView02);
				SwitchButton notification = (SwitchButton) dialog.findViewById(R.id.sb_md);
				SwitchButton easyTo = (SwitchButton) dialog.findViewById(R.id.SwitchbButton01);
				//TextView rates = (TextView)dialog.findViewById(R.id.textView2);
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

							notifi.setVisibility(View.VISIBLE);
							notifi2.setVisibility(View.INVISIBLE);
							db.updateNotificationStatus(String.valueOf(isChecked));

							Calendar timeOff9 = Calendar.getInstance();

							timeOff9.setTimeInMillis(System.currentTimeMillis());

							timeOff9.set(Calendar.HOUR_OF_DAY, 10);

							timeOff9.set(Calendar.MINUTE, 00);

							timeOff9.set(Calendar.SECOND, 00);

							AlarmManager alarmMgr01 = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

							alarmMgr01.setRepeating(AlarmManager.RTC_WAKEUP,
									timeOff9.getTimeInMillis(),
									AlarmManager.INTERVAL_HALF_DAY, pendingIntent);

						}else{
							notifi.setVisibility(View.INVISIBLE);
							notifi2.setVisibility(View.VISIBLE);
							AlarmManager alarmMgr01 = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
							alarmMgr01.cancel(pendingIntent);
							db.updateNotificationStatus02(String.valueOf(isChecked));
						}
					}
				});*/

				dialog.show();

			}
		});

		// step 1. create a MenuCreator
		SwipeMenuCreator creator = new SwipeMenuCreator() {

			@Override
			public void create(SwipeMenu menu) {

				// create "delete" item
				SwipeMenuItem deleteItem = new SwipeMenuItem(
						getApplicationContext());
				// set item background
				//deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
				//      0x3F, 0x25)));
				//setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_actionbarcolor));
				deleteItem.setBackground(getResources().getDrawable(R.drawable.background_delete));
				// set item width
				deleteItem.setWidth(dp2px(70));
				//deleteItem.setHeight(dp2px(70));
				// set a icon
				deleteItem.setIcon(R.drawable.ic_launcher24);
				// add to menu
				menu.addMenuItem(deleteItem);
			}
		};

		// set creator
		((SwipeMenuListView) listview).setMenuCreator(creator);

		// step 2. listener item click event
		((SwipeMenuListView) listview).setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
				// ApplicationInfo item = usersList.get(position);
				switch (index) {
				case 0:
					// open
					//open(item);

					if(usersList1.size()>2){
						db.deleteRow(usersList1.get(position).get("intoCurrencyCode"));
						usersList1.remove(position);
						adapter.notifyDataSetChanged();
					}else{

						Toast.makeText(getApplicationContext(), "Unable to remove. Two currencies are mandatory", Toast.LENGTH_LONG).show();

					}
					break;
				case 1:
					// delete
					//					delete(item);

					break;
				}
				return false;
			}
		});

		// set SwipeListener
		((SwipeMenuListView) listview).setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

			@Override
			public void onSwipeStart(int position) {
				// swipe start
			}

			@Override
			public void onSwipeEnd(int position) {
				// swipe end
			}
		});

		// other setting
		//		listView.setCloseInterpolator(new BounceInterpolator());

		// test item long click
		listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(getApplicationContext(), position + " long click", Toast.LENGTH_SHORT).show();
				return false;
			}
		});

	}

	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getResources().getDisplayMetrics());
	}

	private void prepareListData() {
		listDataHeader = new ArrayList<String>();

		// Adding Main data
		listDataHeader.add("Notification");

	}

	@Override
	protected void onStart() {
		super.onStart();
		isActive = true;
	}
	
	@Override
	protected void onPause() {
		super.onStart();
		if(db.getNotificationStatus().equals("true")){
			AlarmManager alarmMgr = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
			alarmMgr.cancel(alarmIntent);
			}
	}

	@Override
	protected void onStop() {
		super.onStop();
		isActive = false;
		if(db.getNotificationStatus().equals("true")){
			AlarmManager alarmMgr = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
			alarmMgr.cancel(alarmIntent);
			}
	}

	@Override
	public void onResume(){
		super.onResume();

		/*ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		
		if (networkInfo != null && networkInfo.isConnected()) {*/

			if(db.getNotificationStatus()!=null){
				
				if(db.getNotificationStatus().equals("true")){
					
					AlarmManager alarmMgr = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);

					Intent intent = new Intent(getApplicationContext(), BCReceiver.class);

					alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

					alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 60*1 , alarmIntent);
				}
			}
			
			

		//}
		
		if(locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER) && flag){

			prgDialog = new ProgressDialog(context);
			prgDialog.setMessage("Enabling GPS...");
			prgDialog.setIndeterminate(false);
			prgDialog.setCancelable(false);
			prgDialog.show();

			flag = false;

			handler03 = new Handler(); // create Handler object

			runnable03 = new Runnable() {
				@Override
				public void run() {

					gps = new GPSTracker(getApplicationContext());
					if(gps.canGetLocation()){

						latitude = gps.getLatitude();
						longitude = gps.getLongitude();
						//latitude = 0.000;
						//longitude = 0.000;
					}
					if(latitude!=0.00 &&longitude!=0.00){

						prgDialog.dismiss();
						new NetworkThread4().execute();

					}else if(count<5){
						handler03.postDelayed(runnable04, 2000);
						count++;
					}else{
						Toast.makeText(getApplicationContext(), "Your GPS is not working properly. Try again later", Toast.LENGTH_SHORT).show();
						prgDialog.dismiss();
					}



				}
			};

			handler03.postDelayed(runnable03, 100);

			runnable04 = new Runnable() { // runnable to start settings anim
				public void run() {

					new Handler().postDelayed(runnable03, 2500);
				}
			};
		}	
	}

	public void runnable(final int size) {
		handler = new Handler();
		animateViewPager = new Runnable() {
			public void run() {

				if (mViewPager.getCurrentItem() == size - 1) {
					mViewPager.setCurrentItem(0);

				} else {
					mViewPager.setCurrentItem(
							mViewPager.getCurrentItem() + 1, true);

				}
				handler.postDelayed(animateViewPager, ANIM_VIEWPAGER_DELAY);
				//}
			}
		};
	}

	public class NetworkThread3 extends AsyncTask<String, String, String>{

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			prgDialog = new ProgressDialog(context);
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

						//String rates = obj.get("Rate").toString();
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
						usersList1.add(queryValues);
					}


				}


				usersList2.clear();

				for(int i = 0; i<usersList1.size(); i++){


					double value1 = (Double.parseDouble(usersList1.get(i).get("intoCurrentRate")))*value;
					value1 = Double.parseDouble(new DecimalFormat("##.#####").format(value1));


					queryValues = new HashMap<String, String>();
					queryValues.put("ofCurrencyCode", usersList1.get(i).get("ofCurrencyCode"));
					queryValues.put("intoCurrencyCode", usersList1.get(i).get("intoCurrencyCode"));
					queryValues.put("ofCurrencyFullName", usersList1.get(i).get("ofCurrencyFullName"));
					queryValues.put("intoCurrencyFullName", usersList1.get(i).get("intoCurrencyFullName"));
					queryValues.put("intoCurrencyFlag", usersList1.get(i).get("intoCurrencyFlag"));
					queryValues.put("ofRate", ofRateEditText.getText().toString());
					queryValues.put("intoPreviousRate", Double.toString(value1));
					queryValues.put("intoCurrentRate", Double.toString(value1));
					usersList2.add(queryValues);
				}



			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				System.out.println(e.getMessage());
			}

			return null;
		}

		@Override
		protected void onPostExecute(String file_url){

			prgDialog.dismiss();

			//usersList1 = db.getAllCurrency();
			if(usersList2.size()!=0){

				usersList1.clear();
				usersList1.addAll(usersList2);

				ofRateEditText.setText(Double.toString(value));
				ofTextView.setText(usersList1.get(0).get("ofCurrencyFullName"));
				toshortform.setText(usersList1.get(0).get("ofCurrencyCode"));
				adapter =  new OfferCustomAdapter(context, usersList1);	
				listview.setAdapter(adapter);
			}


			Categories = db.getCurrencies02(usersList1.get(0).get("ofCurrencyFullName"));
			addCurrency.setItems(Categories);
			addCurrency.setSelection(db.getAllIntoCurrencyCode(usersList1.get(0).get("ofCurrencyCode")));

		}

	}	

	public class NetworkThread2 extends AsyncTask<String, String, String>{

		JSONObject json;
		String names;


		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			prgDialog = new ProgressDialog(context);
			prgDialog.setMessage("Loading..");
			prgDialog.setIndeterminate(false);
			prgDialog.setCancelable(false);
			prgDialog.show();
			intoHint.setVisibility(View.VISIBLE);

		}

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub


			db.resetTables02();
			String link = "https://openexchangerates.org/api/currencies.json";

			json = jsonParser.getJSONFromUrl(link);

			names = json.toString();

			names = names.replaceAll("\\{", "");

			names = names.replaceAll("\\}", "");

			names = names.replaceAll("\"", "");

			names = names.replaceAll(":", " - ");

			names = names.replaceAll("ó", "o");

			names = names.replaceAll("ão", "ao");

			names = names.replaceAll("é", "e");

			names = names.replaceAll("�", "i");

			names = names.replaceAll("ʻ", "'");

			String[] parts = names.split(",");

			db.insertCurrencies(parts);

			//List<String> Categories = new ArrayList<String>();

			Categories = db.getCurrencies();



			return null;
		}

		@Override
		protected void onPostExecute(String file_url){

			prgDialog.dismiss();

			ArrayAdapter<String> RegTypeAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, Categories);

			RegTypeAdapter.setDropDownViewResource(R.layout.spinner_item);

			of.setAdapter(new NothingSelectedSpinnerAdapter(
					RegTypeAdapter,
					R.layout.spinner_row_nothing_selected,
					// R.layout.contact_spinner_nothing_selected_dropdown, // Optional
					context));

			of.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					if(position==0){

						into.setItems(db.getCurrencies02(""));
						into.setSelection(new int[]{1, 2});

					}

					else{

						intoHint.setVisibility(View.INVISIBLE);
						((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
						((TextView) parent.getChildAt(0)).setTextSize(13);
						ofCurrencyFull = parent.getItemAtPosition(position).toString();
						ofCurrencyShort = parent.getItemAtPosition(position).toString();
						int spaceIndex = ofCurrencyShort.indexOf(" ");
						if (spaceIndex != -1)
						{
							ofCurrencyShort = ofCurrencyShort.substring(0, spaceIndex);
						}

						into.setItems(db.getCurrencies02(ofCurrencyFull));
						into.setSelection(new int[]{1, 2});
					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					// TODO Auto-generated method stub
					//((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
				}
			});


		}

	}

	public class NetworkThread1 extends AsyncTask<String, String, String>{

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			prgDialog = new ProgressDialog(context);
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

				intoCurrencyFLag =  jsonParser04.getJSONFromUrl(link1).toString();

				intoCurrencyFLag = intoCurrencyFLag.replace("\"", "");


				list2.add(intoCurrencyFLag);
			}


			String link = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.xchange%20where%20pair%20in%20%28"+stringCategory+"%29&env=store://datatables.org/alltableswithkeys&format=json";


			JSONObject json = jsonParser.getJSONFromUrl(link);

			db.resetTables();

			try {

				JSONObject arr = (JSONObject) json.get("query");
				JSONObject arr1 = (JSONObject) arr.get("results");
				JSONArray arrOffer = arr1.getJSONArray("rate");

				for(int i = -1; i<arrOffer.length(); i++){
					if(i==-1){

						queryValues = new HashMap<String, String>();

						//String rates = obj.get("Rate").toString();
						queryValues.put("ofCurrencyCode", ofCurrencyShort);
						queryValues.put("intoCurrencyCode", ofCurrencyShort);
						queryValues.put("ofCurrencyFullName", ofCurrencyFull);
						queryValues.put("intoCurrencyFullName", ofCurrencyFull);
						queryValues.put("ofRate", "1.00");
						queryValues.put("intoPreviousRate", "1.00");
						queryValues.put("intoCurrentRate", "1.00");
						queryValues.put("intoCurrenyFlag", "");
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
						usersList1.add(queryValues);
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

			View02.setVisibility(View.VISIBLE);
			view03.setVisibility(View.VISIBLE);
			toshortform.setVisibility(View.VISIBLE);
			ofRateEditText.setVisibility(View.VISIBLE);
			ofTextView.setVisibility(View.VISIBLE);
			listview.setVisibility(View.VISIBLE);
			CalculationButton.setVisibility(View.VISIBLE);
			addCurrency.setVisibility(View.VISIBLE);
			near_by.setVisibility(View.VISIBLE);
			updatelabel.setVisibility(View.VISIBLE);
			refresh.setVisibility(View.VISIBLE);
			nearby_imageview.setVisibility(View.VISIBLE);

			addCurrency.startAnimation(fadeIn);

			View02.setAnimation(fadeIn);
			view03.setAnimation(fadeIn);
			toshortform.setAnimation(fadeIn);
			ofRateEditText.setAnimation(fadeIn);
			ofTextView.setAnimation(fadeIn);
			listview.setAnimation(fadeIn);
			CalculationButton.setAnimation(fadeIn);
			addCurrency.setAnimation(fadeIn);
			near_by.setAnimation(fadeIn);
			updatelabel.setAnimation(fadeIn);
			refresh.setAnimation(fadeIn);
			nearby_imageview.setAnimation(fadeIn);

			usersList1.clear();
			usersList1 = db.getAllCurrency();
			if(usersList1.size()!=0){

				Calendar localCalendar = Calendar.getInstance();
				String str1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(localCalendar.getTime());		
				updatelabel.setText("Last Updated : "+str1);

				ofRateEditText.setText(usersList1.get(0).get("ofRate"));
				ofTextView.setText(usersList1.get(0).get("ofCurrencyFullName"));
				toshortform.setText(usersList1.get(0).get("ofCurrencyCode"));
				adapter =  new OfferCustomAdapter(context, usersList1);	
				listview.setAdapter(adapter);

				Categories = db.getCurrencies02(usersList1.get(0).get("ofCurrencyFullName"));
				addCurrency.setItems(Categories);

				if(usersList1.size()!=0){

					addCurrency.setSelection(db.getAllIntoCurrencyCode(usersList1.get(0).get("ofCurrencyCode")));

				}else{

					addCurrency.setSelection(new int[]{2, 3});
				}

			}

			/*ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

			NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

			if (networkInfo != null && networkInfo.isConnected()) {*/

				AlarmManager alarmMgr = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);

				Intent intent = new Intent(getApplicationContext(), BCReceiver02.class);

				alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

				alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 60*1 , alarmIntent);	


			//}





		}

	}

	public class OfferCustomAdapter extends BaseAdapter {


		Context context;

		ArrayList<HashMap<String, String>> usersListt =new ArrayList<HashMap<String, String>>();

		int lastPosition=0;
		//int i=0, j=1, k=2, l=3;


		public OfferCustomAdapter(Context context, ArrayList<HashMap<String, String>> usersList) {

			this.context = context;
			this.usersListt = usersList;

			//inflator = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		}

		public class ViewHolder{
			TextView textview1, textview2, textview3;
			ImageView flag;


		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return usersListt.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return usersListt.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return usersListt.indexOf(getItem(position));
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub

			try{
				LayoutInflater inflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

				ViewHolder holder = null;

				if(convertView == null){

					holder = new ViewHolder();


					//if(position==i){
					convertView = inflator.inflate(R.layout.activity_tab, parent, false);
					holder.textview1 = (TextView)convertView.findViewById(R.id.textView11);
					holder.textview2 = (TextView)convertView.findViewById(R.id.textView1);
					holder.textview3 = (TextView)convertView.findViewById(R.id.TextView01);
					holder.flag = (ImageView)convertView.findViewById(R.id.ImageView01);
					convertView.setTag(holder);

				}else{

					holder = (ViewHolder) convertView.getTag();

				}


				holder.textview1.setText(usersListt.get(position).get("intoCurrentRate"));
				holder.textview2.setText(usersListt.get(position).get("intoCurrencyFullName"));
				holder.textview3.setText(usersListt.get(position).get("ofRate")+" "+usersListt.get(position).get("ofCurrencyCode")+" = "+usersListt.get(position).get("intoCurrentRate")+" "+usersListt.get(position).get("intoCurrencyCode"));

				Picasso.with(context)
				.load(usersListt.get(position).get("intoCurrencyFlag"))
				.placeholder(R.drawable.ic_launcher14) // optional

				.error(R.drawable.ic_launcher14)         // optional
				.into(holder.flag);


				Animation animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
				convertView.startAnimation(animation);
				lastPosition = position;

			}

			catch (Exception e) {
				//e.printStackTrace();
				System.out.println(e.getMessage());
			}
			return convertView;
		}


	}


	//public static class Utility{
	public static void testing02(){

		if(isActive){	

			//Toast.makeText(context, "isActive", Toast.LENGTH_SHORT).show();

			refresh.startAnimation(rotationrefresh);
			usersList1.clear();
			usersList2.clear();
			usersList1= db.getAllCurrency();
			if(ofRateEditText.getText().length()==0){
				value=1.00;
			}else{
				value = Double.parseDouble(ofRateEditText.getText().toString());
			}
			//value = 1.0;
			for(int i = 0; i<usersList1.size(); i++){

				double value1 = (Double.parseDouble(usersList1.get(i).get("intoCurrentRate")))*value;
				double value2 = (Double.parseDouble(usersList1.get(i).get("intoPreviousRate")))*value;
				value1 = Double.parseDouble(new DecimalFormat("##.#####").format(value1));
				value2 = Double.parseDouble(new DecimalFormat("##.#####").format(value2));

				queryValues = new HashMap<String, String>();
				queryValues.put("ofCurrencyCode", usersList1.get(i).get("ofCurrencyCode"));
				queryValues.put("intoCurrencyCode", usersList1.get(i).get("intoCurrencyCode"));
				queryValues.put("ofCurrencyFullName", usersList1.get(i).get("ofCurrencyFullName"));
				queryValues.put("intoCurrencyFullName", usersList1.get(i).get("intoCurrencyFullName"));
				queryValues.put("intoCurrencyFlag", usersList1.get(i).get("intoCurrencyFlag"));
				queryValues.put("ofRate", ofRateEditText.getText().toString());
				queryValues.put("intoPreviousRate", Double.toString(value2));
				queryValues.put("intoCurrentRate", Double.toString(value1));
				usersList2.add(queryValues);
			}

			MainActivity myActivity = new MainActivity();
			usersList1.clear();
			usersList1.addAll(usersList2);
			adapter =  myActivity.new OfferCustomAdapter(context, usersList1);	
			listview.setAdapter(adapter);
			Calendar localCalendar = Calendar.getInstance();
			String str1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(localCalendar.getTime());		
			updatelabel.setText("Last Updated : "+str1);
		}
	}
	//}

	public static void testing(Context context){

		usersList2.clear();

		usersList1 = db.getAllCurrency();
		value = Double.parseDouble(ofRateEditText.getText().toString());

		for(int i = 0; i<usersList1.size(); i++){

			double value1 = (Double.parseDouble(usersList1.get(i).get("intoCurrentRate")))*value;
			value1 = Double.parseDouble(new DecimalFormat("##.#####").format(value1));


			queryValues = new HashMap<String, String>();
			queryValues.put("ofCurrencyCode", usersList1.get(i).get("ofCurrencyCode"));
			queryValues.put("intoCurrencyCode", usersList1.get(i).get("intoCurrencyCode"));
			queryValues.put("ofCurrencyFullName", usersList1.get(i).get("ofCurrencyFullName"));
			queryValues.put("intoCurrencyFullName", usersList1.get(i).get("intoCurrencyFullName"));
			queryValues.put("intoCurrencyFlag", usersList1.get(i).get("intoCurrencyFlag"));
			queryValues.put("ofRate", ofRateEditText.getText().toString());
			queryValues.put("intoPreviousRate", Double.toString(value1));
			queryValues.put("intoCurrentRate", Double.toString(value1));
			usersList2.add(queryValues);
		}

		if(usersList2.size()!=0){

			usersList1.clear();
			usersList1.addAll(usersList2);
			ofRateEditText.setText(Double.toString(value));
			ofTextView.setText(usersList1.get(0).get("ofCurrencyFullName"));

			MainActivity myActivity = new MainActivity();
			adapter =  myActivity.new OfferCustomAdapter(context, usersList1);	
			listview.setAdapter(adapter);
		}

	}

	public class NetworkThread4 extends AsyncTask<String, String, String>{

		ListView list;
		TextView notFound;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			dialog = new Dialog(context);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.activity_nearby);
			ImageView cancel = (ImageView) dialog.findViewById(R.id.cancelImageView);
			notFound = (TextView) dialog.findViewById(R.id.textView2);
			cancel.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
			list = (ListView)dialog.findViewById(android.R.id.list);

			prgDialog = new ProgressDialog(context);
			prgDialog.setMessage("Getting exchange locations..");
			prgDialog.setIndeterminate(false);
			prgDialog.setCancelable(false);
			prgDialog.show();

		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub


			try {

				String link = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+latitude+","+longitude+"&radius=5000&keyword=money_exchange&key=AIzaSyB0wZCy5uTIa9kLcRXdTQcULpONsk_mA5w";

				JSONObject json = jsonParser.getJSONFromUrl(link);				

				JSONArray arrOffer = json.getJSONArray("results");

				for(int i = 0; i<arrOffer.length(); i++){
					JSONObject obj = (JSONObject) arrOffer.get(i);
					HashMap<String, String> queryValues = new HashMap<String, String>();

					queryValues.put("name", obj.get("name").toString());
					queryValues.put("vicinity", obj.get("vicinity").toString());

					JSONObject arr = (JSONObject) obj.get("geometry");
					JSONObject arr1 = (JSONObject) arr.get("location");
					queryValues.put("lat", arr1.get("lat").toString());
					queryValues.put("lng", arr1.get("lng").toString());

					String distance = gps.getDistance(Double.parseDouble(arr1.get("lat").toString()), Double.parseDouble(arr1.get("lng").toString()));

					queryValues.put("distance", distance);

					usersList3.add(queryValues);

				}



			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(String file_url){

			prgDialog.dismiss();
			dialog.show();
			if(usersList3.size()!=0){
				adapter02 =  new OfferCustomAdapter02(context, usersList3);	
				list.setAdapter(adapter02);
			}else{
				notFound.setVisibility(View.VISIBLE);
			}
		}
	}

	public class OfferCustomAdapter02 extends BaseAdapter {


		Context context;

		ArrayList<HashMap<String, String>> usersListt =new ArrayList<HashMap<String, String>>();

		int lastPosition=0;
		//int i=0, j=1, k=2, l=3;


		public OfferCustomAdapter02(Context context, ArrayList<HashMap<String, String>> usersList) {

			this.context = context;
			this.usersListt = usersList;

			//inflator = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		}

		public class ViewHolder{
			TextView textview1, textview2, textview3;
			ImageView map;

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return usersListt.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return usersListt.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return usersListt.indexOf(getItem(position));
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub

			try{
				LayoutInflater inflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

				ViewHolder holder = null;

				if(convertView == null){

					holder = new ViewHolder();


					//if(position==i){
					convertView = inflator.inflate(R.layout.nearby_tab, parent, false);
					holder.textview1 = (TextView)convertView.findViewById(R.id.exchangeNameTextView);
					holder.textview2 = (TextView)convertView.findViewById(R.id.exchangeAddrTextView);
					holder.textview3 = (TextView)convertView.findViewById(R.id.exchangeDistTextView);
					holder.map = (ImageView)convertView.findViewById(R.id.mapImageView);
					convertView.setTag(holder);

				}else{

					holder = (ViewHolder) convertView.getTag();

				}


				holder.textview1.setText(usersListt.get(position).get("name"));
				holder.textview2.setText(usersListt.get(position).get("vicinity"));
				holder.textview3.setText(usersListt.get(position).get("distance")+" Kms (Approx.)");
				holder.map.setTag(position);
				holder.map.setOnClickListener(new View.OnClickListener() {

					@Override 
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						ConnectivityManager mgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
						NetworkInfo networkInfo = mgr.getActiveNetworkInfo();
						if(networkInfo != null && networkInfo.isConnected()){

							Integer post = (Integer) arg0.getTag();
							Intent intent = new Intent(android.content.Intent.ACTION_VIEW, 
									Uri.parse("http://maps.google.com/maps?saddr="+latitude+","+longitude+"&daddr="+Double.parseDouble(usersList3.get(post).get("lat"))+","+Double.parseDouble(usersList3.get(post).get("lng"))+""));
							intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							context.startActivity(intent);
						}else{
							Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_SHORT).show();
						}
					}
				});


				Animation animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
				convertView.startAnimation(animation);
				lastPosition = position;

			}

			catch (Exception e) {
				//e.printStackTrace();
				System.out.println(e.getMessage());
			}
			return convertView;
		}


	}

}
