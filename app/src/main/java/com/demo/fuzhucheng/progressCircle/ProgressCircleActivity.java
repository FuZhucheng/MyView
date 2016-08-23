package com.demo.fuzhucheng.progressCircle;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.demo.fuzhucheng.R;

public class ProgressCircleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_circle);
        final com.demo.fuzhucheng.progressCircle.ManualCircle myView = (com.demo.fuzhucheng.progressCircle.ManualCircle) findViewById(R.id.myview);
        myView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                myView.add();
            }
        });

        final com.demo.fuzhucheng.progressCircle.AutomaticCircle automaticCircle=(com.demo.fuzhucheng.progressCircle.AutomaticCircle)findViewById(R.id.automaticCircle);
        automaticCircle.setScore(80);
    }
}
