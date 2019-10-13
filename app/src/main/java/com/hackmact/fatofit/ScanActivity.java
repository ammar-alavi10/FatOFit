package com.hackmact.fatofit;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.Result;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import androidx.appcompat.app.AppCompatActivity;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    public class DownloadTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... urls) {
            String result="";
            URL url;
            HttpURLConnection urlConnection = null;

            try{
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while(data !=-1)
                {
                    char current = (char) data;
                    result+= current;
                    data = reader.read();
                }
                return result;

            }
            catch(Exception e)
            {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("fatOfit",s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                String nutritients = jsonObject.getString("nutrition");
                JSONObject nut = new JSONObject(nutritients);
                String calorie = nut.getString("calories");
//                Toast.makeText(ScanActivity.this,calorie,Toast.LENGTH_LONG).show();
                Log.d("FitOfat",calorie);
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();

                int maxc = pref.getInt("maxCal", -1);
                int caltn = pref.getInt("calTaken", -1);
                int calrm = pref.getInt("calRem",-1);
                int calbt = pref.getInt("calBurnt",-1);

                if( Integer.parseInt(calorie) < calrm )
                {
//                    Toast.makeText(ScanActivity.this,"You can eat this food item and you will have "+String.valueOf(calrm-Integer.parseInt(calorie))+" calories left",Toast.LENGTH_LONG).show();
                    calrm = calrm - Integer.parseInt(calorie);
                    editor.putInt("calRem",calrm);
                    caltn += Integer.parseInt(calorie);
                    editor.putInt("calTaken",caltn);
                    editor.commit();



                }
                else{
                    Toast.makeText(ScanActivity.this,"You should not eat this food item",Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }



        }
    }



    ZXingScannerView scannerView;
    private FocusHandler focusHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
        focusHandler = new FocusHandler(new Handler(), scannerView);
    }

    @Override
    public void handleResult(Result result) {

        String code = result.toString();
        Log.d("FitOFat",code);
        if(search(code)!=-1) {
            Toast.makeText(ScanActivity.this, code, Toast.LENGTH_LONG).show();
            DownloadTask task = new DownloadTask();
            task.execute("https://api.spoonacular.com/food/products/upc/028400645492?apiKey=888d39fabae54065a5cddb2ab0f50166&number=1");
        }
        onBackPressed();

    }

    private int search(String code) {
        if(code=="8902080365029")
            return 49;
        if(code=="90162602")
            return 45;
        if(code=="8902080105021")
            return 43;
        if(code=="8901491503037")
            return 175;
        else
            return -1;
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.setAutoFocus(false);
        scannerView.startCamera();
        focusHandler.start();

    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
        focusHandler.stop();
    }
}
