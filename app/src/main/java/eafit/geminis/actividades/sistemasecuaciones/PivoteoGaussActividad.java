package eafit.geminis.actividades.sistemasecuaciones;

import android.app.Activity;
import android.os.Bundle;
import eafit.geminis.R;
import eafit.geminis.actividades.ActividadBase;

public class PivoteoGaussActividad extends ActividadBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_pivoteo_gauss);
        ayudaAmostrar = "pivoteo_parcial";

    }
}
