package eafit.geminis.actividades.interpolacion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import eafit.geminis.R;
import eafit.geminis.actividades.ActividadBase;
import eafit.geminis.actividades.sistemasecuaciones.MenuSistemasEcuacionesActividad;
import eafit.geminis.actividades.sistemasecuaciones.MetodosIterativosActividad;

public class MenuInterpolacionActividad extends ActividadBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_menu_interpolacion);
        ayudaAmostrar="menu interpolacion";
        Button btIrNewtonDiferencias = (Button) findViewById(R.id.bt_ir_newton_diferencias);
        btIrNewtonDiferencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuInterpolacionActividad.this,NewtonDiferenciasActividad.class);
                startActivity(intent);
            }
        });
    }
}
