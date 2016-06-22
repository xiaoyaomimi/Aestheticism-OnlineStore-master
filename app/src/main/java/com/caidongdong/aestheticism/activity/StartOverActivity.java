package com.caidongdong.aestheticism.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.caidongdong.aestheticism.R;

public class StartOverActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        final View startView = View.inflate(this, R.layout.activity_start_over, null);
        setContentView(startView);
        //渐变
        AlphaAnimation aa = new AlphaAnimation(0.3f, 1.0f);
        aa.setDuration(2000);
        startView.setAnimation(aa);
        aa.setAnimationListener(new Animation.AnimationListener() {

                                    @Override
                                    public void onAnimationStart(Animation animation) {
                                        // TODO Auto-generated method stub

                                    }

                                    @Override
                                    public void onAnimationRepeat(Animation animation) {
                                        // TODO Auto-generated method stub

                                    }

                                    @Override
                                    public void onAnimationEnd(Animation animation) {
                                        // TODO Auto-generated method stub
                                        redirectto();
                                    }
                                }
        );

    }
    private void redirectto() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }


}
