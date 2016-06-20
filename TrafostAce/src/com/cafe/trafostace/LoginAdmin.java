package com.cafe.trafostace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
 
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
 
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginAdmin extends Activity {
	
    Intent a;
    EditText username, password;
    String success;
    SessionManager session;
    Button login;
    
    private String url = "http://192.168.43.50/trafostace/login_admin.php";
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		session = new SessionManager(getApplicationContext());
		
		username = (EditText)findViewById(R.id.username);
		password = (EditText)findViewById(R.id.password);
		login = (Button)findViewById(R.id.btnLogin);
		
		
		login.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//url = "http://192.168.43.50/trafostace/login_admin.php?" + "username=" 
				//		+ username.getText().toString() + "&&password=" 
				//		+ password.getText().toString();
				
                if (username.getText().toString().trim().length() > 0
                        && password.getText().toString().trim().length() > 0) 
                {
                    new loginAdmin().execute();
                } 
                else
                {
                    Toast.makeText(getApplicationContext(), "Username/password masih salah", Toast.LENGTH_LONG).show();
                }
			}
		});
	}
		
		public class loginAdmin extends AsyncTask<String, String, String> 
	    {
	        ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();
	        ProgressDialog pDialog;
	 
	        @Override
	        protected void onPreExecute() {
	            // TODO Auto-generated method stub
	            super.onPreExecute();
	 
	            pDialog = new ProgressDialog(LoginAdmin.this);
	            pDialog.setMessage("Waiting Process...");
	            pDialog.setIndeterminate(false);
	            pDialog.setCancelable(true);
	            pDialog.show();
	        }
	        
	        @Override
	        protected String doInBackground(String... arg0) {
	            JSONParser jParser = new JSONParser();
	            //JSONObject json = jParser.getJSONFromUrl(url);
	            
	            String struser = username.getText().toString();
	            String strpass = password.getText().toString();
	 
	            List<NameValuePair> nvp = new ArrayList<NameValuePair>();
	            nvp.add(new BasicNameValuePair("username", struser));
	            nvp.add(new BasicNameValuePair("password", strpass));
	 
	            JSONObject json = jParser.makeHttpRequest(url, "POST", nvp);
	            //try { 
	            //	success = json.getString("success");
	            	
	            //	Log.e("error", "nilai sukses=" + success);
	            	 
	            //    JSONArray hasil = json.getJSONArray("btnLogin");
	 
	            //    if (success.equals("1")) {
	 
	            //        for (int i = 0; i < hasil.length(); i++) {
	 
	            //            JSONObject c = hasil.getJSONObject(i);
	 
	            //            String nama = c.getString("username").trim();
	            //            String email = c.getString("password").trim();
	            //            session.createLoginSession(nama, email);
	            //            Log.e("ok", " ambil data");
	 
	            //        }
	            //    } else {
	            //        Log.e("erro", "tidak bisa ambil data 0");
	            //    }
	                
	            //} catch (Exception e) {
	            	 // TODO: handle exception
	            //    Log.e("erro", "tidak bisa ambil data 1");
	            //}
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
	        
	        @Override
	        protected void onPostExecute(String result) {
	            super.onPostExecute(result);
	            pDialog.dismiss();
	            if (success.equals("1")) {
	                a = new Intent(LoginAdmin.this, MenuAdmin.class);
	                startActivity(a);
	                Toast.makeText(getApplicationContext(), "Berhasil Login !", Toast.LENGTH_LONG).show();
	                
	                finish();
	            } else {
	                Toast.makeText(getApplicationContext(), "Username/password masih salah", Toast.LENGTH_LONG).show();
	            }
	 
	        }
	 
	    }
	}