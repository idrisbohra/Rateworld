package com.rateworld.database;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseActivity extends SQLiteOpenHelper{

	public DatabaseActivity(Context context) {
		super(context, "exchange.db", null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String query = "CREATE TABLE currencyRates (sId INTEGER PRIMARY KEY AUTOINCREMENT, ofCurrencyCode TEXT, intoCurrencyCode TEXT, " +
				"ofCurrencyFullName TEXT, intoCurrencyFullName TEXT, ofRate VARCHAR, intoPreviousRate VARCHAR, intoCurrentRate VARCHAR, intoCurrencyFlag TEXT, status TEXT)";
		
		String query1 = "CREATE TABLE currencies (sId INTEGER PRIMARY KEY AUTOINCREMENT, fullCurrencyName TEXT)";
		
		String query2 = "CREATE TABLE currencyRatesDialog (sId INTEGER PRIMARY KEY AUTOINCREMENT, ofCurrencyCode TEXT, intoCurrencyCode TEXT, " +
				"ofCurrencyFullName TEXT, intoCurrencyFullName TEXT, ofRate VARCHAR, intoPreviousRate VARCHAR, intoCurrentRate VARCHAR, intoCurrencyFlag TEXT, status TEXT)";
		
		db.execSQL(query);
		db.execSQL(query1);
		db.execSQL(query2);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		String query, query1, query2;
		query = "DROP TABLE IF EXISTS currencyRates";
		query1 = "DROP TABLE IF EXISTS currencies";
		query2 = "DROP TABLE IF EXISTS currencyRatesDialog";
		db.execSQL(query);
		db.execSQL(query1);
		db.execSQL(query2);
		onCreate(db);
	}
	
	public void insertCurrency(HashMap<String, String> currency) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("ofCurrencyCode", currency.get("ofCurrencyCode"));
		values.put("intoCurrencyCode", currency.get("intoCurrencyCode"));
		values.put("ofCurrencyFullName", currency.get("ofCurrencyFullName"));
		values.put("intoCurrencyFullName", currency.get("intoCurrencyFullName"));
		values.put("intoCurrencyFlag", currency.get("intoCurrencyFlag"));
		values.put("ofRate", currency.get("ofRate"));
		values.put("intoPreviousRate", currency.get("intoPreviousRate"));
		values.put("intoCurrentRate", currency.get("intoCurrentRate"));
		values.put("status", currency.get("status"));
		db.insert("currencyRates", null, values);
		db.close();
	}
	
	/*public void insertDialogCurrency(HashMap<String, String> currency) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("ofCurrencyCode", currency.get("ofCurrencyCode"));
		values.put("intoCurrencyCode", currency.get("intoCurrencyCode"));
		values.put("ofCurrencyFullName", currency.get("ofCurrencyFullName"));
		values.put("intoCurrencyFullName", currency.get("intoCurrencyFullName"));
		values.put("intoCurrencyFlag", currency.get("intoCurrencyFlag"));
		values.put("ofRate", currency.get("ofRate"));
		values.put("intoPreviousRate", currency.get("intoPreviousRate"));
		values.put("intoCurrentRate", currency.get("intoCurrentRate"));
		values.put("status", "false");
		db.insert("currencyRatesDialog", null, values);
		db.close();
	}*/
	
	public void insertCurrencies(String[] parts) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		for(int i=0; i<parts.length; i++){
		
			values.put("fullCurrencyName", parts[i]);
			db.insert("currencies", null, values);
		}
		
		
		db.close();
	}
	
	public ArrayList<HashMap<String, String>> getAllCurrency(){
		ArrayList<HashMap<String, String>> usersList;
		usersList = new ArrayList<HashMap<String, String>>();
		SQLiteDatabase db = this.getWritableDatabase();
		String sql = "SELECT * from currencyRates";
		
		Cursor cursor = db.rawQuery(sql, null);
		
		
		
		if(cursor.moveToFirst()){
			
			 do {
		        	HashMap<String, String> map = new HashMap<String, String>();
		        	
		        	map.put("ofCurrencyCode", cursor.getString(1));
		        	map.put("intoCurrencyCode", cursor.getString(2));
		        	map.put("ofCurrencyFullName", cursor.getString(3));
		        	map.put("intoCurrencyFullName", cursor.getString(4));
		        	map.put("intoCurrencyFlag", cursor.getString(8));
		        	map.put("ofRate", cursor.getString(5));
		        	map.put("intoPreviousRate", cursor.getString(6));
		        	map.put("intoCurrentRate", cursor.getString(7));
		        	
		        	if(cursor.getPosition()!=0){
		        		
		        		usersList.add(map);
		        	}
		        	
		        	
		        	}while(cursor.moveToNext());
		
		}
		db.close();
		return usersList;
		
	}
	
	public ArrayList<HashMap<String, String>> getAllCurrency01(){
		ArrayList<HashMap<String, String>> usersList;
		usersList = new ArrayList<HashMap<String, String>>();
		SQLiteDatabase db = this.getWritableDatabase();
		String sql = "SELECT * from currencyRates";
		
		Cursor cursor = db.rawQuery(sql, null);
		
		
		
		if(cursor.moveToFirst()){
			
			 do {
		        	HashMap<String, String> map = new HashMap<String, String>();
		        	
		        	map.put("ofCurrencyCode", cursor.getString(1));
		        	map.put("intoCurrencyCode", cursor.getString(2));
		        	map.put("ofCurrencyFullName", cursor.getString(3));
		        	map.put("intoCurrencyFullName", cursor.getString(4));
		        	map.put("intoCurrencyFlag", cursor.getString(8));
		        	map.put("ofRate", cursor.getString(5));
		        	map.put("intoPreviousRate", cursor.getString(6));
		        	map.put("intoCurrentRate", cursor.getString(7));
		        	
		        	//if(cursor.getPosition()!=0){
		        		
		        		usersList.add(map);
		        	//}
		        	
		        	
		        	}while(cursor.moveToNext());
		
		}
		db.close();
		return usersList;
		
	}
	
	
	public ArrayList<String> getAllIntoCurrencyCode(String intoCurrencyCode){
		ArrayList<String> usersList;
		usersList = new ArrayList<String>();
		SQLiteDatabase db = this.getWritableDatabase();
		String sql = "SELECT intoCurrencyCode from currencyRates";
		
		Cursor cursor = db.rawQuery(sql, null);
		
		if(cursor.moveToFirst()){
			
			 do {
		        	
		        	if(!cursor.getString(0).equals(intoCurrencyCode)){
		        		
		        		usersList.add(cursor.getString(0));
		        	}
		        	    	
		        	}while(cursor.moveToNext());
		
		}
		db.close();
		return usersList;
		
	}
	
	public ArrayList<String> getAllIntoCurrencyFullName(String intoCurrencyFullName){
		ArrayList<String> usersList;
		usersList = new ArrayList<String>();
		SQLiteDatabase db = this.getWritableDatabase();
		String sql = "SELECT intoCurrencyFullName from currencyRates";
		
		Cursor cursor = db.rawQuery(sql, null);
		
		if(cursor.moveToFirst()){
			
			 do {
		        	
		        	if(!cursor.getString(0).equals(intoCurrencyFullName)){
		        		
		        		usersList.add(cursor.getString(0));
		        	}
		        	    	
		        	}while(cursor.moveToNext());
		
		}
		db.close();
		return usersList;
		
	}
	
	public ArrayList<String> getCurrencies(){
		ArrayList<String> usersList;
		usersList = new ArrayList<String>();
		SQLiteDatabase db = this.getWritableDatabase();
		String sql = "SELECT fullCurrencyName from currencies";
		
		Cursor cursor = db.rawQuery(sql, null);
		if(cursor.moveToFirst()){
			
			 do {
		        	
		        	//if(!cursor.getString(0).equals(intoCurrencyCode)){
		        		
		        		usersList.add(cursor.getString(0));
		        	//}
		        	    	
		        	}while(cursor.moveToNext());
		
		}
		db.close();
		return usersList;
		
	}
	
	public ArrayList<String> getCurrencies02(String CurrencyName){
		ArrayList<String> usersList;
		usersList = new ArrayList<String>();
		SQLiteDatabase db = this.getWritableDatabase();
		String sql = "SELECT fullCurrencyName from currencies";
		
		Cursor cursor = db.rawQuery(sql, null);
		
		if(cursor.moveToFirst()){
			
			 do {
		        	
		        	if(!cursor.getString(0).equals(CurrencyName)){
		        		
		        		usersList.add(cursor.getString(0));
		        	}
		        	    	
		        	}while(cursor.moveToNext());
		
		}
		db.close();
		return usersList;
		
	}
	
	public void resetTables(){
		SQLiteDatabase db = this.getWritableDatabase();
		// Delete All Rows
		db.delete("currencyRates", null, null);
		db.close();
	}
	
	public void resetTables02(){
		SQLiteDatabase db = this.getWritableDatabase();
		// Delete All Rows
		db.delete("currencies", null, null);
		db.close();
	}
	
	public void deleteRow(String code){
		SQLiteDatabase db = this.getWritableDatabase();
		// Delete All Rows
		db.delete("currencyRates", "intoCurrencyCode ='"+code+"'", null);
		db.close();
	}
	
	public void update(){
		SQLiteDatabase db = this.getWritableDatabase();
		String sql = "UPDATE currencyRates SET intoPreviousRate = intoCurrentRate";
		db.execSQL(sql);
		
		db.close();
	}
	
	public void updateCurrencyRates(String intoCurrencyCode, String intoCurrentRate){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("intoCurrentRate", intoCurrentRate);
		db.update("currencyRates", values, "intoCurrencyCode = '"+intoCurrencyCode+"'", null);
		db.close();
	}
	
	public void updateNotificationStatus(String status){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("status", status);
		db.update("currencyRates", values, "status = 'false'", null);
		db.close();
	}
	
	public void updateNotificationStatus02(String status){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("status", status);
		db.update("currencyRates", values, "status = 'true'", null);
		db.close();
	}
	
	public String getNotificationStatus(){
		String status = null;
		SQLiteDatabase db = this.getWritableDatabase();
		String sql = "SELECT status FROM currencyRates LIMIT 1";
		Cursor cursor = db.rawQuery(sql, null);



		if(cursor.moveToFirst()){

			do {
				
				status = cursor.getString(0);
			}while(cursor.moveToNext());

		}
		db.close();
		return status;
	}

}
