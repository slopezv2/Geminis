package eafit.geminis.actividades.sistemasecuaciones;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.math.BigDecimal;
import java.util.ArrayList;

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
    private EditText edNroEcuaciones, edFactor,edNiter;
    private LinearLayout salidasX, entradaX0;
    private int nroEcuaciones=0;
    private TipoIterativo tipoIterativo;
    private ToggleButton tipoError;
    private boolean esAbsoluto;
    private BigDecimal[][] ab;
    private EditText edTol;
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
        edTol = (EditText) findViewById(R.id.ed_tol_iterativos);
        edNiter = (EditText) findViewById(R.id.ed_niter_iterativos);
        salidasX = (LinearLayout) findViewById(R.id.salidas_x_iterativos);
        entradaX0 = (LinearLayout) findViewById(R.id.ll_entrada_x0_iterativos);
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
        
        entradaX0.removeAllViews();
    }
    private void calcular(){
        try {
            ab = crearAB(tablaEntrada,nroEcuaciones);
            BigDecimal W = new BigDecimal(edFactor.getText().toString());
            BigDecimal[] b = Matriz.obtenerVectorB(ab,nroEcuaciones);
            BigDecimal[] x0 = leerX0(entradaX0,nroEcuaciones);
            BigDecimal tol = new BigDecimal(edTol.getText().toString());
            int niter = Integer.parseInt(edNiter.getText().toString());
            //Toast.makeText(contexto,x0[1].toString(),Toast.LENGTH_LONG).show();
            Respuesta rp = MetodoIterativo.metodo(ab,b,W,x0,tol,nroEcuaciones,niter,tipoIterativo,esAbsoluto);
            tratarSalida(rp,tablaSalida);

        } catch (Exception e) {
            Toast.makeText(contexto,ErrorMetodo.ERROR_ENTRADA_TABLA_SISTEMAS_ECUACIONES,Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
    private void ingresar(){
        limpiar();
        String nro = edNroEcuaciones.getText().toString();
        boolean operar = generarMatrizEntrada(nro,tablaEntrada,tituloEntrada);
        if (operar) {
            nroEcuaciones = Integer.parseInt(nro);
            crearEntradasX0(nroEcuaciones,entradaX0);
        }else {
            Toast.makeText(contexto, ErrorMetodo.ERROR_ENTRADA_NRO_ECUACIONES,Toast.LENGTH_LONG).show();
        }
    }
    private void crearEntradasX0(int n, LinearLayout destino){
        TextView tv= new TextView(contexto);
        tv.setTextColor(Color.BLACK);
        tv.setText("Ingrese los valores iniciales");
        destino.addView(tv);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }
        for(int i = 0; i<n;++i){
            EditText ed = new EditText(contexto);
            ed.setTextColor(Color.BLACK);
            ed.setHintTextColor(Color.BLACK);
            //ed.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            ed.setHint("X"+i);
            ed.setId(100+i);
            destino.addView(ed);
        }
    }
    private BigDecimal[] leerX0(LinearLayout origen, int n){
        BigDecimal[] x0 = new BigDecimal[n+1];
        for(int i = 1; i <= n;++i){
            EditText temp = (EditText) origen.findViewById(100+i-1);
            try {
                x0[i] = new BigDecimal(temp.getText().toString());
            }catch (Exception e){
                Toast.makeText(contexto,e.getMessage(),Toast.LENGTH_LONG);
            }
        }
        return x0;
    }
    private void tratarSalida(Respuesta respuesta,TableLayout tablaS){
        tablaS.removeAllViews();
        TableRow encabezado = new TableRow(contexto);
        TextView tv = new TextView(contexto);
        tv.setTextColor(Color.BLACK);
        tv.setText("N");
        encabezado.addView(tv);
        for(int i = 0; i < nroEcuaciones;++i){
            tv = new TextView(contexto);
            tv.setTextColor(Color.BLACK);
            tv.setText("X"+i);
            encabezado.addView(tv);
        }
        tv = new TextView(contexto);
        tv.setTextColor(Color.BLACK);
        tv.setText("Error");
        encabezado.addView(tv);
        tablaS.addView(encabezado);
        ArrayList<String> iteraciones = respuesta.getTablaIteraciones();
        for(String iteracion:iteraciones){
            encabezado =  new TableRow(contexto);
            String[] campos = iteracion.split(" ");
            for(String campo:campos){
                tv = new TextView(contexto);
                tv.setTextColor(Color.BLACK);
                tv.setText(campo);
                encabezado.addView(tv);
            }
            tablaS.addView(encabezado);
        }
    }
}
