package com.example.sensordemo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity  implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private Sensor lightSensor;
    private Sensor linearAccSenor;
    private ConstraintLayout light;
    private ConstraintLayout proximity;
    private int RED = Color.RED;
    private int GREEN = Color.GREEN;
    private int BLUE = Color.BLUE;
    private ConstraintLayout AccSenor;
    private TextView stepText;
    private TextView lightText,proxText;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        light = findViewById(R.id.lightLayout);
        lightText = findViewById(R.id.lightText);
        AccSenor = findViewById(R.id.linearAccSensor);
        proxText = findViewById(R.id.proximityText);
        proximity = findViewById(R.id.proximityLayout);
        stepText = findViewById(R.id.stepText);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(
                Sensor.TYPE_PROXIMITY);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        linearAccSenor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (proximity != null) {
            sensorManager.registerListener(this, proximitySensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (lightSensor != null) {
            sensorManager.registerListener(this, lightSensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (linearAccSenor !=null) {
            sensorManager.registerListener(this,linearAccSenor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        float currentValue = event.values[0];
        if(sensorType==Sensor.TYPE_LIGHT){
            Random random = new Random();
            lightText.setText("Light Sensor Data: "+String.valueOf(currentValue));
            int randomNumber = random.nextInt(15-1) + 1;
            if(randomNumber<6){
                light.setBackgroundColor(RED);
            }else if(randomNumber<10 && randomNumber>5){
                light.setBackgroundColor(GREEN);
            }
            else if(randomNumber<15 && randomNumber>10){
                light.setBackgroundColor(BLUE);
            }
        }
        if(sensorType==Sensor.TYPE_PROXIMITY){
            Random random = new Random();
            proxText.setText("Proximity Sensor Data: "+String.valueOf(currentValue)+"\nPress in top of your phone to change the size of Layout.");
            int randomNumber = random.nextInt(15-1) + 1;
            if(randomNumber<6){
                proximity.setMaxHeight(200);
            }else if(randomNumber<10 && randomNumber>5){
                proximity.setMaxHeight(250);
            }
            else if(randomNumber<15 && randomNumber>10){
                proximity.setMaxHeight(300);
            }
        }
        if(sensorType==Sensor.TYPE_STEP_COUNTER){
            stepText.setText(String.valueOf(currentValue));
        }
        if(sensorType==Sensor.TYPE_LINEAR_ACCELERATION){
            stepText.setText(String.valueOf("Acceleration Data: "+currentValue+"\nShake your phone quickly to see it change color."));
            if(currentValue>10) {
                AccSenor.setBackgroundColor(Color.RED);
                Toast.makeText(this, "Accelerated very fast.", Toast.LENGTH_SHORT).show();
            }
            else{
                AccSenor.setBackgroundColor(Color.YELLOW);
            }
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}