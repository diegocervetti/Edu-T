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
        //Se agregan las layour al array de pantallas
        pantallas = new int[]{
                R.layout.layout_ayuda_pantalla_1,
                R.layout.layout_ayuda_pantalla_2,
                R.layout.layout_ayuda_pantalla_3,
                R.layout.layout_ayuda_pantalla_4
        };
        //Se inicializa en el primer layout que seria pantallas[0]
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
                    //Si no es la ultima pantalla, pasa a la que sigue
                    viewPager.setCurrentItem(current);
                } else {
                    irMenuPrincipal();
                }
            }
        });
    }

// Agregamos los puntitos que aparecen en la parte de abajo de la pantalla
    private void agregarPuntos(int paginaActual) {
        puntitos = new TextView[pantallas.length];//La cantidad de pantallas es la cantidad de puntitos
        //Remueve todos los puntitos
        puntos.removeAllViews();
        for (int i = 0; i < puntitos.length; i++) {//Agrega puntitos
            puntitos[i] = new TextView(this);
            puntitos[i].setText(Html.fromHtml("&#8226;"));
            puntitos[i].setTextSize(35);
            puntitos[i].setTextColor(getResources().getColor(R.color.punto_inactivo));
            puntos.addView(puntitos[i]);
        }

        if (puntitos.length > 0)//Se comprueba si hay alguna pantalla
            puntitos[paginaActual].setTextColor(getResources().getColor(R.color.punto_activo));//Se pone otro color para la pagina actual
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    //  Setteamos un listener para cuando cambia el viewPager
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            agregarPuntos(position);

            // Si es la última pantalla hay que cambiar el botón "siguiente" por "listo"
            if (position == pantallas.length - 1) {
                // Si es la última pantalla, acomodamos las visuales de los botones
                btnSiguiente.setText(getString(R.string.listo));
                btnSaltear.setVisibility(View.GONE);
            } else {
                // Sigue igual ya que hay pantallas por ver, o por si se pasa de la ultima pantalla a la anterior
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
        //Se hace un intento para pasar de la pantalla actual al menuPrincipal
        Intent i = new Intent(this, MenuPrincipal.class);
        //Se inicia el activity del menuPrincipal y se finaliza la actual
        this.startActivity(i);
        this.finish();
    }

}
