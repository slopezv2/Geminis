package eafit.geminis.actividades.interpolacion;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import eafit.geminis.R;
import eafit.geminis.actividades.ActividadBase;

public class NewtonDiferenciasActividad extends ActividadBase {
    private EditText entradaPuntos;
    private Button btIngresar,btCalcular,btSalir;
    private TableLayout tablaEntrada,tablaSalida;
    private TableRow tituloTablaEntrada,tituloTablaSalida;
    private TextView salidaPolinomio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_newton_diferencias);
        ayudaAmostrar="newton_diferencias";
        entradaPuntos = (EditText) findViewById(R.id.ed_puntos_newton_diferencias);
        btIngresar = (Button) findViewById(R.id.bt_ingresar_newton_diferencias);
        btCalcular = (Button) findViewById(R.id.bt_calcular_newton_diferencias);
        btSalir = (Button) findViewById(R.id.bt_salir_newton_diferencias);
        tablaEntrada = (TableLayout) findViewById(R.id.tabla_ingreso_newton_diferencias);
        tablaSalida = (TableLayout) findViewById(R.id.tabla_salida_newton_diferencias);
        tituloTablaEntrada = (TableRow) findViewById(R.id.fila_titulo_entrada_newton_diferencias);
        tituloTablaSalida = (TableRow) findViewById(R.id.fila_salida_newton_diferencias);
        salidaPolinomio = (TextView) findViewById(R.id.salida_polinomonio_newton_diferencias);
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
    }
    private void ingresar(){

    }
    private void calcular(){
        
    }
}
