package com.example.anonym.atlas;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Anonym on 17-03-2018.
 */

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent=getIntent();
        AssetManager assetManager = getAssets();
        try {
            InputStream inputStream = assetManager.open("atlas.txt");
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while((line = in.readLine()) != null) {
                String IncomingPlace = line.trim();
                Log.i("IncomingPlace : " , IncomingPlace);
            }
        } catch (IOException e) {
            Toast toast = Toast.makeText(this, "Could not load the Places Directory.", Toast.LENGTH_LONG);
            toast.show();
        }
        
    }
}

