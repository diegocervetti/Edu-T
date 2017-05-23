package ar.edu.eestn1ee.www.edu_t;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

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
    //Selected dia
    private int selectedDate,selectedMonth,selectedYear;
    //Reserva datepicker y calendar
    private DatePicker datePicker;
    private Calendar calendar;
    //view de cantidad dias
    private NumberPicker cantidadDiasNP;
    //Dialog
    private Dialog dialog, dialog2;
    //sumador dia
    private int cantidad;


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
        //dialog fecha
        dialog = new Dialog(MenuPrincipal.this);
        dialog.setContentView(R.layout.datepickerview);
        dialog.setTitle("");

        datePicker = (DatePicker) dialog.findViewById(R.id.datePicker1);
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        selectedDate=calendar.get(Calendar.DAY_OF_MONTH);
        selectedMonth=calendar.get(Calendar.MONTH);
        selectedYear=calendar.get(Calendar.YEAR);

        botonCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Se trae date picker
                dialogfecha();

            }
        } );

        botonCantidadDias = (ImageButton) findViewById(R.id.botonCantidadDias);
        diasTituloTV = (TextView) findViewById(R.id.diasTituloTV);

        //Se crea un dialog para el input del numero

        dialog2 = new Dialog(MenuPrincipal.this);
        dialog2.setContentView(R.layout.cantidadview);

        cantidadDiasNP = (NumberPicker) dialog2.findViewById(R.id.numberPicker);
        cantidadDiasNP.setMaxValue(365);
        cantidadDiasNP.setMinValue(1);
        cantidadDiasNP.setWrapSelectorWheel(true);
        cantidadDiasNP.setOnLongPressUpdateInterval(300);
        botonCantidadDias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Preguntar a Diego como tendria que verse el input, mejorar diseño

                dialogcantidad();
                //////////////////////////////////////////////////

            }
        });

        botonCalculadora = (ImageButton) findViewById(R.id.botonCalculadora);
        calculadoraTituloTV = (TextView) findViewById(R.id.calculadoraTituloTV);
        botonCalculadora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO mostrar resultado del cálculo
                //TODO HACER CALCULO DESPUES PORQUE SINO DIEGO ME RETA
                Toast.makeText(MenuPrincipal.this, "CALCULO W.I.P", Toast.LENGTH_SHORT).show();
                hastaElLL.setVisibility(hastaElLL.VISIBLE);
                ViewGroup.LayoutParams paramTemp = centrarFinLicenciaLL.getLayoutParams();
                paramTemp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                centrarFinLicenciaLL.setLayoutParams(paramTemp);
                calculadoraTituloTV.setVisibility(calculadoraTituloTV.GONE);

            }
        });

    }
    ////////////////////////////////////////////////////////////////////
    // Dialog
    public void dialogfecha(){
        datePicker.init(selectedYear,selectedMonth, selectedDate, new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int day) {

                TextView tv = (TextView) findViewById(R.id.fechaDesdeIV);

                String stringOfDate = day + "/" + month + "/" + year;
                tv.setText(stringOfDate);
                dialog.dismiss();

                selectedDate=day;
                selectedMonth=month;
                selectedYear=year;
                desdeElLL.setVisibility(desdeElLL.VISIBLE);
                desdeElLL.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogfecha();
                    }
                });
                ViewGroup.LayoutParams paramTemp = centrarCalendarioLL.getLayoutParams();
                paramTemp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                centrarCalendarioLL.setLayoutParams(paramTemp);
                calendarioTituloTV.setVisibility(calendarioTituloTV.GONE);
            } });
        dialog.show();
    }
    // Dialog 2
    public void dialogcantidad(){
        dialog2.show();

        cantidadDiasNP.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                diasTV.setText(cantidadDiasNP.getValue() + " Dias");
                diasTV.setVisibility(diasTV.VISIBLE);
                diasTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogcantidad();
                    }
                });
                ViewGroup.LayoutParams paramTemp = centrarDiasLL.getLayoutParams();
                paramTemp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                centrarDiasLL.setLayoutParams(paramTemp);
                diasTituloTV.setVisibility(diasTituloTV.GONE);
            }
        });
        cantidadDiasNP.setOnLongPressUpdateInterval(90);
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


