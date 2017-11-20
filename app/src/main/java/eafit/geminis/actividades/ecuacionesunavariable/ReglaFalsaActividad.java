package eafit.geminis.actividades.ecuacionesunavariable;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
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
import eafit.geminis.utilidades.ErrorMetodo;
import eafit.geminis.utilidades.Respuesta;
import eafit.geminis.utilidades.TipoRespuesta;

public class ReglaFalsaActividad extends ActividadBase {
    private TableLayout tabla;
    private TableRow filaEmcabezados;
    private EditText iteraciones,Xini,XSup,resultados,tolerancia;
    private AutoCompleteTextView funcion;
    private ToggleButton tipoError;
    private boolean esErrorAbsoluto = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ayudaAmostrar = "regla_falsa";
        setContentView(R.layout.actividad_regla_falsa);
        tabla = (TableLayout) findViewById(R.id.tabla_regla_falsa);
        filaEmcabezados = (TableRow) findViewById(R.id.fila_titulo_regla_falsa);
        funcion = (AutoCompleteTextView) findViewById(R.id.entrada_funcion_regla_falsa);
        iteraciones = (EditText) findViewById(R.id.entrada_iteraciones_regla_falsa);
        Xini = (EditText) findViewById(R.id.entrada_xinicial_regla_falsa);
        XSup = (EditText) findViewById(R.id.entrada_xsuperior_regla_falsa);
        resultados = (EditText) findViewById(R.id.resultado_regla_falsa);
        tolerancia = (EditText) findViewById(R.id.entrada_tol_regla_falsa);
        Button btCalcular = (Button) findViewById(R.id.bt_calcular_regla_falsa);
        btCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscar();
            }
        });
        tipoError = (ToggleButton) findViewById(R.id.tipo_error_regla_falsa);
        tipoError.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                esErrorAbsoluto = isChecked;
            }
        });
        recuperarFunciones(funcion);
        funcion.setThreshold(2);
    }
    private void buscar(){
        String stFuncion = funcion.getText().toString();
        if (!verificarEntrada(stFuncion,true)){
            Toast.makeText(contexto, ErrorMetodo.ERROR_ENTRADA_FUNCION,Toast.LENGTH_LONG).show();
            return;
        }
        String stIteraciones = iteraciones.getText().toString();
        if(!verificarEntrada(stIteraciones,false)){
            Toast.makeText(contexto,ErrorMetodo.ERROR_NITER_INCORRECTO,Toast.LENGTH_LONG).show();
            return;
        }
        String stXini = Xini.getText().toString();
        if(!verificarEntrada(stXini,false)){
            Toast.makeText(contexto,ErrorMetodo.ERROR_LIMITE_INFERIOR,Toast.LENGTH_LONG).show();
            return;
        }
        String stXSup = XSup.getText().toString();
        if(!verificarEntrada(stXSup,false)){
            Toast.makeText(contexto,ErrorMetodo.ERROR_LIMITE_Superior,Toast.LENGTH_LONG).show();
            return;
        }
        String stTolerancia = tolerancia.getText().toString();
        if(!verificarEntrada(stTolerancia,false)){
            Toast.makeText(contexto,ErrorMetodo.ERROR_TOLERANCIA,Toast.LENGTH_LONG).show();
            return;
        }
        new Evaluador().execute(stFuncion,stIteraciones,stXini,stXSup,stTolerancia,""+esErrorAbsoluto);
    }
    private class Evaluador extends AsyncTask<String,Void,Respuesta> {
        protected Respuesta doInBackground(String... params) {
            String funcion =params[0];
            int valIteraciones = Integer.parseInt(params[1]);
            BigDecimal valXini = new BigDecimal(params[2]);
            BigDecimal valXSup = new BigDecimal(params[3]);
            BigDecimal valTolerencia = new BigDecimal(params[4]);
            boolean valEsErrorAbsoluto = Boolean.parseBoolean(params[5]);
            return Biseccion.metodo(funcion,valXini,valXSup,valTolerencia,valIteraciones,valEsErrorAbsoluto);
        }
        protected void onPostExecute(Respuesta respuesta) {
            tratarRespuesta(respuesta,tabla,filaEmcabezados,resultados);
            if (respuesta.getTipo() != TipoRespuesta.Error) {
                guardarFuncion(funcion.getText().toString());
            }
        }
    }
}
