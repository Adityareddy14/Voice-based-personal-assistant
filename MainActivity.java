package com.example.aditya.voice;

import android.content.Intent;
import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_RECORD_AUDIO = 1;
    private TextToSpeech tts;
    private SpeechRecognizer speechRecog;
   // private static int time=2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        @Override
//        public boolean onCreateOptionsMenu(Menu menu) {
//            MenuInflater menuInflater=getMenuInflater();
//            menuInflater.inflate(R.menu.menu_main);
//
//            return super.onCreateOptionsMenu(menu);
//        }
//
//        @Override
//        public boolean onOptionsItemSelected(MenuItem item) {
//
//            if(item.getItemId()==R.id.action_settings){
//
//                Intent intent=new Intent(getApplicationContext(),Main2Activity.class);
//                startActivity(intent);
//                return true;
//            }
//            else{
//                return false;
//
//            }
//        }



     //   Toolbar toolbar = findViewById(R.id.toolbar);
     //   setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Here, thisActivity is the current activity
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.RECORD_AUDIO)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Permission is not granted
                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                            Manifest.permission.RECORD_AUDIO)) {
                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.
                    } else {
                        // No explanation needed; request the permission
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.RECORD_AUDIO},MY_PERMISSIONS_REQUEST_RECORD_AUDIO);

                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                        // app-defined int constant. The callback method gets the
                        // result of the request.
                    }
                } else {
                    // Permission has already been granted
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,1);
                    speechRecog.startListening(intent);
                }
            }
        });

        initializeTextToSpeech();
        initializeSpeechRecognizer();
    }

    private void initializeSpeechRecognizer() {
        if (SpeechRecognizer.isRecognitionAvailable(this)) {
            speechRecog = SpeechRecognizer.createSpeechRecognizer(this);
            speechRecog.setRecognitionListener(new RecognitionListener() {
                @Override
                public void onReadyForSpeech(Bundle params) {

                }

                @Override
                public void onBeginningOfSpeech() {

                }

                @Override
                public void onRmsChanged(float rmsdB) {

                }

                @Override
                public void onBufferReceived(byte[] buffer) {

                }

                @Override
                public void onEndOfSpeech() {

                }

                @Override
                public void onError(int error) {

                }

                @Override
                public void onResults(Bundle results) {
                    List<String> result_arr = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                    processResult(result_arr.get(0));
                }

                @Override
                public void onPartialResults(Bundle partialResults) {

                }

                @Override
                public void onEvent(int eventType, Bundle params) {

                }
            });
        }
    }

    private void processResult(String result_message) {
        result_message = result_message.toLowerCase();

        if(result_message.indexOf("what") != -1){
            if(result_message.indexOf("your name") != -1){
                speak("My Name is root. Nice to meet you!");
            }
            if (result_message.indexOf("time") != -1){
                String time_now = DateUtils.formatDateTime(this, new Date().getTime(),DateUtils.FORMAT_SHOW_TIME);
                speak("The time is now: " + time_now);
            }
            if (result_message.indexOf("date") != -1){
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Calendar c = Calendar.getInstance();
                String date = sdf.format(c.getTime());
                speak("The date is now: " + date);
            }
        }
//        else if (result_message.indexOf("earth") != -1){
//            speak("Don't be silly, The earth is a sphere. As are all other planets and celestial bodies");
//       }
        else if (result_message.indexOf("open youtube") != -1){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtube.com"));
            speak("Opening youtube");
            startActivity(intent);
        }
        else if (result_message.indexOf("open instagram") != -1){
            speak("Opening instagram");
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://instagram.com"));
            startActivity(intent);
        }
        else if (result_message.indexOf("open internet") != -1){
            speak("Opening intranet");
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://intranet.cb.amrita.edu/"));
            startActivity(intent);
        }
        else if (result_message.indexOf("open amrita management system") != -1){
            speak("Opening aums");
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://amritavidya.amrita.edu:8444/cas/login?service=https%3A%2F%2Famritavidya.amrita.edu%3A8444%2Faums%2FJsp%2FCore_Common%2Findex.jsp"));
            startActivity(intent);
        }
        else if (result_message.indexOf("open cricbuzz") != -1){
            speak("Opening cricbuzz");
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://cricbuzz.com"));
            startActivity(intent);
        }
        else if (result_message.indexOf("open chrome") != -1){
            speak("Open chrome");
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://google.com"));
            startActivity(intent);
        }
        else if (result_message.indexOf("open linkedin") != -1){
            speak("Open linkedin");
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://linkedin.com"));
            startActivity(intent);
        }
        else if (result_message.indexOf("open gmail") != -1){
            speak("Opening gmail");
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://gmail.com"));
            startActivity(intent);
        }
        else if (result_message.indexOf("open redbus") != -1){
            speak("Open redbus");
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://redbus.com"));
            startActivity(intent);
        }
        else if (result_message.indexOf("open internshala") != -1){
            speak("Opening internshala");
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://internshala.com"));
            startActivity(intent);
        }
        else if (result_message.indexOf("show my current location") != -1){
            speak("Opening maps");
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/@10.9117328,76.9066824,15z"));
            startActivity(intent);
        }
        else if (result_message.indexOf("open maps") != -1){
            speak("opening maps");

        }
        else if (result_message.indexOf("open telegram") != -1){
            speak("Opening telegram");
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://telegram.org"));
            startActivity(intent);
        }
        else if (result_message.indexOf("open hotstar") != -1){
            speak("Opening hotstar");
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.hotstar.com"));
            startActivity(intent);
        }
        else if (result_message.indexOf("open twitter") != -1){
           // Log.i("msg","opening twitter");
            speak("Opening twitter");
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com"));
            startActivity(intent);
        }
        else if (result_message.indexOf("open bookmyshow") != -1){
            speak("Opening bookmyshow");
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.bookmyshow.com"));
            startActivity(intent);
        }
        else if (result_message.indexOf("ayya baboi") != -1){
            speak("amma bhaaboi");
        }
        else if (result_message.indexOf("jai lokesh") != -1){
            speak("theeeeyyagundhi");
        }


    }


    private void initializeTextToSpeech() {
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (tts.getEngines().size() == 0 ){
                    Toast.makeText(MainActivity.this, getString(R.string.tts_no_engines),Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    tts.setLanguage(Locale.US);
                   // speak("Hello there, I am ready to start our conversation");
                }
            }
        });
    }

    private void speak(String message) {
        if(Build.VERSION.SDK_INT >= 21){
            tts.speak(message,TextToSpeech.QUEUE_FLUSH,null,null);
        } else {
            tts.speak(message, TextToSpeech.QUEUE_FLUSH,null);
        }
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

    @Override
    protected void onPause() {
        super.onPause();
        tts.shutdown();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Reinitialize the recognizer and tts engines upon resuming from background such as after openning the browser
        initializeSpeechRecognizer();
        initializeTextToSpeech();
    }
}



/* <android.support.design.widget.AppBarLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:theme="@style/AppTheme.AppBarOverlay">

        <android.support.v7.widget.Toolbar
            android:id="@+id/toolbar"
            android:layout_width="match_parent"
            android:layout_height="?attr/actionBarSize"
            android:background="?attr/colorPrimary"
            app:popupTheme="@style/AppTheme.PopupOverlay" />

    </android.support.design.widget.AppBarLayout>
 */