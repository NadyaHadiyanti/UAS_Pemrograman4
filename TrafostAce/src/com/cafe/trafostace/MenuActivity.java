package com.cafe.trafostace;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MenuActivity extends Activity {

	Button makan, minum, detail;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        
		makan = (Button)findViewById(R.id.buttonMakan);
		minum = (Button)findViewById(R.id.buttonMinum);
		detail = (Button)findViewById(R.id.buttonDetail);
    
		TextView txtKategori = (TextView)findViewById(R.id.txtKategori);
		
		//menerima intent yang dikirim
		Intent i = this.getIntent();
		
		makan.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), PesanMakanan.class);
	    		startActivity(i);
			}
		});
		
		minum.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), PesanMinuman.class);
	    		startActivity(i);
			}
		});

		detail.setOnClickListener(new View.OnClickListener() {
	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), PesanDetail.class);
	    		startActivity(i);
			}
		});
    }
}
