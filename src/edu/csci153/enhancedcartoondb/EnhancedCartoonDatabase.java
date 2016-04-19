package edu.csci153.enhancedcartoondb;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class EnhancedCartoonDatabase extends Activity {
	
	static CartoonData cData;
	int selectedRecord = -1;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        cData = new CartoonData(this);
        
        final Button btnA = (Button)findViewById(R.id.add);
        final Button btnD = (Button)findViewById(R.id.del);
        final Button btnDAll = (Button)findViewById(R.id.delA);
        final ListView lv = (ListView)findViewById(R.id.lv);
        
        lv.setOnItemClickListener(new OnItemClickListener() {

			
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				
				selectedRecord = arg2;
				
			}
             	
        });
        
        btnA.setOnClickListener(new OnClickListener(){
        	
        	public void onClick(View v){
        		
        		openAdd();
        		
        	}
        	
        });
        
        btnD.setOnClickListener(new OnClickListener(){
        	
        	public void onClick(View v){
        		
        		openDelete(selectedRecord);
        		
        	}
        	
        });
        
        btnDAll.setOnClickListener(new OnClickListener(){
        	
        	public void onClick(View v){
        		
        		displayDialog("Delete All Records","Are you sure that you want to delete all the records? This can't be undone.");
        		
        	}
        	
        });
        
    }
    
    public void openAdd(){
    	
    	Intent i;
    	
    	i = new Intent(this, Add.class);
    	
    	startActivity(i);
    }
    
    public void openDelete(int selectedRecord){
    	
    	Intent i;
    	
    	i = new Intent(this, Delete.class);
    	
    	if(selectedRecord!= -1){
    	
    	Cursor cursor = getCartoons();
    	
    	cursor.moveToPosition(selectedRecord);
    	/*
    	int cartoonI = cursor.getColumnIndex(CartoonData.CARTOON);
    	int characterI = cursor.getColumnIndex(CartoonData.CHARACTER);
    	int ageI = cursor.getColumnIndex(CartoonData.AGE);
    	int idI = cursor.getColumnIndex(CartoonData.ID);
    	
    	String idD = cursor.getString(idI);
    	String cartoon = cursor.getString(cartoonI);
    	String character = cursor.getString(characterI);
    	int age = cursor.getInt(ageI);
    	
    	i.putExtra("id", idD);
    	i.putExtra("cartoonD", cartoon);
    	i.putExtra("characterD", character);
    	i.putExtra("ageD", age);
    	
    	*/
    	
    	i.putExtra("id", String.valueOf(cursor.getInt(0)));
    	i.putExtra("cartoon", cursor.getString(1));
    	i.putExtra("character", cursor.getString(2));
    	i.putExtra("age", String.valueOf(cursor.getInt(3)));
    	
    	//System.out.println(cursor.getString(1));
    	
    	startActivity(i);
    	}else{ 
    		displayDialogDelete("Input Error","You must select a record to be deleted.");
    	}
    	
    }
    
    public Cursor populateCursor(){
    	
    	String[] FROM = { CartoonData.ID, CartoonData.CARTOON, CartoonData.CHARACTER, CartoonData.AGE};
    	
    	SQLiteDatabase db = cData.getWritableDatabase();
    	
    	Cursor cursor = db.query(CartoonData.TABLE_NAME, FROM, null, null, null, null, null );
    	
    	startManagingCursor(cursor);
    	
    	return cursor;
    }
    
    public void displayRecords(Cursor c){
    	
    	 
        ListView lv = (ListView)findViewById(R.id.lv);
                
        String[] from = { CartoonData.CARTOON, CartoonData.CHARACTER, CartoonData.AGE };
        
        int [] to = new int []{R.id.tv1, R.id.tv2, R.id.tv3};
        
        SimpleCursorAdapter records = new SimpleCursorAdapter(this, R.layout.listrow, c, from, to);
    	
        lv.setAdapter(records);
        
    }
    
    public void deleteAll(){
    	
    	SQLiteDatabase db = cData.getWritableDatabase();	    	
    	db.execSQL("DELETE FROM " +CartoonData.TABLE_NAME);
    	displayRecords(populateCursor());
    	
    }
    
    
    public void displayDialog(String title, String message) {
    	
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setCancelable(false);
    	builder.setTitle(title);
    	builder.setMessage(message);

    	builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
    	  public void onClick(DialogInterface dialog, int i) {
    		  deleteAll();
    	  }
    	});

    	builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
    		public void onClick(DialogInterface dialog, int i){
    			dialog.dismiss();
    		}
    	});
    	
    	AlertDialog alert = builder.create();
    	alert.show();
    }
    
    
    public void displayDialogDelete(String title, String message) {
    	
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setCancelable(false);
    	builder.setTitle(title);
    	builder.setMessage(message);

    	builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
    	  public void onClick(DialogInterface dialog, int i) {
    		  dialog.dismiss();
    	  }
    	});
    	
    	AlertDialog alert = builder.create();
    	alert.show();
    }
    
    
    public void onResume(){
    	super.onResume();
    	
    	displayRecords(populateCursor());
    	selectedRecord = -1;
    }
    
    public Cursor getCartoons(){
    	
    	SQLiteDatabase db = cData.getWritableDatabase();
    	Cursor c = db.rawQuery("SELECT * FROM " + CartoonData.TABLE_NAME, null);
    	
		return c;
    	
    }
    
}