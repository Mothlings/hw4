package com.example.hw4;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;


public class ActivityBplayer extends AppCompatActivity {
Button bSave;
    BPlayer bplayer;
    Intent intent;
    EditText tName,tNumber,tComment;
    MyApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bplayer);
        Log.d("settigs", "started");
        app = (MyApplication) getApplication();
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/CaviarDreams.ttf");


        bSave = (Button) findViewById(R.id.bSave);
        tName = (EditText) findViewById(R.id.tName);
        tNumber = (EditText) findViewById(R.id.tNumber);
        tComment = (EditText) findViewById(R.id.tComment);

        for (Match a:app.myMatches.matches) {
            if(app.getCurrentMatch() == a.getIdMatch()){
                bplayer = a.player;
            }
        }
        tName.setText(bplayer.getName());
        tNumber.setText(bplayer.getNumber());
        tComment.setText(bplayer.getComment());

        tName.setTypeface(custom_font);
        tNumber.setTypeface(custom_font);
        tComment.setTypeface(custom_font);
        bSave.setTypeface(custom_font);

        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               bplayer.setName(tName.getText().toString());
                bplayer.setNumber(tNumber.getText().toString());
                bplayer.setComment(tComment.getText().toString());
                for (Match a:app.myMatches.matches) {
                    if(app.getCurrentMatch() == a.getIdMatch()){
                        a.player = bplayer;
                    }
                }
                Intent returnIntent = new Intent();
                setResult(ActivityBplayer.RESULT_CANCELED, returnIntent);
                SaveGson();
                finish();
            }
        });

        // String editTextValue = simpleEditText.getText().toString();
    }
    public void SaveGson(){

        Gson gson = new Gson();
        String json = gson.toJson(app.myMatches.matches.get(0).player);
        System.out.println(json);

    }


}
