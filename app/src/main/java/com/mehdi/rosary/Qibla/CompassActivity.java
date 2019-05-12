package com.mehdi.rosary.Qibla;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.mehdi.rosary.R;

public class CompassActivity extends Activity implements SensorEventListener {
    private ImageView ImageCompass;
    private float DegreeCurrent = 0f;
    private SensorManager SM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compass);
        ImageCompass = (ImageView)findViewById(R.id.CompassView);
        SM = (SensorManager)getSystemService(SENSOR_SERVICE);
    }
    @Override
    public void onSensorChanged(SensorEvent SE) {
        float Degree = Math.round(SE.values[0]);
       /* Intent i = new Intent(getApplicationContext(), MapsActivity.class);
        i.putExtra("send_string", tvHeading.getText().toString());
        startActivity(i);*/
        RotateAnimation RA = new RotateAnimation(DegreeCurrent, -Degree, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        RA.setDuration(210);
        RA.setFillAfter(true);
        ImageCompass.startAnimation(RA);
        DegreeCurrent = -Degree;
    }

    @Override
    protected void onPause() {
        super.onPause();
        SM.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SM.registerListener(this, SM.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
