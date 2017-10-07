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
import eafit.geminis.metodos.ecuacionesunavariable.RaizMultiple;
import eafit.geminis.utilidades.ErrorMetodo;
import eafit.geminis.utilidades.Respuesta;

public class RaicesMultiplesActividad extends ActividadBase {
    private EditText entradaFx, entradaFPx,entradaF2Px,entradaIteraciones, entradaPuntoInicial, entradaTolerancia;
    private boolean esAbsoluto = false;
    private EditText resultados;
    private TableLayout tabla;
    private TableRow encabezado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_raices_multiples);
        ayudaAmostrar = "raices_multiples";
        entradaFx = (EditText) findViewById(R.id.entrada_funcion_raices_multiples);
        entradaFPx = (EditText) findViewById(R.id.entrada_funcion_p_raices_multiples);
        entradaF2Px = (EditText) findViewById(R.id.entrada_funcion_p_2_raices_multiples);
        entradaIteraciones = (EditText) findViewById(R.id.entrada_iteraciones_raices_multiples);
        entradaPuntoInicial = (EditText) findViewById(R.id.entrada_xinicial_raices_multiples);
        entradaTolerancia = (EditText) findViewById(R.id.entrada_tol_raices_multiples);
        resultados = (EditText) findViewById(R.id.resultado_raices_multiples);
        tabla = (TableLayout) findViewById(R.id.tabla_raices_multiples);
        encabezado = (TableRow) findViewById(R.id.fila_titulo_raices_multiples);
        Button btCalcular = (Button) findViewById(R.id.bt_calcular_raices_multiples);
        ToggleButton btTipoError = (ToggleButton) findViewById(R.id.tipo_error_raices_multiples);
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
        String stFuncionP = entradaFPx.getText().toString();
        if (!verificarEntrada(stFuncionP,true)){
            Toast.makeText(contexto, ErrorMetodo.ERROR_ENTRADA_FUNCION_DF,Toast.LENGTH_LONG).show();
            return;
        }
        String stFuncion2P = entradaF2Px.getText().toString();
        if (!verificarEntrada(stFuncion2P,true)){
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
        new Evaluador().execute(stFuncion,stFuncionP,stFuncion2P, stIteraciones,stXini,stTolerancia,""+esAbsoluto);
    }
    private class Evaluador extends AsyncTask<String,Void,Respuesta> {
        protected Respuesta doInBackground(String... params) {
            String funcion =params[0];
            String funcionP = params[1];
            String funcion2P = params[2];
            int valIteraciones = Integer.parseInt(params[3]);
            BigDecimal valXini = new BigDecimal(params[4]);
            BigDecimal valTolerencia = new BigDecimal(params[5]);
            boolean valEsErrorAbsoluto = Boolean.parseBoolean(params[6]);
            return RaizMultiple.metodo(funcion,funcionP,funcion2P,valXini,valTolerencia,valIteraciones,valEsErrorAbsoluto);
        }
        protected void onPostExecute(Respuesta respuesta) {
            tratarRespuesta(respuesta,tabla,encabezado,resultados);
        }
    }
}
