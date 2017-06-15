package ar.edu.eestn1ee.www.edu_t;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class Preferencias {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context contexto;

    // shareamos de manera privada, no compartimos nada con otra App
    int PRIVATE_MODE = 0;

    // nombre del archivo
    private static final String PREF_NAME = "edu-t-calculo-fecha";

    private static final String SE_USO = "seUso";

    public Preferencias(Context context) {
        this.contexto = context;
        pref = contexto.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setPrimerUso(String esPrimerUso) {
        editor.putString(SE_USO, esPrimerUso);
        editor.commit();
    }

    public String esPrimerUso() {
        return pref.getString(SE_USO , "NO");
    }
}
