package edu.csci153.enhancedcartoondb;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Delete extends Activity {
	
	String id;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete);
        
        Intent i = this.getIntent();
        
        ((TextView)findViewById(R.id.tvCartoonD)).setText(i.getStringExtra("cartoon"));
        ((TextView)findViewById(R.id.tvCharacterD)).setText(i.getStringExtra("character"));
       ((TextView)findViewById(R.id.tvAgeD)).setText(i.getStringExtra("age"));
        id = i.getStringExtra("id");
        
        	System.out.println(id);
        final Button btnDelD = (Button)findViewById(R.id.btnDelD);
        final Button btnCanD = (Button)findViewById(R.id.btnCanD);
        
        btnDelD.setOnClickListener( new OnClickListener(){

			public void onClick(View v) {
					
				delete();
				
			}
        });
        
        
        btnCanD.setOnClickListener( new OnClickListener(){

			public void onClick(View v) {
				finish();
			}
        });
    }
    
    public void delete(){
    	
    SQLiteDatabase db = EnhancedCartoonDatabase.cData.getWritableDatabase();
    	//String where = CartoonData.ID + " = " + Integer.valueOf(id);
    	String where = CartoonData.ID + " = " +id;
    	
    	System.out.println(where);
    	db.delete(CartoonData.TABLE_NAME, where, null);
    	
    	finish();
		
    }
    
    
}