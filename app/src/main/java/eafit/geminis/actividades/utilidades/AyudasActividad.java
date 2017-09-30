package eafit.geminis.actividades.utilidades;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import eafit.geminis.R;
import eafit.geminis.actividades.ActividadBase;

public class AyudasActividad extends ActividadBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intentOriginal = getIntent();
        final String  ayudaElegida = intentOriginal.getStringExtra("ayuda");
        switch (ayudaElegida){
            case "busquedas":
                setContentView(R.layout.ayuda_biseccion);
                break;
            case "biseccion":
                setContentView(R.layout.ayuda_biseccion);
                break;
            default:
                finish();
        }
    }
}
