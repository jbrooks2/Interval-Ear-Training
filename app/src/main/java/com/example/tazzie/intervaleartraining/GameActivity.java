package com.example.tazzie.intervaleartraining;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Random;

public class GameActivity extends ActionBarActivity {
    private TextView title_page, scoreText, result;
    private ProgressBar sound_progress;
    private Button sound_button, unison_button, minor2_button, major2_button,
            minor3_button, major3_button, perfect4_button, tritone_button,
            perfect5_button, minor6_button, major6_button, minor7_button,
            major7_button, octave_button, rev_ans_button, level_button;
    private Context context;
    private int score, total, level;
    private HashMap<Integer, MediaPlayer> mPlayerHash;
    private HashMap<Integer, Button> buttonHash;
    private HashMap<Integer, String> buttonTextHash;
    private MediaPlayer mPlayerNote1, mPlayerNote2;
    private final int max_interval = 13;
    private int answer_value, round_number, correct_num, attempt_num;
    private boolean[] available_interval = new boolean[max_interval];

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);
        try{
            Intent intent = getIntent();
            level = intent.getIntExtra("level", 0);
        }
        catch(Exception e){
            level = 1;
        }
        Toast.makeText(getApplicationContext(), level+" GAMEACTIVITY", Toast.LENGTH_LONG).show();
        sound_progress = (ProgressBar) findViewById(R.id.sound_progress);
        level_button = (Button) findViewById(R.id.level_button);
        level_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                // prevents new stacks of activity
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });
        hashSounds();
        hashButtons();
        hashButtonTexts();
        setButtonsVisible(level);
        generateInterval();
        sound_button = (Button) findViewById(R.id.sound_button);
        sound_progress.setIndeterminate(true);

        sound_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayerNote1.start();
                while (mPlayerNote1.isPlaying()) {
                }
                mPlayerNote2.start();
                while (mPlayerNote2.isPlaying()) {
                }

