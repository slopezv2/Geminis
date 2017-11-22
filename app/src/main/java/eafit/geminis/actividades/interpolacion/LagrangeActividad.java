package eafit.geminis.actividades.interpolacion;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;

import eafit.geminis.R;
import eafit.geminis.actividades.ActividadBase;
import eafit.geminis.metodos.interpolacion.Lagrange;
import eafit.geminis.metodos.interpolacion.NewtonDiferenciasDivididas;
import eafit.geminis.utilidades.ErrorMetodo;

public class LagrangeActividad extends ActividadBase {
    private EditText entradaPuntos, entradaPuntoAdicional;
    private Button btIngresar,btCalcular,btSalir, btEvaluar;
    private TableLayout tablaEntrada;
    private TableRow tituloTablaEntrada;
    private TextView salidaPolinomio,mensajePuntoAdicional;
    private int nroPuntos=0;
    private String polinomio="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_lagrange);
        ayudaAmostrar="lagrange";
        entradaPuntos = (EditText) findViewById(R.id.ed_puntos_lagrange);
        entradaPuntoAdicional = (EditText) findViewById(R.id.ed_punto_adicional_interpolacion);
        mensajePuntoAdicional = (TextView) findViewById(R.id.tx_punto_adicional_interpolacion);
        btIngresar = (Button) findViewById(R.id.bt_ingresar_lagrange);
        btCalcular = (Button) findViewById(R.id.bt_calcular_lagrange);
        btSalir = (Button) findViewById(R.id.bt_salir_lagrange);
        btEvaluar = (Button) findViewById(R.id.bt_evaluar_interpolacion);
        tablaEntrada = (TableLayout) findViewById(R.id.tabla_ingreso_lagrange);
        tituloTablaEntrada = (TableRow) findViewById(R.id.fila_titulo_entrada_lagrange);
        salidaPolinomio = (TextView) findViewById(R.id.salida_polinomonio_lagrange);
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
        btEvaluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evaluar();
            }
        });
    }
    private void ingresar(){
        limpiar();
        String puntos = entradaPuntos.getText().toString();
        try{
            nroPuntos = Integer.parseInt(puntos);
            if (nroPuntos<=0){
                throw new Exception(ErrorMetodo.ERROR_ENTRADA_PUNTOS);
            }
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(contexto, ErrorMetodo.ERROR_ENTRADA_PUNTOS,Toast.LENGTH_LONG).show();
            return;
        }
        generarMatrizE(nroPuntos,tablaEntrada,tituloTablaEntrada);
    }
    private void calcular(){
        BigDecimal[][] puntos = null;
        try{
            puntos = leerEntrada(tablaEntrada,nroPuntos);
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(contexto,ErrorMetodo.ERROR_ENTRADA_TABLA_SISTEMAS_ECUACIONES,Toast.LENGTH_LONG).show();
            return;
        }
        try {
            polinomio = Lagrange.metodo(puntos,nroPuntos);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(contexto,e.getMessage(),Toast.LENGTH_LONG).show();
            return;
        }
        salidaPolinomio.setText(polinomio);
        //TODO
    }
    private void limpiar(){
        salidaPolinomio.setText("");
        polinomio = "";
        btEvaluar.setVisibility(View.GONE);
        entradaPuntoAdicional.setVisibility(View.GONE);
        mensajePuntoAdicional.setVisibility(View.GONE);
    }
    private void evaluar(){

    }
}
