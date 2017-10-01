package eafit.geminis.actividades.sistemasecuaciones;

import android.app.Activity;
import android.os.Bundle;

import eafit.geminis.R;
import eafit.geminis.actividades.ActividadBase;

public class MenuSistemasEcuacionesActividad extends ActividadBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_menu_sistemas_ecuaciones);
        ayudaAmostrar = "menu sistemas ecuaciones";
    }
}
