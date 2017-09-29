package eafit.geminis.actividades.ecuacionesunavariable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import eafit.geminis.R;
import eafit.geminis.actividades.ActividadBase;

public class MenuUnaVariableActividad extends ActividadBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_menu_una_variable);
        Button btBusquedas = (Button) findViewById(R.id.bt_ir_busquedas);
        btBusquedas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuUnaVariableActividad.this,BusquedasActividad.class);
                startActivity(intent);
            }
        });
        Button btBiseccion = (Button) findViewById(R.id.bt_ir_biseccion);
        btBiseccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuUnaVariableActividad.this,BiseccionActividad.class);
                startActivity(intent);
            }
        });
    }
}
