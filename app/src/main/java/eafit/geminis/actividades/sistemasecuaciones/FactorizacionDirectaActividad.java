package eafit.geminis.actividades.sistemasecuaciones;

import eafit.geminis.R;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.math.BigDecimal;

import eafit.geminis.actividades.ActividadBase;
import eafit.geminis.utilidades.TipoFactorizacion;
import eafit.geminis.utilidades.TipoPivoteo;

public class FactorizacionDirectaActividad extends ActividadBase {
    private View restoEntrada, restoSalida;
    private Button btIngresar,btSalir,btCalcular;
    private Spinner spTipoFactorizacion;
    private TableLayout tablaEntrada, tablaSalidaL,tablaSalidaU;
    private TableRow tituloEntrada, tituloSalidaL,tituloSalidaU;
    private EditText edNroEcuaciones;
    private BigDecimal[][] ab;
    private LinearLayout salidasX, salidasZ;
    private int nroEcuaciones=0;
    private TipoFactorizacion tipoFactorizacion;
    private int[] marcas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_factorizacion_directa_);
        ayudaAmostrar="factorizacion_directa";
        restoEntrada = findViewById(R.id.resto_tabla_entrada_ecuaciones);
        restoSalida = findViewById(R.id.resto_salida_factorizacion);
        tablaEntrada = (TableLayout) restoEntrada.findViewById(R.id.tabla_ingreso_ecuaciones_lineales);
        tablaSalidaL = (TableLayout) restoSalida.findViewById(R.id.tabla_resultados_l);
        tablaSalidaU = (TableLayout) restoSalida.findViewById(R.id.tabla_resultados_u);
        tituloEntrada = (TableRow) findViewById(R.id.fila_titulo_matriz_entrada);
        tituloSalidaL = (TableRow) findViewById(R.id.encabezado_tabla_resultados_l);
        tituloSalidaU = (TableRow) findViewById(R.id.encabezado_tabla_resultados_u);
        btIngresar = (Button)restoEntrada.findViewById(R.id.bt_ingresar_sistemas_ecuaciones);
        btCalcular = (Button)restoEntrada.findViewById(R.id.bt_calcular_matriz);
        btSalir = (Button) restoSalida.findViewById(R.id.bt_salir_salida_gauss_simple);
        spTipoFactorizacion = (Spinner) findViewById(R.id.sp_tipo_factorizacion);
        salidasX = (LinearLayout) restoSalida.findViewById(R.id.salidas_x_simple);
        salidasZ = (LinearLayout) restoSalida.findViewById(R.id.salidas_z_simple);
        edNroEcuaciones = (EditText) restoEntrada.findViewById(R.id.et_nro_ecuaciones);
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
        spTipoFactorizacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String seleccion = spTipoFactorizacion.getSelectedItem().toString();
                switch (seleccion){
                    case "Gauss simple":
                        tipoFactorizacion = TipoFactorizacion.GAUUSS;
                        break;
                    case "Gauss pivoteo":
                        tipoFactorizacion = TipoFactorizacion.GAUUSS;
                        break;
                    case "Croult":
                        tipoFactorizacion = TipoFactorizacion.CROULT;
                        break;
                    case "Doolittle":
                        tipoFactorizacion = TipoFactorizacion.DOOLITLE;
                        break;
                    case "Cholesky":
                        tipoFactorizacion = TipoFactorizacion.CHOLESKY;
                        break;
                    default:
                        tipoFactorizacion = TipoFactorizacion.GAUUSS;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void calcular(){
        //TODO
    }
    private void ingresar(){
        limpiar();
        String nro = edNroEcuaciones.getText().toString();
        boolean operar = generarMatrizEntrada(nro,tablaEntrada,tituloEntrada);
        if (operar) {
            nroEcuaciones = Integer.parseInt(nro);
            marcas = new int[nroEcuaciones+1];
            for (int i =1;i<=nroEcuaciones;++i){
                marcas[i]=i;
            }
        }else {
            Toast.makeText(contexto,"No se pudo generar",Toast.LENGTH_LONG).show();
        }
    }
    private void limpiar(){

    }
    private void terminar(){

    }
}
