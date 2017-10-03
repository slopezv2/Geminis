package eafit.geminis.actividades.ecuacionesunavariable;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.math.BigDecimal;

import eafit.geminis.R;
import eafit.geminis.actividades.ActividadBase;
import eafit.geminis.metodos.ecuacionesunavariable.Biseccion;
import eafit.geminis.metodos.ecuacionesunavariable.PuntoFijo;
import eafit.geminis.utilidades.ErrorMetodo;
import eafit.geminis.utilidades.Respuesta;

public class PuntoFijoActividad extends ActividadBase {
    public EditText entradaFx, entradaGx,entradaIteraciones, entradaPuntoInicial, entradaTolerancia;
    public boolean esAbsoluto = false;
    public EditText resultados;
    public TableLayout tabla;
    public TableRow encabezado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_punto_fijo);
        ayudaAmostrar="punto fijo";
        entradaFx = (EditText) findViewById(R.id.entrada_funcion_punto_fijo);
        entradaGx = (EditText) findViewById(R.id.entrada_funcion_g_punto_fijo);
        entradaIteraciones = (EditText) findViewById(R.id.entrada_iteraciones_punto_fijo);
        entradaPuntoInicial = (EditText) findViewById(R.id.entrada_xinicial_punto_fijo);
        entradaTolerancia = (EditText) findViewById(R.id.entrada_tol_punto_fijo);
        resultados = (EditText) findViewById(R.id.resultado_punto_fijo);
        tabla = (TableLayout) findViewById(R.id.tabla_punto_fijo);
        encabezado = (TableRow) findViewById(R.id.fila_titulo_punto_fijo);
        Button btCalcular = (Button) findViewById(R.id.bt_calcular_punto_fijo);
        ToggleButton btTipoError = (ToggleButton) findViewById(R.id.tipo_error_punto_fijo);
        btCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscar();
            }
        });
        btTipoError.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                esAbsoluto = isChecked;
            }
        });
    }
    private void buscar(){
        String stFuncion = entradaFx.getText().toString();
        if (!verificarEntrada(stFuncion,true)){
            Toast.makeText(contexto, ErrorMetodo.ERROR_ENTRADA_FUNCION,Toast.LENGTH_LONG).show();
            return;
        }
        String stFuncionG = entradaGx.getText().toString();
        if (!verificarEntrada(stFuncionG,true)){
            Toast.makeText(contexto, ErrorMetodo.ERROR_ENTRADA_FUNCION_G,Toast.LENGTH_LONG).show();
            return;
        }
        String stIteraciones = entradaIteraciones.getText().toString();
        if(!verificarEntrada(stIteraciones,false)){
            Toast.makeText(contexto,ErrorMetodo.ERROR_NITER_INCORRECTO,Toast.LENGTH_LONG).show();
            return;
        }
        String stXini = entradaPuntoInicial.getText().toString();
        if(!verificarEntrada(stXini,false)){
            Toast.makeText(contexto,ErrorMetodo.ERROR_LIMITE_INFERIOR,Toast.LENGTH_LONG).show();
            return;
        }
        String stTolerancia = entradaTolerancia.getText().toString();
        if(!verificarEntrada(stTolerancia,false)){
            Toast.makeText(contexto,ErrorMetodo.ERROR_TOLERANCIA,Toast.LENGTH_LONG).show();
            return;
        }
        new Evaluador().execute(stFuncion,stFuncionG, stIteraciones,stXini,stTolerancia,""+esAbsoluto);
    }
    private class Evaluador extends AsyncTask<String,Void,Respuesta> {
        protected Respuesta doInBackground(String... params) {
            String funcion =params[0];
            String funcionG = params[1];
            int valIteraciones = Integer.parseInt(params[2]);
            BigDecimal valXini = new BigDecimal(params[3]);
            BigDecimal valTolerencia = new BigDecimal(params[4]);
            boolean valEsErrorAbsoluto = Boolean.parseBoolean(params[5]);
            return PuntoFijo.metodo(funcion,funcionG,valXini,valTolerencia,valIteraciones,valEsErrorAbsoluto);
        }
        protected void onPostExecute(Respuesta respuesta) {
            tratarRespuesta(respuesta,tabla,encabezado,resultados);
        }
    }
}
