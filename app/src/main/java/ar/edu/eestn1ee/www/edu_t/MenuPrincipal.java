package ar.edu.eestn1ee.www.edu_t;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    private Button botonCerrar;
    //Dialog
    private Dialog dialogFecha, dialogCantidadDeDias;
    //sumador dia
    private int cantidad;
    //DateTime
    private DateTime fechaSuma,fechaSumada;
    //Comprobador
     private Boolean comprobador1=false, comprobador2=false;
    //Checkbox
     private CheckBox checkBox;
    //Calendarios
    private Calendar fechaInicial = Calendar.getInstance(),fechaFinal= Calendar.getInstance();
    //Cantidad de Dia Guardado
    private int selectedCantidadDias = 1;

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

        //Se crea el dialogFecha fecha
        dialogFecha = new Dialog(MenuPrincipal.this);
        //Se le aplica la view del datepicker
        dialogFecha.setContentView(R.layout.datepickerview);
        //Se aplica un titulo vacio
        dialogFecha.setTitle("");

        datePicker = (DatePicker) dialogFecha.findViewById(R.id.datePicker1);
        //Se obtiene una instancia del calendario
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        //Se define cada variable con su tipo de fecha
        selectedDate=calendar.get(Calendar.DAY_OF_MONTH);
        selectedMonth=calendar.get(Calendar.MONTH);
        selectedYear=calendar.get(Calendar.YEAR);
        //Al hacer click en el boton calendario se llama al datepicker
        botonCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Se trae date picker
                   callDialogFecha();

            }
        } );

        botonCantidadDias = (ImageButton) findViewById(R.id.botonCantidadDias);
        diasTituloTV = (TextView) findViewById(R.id.diasTituloTV);


        //Se crea un dialogFecha para el input del numero
        dialogCantidadDeDias = new Dialog(MenuPrincipal.this);
        //Se aplica el numberPicker
        dialogCantidadDeDias.setContentView(R.layout.cantidadview);

        cantidadDiasNP = (NumberPicker) dialogCantidadDeDias.findViewById(R.id.numberPicker);
        cantidadDiasNP.setMaxValue(365);// Valor maximo 1 año
        cantidadDiasNP.setMinValue(1);//Valor minimo 1 dia
        cantidadDiasNP.setWrapSelectorWheel(true);//Se puede mover la rueda
        cantidadDiasNP.setOnLongPressUpdateInterval(300);//Cuando se mantiene presionado se mueve la rueda

        botonCerrar = (Button) dialogCantidadDeDias.findViewById(R.id.btnCerrar);

        //Cuando se hace click se llama al dialogFecha cantidad de dias
        botonCantidadDias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callDialogCantidad();
            }
        });

        botonCalculadora = (ImageButton) findViewById(R.id.botonCalculadora);
        calculadoraTituloTV = (TextView) findViewById(R.id.calculadoraTituloTV);
        botonCalculadora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(!comprobador1 && !comprobador2){
                    Toast.makeText(MenuPrincipal.this, "Seleccione la Fecha de Inicio y\nla Cantidad de Días", Toast.LENGTH_SHORT).show();
                }else if(!comprobador1){
                    Toast.makeText(MenuPrincipal.this, "Seleccione la Fecha de Inicio", Toast.LENGTH_SHORT).show();
                }else if(!comprobador2){
                    Toast.makeText(MenuPrincipal.this, "Seleccione la Cantidad de Días", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //Checkbox de dias {hábiles
        checkBox = (CheckBox) findViewById(R.id.checkBoxDias);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ComprobarDiaAndCantidadIsSelected();
            }
        });

    }
    ////////////////////////////////////////////////////////////////////
    // Dialog
    public void callDialogFecha(){
        datePicker.init(selectedYear,selectedMonth, selectedDate, new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int day) {

                TextView tv = (TextView) findViewById(R.id.fechaDesdeIV);
                //Obtenemos y aplicamos la fecha seleccionada
                String stringOfDate = day + "/" + (month+1) + "/" + year;
                tv.setText(stringOfDate);
                dialogFecha.dismiss();
                //Se guarda los dias seleccionados
                selectedDate=day;
                selectedMonth=month;
                selectedYear=year;
                //Se activa el comprobador de que se seleccionó el dia
                comprobador1 = true;
                //Comprueba si ya se seleccionó la cantidad
                ComprobarDiaAndCantidadIsSelected();
                desdeElLL.setVisibility(View.VISIBLE);
                desdeElLL.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callDialogFecha();
                    }
                });
                ViewGroup.LayoutParams paramTemp = centrarCalendarioLL.getLayoutParams();
                paramTemp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                centrarCalendarioLL.setLayoutParams(paramTemp);
                calendarioTituloTV.setVisibility(View.GONE);
            } });
        dialogFecha.show();
    }
    // Dialog 2
    public void callDialogCantidad(){
        dialogCantidadDeDias.show();
        //Cerramos el dialogo2 si tocamos el botón cerrar
        botonCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCantidadDeDias.dismiss();
            }
        });

        //
        //Setteamos 1 por defecto
        cantidadDiasNP.setValue(selectedCantidadDias);
        ActualizarCantidadDias();
        cantidadDiasNP.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                //se actualiza la cantidad de dias seleccionado
                selectedCantidadDias = newVal;
                ActualizarCantidadDias();
            }
        });
        cantidadDiasNP.setOnLongPressUpdateInterval(90);
    }

    //Actualizar Cantidad Dia
    public void ActualizarCantidadDias(){
        if(cantidadDiasNP.getValue() == 1){
            //Si el valor es 1, se pone Día en singular
            diasTV.setText("1 Día");
        }else {
            //Si el valor no es 1, se pone en plural
            diasTV.setText(cantidadDiasNP.getValue() + " Días");
        }
        diasTV.setVisibility(View.VISIBLE);
        diasTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callDialogCantidad();
            }
        });
        //Activa el comprobador2
        comprobador2 = true;
        ComprobarDiaAndCantidadIsSelected();

        ViewGroup.LayoutParams paramTemp = centrarDiasLL.getLayoutParams();
        paramTemp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        centrarDiasLL.setLayoutParams(paramTemp);
        diasTituloTV.setVisibility(View.GONE);
    }


    //Comprobar si se selecciono el dia y la cantidad
    public void ComprobarDiaAndCantidadIsSelected(){
        //Comprueba si se selecciono el dia y la cantidad
        if(comprobador1 && comprobador2){
            //Se oculta y centran los view
            hastaElLL.setVisibility(View.VISIBLE);
            ViewGroup.LayoutParams paramTemp = centrarFinLicenciaLL.getLayoutParams();
            paramTemp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            centrarFinLicenciaLL.setLayoutParams(paramTemp);
            calculadoraTituloTV.setVisibility(View.GONE);

            fechaInicial.set(selectedYear,selectedMonth,selectedDate);
            //Se crea dateTime con la fecha inicial
            fechaSumada = new DateTime(fechaInicial);

            //Agregar cantidad dias restando 1 porque está incluido el dia inicial
            fechaSumada = fechaSumada.plusDays(cantidadDiasNP.getValue()-1);
            //Se pasa de JodaTime a Java
            fechaFinal = fechaSumada.toCalendar(Locale.getDefault());
            //Si se chequea el checkbox, se calcula los dias habiles
            if(checkBox.isChecked()) {
                diasHabiles(fechaInicial, fechaFinal);

            }
                //Se aplica el la fecha al resultadoTV
                resultadoTV.setText((fechaSumada.getDayOfMonth()) + "/" + fechaSumada.getMonthOfYear() + "/" + fechaSumada.getYear());

        }
    }

    //CALCULAR FINES DE SEMANA
    public int diasHabiles(Calendar fechaInicial, Calendar fechaFinal) {
        int diffDays = 0;

        //Mientras la fecha inicial sea menor o igual que la fecha final se cuentan los dias
        while (fechaInicial.before(fechaFinal) || fechaInicial.equals(fechaFinal)) {

            if (fechaInicial.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || fechaInicial.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                //Se aumentan los dias de diferencia
                diffDays++;
                //Se le agrega un dia más, ya que los sabados y domingos no cuentan
                fechaSumada = fechaSumada.plusDays(1);
                fechaFinal = fechaSumada.toCalendar(Locale.getDefault());
            }
            //Se suma 1 dia para hacer la validacion del siguiente dia.
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
                Intent i = new Intent(MenuPrincipal.this, AyudaActivity.class);
                MenuPrincipal.this.startActivity(i);
                return true;

            case R.id.Acerca:
                //Toast.makeText(this,"ACERCA", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this,ContributorsActivity.class);
                this.startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}


