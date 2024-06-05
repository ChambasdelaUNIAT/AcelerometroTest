package com.example.acelerometrocolores;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.Sensor;
import android.media.MediaPlayer;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    SensorManager sensorManager;
    Sensor sensor;
    SensorEventListener sensorEventListener;

    int sentidoGiro = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if(sensor==null)
            finish();

        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                System.out.println("Acelerometro en x=" + x);
                if (x <= -4 && sentidoGiro == 0){
                    sentidoGiro++;
                    getWindow().getDecorView().setBackgroundColor(Color.CYAN);
                }else if(x>4 && sentidoGiro==1){
                    sentidoGiro++;
                    getWindow().getDecorView().setBackgroundColor(Color.RED);
                }
                if(sentidoGiro==2){
                    sound();
                    sentidoGiro=0;
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        start();

    }

    private void sound(){
        MediaPlayer mediaPlayer=MediaPlayer.create(this,R.raw.resonancia);
        mediaPlayer.start();
    }

    private void start(){
        sensorManager.registerListener(sensorEventListener,sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void stop(){
        sensorManager.unregisterListener(sensorEventListener);
    }

    @Override
    protected void onPause() {
        stop();
        super.onPause();
    }

    @Override
    protected void onResume(){
        start();
        super.onResume();
    }

}