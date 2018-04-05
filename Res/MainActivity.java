package com.example.michu.accelerationmaxfrequencytest;

import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
/*
    This Class is for testing purposes.

    It measures the maximum sampling rate of the acceleration sensor.

 */
public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private TextView txt1;
    private TextView txt2;
    private TextView txtResult;
    private long timestamp;
    private long firststamp;
    private long runningSince;
    private long amount;
    private double frequency;
    private double resultFrequency;
    private SensorManager mSensorManager;
    private float[] mAccelGravityData = new float[3];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt1 = (TextView) findViewById(R.id.txt1);
        txt2 = (TextView) findViewById(R.id.txt2);
        txtResult = (TextView) findViewById(R.id.txtResult);
        this.amount = 0;
        this.firststamp = System.currentTimeMillis();

        mSensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener((SensorEventListener) this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
    }


    private int getGyro() {
        return 0;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // load New SensorData
        mAccelGravityData[0]=(mAccelGravityData[0]*2+event.values[0])*0.33334f;
        mAccelGravityData[1]=(mAccelGravityData[1]*2+event.values[1])*0.33334f;
        mAccelGravityData[2]=(mAccelGravityData[2]*2+event.values[2])*0.33334f;
        timestamp = System.currentTimeMillis();
        runningSince = (timestamp - firststamp) /1000;
        if (runningSince > 0){
            frequency = amount / runningSince;
        }
        amount++;

        String message1 = "mAccelGravityData X " + mAccelGravityData +System.getProperty ("line.separator");
        message1 += "mAccelGravityData Y " + mAccelGravityData[1] +System.getProperty ("line.separator");
        message1 += "mAccelGravityData Z" + mAccelGravityData[2];
        txt1.setText(message1);

        String message2 ="Amount: "+amount + System.getProperty ("line.separator");
        message2 += "Running since [s]:" + (timestamp - firststamp) / 1000 + System.getProperty("line.separator");
        message2 += "Abtastfrequenz: " + frequency;

        txt2.setText(message2);

        if(runningSince > 3 && resultFrequency == 0.0 ){
            resultFrequency = frequency;
            String message3 = "The average Frequency after 3 seconds was:" + resultFrequency;
            txtResult.setText(message3);
        }


    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

}
