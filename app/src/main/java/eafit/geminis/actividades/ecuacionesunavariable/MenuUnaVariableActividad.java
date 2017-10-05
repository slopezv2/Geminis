package eafit.geminis.actividades.ecuacionesunavariable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import eafit.geminis.R;
import eafit.geminis.actividades.ActividadBase;
import eafit.geminis.metodos.ecuacionesunavariable.ReglaFalsa;

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
        Button btReglaFalsa = (Button) findViewById(R.id.bt_ir_regla_falsa);
        btReglaFalsa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuUnaVariableActividad.this,ReglaFalsaActividad.class);
                startActivity(intent);
            }
        });
        Button btPuntoFijo = (Button) findViewById(R.id.bt_ir_punto_fijo);
        btPuntoFijo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuUnaVariableActividad.this,PuntoFijoActividad.class);
                startActivity(intent);
            }
        });
        Button btNewton = (Button) findViewById(R.id.bt_ir_newton);
        btNewton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuUnaVariableActividad.this,NewtonActividad.class);
                startActivity(intent);
            }
        });
        Button btSecante = (Button) findViewById(R.id.bt_ir_secante);
        btSecante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuUnaVariableActividad.this,SecanteActividad.class);
                startActivity(intent);
            }
        });
        Button btRaices = (Button) findViewById(R.id.bt_ir_raices_multiples);
        btRaices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuUnaVariableActividad.this,RaicesMultiplesActividad.class);
                startActivity(intent);
            }
        });
    }
}
