package com.example.aditya.voice;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Main2Activity extends AppCompatActivity {
    private static int time=2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Thread background = new Thread(){
            public void run(){
                try{
                    sleep(1300);

                    Intent i=new Intent(Main2Activity.this,MainActivity.class);
                    startActivity(i);

                    finish();
                }
                catch (Exception e){

                }
            }
        };
        background.start();

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent i = new Intent(Main2Activity.this,MainActivity.class);
//                startActivity(i);
//                finish();
//            }
//        },time);
    }
}
