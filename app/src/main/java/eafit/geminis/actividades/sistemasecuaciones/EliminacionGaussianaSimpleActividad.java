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
    private TableLayout tabla, tablaSalida;
    private Button btIngresar,calcular;
    private EditText edNroEcuaciones;
    private TableRow titulo, tituloSalida;
    private int nroEcuaciones=0;
    private Button salir;
    private BigDecimal[][] ab;
    private LinearLayout salidasX;
    private int[] marcas;
    private int actual =0;
    private TextView txIteracion;
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
        calcular = (Button) resto.findViewById(R.id.bt_calcular_matriz);
        txIteracion = (TextView)restoSalida.findViewById(R.id.tx_iteracion_gauss_simple);
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
                limpiar();
                String nro = edNroEcuaciones.getText().toString();
                boolean operar = generarMatrizEntrada(nro,tabla,titulo);
                if (operar) {
                    nroEcuaciones = Integer.parseInt(nro);
                    marcas = new int[nroEcuaciones+1];
                    for (int i =1;i<=nroEcuaciones;++i){
                        marcas[i]=i;
                    }
                }else {
                    Toast.makeText(contexto,ErrorMetodo.ERROR_ENTRADA_NRO_ECUACIONES,Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * Obtener la matriz Ab de la entrada, procesarla y pintar en interfaz
     */
    private void calcularResultado(){
        if (actual==0){
            try {
                ab = crearAB(tabla,nroEcuaciones);
                actual++;
            } catch (Exception e) {
                Toast.makeText(contexto,ErrorMetodo.ERROR_ENTRADA_TABLA_SISTEMAS_ECUACIONES,Toast.LENGTH_LONG).show();
                return;
            }
            calcular.setText("Siguiente");
            escribirSalidaAB(ab,tablaSalida,tituloSalida);

        }else if(actual <= nroEcuaciones-1){
            try {
                ab = EliminacionGaussianaSimple.metodo(ab,nroEcuaciones,actual);
                txIteracion.setText("Iteracion: "+actual);
                actual++;
            } catch (ArithmeticException e) {
                Toast.makeText(contexto,ErrorMetodo.ERROR_DIVISION_CERO,Toast.LENGTH_LONG).show();
                return;
            }catch (Exception e){
                Toast.makeText(contexto,e.getMessage(),Toast.LENGTH_LONG).show();
                return;
            }
            //Métodos genéricos, definidos en clase padre
            escribirSalidaAB(ab,tablaSalida,tituloSalida);

        }else if(actual==nroEcuaciones) {
            BigDecimal[] xDespejadas = null;
            try {
                xDespejadas= Matriz.sustitucionRegresiva(ab,nroEcuaciones);
                txIteracion.setText("Iteración: "+actual);
                actual++;
            }catch (Exception e){
                Toast.makeText(contexto,ErrorMetodo.ERROR_DESPEJE_REGRESIVO,Toast.LENGTH_LONG).show();
                return;
            }
            escribirSalidaX(xDespejadas, salidasX,marcas,'X');
            fin();
        }

    }
    private void fin(){
        calcular.setText("Terminado");
        calcular.setEnabled(false);
    }
    private void limpiar(){
        calcular.setText("Calcular");
        calcular.setEnabled(true);
        actual=0;
        txIteracion.setText("Iteración: "+actual);
        tablaSalida.removeAllViews();
        tablaSalida.addView(tituloSalida);
        tabla.removeAllViews();
        tabla.addView(titulo);
        salidasX.removeAllViews();
    }
}
