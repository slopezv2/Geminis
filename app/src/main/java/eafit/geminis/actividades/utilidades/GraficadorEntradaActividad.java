package eafit.geminis.actividades.utilidades;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

import eafit.geminis.R;
import eafit.geminis.actividades.ActividadBase;

public class GraficadorEntradaActividad extends ActividadBase {
    private LinearLayout layout;
    private ArrayList<String> funciones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        funciones = new ArrayList<String>();
        mensajeAyuda = "entrada_graficador";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_graficador_entrada);
        Button bt_graficar = (Button) findViewById(R.id.bt_graficar);
        bt_graficar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(GraficadorEntradaActividad.this,GraficadorActividad.class);
                startActivity(intent);
            }
        });
    }

}
