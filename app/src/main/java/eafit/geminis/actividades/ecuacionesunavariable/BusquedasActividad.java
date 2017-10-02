package eafit.geminis.actividades.ecuacionesunavariable;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.ArrayList;

import eafit.geminis.R;
import eafit.geminis.actividades.ActividadBase;
import eafit.geminis.metodos.ecuacionesunavariable.BusquedaIncremental;
import eafit.geminis.utilidades.ErrorMetodo;
import eafit.geminis.utilidades.Respuesta;

public class BusquedasActividad extends ActividadBase {
    private TableLayout tabla;
    private TableRow filaEmcabezados;
    private EditText funcion,iteraciones,delta,valorInicial,resultados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ayudaAmostrar = "biseccion";
        setContentView(R.layout.actividad_busquedas);
        this.tabla = (TableLayout)findViewById(R.id.tabla_busquedas);
        this.filaEmcabezados = (TableRow)findViewById(R.id.fila_titulo_busquedas);
        Button btCalcular = (Button) findViewById(R.id.bt_calcular_busquedas);
        funcion = (EditText) findViewById(R.id.entrada_funcion_busquedas);
        iteraciones = (EditText) findViewById(R.id.entrada_iteraciones_busquedas);
        delta = (EditText) findViewById(R.id.entrada_delta_busquedas);
        valorInicial = (EditText) findViewById(R.id.entrada_valor_inicial_busquedas);
        resultados = (EditText) findViewById(R.id.resultado_busquedas);
        btCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscar();
            }
        });
    }
    private void buscar(){
        String stFuncion = funcion.getText().toString();
        if (!verificarEntrada(stFuncion,true)){
            Toast.makeText(contexto,ErrorMetodo.ERROR_ENTRADA_FUNCION,Toast.LENGTH_LONG).show();
            return;
        }
        String stIteraciones = iteraciones.getText().toString();
        if(!verificarEntrada(stIteraciones,false)){
            Toast.makeText(contexto,ErrorMetodo.ERROR_NITER_INCORRECTO,Toast.LENGTH_LONG).show();
            return;
        }
        String stDelta = delta.getText().toString();
        if(!verificarEntrada(stDelta,false)){
            Toast.makeText(contexto,ErrorMetodo.ERROR_DELTA_INCORRECTO,Toast.LENGTH_LONG).show();
            return;
        }
        String stValorInicial = valorInicial.getText().toString();
        if(!verificarEntrada(stValorInicial,false)){
            Toast.makeText(contexto,ErrorMetodo.ERROR_VALOR_INICIAL,Toast.LENGTH_LONG).show();
            return;
        }
        new Evaluador().execute(stFuncion,stIteraciones,stDelta,stValorInicial);
    }



    private class Evaluador extends AsyncTask<String,Void,Respuesta> {


        protected Respuesta doInBackground(String... params) {
            String funcion =params[0];
            int iteraciones = Integer.parseInt(params[1]);
            BigDecimal delta = new BigDecimal(params[2]);
            BigDecimal valorInicial = new BigDecimal(params[3]);
            return BusquedaIncremental.metodo(funcion,valorInicial,delta,iteraciones);
        }


        protected void onPostExecute(Respuesta respuesta) {
            tratarRespuesta(respuesta,tabla,filaEmcabezados,resultados);
        }
    }
}
