package eafit.geminis.actividades.ecuacionesunavariable;

import android.app.Activity;
import android.os.Bundle;

import eafit.geminis.R;
import eafit.geminis.actividades.ActividadBase;

public class NewtonActividad extends ActividadBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_newton);
        ayudaAmostrar = "newton";
    }
}
