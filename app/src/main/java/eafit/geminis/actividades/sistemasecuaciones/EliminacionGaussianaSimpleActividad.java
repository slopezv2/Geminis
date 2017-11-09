package eafit.geminis.actividades.sistemasecuaciones;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.math.BigDecimal;

import eafit.geminis.R;
import eafit.geminis.actividades.ActividadBase;
import eafit.geminis.metodos.sistemasecuaciones.EliminacionGaussianaSimple;
import eafit.geminis.utilidades.ErrorMetodo;
import eafit.geminis.utilidades.Matriz;

public class EliminacionGaussianaSimpleActividad extends ActividadBase {
    private String[] entrada;
    private TableLayout tabla, tablaSalida;
    private Button btIngresar;
    private EditText edNroEcuaciones;
    private TableRow titulo, tituloSalida;
    private int nroEcuaciones=0;
    private Button salir;
    private BigDecimal[][] ab;
    private LinearLayout salidasX;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ayudaAmostrar = "gauss_simple";
        setContentView(R.layout.actividad_eliminacion_gaussiana_simple);
        View resto = findViewById(R.id.resto_tabla_entrada_ecuaciones);
        View restoSalida = findViewById(R.id.resto_salida_ecuaciones_simple);
        tablaSalida = (TableLayout) restoSalida.findViewById(R.id.tabla_resultados_ab);
        tituloSalida = (TableRow) restoSalida.findViewById(R.id.encabezado_tabla_resultados_ab);
        salir = (Button) restoSalida.findViewById(R.id.bt_salir_salida_gauss_simple);
        btIngresar = (Button) resto.findViewById(R.id.bt_ingresar_sistemas_ecuaciones);
        edNroEcuaciones = (EditText) resto.findViewById(R.id.et_nro_ecuaciones);
        tabla = (TableLayout) resto.findViewById(R.id.tabla_ingreso_ecuaciones_lineales);
        titulo = (TableRow) resto.findViewById(R.id.fila_titulo_matriz_entrada);
        salidasX = (LinearLayout)resto.findViewById(R.id.salidas_x_simple);
        Button calcular = (Button) resto.findViewById(R.id.bt_calcular_matriz);
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
                }
            }
        });
    }
    private void calcularResultado(){
        try {
            ab = crearAB(tabla,nroEcuaciones);
        } catch (Exception e) {
            Toast.makeText(contexto,ErrorMetodo.ERROR_ENTRADA_TABLA_SISTEMAS_ECUACIONES,Toast.LENGTH_LONG).show();
            return;
        }
        BigDecimal[][] resultadoAb = EliminacionGaussianaSimple.metodo(ab,nroEcuaciones);
        BigDecimal[] xDespejadas = Matriz.sustitucionRegresiva(resultadoAb,nroEcuaciones);
        escribirSalida(resultadoAb,xDespejadas);
    }
    private void escribirSalida(BigDecimal[][] AB,BigDecimal[] xDespejadas){
        tablaSalida.removeAllViews();
        for(int i = 1; i < AB.length;++i){
            TableRow fila = new TableRow(contexto);
            for(int j = 1;j < AB[i].length;++j){

            }
        }
    }
}
