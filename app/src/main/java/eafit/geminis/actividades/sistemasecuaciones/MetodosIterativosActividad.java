package eafit.geminis.actividades.sistemasecuaciones;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.math.BigDecimal;

import eafit.geminis.R;
import eafit.geminis.actividades.ActividadBase;
import eafit.geminis.metodos.sistemasecuaciones.MetodoIterativo;
import eafit.geminis.utilidades.ErrorMetodo;
import eafit.geminis.utilidades.Matriz;
import eafit.geminis.utilidades.Respuesta;
import eafit.geminis.utilidades.TipoIterativo;

public class MetodosIterativosActividad extends ActividadBase {
    private View restoEntrada;
    private Button btIngresar,btSalir,btCalcular;
    private Spinner spTipoIterativos;
    private TableLayout tablaEntrada, tablaSalida;
    private TableRow tituloEntrada, tituloSalida;
    private EditText edNroEcuaciones, edFactor;
    private LinearLayout salidasX;
    private int nroEcuaciones=0;
    private TipoIterativo tipoIterativo;
    private ToggleButton tipoError;
    private boolean esAbsoluto;
    private BigDecimal[][] ab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_metodos_iterativos);
        ayudaAmostrar="metodos_iterativos";
        restoEntrada = findViewById(R.id.entrada_sistema_ecuaciones);
        btIngresar = (Button) restoEntrada.findViewById(R.id.bt_ingresar_sistemas_ecuaciones);
        btSalir = (Button) findViewById(R.id.bt_salir_iterativos);
        btCalcular = (Button) restoEntrada.findViewById(R.id.bt_calcular_matriz);
        spTipoIterativos = (Spinner) findViewById(R.id.sp_tipo_iterativo);
        tablaEntrada = (TableLayout) restoEntrada.findViewById(R.id.tabla_ingreso_ecuaciones_lineales);
        tablaSalida = (TableLayout) findViewById(R.id.tabla_salida_iterativos);
        tituloEntrada = (TableRow) restoEntrada.findViewById(R.id.fila_titulo_matriz_entrada);
        tituloSalida = (TableRow) findViewById(R.id.fila_titulo__salida_iterativos);
        edNroEcuaciones = (EditText) restoEntrada.findViewById(R.id.et_nro_ecuaciones);
        edFactor = (EditText) findViewById(R.id.ed_factor_iterativo);
        salidasX = (LinearLayout) findViewById(R.id.salidas_x_iterativos);
        tipoError = (ToggleButton) findViewById(R.id.tipo_error_iterativos);
        tipoError.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                esAbsoluto = isChecked;
            }
        });
        spTipoIterativos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String seleccion = spTipoIterativos.getSelectedItem().toString();
                switch (seleccion){
                    case "Jacobi relajado":
                        tipoIterativo = TipoIterativo.JACOBI_RELAJADO;
                        break;
                    case "Gauss Seidel relajado":
                        tipoIterativo = TipoIterativo.GAUSS_SEIDEL_RELAJADO;
                        break;
                    default:

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
        btIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingresar();
            }
        });
    }
    private void limpiar(){
        /// TODO: 19/11/2017
    }
    private void calcular(){
        try {
            ab = crearAB(tablaEntrada,nroEcuaciones);
            BigDecimal W = new BigDecimal(edFactor.getText().toString());
            BigDecimal[] b = Matriz.obtenerVectorB(ab,nroEcuaciones);
            //TODO
            //Respuesta rp = MetodoIterativo.metodo(ab,b,W,,)
        } catch (Exception e) {
            Toast.makeText(contexto,ErrorMetodo.ERROR_ENTRADA_TABLA_SISTEMAS_ECUACIONES,Toast.LENGTH_LONG).show();
        }
    }
    private void ingresar(){
        limpiar();
        String nro = edNroEcuaciones.getText().toString();
        boolean operar = generarMatrizEntrada(nro,tablaEntrada,tituloEntrada);
        if (operar) {
            nroEcuaciones = Integer.parseInt(nro);
        }else {
            Toast.makeText(contexto, ErrorMetodo.ERROR_ENTRADA_NRO_ECUACIONES,Toast.LENGTH_LONG).show();
        }
    }
}
