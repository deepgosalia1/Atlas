package com.example.anonym.atlas;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Anonym on 17-03-2018.
 */

public class MainActivity extends AppCompatActivity{

    private String IncomingPlace,randomselectedplace,placeEntered,final_place_to_be_passed;
    Random random=new Random();
    ArrayList<String> placelist=new ArrayList<>();
    TextView display_random_place;
    EditText enterplace;

    public void generatePlace(View view){

        //taken a random place for current testing purposes.
        randomselectedplace=placelist.get(random.nextInt(placelist.size()));
        display_random_place.setText(randomselectedplace);
        final_place_to_be_passed=randomselectedplace;

    }
    public void redirectToMaps(View view){

        Intent intent1 = new Intent(MainActivity.this,MapsActivity.class);
        intent1.putExtra("trial",final_place_to_be_passed);
        startActivity(intent1);

    }

    public void submitplace(View view){

        placeEntered=enterplace.getText().toString();
        display_random_place.setText(placeEntered);
        final_place_to_be_passed=placeEntered;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent=getIntent();
        display_random_place=(TextView)findViewById(R.id.displayrandomplace);
        enterplace=findViewById(R.id.enterplace);
        AssetManager assetManager = getAssets();
        try {
            InputStream inputStream = assetManager.open("atlas.txt");
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while((line = in.readLine()) != null) {
                IncomingPlace = line.trim();
                placelist.add(IncomingPlace);
            }
        } catch (IOException e) {
            Toast toast = Toast.makeText(this, "Could not load the Places Directory.", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}

