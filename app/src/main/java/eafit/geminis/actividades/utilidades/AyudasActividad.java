package eafit.geminis.actividades.utilidades;

import android.content.Intent;
import android.os.Bundle;
import eafit.geminis.R;
import eafit.geminis.actividades.ActividadBase;

public class AyudasActividad extends ActividadBase {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intentOriginal = getIntent();
        final String  ayudaElegida = intentOriginal.getStringExtra("ayuda");
        //Elegir ayuda a mostrar
        switch (ayudaElegida){
            case "inicio":
                setContentView(R.layout.ayuda_inicio);
                break;
            case "busquedas":
                setContentView(R.layout.ayuda_busquedas_incrementales);
                break;
            case "biseccion":
                setContentView(R.layout.ayuda_biseccion);
                break;
            case "regla_falsa":
                setContentView(R.layout.ayuda_regla_falsa);
                break;
            case "punto_fijo":
                setContentView(R.layout.ayuda_punto_fijo);
                break;
            case "newton":
                setContentView(R.layout.ayuda_newton);
                break;
            case "secante":
                setContentView(R.layout.ayuda_secante);
                break;
            case "raices_multiples":
                setContentView(R.layout.ayuda_raices_multiples);
                break;
            case "entrada_graficador":
                setContentView(R.layout.ayuda_entrada_graficador);
                break;
            case "graficador":
                setContentView(R.layout.ayuda_graficador);
                break;
            case "gauss_simple":
                setContentView(R.layout.ayuda_eliminacion_gauss_simple);
                break;
            case "pivoteo_parcial":
                setContentView(R.layout.ayuda_eliminacion_gauss_pivoteo);
                break;
            case "factorizacion_directa":
                setContentView(R.layout.ayuda_factorizacion_directa);
                break;
            case "metodos_iterativos":
                setContentView(R.layout.ayuda_metodos_iterativos);
                break;
            case "newton_diferencias":
                setContentView(R.layout.ayuda_newton_diferencias);
                break;
            case "lagrange":
                setContentView(R.layout.ayuda_lagrange);
                break;
            default:
                finish();
        }
    }
}