//                loadProgress(v);
            }
        });
    }

    public void loadProgress(View view){
        final int totalProgressTime = 100;
        final Thread t = new Thread() {
            @Override
            public void run() {
                int jumpTime = 0;
                boolean finishedPlaying = false;
                while (jumpTime < totalProgressTime) {

                    try {
                        mPlayerNote1.start();
                        while (mPlayerNote1.isPlaying()) {
                            sleep(200);
                            jumpTime += 5;
                            if (!mPlayerNote1.isPlaying())
                                jumpTime = 50;
                            sound_progress.setProgress(jumpTime);
                        }
                        mPlayerNote2.start();
                        while (mPlayerNote2.isPlaying()) {
                            sleep(200);
                            jumpTime += 5;
                            if (!mPlayerNote2.isPlaying())
                                jumpTime = 100;
                            sound_progress.setProgress(jumpTime);
                        }
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();
    }
    /*
    Index and the interval they correspond in the available_interval array
     0 unison
     1 octave
     2 minor 2nd
     3 major 2nd
     4 minor 3rd
     5 major 3rd
     6 perfect 4th
     7 tritone
     8 perfect 5th
     9 minor 6th
     10 major 6th
     11 minor 7th
     12 major 7th
    */

    public void hashSounds() {
        int number = 0;
        mPlayerHash = new HashMap<Integer, MediaPlayer>();
        mPlayerHash.put(number++, MediaPlayer.create(this, R.raw.c3));
        mPlayerHash.put(number++, MediaPlayer.create(this, R.raw.c_sharp3));
        mPlayerHash.put(number++, MediaPlayer.create(this, R.raw.d3));
        mPlayerHash.put(number++, MediaPlayer.create(this, R.raw.d_sharp3));
        mPlayerHash.put(number++, MediaPlayer.create(this, R.raw.e3));
        mPlayerHash.put(number++, MediaPlayer.create(this, R.raw.f3));
        mPlayerHash.put(number++, MediaPlayer.create(this, R.raw.f_sharp3));
        mPlayerHash.put(number++, MediaPlayer.create(this, R.raw.g3));
        mPlayerHash.put(number++, MediaPlayer.create(this, R.raw.g_sharp3));
        mPlayerHash.put(number++, MediaPlayer.create(this, R.raw.a3));
        mPlayerHash.put(number++, MediaPlayer.create(this, R.raw.a_sharp3));
        mPlayerHash.put(number++, MediaPlayer.create(this, R.raw.b3));

//        mPlayerHash.put(number++, MediaPlayer.create(this, R.raw.c4));
//        mPlayerHash.put(number++, MediaPlayer.create(this, R.raw.c_sharp4));
//        mPlayerHash.put(number++, MediaPlayer.create(this, R.raw.d4));
//        mPlayerHash.put(number++, MediaPlayer.create(this, R.raw.d_sharp4));
//        mPlayerHash.put(number++, MediaPlayer.create(this, R.raw.e4));
//        mPlayerHash.put(number++, MediaPlayer.create(this, R.raw.f4));
//        mPlayerHash.put(number++, MediaPlayer.create(this, R.raw.f_sharp4));
//        mPlayerHash.put(number++, MediaPlayer.create(this, R.raw.g4));
//        mPlayerHash.put(number++, MediaPlayer.create(this, R.raw.g_sharp4));
//        mPlayerHash.put(number++, MediaPlayer.create(this, R.raw.a4));
//        mPlayerHash.put(number++, MediaPlayer.create(this, R.raw.a_sharp4));
//        mPlayerHash.put(number++, MediaPlayer.create(this, R.raw.b4));
//
//        mPlayerHash.put(number++, MediaPlayer.create(this, R.raw.c5));
//        mPlayerHash.put(number++, MediaPlayer.create(this, R.raw.c_sharp5));
//        mPlayerHash.put(number++, MediaPlayer.create(this, R.raw.d5));
//        mPlayerHash.put(number++, MediaPlayer.create(this, R.raw.d_sharp5));
//        mPlayerHash.put(number++, MediaPlayer.create(this, R.raw.e5));
//        mPlayerHash.put(number++, MediaPlayer.create(this, R.raw.f5));
//        mPlayerHash.put(number++, MediaPlayer.create(this, R.raw.f_sharp5));
//        mPlayerHash.put(number++, MediaPlayer.create(this, R.raw.g5));
//        mPlayerHash.put(number++, MediaPlayer.create(this, R.raw.g_sharp5));
//        mPlayerHash.put(number++, MediaPlayer.create(this, R.raw.a5));
//        mPlayerHash.put(number++, MediaPlayer.create(this, R.raw.a_sharp5));
//        mPlayerHash.put(number++, MediaPlayer.create(this, R.raw.b5));
    }

    public void hashButtons(){
        int number = 0;
        buttonHash = new HashMap<Integer, Button>();
        buttonHash.put(number++, unison_button);    // 0 unison
        buttonHash.put(number++, octave_button);    // 1 octave
        buttonHash.put(number++, minor2_button);    // 2 minor 2nd
        buttonHash.put(number++, major2_button);    // 3 major 2nd
        buttonHash.put(number++, minor3_button);    // 4 minor 3rd
        buttonHash.put(number++, major3_button);    // 5 major 3rd
        buttonHash.put(number++, perfect4_button);  // 6 perfect 4th
        buttonHash.put(number++, tritone_button);   // 7 tritone
        buttonHash.put(number++, perfect5_button);  // 8 perfect 5th
        buttonHash.put(number++, minor6_button);    // 9 minor 6th
        buttonHash.put(number++, major6_button);    // 10 major 6th
        buttonHash.put(number++, minor7_button);    // 11 minor 7th
        buttonHash.put(number++, major7_button);    // 12 major 7th
    }

    public void hashButtonTexts(){
        int number = 0;
        buttonTextHash = new HashMap<Integer, String>();
        buttonTextHash.put(number++, "UNISON");         // 0 unison
        buttonTextHash.put(number++, "OCTAVE");         // 1 octave
        buttonTextHash.put(number++, "MINOR 2ND");      // 2 minor 2nd
        buttonTextHash.put(number++, "MAJOR 2ND");      // 3 major 2nd
        buttonTextHash.put(number++, "MINOR 3RD");      // 4 minor 3rd
        buttonTextHash.put(number++, "MAJOR 3RD");      // 5 major 3rd
        buttonTextHash.put(number++, "PERFECT 4TH");    // 6 perfect 4th
        buttonTextHash.put(number++, "TRITONE");        // 7 tritone
        buttonTextHash.put(number++, "PERFECT 5TH");    // 8 perfect 5th
        buttonTextHash.put(number++, "MINOR 6TH");      // 9 minor 6th
        buttonTextHash.put(number++, "MAJOR 6TH");      // 10 major 6th
        buttonTextHash.put(number++, "MINOR 7TH");      // 11 minor 7th
        buttonTextHash.put(number++, "MAJOR 7TH");      // 12 major 7th
    }

    public void generateInterval(){
        Random r = new Random();
        // First note that is going to be played
        int firstNote= 0;//r.nextInt(12);

        // Generate the second note. The distance can't be more than 13
        int secondNote = GenerateSecondNote() + firstNote;

        // play Unison if the notes go out of bound
        if(firstNote + secondNote > 11){
            secondNote = 0;
        }

        answer_value = secondNote - firstNote;
        //Add code that plays the notes
        mPlayerNote1 = mPlayerHash.get(firstNote);
        Toast.makeText(getApplicationContext(), firstNote+" Note1", Toast.LENGTH_LONG).show();
        mPlayerNote2 = mPlayerHash.get(secondNote);
        Toast.makeText(getApplicationContext(), secondNote +" Note2", Toast.LENGTH_LONG).show();
    }

    public int GenerateSecondNote(){
        Random r = new Random();
        do {
            int n = r.nextInt(12);
            if(available_interval[n]){
                return n;
            }
        } while (true);
    }

    private void setButtonsVisible(int level){
        if(level >= 0){
            /*************************************
            * lv1: Unison = 0
            * 	   Perfect 4th = 5
            *      Perfect 5th = 7
            ***************************************/
            available_interval[0] = true;
            available_interval[5] = true;
            available_interval[7] = true;

            unison_button = (Button) findViewById(R.id.unison_button);
            unison_button.setVisibility(View.VISIBLE);
            unison_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAnswer(0, unison_button);
                }
            });

            // Change id of this button in the xml file to perfect4_button
            perfect4_button = (Button) findViewById(R.id.perfect4_button);
            perfect4_button.setVisibility(View.VISIBLE);
            perfect4_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAnswer(5, perfect4_button);
                }
            });

            perfect5_button = (Button) findViewById(R.id.perfect5_button);
            perfect5_button.setVisibility(View.VISIBLE);
            perfect5_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAnswer(7, perfect5_button);
                }
            });
        }

        if(level >= 2){
            /***************************
             * lv2: Major 2nd = 2
             *      Major 3rd = 4
             ****************************/
            available_interval[2] = true;
            available_interval[4] = true;

            major2_button = (Button) findViewById(R.id.major2_button);
            major2_button.setVisibility(View.VISIBLE);
//            final MediaPlayer mPlayer_major2 = MediaPlayer.create(this, R.raw.c_major2);
//            major2_button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mPlayer_major2.start();
//                }
//            });

            major3_button = (Button) findViewById(R.id.major3_button);
            major3_button.setVisibility(View.VISIBLE);
//            final MediaPlayer mPlayer_major3 = MediaPlayer.create(this, R.raw.c_major3);
//            major3_button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mPlayer_major3.start();
//                }
//            });
        }

        if(level >= 3){
            /***************************
             * lv3: Major 6th 9
             *      Major 7th 11
             *      Octave    12
             ***************************/
            available_interval[9] = true;
            available_interval[11] = true;
            available_interval[12] = true;

            major6_button = (Button) findViewById(R.id.major6_button);
            major6_button.setVisibility(View.VISIBLE);
//            final MediaPlayer mPlayer_major6 = MediaPlayer.create(this, R.raw.c_major6);
//            major6_button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mPlayer_major6.start();
//                }
//            });

            major7_button = (Button) findViewById(R.id.major7_button);
            major7_button.setVisibility(View.VISIBLE);
//            final MediaPlayer mPlayer_major7 = MediaPlayer.create(this, R.raw.c_major7);
//            major7_button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mPlayer_major7.start();
//                }
//            });

            octave_button = (Button) findViewById(R.id.octave_button);
            octave_button.setVisibility(View.VISIBLE);
//            final MediaPlayer mPlayer_octave = MediaPlayer.create(this, R.raw.c_octave);
//            octave_button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mPlayer_octave.start();
//                }
//            });
        }

        if(level >= 4){
            /******************************
             * lv4: Minor 2nd 1
             *      Minor 3rd 3
             ******************************/
            available_interval[1] = true;
            available_interval[3] = true;

            minor2_button = (Button) findViewById(R.id.minor2_button);
            minor2_button.setVisibility(View.VISIBLE);
//            final MediaPlayer mPlayer_minor2 = MediaPlayer.create(this, R.raw.c_minor2);
//            minor2_button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mPlayer_minor2.start();
//                }
//            });

            minor3_button = (Button) findViewById(R.id.minor3_button);
            minor3_button.setVisibility(View.VISIBLE);
//            final MediaPlayer mPlayer_minor3 = MediaPlayer.create(this, R.raw.c_minor3);
//            minor3_button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mPlayer_minor3.start();
//                }
//            });
        }

        if(level >= 5){
            /***************************************
             *      Tritone(Augmented 4th) = 6
             * lv5: Minor 6th = 8
             *      Minor 7th = 10
             ***************************************/
            available_interval[6] = true;
            available_interval[8] = true;
            available_interval[10] = true;

            minor6_button = (Button) findViewById(R.id.minor6_button);
            minor6_button.setVisibility(View.VISIBLE);
//            final MediaPlayer mPlayer_minor6 = MediaPlayer.create(this, R.raw.c_minor6);
//            minor6_button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mPlayer_minor6.start();
//                }
//            });

            minor7_button = (Button) findViewById(R.id.minor7_button);
            minor7_button.setVisibility(View.VISIBLE);
//            final MediaPlayer mPlayer_minor7 = MediaPlayer.create(this, R.raw.c_minor7);
//            minor7_button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mPlayer_minor7.start();
//                }
//            });
        }
    }

    public void checkAnswer(int button_value, Button button){
        if (button_value == answer_value) {
            Toast.makeText(getApplicationContext(), "Correct!!", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "WRONG!!!!", Toast.LENGTH_LONG).show();
//            int wrongAnswer = getResources().getColor(R.color.wrong_answer);
//            button.setBackgroundColor(wrongAnswer);
        }
//        findAnswerButton();
    }

    public void findAnswerButton(){
        int correctAnswer = getResources().getColor(R.color.correct_answer);
        buttonHash.get(answer_value).setBackgroundColor(correctAnswer);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}