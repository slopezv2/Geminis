package eafit.geminis.actividades.sistemasecuaciones;

import eafit.geminis.R;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import eafit.geminis.actividades.ActividadBase;

public class FactorizacionDirectaActividad extends ActividadBase {
    private View restoEntrada, restoSalida;
    private Button btIngresar,btSalir,btCalcular;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_factorizacion_directa_);
        ayudaAmostrar="factorizacion_directa";
        restoEntrada = findViewById(R.id.resto_tabla_entrada_ecuaciones);
        restoSalida = findViewById(R.id.resto_salida_ecuaciones_simple);
        btIngresar = (Button)restoEntrada.findViewById(R.id.bt_ingresar_sistemas_ecuaciones);
        btCalcular = (Button)restoEntrada.findViewById(R.id.bt_calcular_matriz);
        btSalir = (Button) restoSalida.findViewById(R.id.bt_salir_salida_gauss_simple);
    }
}
