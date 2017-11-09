package eafit.geminis.actividades.sistemasecuaciones;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
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
        //Ayuda a mostrar
        ayudaAmostrar = "gauss_simple";
        setContentView(R.layout.actividad_eliminacion_gaussiana_simple);
        // el include con la entrada
        View resto = findViewById(R.id.resto_tabla_entrada_ecuaciones);
        // el include con la salida
        View restoSalida = findViewById(R.id.resto_salida_ecuaciones_simple);
        // Inicializacion elementos
        tablaSalida = (TableLayout) restoSalida.findViewById(R.id.tabla_resultados_ab);
        tituloSalida = (TableRow) restoSalida.findViewById(R.id.encabezado_tabla_resultados_ab);
        salir = (Button) restoSalida.findViewById(R.id.bt_salir_salida_gauss_simple);
        btIngresar = (Button) resto.findViewById(R.id.bt_ingresar_sistemas_ecuaciones);
        edNroEcuaciones = (EditText) resto.findViewById(R.id.et_nro_ecuaciones);
        tabla = (TableLayout) resto.findViewById(R.id.tabla_ingreso_ecuaciones_lineales);
        titulo = (TableRow) resto.findViewById(R.id.fila_titulo_matriz_entrada);
        salidasX = (LinearLayout)restoSalida.findViewById(R.id.salidas_x_simple);
        final Button calcular = (Button) resto.findViewById(R.id.bt_calcular_matriz);
        // Listeners de botones
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
    }

    /**
     * Obtener la matriz Ab de la entrada, procesarla y pintar en interfaz
     */
    private void calcularResultado(){
        try {
            ab = crearAB(tabla,nroEcuaciones);
        } catch (Exception e) {
            Toast.makeText(contexto,ErrorMetodo.ERROR_ENTRADA_TABLA_SISTEMAS_ECUACIONES,Toast.LENGTH_LONG).show();
            return;
        }
        BigDecimal[][] resultadoAb = new BigDecimal[0][];
        try {
            resultadoAb = EliminacionGaussianaSimple.metodo(ab,nroEcuaciones);
        } catch (ArithmeticException e) {
            Toast.makeText(contexto,ErrorMetodo.ERROR_DIVISION_CERO,Toast.LENGTH_LONG).show();
            return;
        }catch (Exception e){
            Toast.makeText(contexto,e.getMessage(),Toast.LENGTH_LONG).show();
            return;
        }
        boolean despeje = true;
        BigDecimal[] xDespejadas = null;
        try {
            xDespejadas= Matriz.sustitucionRegresiva(resultadoAb,nroEcuaciones);
        }catch (Exception e){
            despeje = false;
            Toast.makeText(contexto,ErrorMetodo.ERROR_DESPEJE_REGRESIVO,Toast.LENGTH_LONG).show();
        }
        //Métodos genéricos, definidos en clase padre
        escribirSalidaAB(resultadoAb,tablaSalida,tituloSalida);
        if (despeje) {
            escribirSalidaX(xDespejadas, salidasX);
        }
    }
}
