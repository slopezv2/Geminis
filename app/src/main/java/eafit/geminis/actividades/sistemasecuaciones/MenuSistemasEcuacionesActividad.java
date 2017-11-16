package eafit.geminis.actividades.sistemasecuaciones;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import eafit.geminis.R;
import eafit.geminis.actividades.ActividadBase;

public class MenuSistemasEcuacionesActividad extends ActividadBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_menu_sistemas_ecuaciones);
        ayudaAmostrar = "menu sistemas ecuaciones";
        Button irEliminacionGaussianaSimple = (Button) findViewById(R.id.bt_ir_gauss_simple);
        irEliminacionGaussianaSimple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuSistemasEcuacionesActividad.this,EliminacionGaussianaSimpleActividad.class);
                startActivity(intent);
            }
        });
        Button irPivoteo = (Button) findViewById(R.id.bt_ir_gauss_pivoteo);
        irPivoteo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuSistemasEcuacionesActividad.this,PivoteoGaussActividad.class);
                startActivity(intent);
            }
        });
        Button irFactorizacion = (Button) findViewById(R.id.bt_ir_factorizacion_directa);
        irFactorizacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuSistemasEcuacionesActividad.this,FactorizacionDirectaActividad.class);
                startActivity(intent);
            }
        });
    }
}
