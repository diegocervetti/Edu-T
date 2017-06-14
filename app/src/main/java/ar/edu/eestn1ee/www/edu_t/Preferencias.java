package ar.edu.eestn1ee.www.edu_t;


import android.content.Context;
import android.content.SharedPreferences;

public class Preferencias {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context contexto;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "edu-t-calculo-fecha";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    public Preferencias(Context context) {
        this.contexto = context;
        pref = contexto.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setPrimerUso(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean esPrimerUso() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }
}
