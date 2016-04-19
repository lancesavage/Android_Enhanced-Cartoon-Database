package edu.csci153.enhancedcartoondb;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class Add extends Activity {
    /** Called when the activity is first created. */
	static int addID;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
        
        final Spinner ageSpin = (Spinner)findViewById(R.id.spinner);
       
        ArrayAdapter<CharSequence> aa = ArrayAdapter.createFromResource(this, R.array.validAges, android.R.layout.simple_spinner_item);
        
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        ageSpin.setAdapter(aa);
        
        Button btnA = (Button)findViewById(R.id.btnAddA);
        Button btnC = (Button)findViewById(R.id.btnCloseA);
        
        
        btnA.setOnClickListener(new OnClickListener(){
        	
        	public void onClick(View v){
        		addCharacter();
        	}
        	
        });
        
        
        btnC.setOnClickListener(new OnClickListener(){
        	
        	public void onClick(View v){
        		close();
        	}
        	
        });
    }
    
    
    public void addCharacter(){
    	
    	if(TextUtils.isEmpty(((EditText)findViewById(R.id.etCartoon)).getText())){
    		
    		displayDialog("Input Error", "You must enter in a Cartoon Name");
    	
    		((EditText)findViewById(R.id.etCartoon)).requestFocus();
    	
    	}else if(TextUtils.isEmpty(((EditText)findViewById(R.id.etCharacter)).getText())){
    	
    		displayDialog("Input Error", "You must enter in a Character Name");
    		
    		((EditText)findViewById(R.id.etCharacter)).requestFocus();
    	
    	}else{
    	
    	String cartoon = ((EditText)findViewById(R.id.etCartoon)).getText().toString();
    	String character = ((EditText)findViewById(R.id.etCharacter)).getText().toString();
        String age = ((Spinner)findViewById(R.id.spinner)).getSelectedItem().toString();
        
        SQLiteDatabase db = EnhancedCartoonDatabase.cData.getWritableDatabase();
        ContentValues values = new ContentValues();
        addID++;
       
        values.put(CartoonData.ID, addID );
        values.put(CartoonData.CARTOON, cartoon );
        values.put(CartoonData.CHARACTER, character);
        values.put(CartoonData.AGE, Integer.valueOf(age));
        
        db.insert(CartoonData.TABLE_NAME, null, values);
       
        
        displayDialog("Character Added", "Your Character was successfully added!");
        
        ((EditText)findViewById(R.id.etCartoon)).setText("");
        ((EditText)findViewById(R.id.etCharacter)).setText("");
        ((Spinner)findViewById(R.id.spinner)).setSelection(0);
        ((EditText)findViewById(R.id.etCartoon)).requestFocus();
    	}
    }
    
    public void displayDialog(String title, String message) {
    	
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
    
    
    public void close(){
    	
    	finish();
    	
    }
}