package ar.edu.eestn1ee.www.edu_t;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.CheckBox;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import org.joda.time.DateTime;



import java.util.Calendar;
import java.util.Locale;

import static java.util.Calendar.DATE;

public class MenuPrincipal extends AppCompatActivity {

    //Linear Layouts y Views que se ocultan cuando nunca se calculó una licencia
    private LinearLayout desdeElLL; //Calendario
    private TextView diasTV; //Cantidad de días ingresado por el usuario
    private LinearLayout hastaElLL; //ViewGroup con fecha resultado y checkbox
    private  TextView resultadoTV;
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
    //DateTime
    private DateTime fechaSuma,fechaSumada;
    //Comprobador
     private Boolean comprobador1=false,comprobador2=false;
    //Checkbox
     private CheckBox checkBox;
    //Calendarios
    private Calendar fechaInicial = Calendar.getInstance(),fechaFinal= Calendar.getInstance();
    //Dia guardado
    private int diaGuardado = 1;

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
        desdeElLL.setVisibility(View.GONE);
        diasTV = (TextView) findViewById(R.id.diasTV);
        diasTV.setVisibility(View.GONE);
        hastaElLL = (LinearLayout) findViewById(R.id.hastaElLL);
        hastaElLL.setVisibility(View.GONE);

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

        //TextView del resultado
        resultadoTV = (TextView) findViewById(R.id.resultado);

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
               // Toast.makeText(MenuPrincipal.this, "Me quiero ir :v", Toast.LENGTH_SHORT).show();
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


                dialogcantidad();
                //////////////////////////////////////////////////

            }
        });

        botonCalculadora = (ImageButton) findViewById(R.id.botonCalculadora);
        calculadoraTituloTV = (TextView) findViewById(R.id.calculadoraTituloTV);
        botonCalculadora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Toast.makeText(MenuPrincipal.this, "CALCULO W.I.P", Toast.LENGTH_SHORT).show();
                hastaElLL.setVisibility(hastaElLL.VISIBLE);
                ViewGroup.LayoutParams paramTemp = centrarFinLicenciaLL.getLayoutParams();
                paramTemp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                centrarFinLicenciaLL.setLayoutParams(paramTemp);
                calculadoraTituloTV.setVisibility(calculadoraTituloTV.GONE);
*/               if(!comprobador1 && !comprobador2){
                    Toast.makeText(MenuPrincipal.this, "Seleccione La fecha y la Cantidad de Días", Toast.LENGTH_SHORT).show();
                }else if(!comprobador1){
                    Toast.makeText(MenuPrincipal.this, "Seleccione La fecha", Toast.LENGTH_SHORT).show();
                }else if(!comprobador2){
                    Toast.makeText(MenuPrincipal.this, "Seleccione La Cantidad de Días", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //Checkbox de dias habiles
        checkBox = (CheckBox) findViewById(R.id.checkBoxDias);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                comprobar();
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

                String stringOfDate = day + "/" + (month+1) + "/" + year;
                tv.setText(stringOfDate);
                dialog.dismiss();

                selectedDate=day;
                selectedMonth=month;
                selectedYear=year;
                comprobador1 = true;
                comprobar();
                desdeElLL.setVisibility(View.VISIBLE);
                desdeElLL.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogfecha();
                    }
                });
                ViewGroup.LayoutParams paramTemp = centrarCalendarioLL.getLayoutParams();
                paramTemp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                centrarCalendarioLL.setLayoutParams(paramTemp);
                calendarioTituloTV.setVisibility(View.GONE);
            } });
        dialog.show();
    }
    // Dialog 2
    public void dialogcantidad(){
        dialog2.show();
        cantidadDiasNP.setValue(diaGuardado);
        ActualizarCantidadDias();
        cantidadDiasNP.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                diaGuardado = newVal;
                ActualizarCantidadDias();
            }
        });
        cantidadDiasNP.setOnLongPressUpdateInterval(90);
    }
    //Actualizar cantidad Dias
    public void ActualizarCantidadDias(){
        if(cantidadDiasNP.getValue() == 1){
            diasTV.setText("1 Día");
        }else{
            diasTV.setText(cantidadDiasNP.getValue() + " Días");
        }
        diasTV.setVisibility(View.VISIBLE);
        diasTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogcantidad();
            }
        });

        comprobador2 = true;
        comprobar();

        ViewGroup.LayoutParams paramTemp = centrarDiasLL.getLayoutParams();
        paramTemp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        centrarDiasLL.setLayoutParams(paramTemp);
        diasTituloTV.setVisibility(View.GONE);

    }
    //Comprobar
    public void comprobar(){

        if(comprobador1 && comprobador2){
            hastaElLL.setVisibility(View.VISIBLE);
            ViewGroup.LayoutParams paramTemp = centrarFinLicenciaLL.getLayoutParams();
            paramTemp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            centrarFinLicenciaLL.setLayoutParams(paramTemp);
            calculadoraTituloTV.setVisibility(View.GONE);

            fechaInicial.set(selectedYear,selectedMonth,selectedDate);
            //se crea dateTime
            fechaSumada = new DateTime(fechaInicial);

            //Agregar cantidad dias
            fechaSumada = fechaSumada.plusDays(cantidadDiasNP.getValue());
            fechaFinal = fechaSumada.toCalendar(Locale.getDefault());
            if(checkBox.isChecked()) {
                diasHabiles(fechaInicial, fechaFinal);

            }

                resultadoTV.setText((fechaSumada.getDayOfMonth()-1) + "/" + fechaSumada.getMonthOfYear() + "/" + fechaSumada.getYear());

        }
    }

    //CALCULAR FINES DE SEMANA

    public int diasHabiles(Calendar fechaInicial, Calendar fechaFinal) {
        int diffDays = 0;

        //mientras la fecha inicial sea menor o igual que la fecha final se cuentan los dias
        while (fechaInicial.before(fechaFinal) || fechaInicial.equals(fechaFinal)) {

            if (fechaInicial.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || fechaInicial.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                //se aumentan los dias de diferencia entre min y max
                diffDays++;
                fechaSumada = fechaSumada.plusDays(1);
                fechaFinal = fechaSumada.toCalendar(Locale.getDefault());
            }
            //se suma 1 dia para hacer la validacion del siguiente dia.
            fechaInicial.add(DATE, 1);
        }
        return diffDays;
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


