package com.cafe.trafostace;

import java.util.ArrayList;
import java.util.List;
 
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
 
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
 
public class InsertMenu extends Activity {
 
    ProgressDialog pDialog;
 
    JSONParser jsonParser = new JSONParser();
    EditText nama,kategori,harga,status; 
    Button tambah;
    Button cancel;
    private static String url = "http://192.168.43.50/trafostace/create_menu.php";
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_insertmakan);
 
        nama =(EditText)findViewById(R.id.nama);
        kategori =(EditText)findViewById(R.id.kategori);
        harga =(EditText)findViewById(R.id.harga);
        status =(EditText)findViewById(R.id.status);
        
        tambah =(Button)findViewById(R.id.Tambah);
        cancel =(Button)findViewById(R.id.btnCancel);
        
        tambah.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View v) {
            	new insertMenu().execute();
            }
        });
        
        cancel.setOnClickListener(new View.OnClickListener() {
        	 
            @Override
            public void onClick(View v) {
            finish();
            }
        });
    }
 
    public class insertMenu extends AsyncTask<String, String, String> {
 
        String success;
 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(InsertMenu.this);
            pDialog.setMessage("Proses menambahkan...");
            pDialog.setIndeterminate(false);
            pDialog.show();
        }
 
        @Override
        protected String doInBackground(String... params) {

            String strnama = nama.getText().toString();
            String strkateg = kategori.getText().toString();
            String strharga = harga.getText().toString();
            String strstat = status.getText().toString();
 
            List<NameValuePair> rgs = new ArrayList<NameValuePair>();
            rgs.add(new BasicNameValuePair("nama", strnama));
            rgs.add(new BasicNameValuePair("kategori", strkateg));
            rgs.add(new BasicNameValuePair("harga", strharga));
            rgs.add(new BasicNameValuePair("status", strstat));
 
            JSONObject json = jsonParser.makeHttpRequest(url, "POST", rgs);
            
            // check log cat for response
            Log.d("Create Response", json.toString());
            
            try { 
            	success = json.getString("success");
                Intent i = new Intent(getApplicationContext(), MenuAdmin.class);
                startActivity(i);
                // closing this screen
                finish();
 
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }
 
            return null;
        }
 
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
 
            if (success.equals("1")) {
                Toast.makeText(getApplicationContext(), "Menu berhasil ditambahkan", Toast.LENGTH_LONG).show();
 
            } else {
                Toast.makeText(getApplicationContext(), "Menu gagal ditambahkan", Toast.LENGTH_LONG).show();
            }
        }
 
    }
 
}