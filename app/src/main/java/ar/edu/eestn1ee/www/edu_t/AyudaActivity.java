package ar.edu.eestn1ee.www.edu_t;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AyudaActivity extends Activity {
    private ViewPager viewPager ;
    private propioViewPagerAdapter elPagerAdapter;
    private LinearLayout puntos ;
    private Button btnSaltear ;
    private Button btnSiguiente ;
    private int[] pantallas; //array para meter adentro todas las pantallas de ayuda
    private TextView[] puntitos; //array para meter los puntos que aparecen al final de la pantalla

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayuda);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        puntos = (LinearLayout) findViewById(R.id.puntos);
        btnSaltear = (Button) findViewById(R.id.btn_saltear);
        btnSiguiente = (Button) findViewById(R.id.btn_siguiente);

        pantallas = new int[]{
                R.layout.layout_ayuda_pantalla_1,
                R.layout.layout_ayuda_pantalla_2,
                R.layout.layout_ayuda_pantalla_3,
                R.layout.layout_ayuda_pantalla_4
        };

        agregarPuntos(0);

        elPagerAdapter = new propioViewPagerAdapter();
        viewPager.setAdapter(elPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        btnSaltear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irMenuPrincipal();
            }
        });

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // es la última pantalla?
                // última --> intent al menú principal
                int current = getItem(+1);
                if (current < pantallas.length) {
                    // no soy la última pantalla así que paso a la siguiente
                    viewPager.setCurrentItem(current);
                } else {
                    irMenuPrincipal();
                }
            }
        });
    }

// agregamos los puntitos que aparecen en la parte de abajo de la pantalla
    private void agregarPuntos(int paginaActual) {
        puntitos = new TextView[pantallas.length];

        puntos.removeAllViews();
        for (int i = 0; i < puntitos.length; i++) {
            puntitos[i] = new TextView(this);
            puntitos[i].setText(Html.fromHtml("&#8226;"));
            puntitos[i].setTextSize(35);
            puntitos[i].setTextColor(getResources().getColor(R.color.punto_inactivo));
            puntos.addView(puntitos[i]);
        }

        if (puntitos.length > 0)
            puntitos[paginaActual].setTextColor(getResources().getColor(R.color.punto_activo));
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    //  seteamos un listener para cuando cambia el viewPager
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            agregarPuntos(position);

            // si es la última pantalla hay que cambiar el botón siguiente x listo
            if (position == pantallas.length - 1) {
                // es la última pantalla así que acomodamos las visuales de los botones
                btnSiguiente.setText(getString(R.string.listo));
                btnSaltear.setVisibility(View.GONE);
            } else {
                // sigue igual porque hay más pantallas por ver
                btnSiguiente.setText(getString(R.string.siguiente));
                btnSaltear.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    /**
     * View pager adapter
     */
    public class propioViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public propioViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(pantallas[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return pantallas.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

    public void irMenuPrincipal(){
        Intent i = new Intent(this, MenuPrincipal.class);
        this.startActivity(i);
        this.finish();
    }

}
