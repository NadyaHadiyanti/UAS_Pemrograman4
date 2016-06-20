package com.cafe.trafostace;

import java.util.HashMap;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;

import org.json.JSONArray;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MenuAdmin extends Activity {
	
	Button makanan,pesanan,keluar,tambah;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin);
		
	      makanan = (Button)findViewById(R.id.Makanan);
	      pesanan = (Button)findViewById(R.id.Pesanan);
	      tambah = (Button)findViewById(R.id.btnTambah);
	      keluar = (Button)findViewById(R.id.Keluar);
	      
	      makanan.setOnClickListener(new View.OnClickListener(){

	    	  @Override
			  public void onClick(View v){
				  //TODO Auto-generated method stub
				  Intent i = new Intent(getApplicationContext(), MenuMakanan.class);
				  startActivity(i);
	    	  }
	      });
	      
	      pesanan.setOnClickListener(new View.OnClickListener(){
	        	
	    	  @Override
	    	  public void onClick(View v){
	    	  //TODO Auto-generated method stub
	    		  Intent i = new Intent(getApplicationContext(), DaftarPesanan.class);
	    		  startActivity(i);
	    	  }	
		  });
	      
	      tambah.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i = new Intent(getApplicationContext(), InsertMenu.class);
		    		startActivity(i);
				}
			});
	      
	      keluar.setOnClickListener(new View.OnClickListener(){
	        	
	    	  @Override
	    	  public void onClick(View v){
		    	  //TODO Auto-generated method stub
	    		  finish();
	    	  }	
		  });
	}
}
