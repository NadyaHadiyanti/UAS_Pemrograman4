package com.cafe.trafostace;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DaftarPesanan extends Activity {
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pesanan);
		
		TextView txtPesanan = (TextView)findViewById(R.id.textPesanan);
		Button pesanan = (Button)findViewById(R.id.buttonKembali2);
		
		pesanan.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

}
