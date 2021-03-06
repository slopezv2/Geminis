package eafit.geminis.actividades.interpolacion;


import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
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
import eafit.geminis.metodos.interpolacion.NewtonDiferenciasDivididas;
import eafit.geminis.utilidades.ErrorMetodo;
import eafit.geminis.utilidades.EvalExEval;

public class NewtonDiferenciasActividad extends ActividadBase {
    private EditText entradaPuntos, entradaPuntoAdicional;
    private Button btIngresar,btCalcular,btSalir, btEvaluar;
    private TableLayout tablaEntrada,tablaSalida;
    private TableRow tituloTablaEntrada,tituloTablaSalida;
    private TextView salidaPolinomio,mensajePuntoAdicional, salidaPuntoAdicional;
    private int nroPuntos=0;
    private String polinomio="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_newton_diferencias);
        ayudaAmostrar="newton_diferencias";
        entradaPuntos = (EditText) findViewById(R.id.ed_puntos_newton_diferencias);
        entradaPuntoAdicional = (EditText) findViewById(R.id.ed_punto_adicional_interpolacion);
        mensajePuntoAdicional = (TextView) findViewById(R.id.tx_punto_adicional_interpolacion);
        salidaPuntoAdicional = (TextView) findViewById(R.id.salida_punto_adicional_interpolacion);
        btIngresar = (Button) findViewById(R.id.bt_ingresar_newton_diferencias);
        btCalcular = (Button) findViewById(R.id.bt_calcular_newton_diferencias);
        btEvaluar = (Button) findViewById(R.id.bt_evaluar_interpolacion);
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
        BigDecimal[][] res = null;
        try{
            puntos = leerEntrada(tablaEntrada,nroPuntos);
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(contexto,ErrorMetodo.ERROR_ENTRADA_TABLA_SISTEMAS_ECUACIONES,Toast.LENGTH_LONG).show();
            return;
        }
        try {
            res = NewtonDiferenciasDivididas.metodo(puntos,nroPuntos);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(contexto,e.getMessage(),Toast.LENGTH_LONG).show();
            return;
        }
        escribirSalida(res,tablaSalida,tituloTablaSalida,nroPuntos);
        polinomio = NewtonDiferenciasDivididas.obtenerPolinomio(res,nroPuntos);
        salidaPolinomio.setText(polinomio);
        btEvaluar.setVisibility(View.VISIBLE);
        entradaPuntoAdicional.setVisibility(View.VISIBLE);
        mensajePuntoAdicional.setVisibility(View.VISIBLE);
    }
    private void escribirSalida(BigDecimal[][] resultados, TableLayout tabla, TableRow encabezado,int n){
        encabezado.removeAllViews();
        tabla.removeAllViews();
        TextView tX = new TextView(contexto);
        tX.setTextColor(Color.BLACK);
        tX.setText("xi");

        tX.setTextSize(16);
        tX.setPadding(5,5,5,5);
        tX.setBackgroundResource(R.drawable.border);
        tX.setGravity(Gravity.CENTER);

        encabezado.addView(tX);
        tX = new TextView(contexto);
        tX.setTextColor(Color.BLACK);
        tX.setText("Fxi");

        tX.setTextSize(16);
        tX.setPadding(5,5,5,5);
        tX.setBackgroundResource(R.drawable.border);
        tX.setGravity(Gravity.CENTER);

        encabezado.addView(tX);
        for(int i =1; i < n;++i){
            tX = new TextView(contexto);
            tX.setTextColor(Color.BLACK);
            tX.setText(i+"");

            tX.setTextSize(16);
            tX.setPadding(5,5,5,5);
            tX.setBackgroundResource(R.drawable.border);
            tX.setGravity(Gravity.CENTER);

            encabezado.addView(tX);
        }
        tabla.addView(encabezado);
        for(int i = 0; i < resultados.length;++i){
            encabezado = new TableRow(contexto);
            for(int j = 0; j < i+2; ++j){
                tX = new TextView(contexto);
                tX.setTextColor(Color.BLACK);
                tX.setText(resultados[i][j].toString());

                tX.setTextSize(16);
                tX.setPadding(5,5,5,5);
                tX.setBackgroundResource(R.drawable.border);
                tX.setGravity(Gravity.CENTER);

                encabezado.addView(tX);
            }
            tabla.addView(encabezado);
        }
    }
    private void limpiar(){
        salidaPolinomio.setText("");
        polinomio = "";
        btEvaluar.setVisibility(View.GONE);
        entradaPuntoAdicional.setVisibility(View.GONE);
        mensajePuntoAdicional.setVisibility(View.GONE);
        salidaPuntoAdicional.setText("");
    }
    private void evaluar(){
        String stPunto = entradaPuntoAdicional.getText().toString();
        BigDecimal punto = BigDecimal.ZERO;
        try {
            punto = new BigDecimal(stPunto);
        }catch (Exception e){
            Toast.makeText(contexto,ErrorMetodo.ERROR_VALOR_NUMERICO,Toast.LENGTH_LONG).show();
            return;
        }
        EvalExEval evaluador = new EvalExEval();
        try{
            BigDecimal res = evaluador.evaluar(polinomio,punto,true);
            salidaPuntoAdicional.setText("Fx= "+res.toString());
        }catch (Exception e){
            Toast.makeText(contexto,e.getMessage(),Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
