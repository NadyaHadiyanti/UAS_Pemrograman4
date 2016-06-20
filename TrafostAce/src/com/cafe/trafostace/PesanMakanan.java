package com.cafe.trafostace;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class PesanMakanan extends Activity {
	
	ListView list;
	Button makan;
	JSONParser jParser = new JSONParser();
    ArrayList<ClassMenu> daftar_menu = new ArrayList<ClassMenu>();
	private String url = "http://192.168.43.50/trafostace/menu_makan.php";
    JSONArray daftarMenu = null;
    
    // JSON Node names, ini harus sesuai yang di API
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MENU = "menu";
    public static final String TAG_ID_MN = "id_menu";
    public static final String TAG_NAMA_MN = "nama_menu";
    public static final String TAG_HARGA = "harga";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_makan);
		list = (ListView) findViewById(R.id.listMakan);
		
		//jalankan ReadMhsTask
        ReadMenuTask m = (ReadMenuTask) new ReadMenuTask().execute();
        
		TextView makanan = (TextView)findViewById(R.id.txtMakan);
		makan = (Button)findViewById(R.id.btnKembali);
		
		makan.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

class ReadMenuTask extends AsyncTask<String, Void, String> {
    ProgressDialog pDialog;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(PesanMakanan.this);
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
            Toast.makeText(PesanMakanan.this, "Unable to connect to server,please check your internet connection!", Toast.LENGTH_LONG).show();
        }

        if(result.equalsIgnoreCase("no results"))
        {
            Toast.makeText(PesanMakanan.this, "Data empty", Toast.LENGTH_LONG).show();
        }
        else
        {
            list.setAdapter(new MenuAdapter(PesanMakanan.this,daftar_menu)); //Adapter menampilkan data mahasiswa ke dalam listView
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
