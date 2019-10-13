package com.hackmact.fatofit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;



public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    DatabaseReference reff;
    TextView tvheight,tvweight,tvid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        if(mAuth.getCurrentUser()!=null)
//        {
//            FirebaseUser user = mAuth.getCurrentUser();
//            updateUI(user);
//        }
        tvid = findViewById(R.id.textViewidval);
        tvheight = findViewById(R.id.textViewheightval);
        tvweight = findViewById(R.id.textViewweightval);


        reff= FirebaseDatabase.getInstance().getReference();

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                updateUI(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void updateUI(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds:dataSnapshot.getChildren()) {
            User unifo = new User();
            unifo.setId(ds.child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).getValue(User.class).getId());
            unifo.setHeight(ds.child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).getValue(User.class).getHeight());
            unifo.setWeight(ds.child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).getValue(User.class).getWeight());
            unifo.setGender(ds.child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).getValue(User.class).getGender());
            unifo.setAge(ds.child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).getValue(User.class).getAge());
            tvid.setText(unifo.getId());
            tvheight.setText(unifo.getHeight());
            tvweight.setText(unifo.getWeight());

            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
            SharedPreferences.Editor editor = pref.edit();

            float height = Integer.parseInt(unifo.getHeight());
            int weight = Integer.parseInt(unifo.getHeight());
            int targetweight,desiredweight;
            //calc maxc
            int maxc = (int)(10*Integer.parseInt(unifo.getWeight())+6.25*Integer.parseInt(unifo.getHeight())-5*Integer.parseInt(unifo.getAge()));
            if(unifo.getGender()=="M")
            {
                maxc=maxc+5;
                targetweight = (int)(22*(height/100)*(height/100));
                desiredweight = targetweight - weight;
                if(desiredweight<0)
                    maxc+=500;
                else
                    maxc-=500;
            }
            else
            {
                maxc-=161;
                targetweight = (int)(22*(height/100)*(height/100));
                desiredweight = targetweight - weight;
                if(desiredweight<0)
                    maxc+=500;
                else
                    maxc-=500;
            }
            float weeks = (float)desiredweight/(float)0.45;
            int noweeks = (int)weeks;

            editor.putInt("Desired",targetweight);
            editor.putInt("noofweeks",noweeks);


            editor.putInt("maxCal", maxc);
            editor.putInt("calRem", maxc);

            editor.putInt("calTaken", 0);
            editor.putInt("calBurnt",0);
            editor.commit();


        }

    }

    public void toscanactivity(View view) {
        Intent intent = new Intent(MainActivity.this,ScanFood.class);
        startActivity(intent);


    }

    public void todiary(View view) {
    }

    public void toprofile(View view) {
        Intent integer = new Intent(MainActivity.this,Profile.class);
        startActivity(integer);

    }
}
