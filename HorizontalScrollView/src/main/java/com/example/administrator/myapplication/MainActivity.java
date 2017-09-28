package com.example.administrator.myapplication;


import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private MyHorizontalScrollView msv;
    private View v_line1;
    private View v_line2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        msv = (MyHorizontalScrollView) findViewById(R.id.sl_scroll);
        RelativeLayout rl_viewStatus = (RelativeLayout) findViewById(R.id.rl_one);
        v_line1 = findViewById(R.id.v_line1);
        v_line2 = findViewById(R.id.v_line2);

        ObjectAnimator.ofFloat(rl_viewStatus, "scaleY", 0.9f).setDuration(5)
                .start();
        msv.setIsAnim(true);
        msv.setOnViewChangedListener(new MyHorizontalScrollView.OnViewChangedListener() {

            @Override
            public void secondViewShow() {
                // TODO Auto-generated method stub
                v_line1.setBackgroundColor(Color.parseColor("#25ffffff"));
                v_line2.setBackgroundColor(Color.parseColor("#ffffff"));
            }

            @Override
            public void firstViewShow() {
                // TODO Auto-generated method stub
                v_line1.setBackgroundColor(Color.parseColor("#ffffff"));
                v_line2.setBackgroundColor(Color.parseColor("#25ffffff"));
            }
        });
    }


}
