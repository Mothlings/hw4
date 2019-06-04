package com.example.hw4;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ActivityTimeSlot extends AppCompatActivity implements View.OnClickListener, Serializable, AdapterView.OnItemSelectedListener {

    //final String uuid = UUID.randomUUID().toString().replace("-", "");
    Button bStart, bStop, bSettings, bNew, bRecycleView;
    TextView tStart, tStop, tList;
    File file;
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    CountDownTimer countDownTimer;
    long timeLeftInMillecedonds = 600000; // 600,000 = 10 minutes
    boolean timerRunning;
    SharedPreferences sp;
    String idApp;
    private static final DateFormat hh = new SimpleDateFormat("hh");
    private static final DateFormat mm = new SimpleDateFormat("mm");
    private static final DateFormat ss = new SimpleDateFormat("ss");
    String startTime;
    String endTime;
    Match match;
    Spinner spinner;
  //  Intent intent;
    public MyApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_slot);
        SharedPreferences userDetails = getSharedPreferences("idApp", MODE_PRIVATE);
        SharedPreferences.Editor edit = userDetails.edit();
        if (userDetails.getString("idApp", "")== "" ) {
            edit.putString("idApp", UUID.randomUUID().toString().replace("-", ""));
            edit.commit();
        }
        idApp = userDetails.getString("idApp", "");
        tStart = (TextView) findViewById(R.id.tStart);
        tStop = (TextView) findViewById(R.id.tStop);
        tList = (TextView) findViewById(R.id.tList);


        spinner = findViewById(R.id.spinner);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/CaviarDreams.ttf");

        bNew = (Button) findViewById(R.id.bNew);
        bRecycleView = (Button) findViewById(R.id.bRecycleView);
        bStart = (Button) findViewById(R.id.bStart);
        bStop = (Button) findViewById(R.id.bStop);
        bSettings = (Button) findViewById(R.id.bSettings);
        bNew.setOnClickListener(this);
        bRecycleView.setOnClickListener(this);
        bStart.setOnClickListener(this);
        bStop.setOnClickListener(this);
        bSettings.setOnClickListener(this);

        bRecycleView.setTypeface(custom_font);
        bNew.setTypeface(custom_font);
        bStart.setTypeface(custom_font);
        bStop.setTypeface(custom_font);
        bSettings.setTypeface(custom_font);


        tList.setTypeface(custom_font);
        tStop.setTypeface(custom_font);
        tStart.setTypeface(custom_font);
       // intent = new Intent(); //might need to add parameters
       /* if(getIntent().getSerializableExtra("MyPlayer") == null){
            bPlayer = new BPlayer();
            bPlayer.setIdApp(idApp);
            intent.putExtra("MyPlayer", bPlayer);
        }
        else{
            bPlayer = (BPlayer)getIntent().getSerializableExtra("MyPlayer");
        }*/

        app = (MyApplication) getApplication();
        app.readFromFile();
        ArrayAdapter<Match> adapter = new ArrayAdapter<Match>(this, android.R.layout.simple_spinner_item, app.myMatches.matches);
        if(app.myMatches.matches.size() == 0){
            Match m1 = new Match();
            app.myMatches.matches.add(m1);
            app.currentMatch = m1.getIdMatch();
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                match = (Match) parent.getSelectedItem();
                // CHANGE EVERYTHING ON SCREEN
                tList.setText("");
                app.currentMatch =  match.getIdMatch();
                for (TimeSlot a:match.player.timeSlotList)
                {

                    tList.setText("TimeSlot: " + a.idTimeSlot +" Player: " + a.idBPlayer + " Start: " + a.startTime + " End: " + a.endTime + "\n" + tList.getText());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void NewMatch(){
        Match m = new Match();
        app.myMatches.matches.add(m);
        match = m;
        tList.setText("");
        spinner.setSelection(app.myMatches.matches.size()-1);

        app.saveToFile();
      //
    }

/*


    private Gson getGson() {
        if (gson == null)
            gson = new GsonBuilder().setPrettyPrinting().create();
        return gson;
    }
    private File getFile() {
        if (file == null) {
            File filesDir = getFilesDir();
            file = new File(filesDir, "matches.json");
        }
        Log.i(TAG, file.getPath());
        return file;
    }


    public void SaveGson() {
     //   Gson gson = new Gson();
       // String json = gson.toJson(app.matches);
     //   System.out.println(json);
        String s = getGson().toJson(app.matches);





    }

    public void LoadGson(){

    }
*/

private boolean isExternalStorageWritable(){
    if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
        Log.i("State","Yes, it is writable!");
        return true;
    }else{
        return false;
    }
}

    public void writeFile(View v){
        if(isExternalStorageWritable() && checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            app.save();

        }else{
            Toast.makeText(this, "Cannot Write to External Storage.", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean checkPermission(String permission){
        int check = ContextCompat.checkSelfPermission(this, permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }


    public void StartTimer(){
        bStop.setEnabled(true);
        bStart.setEnabled(false);
        Date date = new Date();
        startTime =Integer.toString(Integer.parseInt(hh.format(date)) * 60 * 60 + Integer.parseInt(mm.format(date)) * 60 + Integer.parseInt(ss.format(date)));
        tStart.setText(startTime);
    }

    public void StopTimer(){
        bStop.setEnabled(false);
        bStart.setEnabled(true);
        Date date = new Date();
        endTime = Integer.toString(Integer.parseInt(hh.format(date)) * 60 * 60 + Integer.parseInt(mm.format(date)) * 60 + Integer.parseInt(ss.format(date)));
        tStop.setText(endTime);
        TimeSlot t = new TimeSlot();

        SharedPreferences userDetails = getSharedPreferences("idApp", MODE_PRIVATE);
        SharedPreferences.Editor edit = userDetails.edit();
        t.setIdApp(userDetails.getString("idApp", ""));
        t.setIdTimeSlot(UUID.randomUUID().toString().replace("-", ""));
        //t.setIdBPlayer(bPlayer.getIdBPlayer());
        t.setIdBPlayer("sdfsdfsdf");
        t.setStartTime(Integer.parseInt(startTime));
        t.setEndTime(Integer.parseInt(endTime));
        match.player.timeSlotList.add(t);
        tList.setText("");
        for (TimeSlot a:match.player.timeSlotList)
        {
            tList.setText("TimeSlot: " + a.idTimeSlot +" Player: " + a.idBPlayer + " Start: " + a.startTime + " End: " + a.endTime + "\n" + tList.getText());


        }
     //   SaveGson();

    }

public void RecycleViewActiv(){

}
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bStart:
                StartTimer();
                break;
            case R.id.bStop:
                StopTimer();
                break;
            case R.id.bSettings:
                Intent i = new Intent(this,ActivityBplayer.class);
                startActivityForResult(i, 1);
               // startActivity(new Intent(ActivityTimeSlot.this, ActivityBplayer.class));

                break;
            case R.id.bNew:
                NewMatch();
                break;
            case R.id.bRecycleView:
                Intent j = new Intent(this,MyRecyclerView.class);
                startActivityForResult(j, 1);
               RecycleViewActiv();
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}