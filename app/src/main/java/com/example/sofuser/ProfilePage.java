package com.example.sofuser;

import android.annotation.SuppressLint;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ProfilePage extends AppCompatActivity {


    TextView userName,reputation,broz,silver,gold,location,date;
    ImageView profileImage;
    String name,rp,br,sl,gl,loc,dt,profile;

    Toolbar toolbar;


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);


        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("User Page");



        userName = (TextView) findViewById(R.id.txtName);
        reputation = (TextView) findViewById(R.id.txtReputation);
        broz = (TextView) findViewById(R.id.txtBronz);
        silver = (TextView) findViewById(R.id.txtSilver);
        gold = (TextView) findViewById(R.id.txtGold);
        location = (TextView) findViewById(R.id.txtLocation);
        date = (TextView) findViewById(R.id.txtOpen);
        profileImage = (ImageView) findViewById(R.id.profileImage);

        name = getIntent().getStringExtra("name");
        rp = getIntent().getStringExtra("reputation");
        br = getIntent().getStringExtra("bronz");
        sl = getIntent().getStringExtra("silver");
        gl = getIntent().getStringExtra("gold");
        dt = getIntent().getStringExtra("date");
        loc = getIntent().getStringExtra("location");
        profile=getIntent().getStringExtra("profileImage");


        userName.setText("Name: "+name);
        reputation.setText("Reputation: "+rp);
        broz.setText("Bronz: "+br);
        silver.setText("Silver: "+sl);
        gold.setText("Gold: "+gl);
        location.setText("Location: "+loc);
        Glide.with(ProfilePage.this).load(profile).into(profileImage);
        date.setText("Creationdate: "+dt);

    }
}
