package ar.edu.eestn1ee.www.edu_t;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MenuPrincipal extends AppCompatActivity {

    //Linear Layouts y Views que se ocultan cuando nunca se calculó una licencia
    private LinearLayout desdeElLL; //Calendario
    private TextView diasTV; //Cantidad de días ingresado por el usuario
    private LinearLayout hastaElLL; //ViewGroup con fecha resultado y checkbox
    //Linear Layouts y Views qe se centran cuando nunca se calculó una licencia
    private LinearLayout centrarCalendarioLL;
    private LinearLayout centrarDiasLL;
    private LinearLayout centrarFinLicenciaLL;
    //Linear Layout root para modificar margen
    private LinearLayout rootLL;
    //Botones
    private ImageButton botonCalendario;
    private ImageButton botonCantidadDias;
    private ImageButton botonCalculadora;
    //Titulos de los botones
    private TextView calendarioTituloTV;
    private TextView diasTituloTV;
    private TextView calculadoraTituloTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        //Quitamos margen al rootView
        rootLL = (LinearLayout)findViewById(R.id.rootLL);
        ViewGroup.MarginLayoutParams margenes = (ViewGroup.MarginLayoutParams) rootLL.getLayoutParams();
        //margenes.leftMargin = 0 ;
        //rootLL.setLayoutParams(margenes);

        //Ocultamos Views cuando no hay nada calculado
        desdeElLL = (LinearLayout) findViewById(R.id.desdeElLL);
        desdeElLL.setVisibility(desdeElLL.GONE);
        diasTV = (TextView) findViewById(R.id.diasTV);
        diasTV.setVisibility(diasTV.GONE);
        hastaElLL = (LinearLayout) findViewById(R.id.hastaElLL);
        hastaElLL.setVisibility(hastaElLL.GONE);

        //Centramos Views cuando no hay nada calculado
        centrarCalendarioLL = (LinearLayout) findViewById(R.id.centrarCalendarioLL);
        ViewGroup.LayoutParams parametros = centrarCalendarioLL.getLayoutParams(); //Traemos los params del View para cambiarlos
        parametros.width = ViewGroup.LayoutParams.MATCH_PARENT; //Pasamos a Match parent para poder centrarlo en la página
        centrarCalendarioLL.setLayoutParams(parametros);


        //Centramos Cantidad de Días
        centrarDiasLL = (LinearLayout) findViewById(R.id.centrarDiasLL);
        parametros = centrarDiasLL.getLayoutParams();
        parametros.width = ViewGroup.LayoutParams.MATCH_PARENT;
        centrarDiasLL.setLayoutParams(parametros);


        //Centramos Fin de Licencia
        centrarFinLicenciaLL = (LinearLayout) findViewById(R.id.centrarFinLicenciaLL);
        parametros = centrarFinLicenciaLL.getLayoutParams();
        parametros.width = ViewGroup.LayoutParams.MATCH_PARENT;
        centrarFinLicenciaLL.setLayoutParams(parametros);


        ///////////////////////////////////////////////////////////
        // Escuchamos los clics sobre los íconos
        botonCalendario = (ImageButton) findViewById(R.id.botonCalendario);
        calendarioTituloTV = (TextView) findViewById(R.id.calendarioTituloTV);
        botonCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO traer date picker
                desdeElLL.setVisibility(desdeElLL.VISIBLE);
                ViewGroup.LayoutParams paramTemp = centrarCalendarioLL.getLayoutParams();
                paramTemp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                centrarCalendarioLL.setLayoutParams(paramTemp);
                calendarioTituloTV.setVisibility(calendarioTituloTV.GONE);
            }
        });

        botonCantidadDias = (ImageButton) findViewById(R.id.botonCantidadDias);
        diasTituloTV = (TextView) findViewById(R.id.diasTituloTV);
        botonCantidadDias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO traer selector de dias
                diasTV.setVisibility(diasTV.VISIBLE);
                ViewGroup.LayoutParams paramTemp = centrarDiasLL.getLayoutParams();
                paramTemp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                centrarDiasLL.setLayoutParams(paramTemp);
                diasTituloTV.setVisibility(diasTituloTV.GONE);
            }
        });

        botonCalculadora = (ImageButton) findViewById(R.id.botonCalculadora);
        calculadoraTituloTV = (TextView) findViewById(R.id.calculadoraTituloTV);
        botonCalculadora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO mostrar resultado del cálculo
                hastaElLL.setVisibility(hastaElLL.VISIBLE);
                ViewGroup.LayoutParams paramTemp = centrarFinLicenciaLL.getLayoutParams();
                paramTemp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                centrarFinLicenciaLL.setLayoutParams(paramTemp);
                calculadoraTituloTV.setVisibility(calculadoraTituloTV.GONE);

            }
        });

    }

    /////////////////////////////////////////////////
    // Menu de los tres puntos con opciones
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.Ayuda:
                Toast.makeText(this,"AYUDA", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.Acerca:
                Toast.makeText(this,"ACERCA", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
