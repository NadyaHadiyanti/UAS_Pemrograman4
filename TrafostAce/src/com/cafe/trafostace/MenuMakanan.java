package com.cafe.trafostace;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MenuMakanan extends Activity {
	
	ListView list;
	JSONParser jParser = new JSONParser();
    ArrayList<ClassMenu> daftar_menu = new ArrayList<ClassMenu>();
	private String url = "http://192.168.43.50/trafostace/menu.php";
    JSONArray daftarMenu = null;
	
    // JSON Node names, ini harus sesuai yang di API
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MENU = "menu";
    public static final String TAG_ID_MN = "id_menu";
    public static final String TAG_NAMA_MN = "nama_menu";
    public static final String TAG_HARGA = "harga";
    public static final String TAG_STATUS = "status";
    
    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admakanan);
		
		list = (ListView) findViewById(R.id.listMenu);
		
		//jalankan ReadMhsTask
        ReadMenuTask m = (ReadMenuTask) new ReadMenuTask().execute();
        
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int urutan, long id) {
                //dapatkan data id, nama, nim mahasiswa dan simpan dalam variable string
                String idmenu = ((TextView) view.findViewById(R.id.id_menu)).getText().toString();
                String nama = ((TextView) view.findViewById(R.id.nama_menu)).getText().toString();
                String harga = ((TextView) view.findViewById(R.id.harga)).getText().toString();
                String status = ((TextView) view.findViewById(R.id.status)).getText().toString();

                //varible string tadi kita simpan dalam suatu Bundle, dan kita parse bundle tersebut bersama intent menuju kelas UpdateDeleteActivity
                Intent i = null;
                i = new Intent(MenuMakanan.this, UpdateDeleteActivity.class);
                Bundle b = new Bundle();
                b.putString("id_menu", idmenu);
                b.putString("nama_menu", nama);
                b.putString("harga", harga);
                b.putString("status", status);
                i.putExtras(b);
                startActivity(i);
            }
        });
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.action_settings) {
            Intent i = new Intent(MenuMakanan.this, InsertMenu.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
	
	class ReadMenuTask extends AsyncTask<String, Void, String> {
	    ProgressDialog pDialog;

	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	        pDialog = new ProgressDialog(MenuMakanan.this);
	        pDialog.setMessage("Mohon Tunggu..");
	        pDialog.setIndeterminate(true);
	        pDialog.setCancelable(true);
	        pDialog.show();
	    }

	    @Override
	    protected String doInBackground(String... sText) {
	        String returnResult = getMenuList(); //memanggil method getMhsList()
	        return returnResult;

	    }

	    @Override
	    protected void onPostExecute(String result) {
	        super.onPostExecute(result);
	        pDialog.dismiss();
	        if(result.equalsIgnoreCase("Exception Caught"))
	        {
	            Toast.makeText(MenuMakanan.this, "Unable to connect to server,please check your internet connection!", Toast.LENGTH_LONG).show();
	        }

	        if(result.equalsIgnoreCase("no results"))
	        {
	            Toast.makeText(MenuMakanan.this, "Data empty", Toast.LENGTH_LONG).show();
	        }
	        else
	        {
	        	 list.setAdapter(new MenuAdapter(MenuMakanan.this,daftar_menu)); //Adapter menampilkan data mahasiswa ke dalam listView
	        }
	    }
	    
	  //method untuk memperoleh daftar mahasiswa dari JSON
	    public String getMenuList()
	    {
	        ClassMenu tempMenu = new ClassMenu();
	        List<NameValuePair> params = new ArrayList<NameValuePair>();
	        try {
	            JSONObject json = jParser.makeHttpRequest(url,"POST", params);

	            int success = json.getInt(TAG_SUCCESS);
	            if (success == 1) { //Ada record Data (SUCCESS = 1)
	                //Getting Array of daftar_mhs
	                daftarMenu = json.getJSONArray(TAG_MENU);
	                // looping through All daftar_mhs
	                for (int i = 0; i < daftarMenu.length() ; i++){
	                    JSONObject c = daftarMenu.getJSONObject(i);
	                    tempMenu = new ClassMenu();
	                    tempMenu.setMenuId(c.getString(TAG_ID_MN));
	                    tempMenu.setMenuName(c.getString(TAG_NAMA_MN));
	                    tempMenu.setHarga(c.getString(TAG_HARGA));
	                    tempMenu.setStatus(c.getString(TAG_STATUS));
	                    daftar_menu.add(tempMenu);
	                }
	                return "OK";
	            }
	            else {
	                //Tidak Ada Record Data (SUCCESS = 0)
	                return "no results";
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	            return "Exception Caught";
	        }
	    }

	}
}
