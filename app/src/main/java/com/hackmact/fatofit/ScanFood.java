package com.hackmact.fatofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ScanFood extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_food);
    }

    public void scanbarcode(View view) {
        startActivity(new Intent(ScanFood.this,ScanActivity.class));
    }

    public void scanfood(View view) {
    }
}
