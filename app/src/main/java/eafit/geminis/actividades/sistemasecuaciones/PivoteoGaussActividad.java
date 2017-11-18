package eafit.geminis.actividades.sistemasecuaciones;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import eafit.geminis.utilidades.Matriz;
import eafit.geminis.utilidades.MatrizMarca;
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
    private LinearLayout salidasX;
    private Spinner spOpciones;
    private TipoPivoteo tipoPivoteo = TipoPivoteo.PARCIAL;
    private int[] marcas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_pivoteo_gauss);
        ayudaAmostrar = "pivoteo_parcial";
        // el include con la entrada
        View resto = findViewById(R.id.resto_tabla_entrada_ecuaciones);
        // Inicializacion elementos
        spOpciones = (Spinner) findViewById(R.id.sp_opciones);
        spOpciones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String seleccion = spOpciones.getSelectedItem().toString();
                switch (seleccion){
                    case "Pivoteo Parcial":
                        tipoPivoteo = TipoPivoteo.PARCIAL;
                        break;
                    case "Pivoteo Total":
                        tipoPivoteo = TipoPivoteo.TOTAL;
                        break;
                        default:

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        tablaSalida = (TableLayout) findViewById(R.id.tabla_resultados_ab);
        tituloSalida = (TableRow) findViewById(R.id.encabezado_tabla_resultados_ab);
        tvIteracion = (TextView) findViewById(R.id.iteracion_gauss_pivote);
        salidasX = (LinearLayout) findViewById(R.id.salidas_x_simple);
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
                spOpciones.setEnabled(true);
                btSiguiente.setText("Siguiente");
                String nro = edNroEcuaciones.getText().toString();
                boolean operar = generarMatrizEntrada(nro,tabla,titulo);
                if (operar) {
                    tabla.setVisibility(View.VISIBLE);
                    int nr =Integer.parseInt(nro);
                    nroEcuaciones = nr;
                    marcas = new int[nr+1];
                    limpiar();

                }else {
                    Toast.makeText(contexto,ErrorMetodo.ERROR_ENTRADA_NRO_ECUACIONES,Toast.LENGTH_LONG).show();
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
        if (actual<ab.length-1) {
            try {
                MatrizMarca aux = GaussConPivoteo.metodo(ab, actual, ab.length - 1, tipoPivoteo,marcas);
                ab = aux.getAb();
                marcas = aux.getMarcas();
            } catch (Exception e) {
                Toast.makeText(contexto, e.getMessage(), Toast.LENGTH_LONG).show();
                btSiguiente.setEnabled(false);
                return;
            }
            escribirSalidaAB(ab, tablaSalida, tituloSalida);
            actual++;
            tvIteracion.setText("Iteración: "+actual);
            if(actual==ab.length-1){
                btSiguiente.setText("Despejado");
                BigDecimal[] xs = null;
                try {
                    xs = Matriz.sustitucionRegresiva(ab,ab.length-1);
                    escribirSalidaX(xs,salidasX,marcas,'X');
                } catch (Exception e) {
                    Toast.makeText(contexto, e.getMessage(), Toast.LENGTH_LONG).show();
                }
                btSiguiente.setEnabled(false);
                spOpciones.setEnabled(true);//despeje y mostrar ecuaciones
            }
        }else {
            btSiguiente.setEnabled(false);
            spOpciones.setEnabled(true);
        }
    }

    private void calcularResultado() {
        limpiar();
        try {
            ab = crearAB(tabla,nroEcuaciones);
        } catch (Exception e) {
            Toast.makeText(contexto, ErrorMetodo.ERROR_ENTRADA_TABLA_SISTEMAS_ECUACIONES,Toast.LENGTH_LONG).show();
            return;
        }
        spOpciones.setEnabled(false);
        escribirSalidaAB(ab,tablaSalida,tituloSalida);
        btSiguiente.setVisibility(View.VISIBLE);
        tablaSalida.setVisibility(View.VISIBLE);
    }
    private void limpiar(){
        spOpciones.setEnabled(true);
        btSiguiente.setText("Siguiente");
        this.actual = 1;
        tvIteracion.setText("Iteración: "+actual);
        btSiguiente.setEnabled(true);
        calcular.setVisibility(View.VISIBLE);
        for(int i = 1;i<= nroEcuaciones;++i){
            marcas[i]=i;
        }
        tablaSalida.removeAllViews();
        tablaSalida.addView(tituloSalida);
        salidasX.removeAllViews();
    }
}