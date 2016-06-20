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
 
public class MainActivity extends Activity {
 
    ProgressDialog pDialog;
 
    JSONParser jsonParser = new JSONParser();
    EditText nomeja,nama; 
    Button daftar;
    Button admin;
    private static String url = "http://192.168.43.50/trafostace/create_regis.php";
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 
        nomeja   =(EditText)findViewById(R.id.txtNomeja);
        nama =(EditText)findViewById(R.id.txtNama);
        
        daftar =(Button)findViewById(R.id.btnDaftar);
        admin =(Button)findViewById(R.id.btnAdmin);
        
        daftar.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View v) {
            	if (nomeja.getText().toString().trim().length() > 0
                        &&nama.getText().toString().trim().length() > 0) 
                {
                    new daftarRegis().execute();
                } 
                else
                {
                    Toast.makeText(getApplicationContext(), "Masih terdapat form yang belum diisi", Toast.LENGTH_LONG).show();
                }
            }
        });
        
        admin.setOnClickListener(new View.OnClickListener() {
        	 
            @Override
            public void onClick(View v) {
            	Intent i = new Intent(MainActivity.this, LoginAdmin.class);
				startActivity(i);
            }
        });
    }
 
    public class daftarRegis extends AsyncTask<String, String, String> {
 
        String success;
 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Proses mendaftar...");
            pDialog.setIndeterminate(false);
            pDialog.show();
        }
 
        @Override
        protected String doInBackground(String... params) {

            String strnomeja     = nomeja.getText().toString();
            String strnama      = nama.getText().toString();
 
            List<NameValuePair> rgs = new ArrayList<NameValuePair>();
            rgs.add(new BasicNameValuePair("nomeja", strnomeja));
            rgs.add(new BasicNameValuePair("nama", strnama));
 
            JSONObject json = jsonParser.makeHttpRequest(url, "POST", rgs);
            
            // check log cat for response
            Log.d("Create Response", json.toString());
            
            try { 
            	success = json.getString("success");
                Intent i = new Intent(getApplicationContext(), MenuActivity.class);
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
                Toast.makeText(getApplicationContext(), "Regitrasi sukses", Toast.LENGTH_LONG).show();
 
            } else {
                Toast.makeText(getApplicationContext(), "Registrasi gagal", Toast.LENGTH_LONG).show();
            }
        }
 
    }
 
}
