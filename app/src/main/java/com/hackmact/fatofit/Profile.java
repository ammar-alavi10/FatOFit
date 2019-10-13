package com.hackmact.fatofit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Profile extends AppCompatActivity {

    private FirebaseAuth mAuth;
    DatabaseReference reff;

    TextView tvcalrem , tvcaltaken , tvmaxcal , tvcalburnt, tvweeks, tvgoal;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        tvcalburnt = findViewById(R.id.calburnt);
        tvcaltaken = findViewById(R.id.caltaken);
        tvcalrem = findViewById(R.id.calrem);
        tvmaxcal = findViewById(R.id.maxcal);
        progressBar = findViewById(R.id.progressBar);
        tvweeks = findViewById(R.id.weekstv);
        tvgoal = findViewById(R.id.goalweighttv);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

        int maxc = pref.getInt("maxCal", -1);
        int caltn = pref.getInt("calTaken", -1);
        int calrm = pref.getInt("calRem",-1);
        int calbt = pref.getInt("calBurnt",-1);
        int weeks = pref.getInt("noofweeks",-1);
        int goalw = pref.getInt("Desired",-1);
        float progress = ((float)calrm/(float)maxc);


        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+1:00"));
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("HH:mm a");
// you can get seconds by adding  "...:ss" to it
        date.setTimeZone(TimeZone.getTimeZone("GMT+1:00"));

        String localTime = date.format(currentLocalTime);
        String t = localTime.substring(0,2);
        int hour = Integer.parseInt(t);
        if(hour<9)
        {
            maxc =(int) ((maxc/5.0)*1.5);
        }
        else if(hour<12)
        {
            maxc =(int) ((maxc/5.0)*1.2);
        }
        else if(hour<17)
        {
            maxc =(int) ((maxc/5.0)*1);
        }
        else if(hour<21)
        {
            maxc =(int) ((maxc/5.0)*0.8);
        }
        else
        {
            maxc =(int) ((maxc/5.0)*0.5);
        }




        tvcaltaken.setText(Integer.toString(caltn));
        tvcalburnt.setText(Integer.toString(calbt));
        tvmaxcal.setText(Integer.toString(maxc));
        tvcalrem.setText(Integer.toString(maxc-caltn+calbt));
        tvweeks.setText("No of weeks required : "+Integer.toString(weeks));
        tvweeks.setText("Goal Weight : "+Integer.toString(goalw));
        progressBar.setProgress((int)(progress*100));

//        reff= FirebaseDatabase.getInstance().getReference();
//
//        reff.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                updateUI(dataSnapshot);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });



    }
//    public void updateUI(DataSnapshot dataSnapshot)
//    {
//        for(DataSnapshot ds:dataSnapshot.getChildren()) {
//            User unifo = new User();
//            unifo.setHeight(ds.child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).getValue(User.class).getHeight());
//            unifo.setWeight(ds.child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).getValue(User.class).getWeight());
//            unifo.setGender(ds.child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).getValue(User.class).getGender());
//            unifo.setAge(ds.child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).getValue(User.class).getAge());
//
//
//
//        }
//    }

}
