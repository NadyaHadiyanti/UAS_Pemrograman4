package com.cafe.trafostace;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Afifatul on 6/18/2015.
 */
public class UpdateDeleteActivity extends Activity {

    JSONParser jParser = new JSONParser();
    String url_update = "http://192.168.43.50/trafostace/update_menu.php";
    String url_delete = "http://192.168.43.50/trafostace/delete_menu.php";
    // JSON Node names, ini harus sesuai yang di API
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MENU = "menu";
    public static final String TAG_ID_MN = "id_menu";
    public static final String TAG_NAMA_MN = "nama_menu";
    public static final String TAG_HARGA = "harga";
    public static final String TAG_STATUS = "status";

    EditText nama, harga, status;
    TextView TxtViewId;
    Button update, delete;
    String strnama, strharga, strid, strstatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatedelete);

        nama = (EditText) findViewById(R.id.textNama);
        harga = (EditText) findViewById(R.id.textHarga);
        status = (EditText) findViewById(R.id.textStatus);
        TxtViewId = (TextView) findViewById(R.id.id_menu);

        update = (Button) findViewById(R.id.btnUpdate);
        delete = (Button) findViewById(R.id.btnDelete);

        //menangkap bundle yang telah di-parsed dari MainActivity
        Bundle b = getIntent().getExtras();
        String isi_id_menu = b.getString("id_menu");
        String isi_nama_menu = b.getString("nama_menu");
        String isi_harga = b.getString("harga");
        String isi_status = b.getString("status");
        //meng-set bundle tersebut
        nama.setText(isi_nama_menu);
        harga.setText(isi_harga);
        status.setText(isi_status);
        TxtViewId.setText(isi_id_menu);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strid = TxtViewId.getText().toString();
                strnama = nama.getText().toString();
                strharga = harga.getText().toString();
                strstatus = status.getText().toString();
                new UpdateMenuTask().execute();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strid = TxtViewId.getText().toString();
                new DeleteMenuTask().execute();
            }
        });
    }

    class UpdateMenuTask extends AsyncTask<String, Void, String>
    {
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(UpdateDeleteActivity.this);
            pDialog.setMessage("Mohon Tunggu..");
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... sText) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(TAG_ID_MN, strid));
            params.add(new BasicNameValuePair(TAG_NAMA_MN, strnama));
            params.add(new BasicNameValuePair(TAG_HARGA, strharga));
            params.add(new BasicNameValuePair(TAG_STATUS, strstatus));

            try {
                JSONObject json = jParser.makeHttpRequest(url_update,"POST", params);

                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {

                    return "OK";
                }
                else {

                    return "FAIL";

                }

            } catch (Exception e) {
                e.printStackTrace();
                return "Exception Caught";
            }

        }

        @Override
        protected void onPostExecute(String result) {

            super.onPostExecute(result);
            pDialog.dismiss();

            if(result.equalsIgnoreCase("Exception Caught"))
            {

                Toast.makeText(UpdateDeleteActivity.this, "Unable to connect to server,please check your internet connection!", Toast.LENGTH_LONG).show();
            }

            if(result.equalsIgnoreCase("FAIL"))
            {
                Toast.makeText(UpdateDeleteActivity.this, "Fail.. Try Again", Toast.LENGTH_LONG).show();
            }

            else {
                Intent i = null;
                i = new Intent(UpdateDeleteActivity.this, MenuMakanan.class);
                startActivity(i);
            }

        }
    }


    class DeleteMenuTask extends AsyncTask<String, Void, String>
    {
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(UpdateDeleteActivity.this);
            pDialog.setMessage("Mohon Tunggu..");
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... sText) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(TAG_ID_MN, strid));

            try {
                JSONObject json = jParser.makeHttpRequest(url_delete,"POST", params);

                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {

                    return "OK";
                }
                else {

                    return "FAIL";

                }

            } catch (Exception e) {
                e.printStackTrace();
                return "Exception Caught";
            }

        }

        @Override
        protected void onPostExecute(String result) {

            super.onPostExecute(result);
            pDialog.dismiss();

            if(result.equalsIgnoreCase("Exception Caught"))
            {

                Toast.makeText(UpdateDeleteActivity.this, "Unable to connect to server,please check your internet connection!", Toast.LENGTH_LONG).show();
            }

            if(result.equalsIgnoreCase("FAIL"))
            {
                Toast.makeText(UpdateDeleteActivity.this, "Fail.. Try Again", Toast.LENGTH_LONG).show();
            }

            else {
                Intent i = null;
                i = new Intent(UpdateDeleteActivity.this, MenuMakanan.class);
                startActivity(i);
            }

        }
    }
}