package ar.edu.eestn1ee.www.edu_t;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.WindowManager;

import net.danlew.android.joda.JodaTimeAndroid;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO listo inicializar J-time
        JodaTimeAndroid.init(this);

        setContentView(R.layout.activity_splash);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        irAlMenu();
    }

    public void irAlMenu() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i = new Intent(SplashActivity.this, MenuPrincipal.class);
                SplashActivity.this.startActivity(i);
                SplashActivity.this.finish();
            }
        }, 2000);
    }

}
