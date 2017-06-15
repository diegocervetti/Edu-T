package ar.edu.eestn1ee.www.edu_t;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;


import net.danlew.android.joda.JodaTimeAndroid;

public class SplashActivity extends Activity {

    private Preferencias preferencias;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JodaTimeAndroid.init(this);


        setContentView(R.layout.activity_splash);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                chequearDondeIr();
            }
        }, 1500);


    }

    public void chequearDondeIr(){
        //TODO no funciona bien el shared preferences manager
        // problema siempre muestra la pantalla de ayuda, la idea es que solo la muestre la primera vez que se usa la App...

        // Es la primera vez que se usa la App? --> Mostramos Intro Ayuda
        preferencias = new Preferencias(this);
        if (preferencias.esPrimerUso().equals("SI") ) {
            irAlMenu();
        } else {
            irAyuda();
        }
    }

    public void irAlMenu() {
                Intent i = new Intent(SplashActivity.this, MenuPrincipal.class);
                SplashActivity.this.startActivity(i);
                SplashActivity.this.finish();
    }

    public void irAyuda() {
                preferencias.setPrimerUso("SI");
                Intent i = new Intent(SplashActivity.this, AyudaActivity.class);
                SplashActivity.this.startActivity(i);
                SplashActivity.this.finish();
            }

}
