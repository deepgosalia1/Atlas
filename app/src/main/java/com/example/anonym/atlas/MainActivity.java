package com.example.anonym.atlas;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
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


public class MainActivity extends AppCompatActivity{

    private String IncomingPlace,randomselectedplace,placeEntered,final_place_to_be_passed;
    Random random=new Random();
    ArrayList<String> placelist=new ArrayList<String>();
    ArrayList<String> temporary=new ArrayList<String>();
    ArrayList<String> turnlist=new ArrayList<String>();
    ArrayList<String> caplist=new ArrayList<String>();
    TextView comp_place;
    TextView user_place;
    EditText enterplace;
    int firstuse=0;
    Character x = '#';
    private boolean userTurn = false;

    public void redirectToMaps(View view) {
        if (user_place.getText().toString() == "" && comp_place.getText().toString() == "") {

            Toast.makeText(this, "Please enter the place name first, in order to look up on the map", Toast.LENGTH_LONG).show();

        } else {
            Intent intent1 = new Intent(MainActivity.this, MapsActivity.class);
            intent1.putExtra("trial", final_place_to_be_passed);
            startActivity(intent1);
        }
    }

    public void submitplace(View view) {
        if (enterplace.getText().toString() == "") {
            Toast.makeText(this, "Please enter a name of the place first.", Toast.LENGTH_LONG).show();
        } else {
            placeEntered = enterplace.getText().toString().toLowerCase();
            Character temp = placeEntered.charAt(0);
            Log.d("place enter index 0 : ", temp.toString());
            Log.d("place enter index 0 : ", x.toString());
            Log.d("place enter index 0 : ", String.valueOf(firstuse));

            if ((turnlist.contains(placeEntered) && firstuse == 1 || (turnlist.contains(placeEntered) && temp == x))) {
                int index = turnlist.indexOf(placeEntered);
                String place = caplist.get(index);
                user_place.setText(place);
                final_place_to_be_passed = placeEntered;
                turnlist.remove(placeEntered);
                caplist.remove(place);
                firstuse=0;
                computerturn();
            } else if (!turnlist.contains(placeEntered) || temp != x) {
                Toast.makeText(this, "Computer challenged, place invalid or repeated, Hence Computer Wins!", Toast.LENGTH_LONG).show();
                onStart(null);
            }

        }
    }

    public void computerturn(){
        int flag=0;
        Character initial=null;
        String input;
        Log.d("input of user : ",user_place.getText().toString());
        if (firstuse==1)
        {
            int no = random.nextInt(caplist.size())-1;
            String temp = caplist.get(no);
            comp_place.setText(temp);
            Log.d("Output of computer : ",temp);
            enterplace.setText("");
            turnlist.remove(no);
            caplist.remove(no);
            x = temp.charAt(temp.length()-1);
            enterplace.setHint("Enter place from "+x.toString()+"... ");
            firstuse=0;
        }
        else
        {
            input = user_place.getText().toString().toLowerCase();
            initial = input.charAt(input.length()-1);
            String result;
            int low=0;
            int high = caplist.size()-1;

            while(low<=high)
            {
                int mid=(low+high)/2;
                String getword = turnlist.get(mid);
                Log.d("Info: len of getword",String.valueOf(getword.length()));
                if(mid==turnlist.size())
                    break;
                Character test = turnlist.get(mid).toLowerCase().charAt(0);
                Log.d("Info:Initial of place",test.toString());
                if(test==initial)
                {
                    result = caplist.get(mid);
                    final_place_to_be_passed = result;
                    comp_place.setText(result);
                    Log.d("Output : ",result);
                    enterplace.setText("");
                    turnlist.remove(mid);
                    caplist.remove(mid);
                    x = result.charAt(result.length()-1);
                    enterplace.setHint("Enter a place from "+x.toString());
                    flag=1;
                    break;
                }
                else if(test < initial) {
                    low = mid + 1;
                    Log.d("Info: low val ", String.valueOf(low));
                }
                else {
                    high = mid - 1;
                    Log.d("Info: high val ", String.valueOf(high));
                }
            }
            if(flag==0){
                String print = String.valueOf(initial);
                Toast.makeText(this,"Computer couldn't find any place from"+print.toUpperCase()+"You win!!",Toast.LENGTH_LONG).show();
                onStart(null);
            }
        }
        Log.d("Placelist + temp size",String.valueOf(placelist.size()) +"+"+String.valueOf(temporary.size()));
        Log.d("Caplist + turnlist size",String.valueOf(caplist.size()) +"+"+String.valueOf(turnlist.size()));
    }

    public boolean onStart(View view) {
        userTurn = random.nextBoolean();
        user_place.setText("");
        comp_place.setText("");
        firstuse=1;
        caplist.addAll(placelist);
        turnlist.addAll(temporary);
        enterplace.setText("");
        enterplace.setHint("Enter a place here");
        if (userTurn) {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "User Turn",Toast.LENGTH_SHORT).show();
                }
            }, 2000);
        } else {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "Computer Turn",Toast.LENGTH_SHORT).show();
                }
            }, 2000);
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent=getIntent();
        comp_place=(TextView)findViewById(R.id.comp_place);
        user_place=(TextView)findViewById(R.id.user_place);
        enterplace=findViewById(R.id.enterplace);
        AssetManager assetManager = getAssets();
        try {
            InputStream inputStream = assetManager.open("atlas.txt");
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while((line = in.readLine()) != null) {
                IncomingPlace = line.trim();
                placelist.add(IncomingPlace);
                temporary.add(IncomingPlace.toLowerCase());
            }
        } catch (IOException e) {
            Toast.makeText(this, "Could not load the 'Places' Directory.", Toast.LENGTH_LONG).show();
        }
        onStart(null);
    }
}

