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
import eafit.geminis.metodos.ecuacionesunavariable.Newton;
import eafit.geminis.metodos.ecuacionesunavariable.Secante;
import eafit.geminis.utilidades.ErrorMetodo;
import eafit.geminis.utilidades.Respuesta;

public class SecanteActividad extends ActividadBase {
    public EditText entradaFx, entradaPuntoFinal,entradaIteraciones, entradaPuntoInicial, entradaTolerancia;
    public boolean esAbsoluto = false;
    public EditText resultados;
    public TableLayout tabla;
    public TableRow encabezado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_secante);
        ayudaAmostrar = "secante";
        entradaFx = (EditText) findViewById(R.id.entrada_funcion_secante);
        entradaPuntoFinal = (EditText) findViewById(R.id.entrada_xsuperior_secante);
        entradaIteraciones = (EditText) findViewById(R.id.entrada_iteraciones_secante);
        entradaPuntoInicial = (EditText) findViewById(R.id.entrada_xinicial_secante);
        entradaTolerancia = (EditText) findViewById(R.id.entrada_tol_secante);
        resultados = (EditText) findViewById(R.id.resultado_secante);
        tabla = (TableLayout) findViewById(R.id.tabla_secante);
        encabezado = (TableRow) findViewById(R.id.fila_titulo_secante);
        Button btCalcular = (Button) findViewById(R.id.bt_calcular_secante);
        ToggleButton btTipoError = (ToggleButton) findViewById(R.id.tipo_error_secante);
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
        String stFuncionG = entradaPuntoFinal.getText().toString();
        if (!verificarEntrada(stFuncionG,false)){
            Toast.makeText(contexto, ErrorMetodo.ERROR_ENTRADA_FUNCION_DF,Toast.LENGTH_LONG).show();
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
            BigDecimal xSup = new BigDecimal(params[1]);
            int valIteraciones = Integer.parseInt(params[2]);
            BigDecimal Xini = new BigDecimal(params[3]);
            BigDecimal valTolerencia = new BigDecimal(params[4]);
            boolean valEsErrorAbsoluto = Boolean.parseBoolean(params[5]);
            return Secante.metodo(funcion,Xini,xSup,valTolerencia,valIteraciones,valEsErrorAbsoluto);
        }
        protected void onPostExecute(Respuesta respuesta) {
            tratarRespuesta(respuesta,tabla,encabezado,resultados);
        }
    }
}
