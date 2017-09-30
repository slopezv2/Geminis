package eafit.geminis.actividades.ecuacionesunavariable;

import android.app.Activity;
import android.os.Bundle;

import eafit.geminis.R;
import eafit.geminis.actividades.ActividadBase;

public class ReglaFalsaActividad extends ActividadBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ayudaAmostrar ="regla falsa";
        setContentView(R.layout.actividad_regla_falsa);
    }
}
