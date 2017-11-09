package eafit.geminis.actividades.sistemasecuaciones;

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
import eafit.geminis.metodos.sistemasecuaciones.EliminacionGaussianaSimple;
import eafit.geminis.metodos.sistemasecuaciones.GaussConPivoteo;
import eafit.geminis.utilidades.ErrorMetodo;
import eafit.geminis.utilidades.TipoPivoteo;

public class PivoteoGaussActividad extends ActividadBase {
    private TableLayout tabla, tablaSalida;
    private Button btIngresar, btSiguiente;
    private TextView tvIteracion;
    private EditText edNroEcuaciones;
    private TableRow titulo, tituloSalida;
    private int nroEcuaciones=0;
    private int actual = 1;
    private Button salir, calcular;
    private BigDecimal[][] ab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_pivoteo_gauss);
        ayudaAmostrar = "pivoteo_parcial";
        // el include con la entrada
        View resto = findViewById(R.id.resto_tabla_entrada_ecuaciones);
        // Inicializacion elementos
        tablaSalida = (TableLayout) findViewById(R.id.tabla_resultados_ab);
        tituloSalida = (TableRow) findViewById(R.id.encabezado_tabla_resultados_ab);
        tvIteracion = (TextView) findViewById(R.id.iteracion_gauss_pivote);
        salir = (Button) findViewById(R.id.bt_salir_pivote);
        btIngresar = (Button) resto.findViewById(R.id.bt_ingresar_sistemas_ecuaciones);
        edNroEcuaciones = (EditText) resto.findViewById(R.id.et_nro_ecuaciones);
        tabla = (TableLayout) resto.findViewById(R.id.tabla_ingreso_ecuaciones_lineales);
        titulo = (TableRow) resto.findViewById(R.id.fila_titulo_matriz_entrada);
        btSiguiente = (Button) findViewById(R.id.bt_siguiente_pivoteo);
        calcular = (Button)resto.findViewById(R.id.bt_calcular_matriz);
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcularResultado();
            }
        });
        btIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nro = edNroEcuaciones.getText().toString();
                boolean operar = generarMatrizEntrada(nro,tabla,titulo);
                if (operar) {
                    tabla.setVisibility(View.VISIBLE);
                    nroEcuaciones = Integer.parseInt(nro);
                    calcular.setVisibility(View.VISIBLE);
                }
            }
        });
        btSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                siguiente();
            }
        });
    }

    private void siguiente() {
        //TODO
        if (actual<ab.length) {
            try {
                ab = GaussConPivoteo.metodo(ab, actual, ab.length - 1, TipoPivoteo.PARCIAL);
            } catch (Exception e) {
                Toast.makeText(contexto, e.getMessage(), Toast.LENGTH_LONG).show();
                btSiguiente.setEnabled(false);
                return;
            }
            escribirSalidaAB(ab, tablaSalida, tituloSalida);
            actual++;
            tvIteracion.setText("Iteración: "+actual);
            if(actual==ab.length-1){
                //TODO
                //despeje y mostrar ecuaciones
            }
        }else {
            btSiguiente.setEnabled(false);
        }
    }

    private void calcularResultado() {
        //TODO
        this.actual = 1;
        tvIteracion.setText("Iteración: "+actual);
        btSiguiente.setEnabled(true);
        try {
            ab = crearAB(tabla,nroEcuaciones);
        } catch (Exception e) {
            Toast.makeText(contexto, ErrorMetodo.ERROR_ENTRADA_TABLA_SISTEMAS_ECUACIONES,Toast.LENGTH_LONG).show();
            return;
        }
        escribirSalidaAB(ab,tablaSalida,tituloSalida);
        btSiguiente.setVisibility(View.VISIBLE);
        tablaSalida.setVisibility(View.VISIBLE);
    }
}
