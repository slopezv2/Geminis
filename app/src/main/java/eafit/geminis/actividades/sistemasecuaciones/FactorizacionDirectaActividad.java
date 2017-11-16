package eafit.geminis.actividades.sistemasecuaciones;

import eafit.geminis.R;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.math.BigDecimal;

import eafit.geminis.actividades.ActividadBase;
import eafit.geminis.utilidades.TipoPivoteo;

public class FactorizacionDirectaActividad extends ActividadBase {
    private View restoEntrada, restoSalida;
    private Button btIngresar,btSalir,btCalcular;
    private Spinner spTipoFactorizacion;
    private TableLayout tablaEntrada, tablaSalida;
    private TableRow tituloEntrada, tituloSalida;
    private BigDecimal[][] ab;
    private LinearLayout salidasX;
    private int nroEcuaciones=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_factorizacion_directa_);
        ayudaAmostrar="factorizacion_directa";
        restoEntrada = findViewById(R.id.resto_tabla_entrada_ecuaciones);
        restoSalida = findViewById(R.id.resto_salida_ecuaciones_simple);
        tablaEntrada = (TableLayout) restoEntrada.findViewById(R.id.tabla_ingreso_ecuaciones_lineales);
        tablaSalida = (TableLayout) restoSalida.findViewById(R.id.tabla_resultados_ab);
        btIngresar = (Button)restoEntrada.findViewById(R.id.bt_ingresar_sistemas_ecuaciones);
        btCalcular = (Button)restoEntrada.findViewById(R.id.bt_calcular_matriz);
        btSalir = (Button) restoSalida.findViewById(R.id.bt_salir_salida_gauss_simple);
        spTipoFactorizacion = (Spinner) findViewById(R.id.sp_tipo_factorizacion);
        btIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingresar();
            }
        });
        btCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcular();
            }
        });
        btSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        spTipoFactorizacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String seleccion = spTipoFactorizacion.getSelectedItem().toString();
                switch (seleccion){
                    case "Gauss simple":

                        break;
                    case "Gauss pivoteo":

                        break;
                    case "Croult":

                        break;
                    case "Doolittle":
                        break;
                    case "Cholesky":

                        break;
                    default:

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void calcular(){
        //TODO
    }
    private void ingresar(){
        //TODO
    }
}
