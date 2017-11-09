package eafit.geminis.actividades.sistemasecuaciones;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.math.BigDecimal;

import eafit.geminis.R;
import eafit.geminis.actividades.ActividadBase;

public class PivoteoGaussActividad extends ActividadBase {
    private TableLayout tabla, tablaSalida;
    private Button btIngresar, btSiguiente;
    private EditText edNroEcuaciones;
    private TableRow titulo, tituloSalida;
    private int nroEcuaciones=0;
    private int actual = 0;
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
                calcularResultado(actual);
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
    }

    private void calcularResultado(int actual) {
        btSiguiente.setVisibility(View.VISIBLE);
        tablaSalida.setVisibility(View.VISIBLE);
    }
}
