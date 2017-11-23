package eafit.geminis.actividades;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import eafit.geminis.R;
import eafit.geminis.actividades.utilidades.AyudasActividad;
import eafit.geminis.utilidades.ErrorMetodo;
import eafit.geminis.utilidades.Guardado;
import eafit.geminis.utilidades.Respuesta;

public abstract class ActividadBase extends Activity {
    protected Context contexto;
    protected String ayudaAmostrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ayudaAmostrar = "";
        contexto = getApplicationContext();
        super.onCreate(savedInstanceState);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Lanzar la ayuda desde la actividad actual
        switch (item.getItemId()) {
            case R.id.AcercaDe:
                Intent intent = new Intent(contexto, AyudasActividad.class);
                intent.putExtra("ayuda",ayudaAmostrar);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    /**
     * Metodo para verificar si es una entrada numerica válida
     * @param entrada string a verificar
     * @param esFuncion si se trata de una funcion
     * @return true si está bien, false en caso contrario
     */
    protected boolean verificarEntrada(String entrada,boolean esFuncion){
        if (entrada==null){
            return false;
        }else if (entrada.isEmpty()){
            return false;
        }else if (!esFuncion){
            try{
                BigDecimal numero = new BigDecimal(entrada);
                numero.doubleValue();
            }catch (Exception ae){
                return false;
            }
        }
        return true;
    }
    /**
     * Metodo para pintar las respuestas en GUI
     * @param respuesta
     * @param tabla
     * @param filaEmcabezados
     * @param resultados
     */
    protected void tratarRespuesta(Respuesta respuesta,TableLayout tabla, TableRow filaEmcabezados, EditText resultados){
        tabla.removeAllViews();
        tabla.addView(filaEmcabezados);
        switch (respuesta.getTipo()){
            case Error:
                Toast.makeText(contexto,respuesta.getMensaje(),Toast.LENGTH_LONG).show();
                break;
            case FRACASO:
                resultados.setText(respuesta.getMensaje());
                break;
            case INTERVALO:
                resultados.setText("Se presume hay una raiz entre "+respuesta.getIntervalo().get(0)+" y "+respuesta.getIntervalo().get(1));
                break;
            case RAIZ:
                resultados.setText("Se encontró una raiz en: "+respuesta.getValor());
                break;
            case APROXIMACION:
                resultados.setText(respuesta.getValor()+" es una aproximación a una raiz con una " +
                        "tolerancia = "+respuesta.getTolerancia());
            default:
        }
        ArrayList<String> arr = respuesta.getTablaIteraciones();
        if(arr!=null){
            for(int i = 0; i < arr.size();i++){
                insertarFila(arr.get(i),i,tabla,filaEmcabezados);
            }
        }
    }
    /**
     * Metodo para insertar una fila de string separado por ' ' (espacio en blanco) en una tabla dada
     * @param fila
     * @param iteracion
     * @param tabla
     * @param filaEmcabezados
     */
    protected void insertarFila(String fila, int iteracion, TableLayout tabla, TableRow filaEmcabezados){
        ViewGroup.LayoutParams parametros_fila = filaEmcabezados.getLayoutParams();
        TableRow nuevaFila = new TableRow(contexto);
        String[] elementosIteracion = fila.split(" ");
        boolean esConvertible = true;
        BigDecimal ultimo_Elemento = BigDecimal.ZERO;
        try {
            ultimo_Elemento= new BigDecimal(elementosIteracion[elementosIteracion.length - 1]);
            ultimo_Elemento.doubleValue();
        }catch (Exception ae){
            esConvertible = false;
        }
        if (esConvertible) {
            DecimalFormat formateador = new DecimalFormat("0.00E0");
            String error = formateador.format(ultimo_Elemento);
            elementosIteracion[elementosIteracion.length - 1] = error;
            if(elementosIteracion.length>3 ){
                BigDecimal penultimo_elemento = new BigDecimal(elementosIteracion[elementosIteracion.length - 2]);
                error = formateador.format(penultimo_elemento.doubleValue());
                elementosIteracion[elementosIteracion.length-2] = error;
            }
        }
        for(String elemento: elementosIteracion){
            TextView tvDato = new TextView(contexto);
            tvDato.setPadding(5,5,5,5);
            tvDato.setTextSize(16);
            tvDato.setBackgroundResource(R.drawable.border);
            tvDato.setTextColor(Color.rgb(126,138,151));
            tvDato.setGravity(Gravity.START);
            tvDato.setText(elemento);
            nuevaFila.addView(tvDato);
        }
        tabla.addView(nuevaFila);
    }
    /**
     * Metodo para guardar en archivo una funcion dada
     * @param funcion
     */
    protected void guardarFuncion(String funcion){
        boolean res = Guardado.guardarFuncion(contexto,funcion);
        if (!res){
            Toast.makeText(contexto, ErrorMetodo.NO_GUARDO_FUNCION,Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * Metodo para traer de memoria las funciones guardadas y ponerlas en ed para sugerencia
     * @param ed
     */
    protected void recuperarFunciones(AutoCompleteTextView ed){
        String[] funciones = Guardado.recuperarFunciones(contexto);
        if (funciones != null && funciones[0].compareTo("")!=0){
            ArrayAdapter<String> adaptador = new ArrayAdapter<String>(contexto,android.R.layout.simple_dropdown_item_1line,funciones);
            ed.setAdapter(adaptador);
        }
    }
    /**
     * Eliminar las funciones guardadas
     */
    protected void borrarFunciones(){
        boolean res =Guardado.Borrar(contexto);
        if (!res){
            Toast.makeText(contexto, ErrorMetodo.NO_BORRADO_FUNCION,Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * Generar la entrada para el sistema de ecuaciones
     * @param nroEcuaciones
     * @param tabla
     * @param titulo
     * @return true si fue exitoso, false en caso contrario
     */
    protected boolean generarMatrizEntrada(String nroEcuaciones, TableLayout tabla, TableRow titulo) {
        if(nroEcuaciones.isEmpty()){
            Toast.makeText(contexto, ErrorMetodo.NRO_ECUACIONES_VACIO,Toast.LENGTH_LONG).show();
            return false;
        }else {
            int intEcuaciones = Integer.parseInt(nroEcuaciones);
            if (intEcuaciones <= 0){
                Toast.makeText(contexto, ErrorMetodo.NRO_ECUACIONES_VACIO,Toast.LENGTH_LONG).show();
                return false;
            }else {
                tabla.removeAllViews();
                TableRow fila;
                fila = new TableRow(contexto);
                ViewGroup.LayoutParams tbParams= titulo.getLayoutParams();
                for(int j=0;j<=intEcuaciones;++j){
                    TextView textView;
                    textView= new TextView(this);
                    if(j==intEcuaciones){
                        textView.setText("b");
                    }else {
                        textView.setText("x" + (j + 1));
                    }
                    textView.setPadding(5,5,5,5);
                    textView.setBackgroundResource(R.drawable.border);
                    textView.setGravity(Gravity.CENTER);
                    textView.setTextSize(16);
                    fila.addView(textView);
                }
                fila.setLayoutParams(tbParams);
                tabla.addView(fila);
                int idEditText =0;
                for (int j=0;j<intEcuaciones;++j){
                    TableRow fila2 = new TableRow(contexto);
                    fila.setLayoutParams(tbParams);
                    for (int i = 0;i<= intEcuaciones;++i){
                        EditText editText = new EditText(this);
                        editText.setId(idEditText);
                        editText.setTextSize(16);
                        editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                        editText.setHint("0");
                        editText.setPadding(5,5,5,5);
                        editText.setWidth(90);
                        editText.setBackgroundResource(R.drawable.border);
                        editText.setTextColor(Color.rgb(126,138,151));
                        fila2.addView(editText);
                        ++idEditText;
                    }
                    fila2.setLayoutParams(tbParams);
                    tabla.addView(fila2);
                }
            }
            return true;
        }
    }
    /**
     * Lee la tabla de entrada ab y retorna los datos en BigDecimal[][] ab
     * @param tabla
     * @param nroEcuaciones
     * @return
     * @throws Exception
     */
    protected BigDecimal[][] crearAB(TableLayout tabla,int nroEcuaciones) throws Exception{
        BigDecimal[][] ab = new BigDecimal[nroEcuaciones+1][nroEcuaciones+2];
        for (int i = 1; i <= nroEcuaciones;++i){
            for(int j = 1;j<=nroEcuaciones+1;++j){
                String contenido = ((EditText)((TableRow)tabla.getChildAt(i)).getChildAt(j-1))
                        .getText().toString();
                BigDecimal valor = new BigDecimal(Double.parseDouble(contenido));
                ab[i][j]=valor;
            }
        }
        return ab;
    }
    /**
     * Metodo para escribir en tabla los resultados de una matriz tipo bidimensional
     * @param AB
     * @param tablaSalida
     * @param tituloSalida
     */
    protected void escribirSalidaAB(BigDecimal[][] AB,TableLayout tablaSalida, TableRow tituloSalida){
        tablaSalida.removeAllViews();
        TableRow encabezado = new TableRow(contexto);
        int limite = AB[1].length;
        for(int i = 1; i < limite;++i){
            TextView tv = new TextView(contexto);
            //tv.setWidth(150);
            if(i != limite-1) {
                tv.setText("x" + i);
            }else {
                tv.setText("b");
            }
            tv.setPadding(5,5,5,5);
            tv.setBackgroundResource(R.drawable.border);
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(16);
            tv.setTextColor(Color.BLACK);
            encabezado.addView(tv);
        }
        tablaSalida.addView(encabezado);
        for(int i = 1; i < AB.length;++i){
            TableRow fila = new TableRow(contexto);
            for(int j = 1;j < AB[i].length;++j){
                TextView tv = new TextView(contexto);
                tv.setText(AB[i][j].doubleValue()+"");
                if(Build.VERSION.SDK_INT>=17){
                    tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                }
                tv.setTextSize(16);
                tv.setPadding(5,5,5,5);
                //tv.setWidth(90);
                tv.setBackgroundResource(R.drawable.border);
                tv.setTextColor(Color.rgb(126,138,151));
                fila.addView(tv);
            }
            tablaSalida.addView(fila);
        }
    }

    /**
     * Metodo para escribir en LinearLayout los resultados de x despejadas
     * @param lasx
     * @param destino
     * @param marcas
     * @param letra
     */
    protected void escribirSalidaX(BigDecimal[] lasx, LinearLayout destino, int[] marcas, char letra){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        destino.removeAllViews();
        for(int i = 1;i<lasx.length;++i){
            TextView tv = new TextView(contexto);
            tv.setText(letra+""+marcas[i]+": "+lasx[i].doubleValue());
            destino.addView(tv,params);
            if(Build.VERSION.SDK_INT>=17){
                tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            }
            tv.setTextSize(16);
            tv.setPadding(5,5,5,5);
            tv.setTextColor(Color.BLACK);
        }
    }
    /**
     * Metodo para escribir una matriz sencilla tipo L o U en factorizacion
     * @param L
     * @param n
     * @param tablaSalida
     */
    protected void escribirMatrizSimple(BigDecimal[][] L, int n,TableLayout tablaSalida){
        tablaSalida.removeAllViews();
        TableRow encabezado = new TableRow(contexto);
        for(int i = 1; i<=n;++i){
            TextView tv = new TextView(contexto);
            //tv.setWidth(150);
            tv.setText("X" + i);
            tv.setPadding(5,5,5,5);
            tv.setBackgroundResource(R.drawable.border);
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(16);
            tv.setTextColor(Color.BLACK);
            encabezado.addView(tv);
        }
        tablaSalida.addView(encabezado);
        for(int i = 1; i <= n;++i){
            TableRow fila = new TableRow(contexto);
            for(int j = 1;j <= n;++j){
                TextView tv = new TextView(contexto);
                BigDecimal valActual =L[i][j];
                if (valActual != null){
                    tv.setText(valActual.doubleValue()+"");
                }else {
                    tv.setText("0");
                }
                if(Build.VERSION.SDK_INT>=17){
                    tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                }
                tv.setTextSize(16);
                tv.setPadding(5,5,5,5);
                //tv.setWidth(90);
                tv.setBackgroundResource(R.drawable.border);
                tv.setTextColor(Color.rgb(126,138,151));
                fila.addView(tv);
            }
            tablaSalida.addView(fila);
        }
    }
    protected void generarMatrizE(int nroPuntos,TableLayout tabla,TableRow encabezado ){
        encabezado.removeAllViews();
        tabla.removeAllViews();
        TextView tX = new TextView(contexto);
        tX.setTextColor(Color.BLACK);
        tX.setText("xi");

        tX.setTextSize(16);
        tX.setPadding(5,5,5,5);
        tX.setBackgroundResource(R.drawable.border);
        tX.setGravity(Gravity.CENTER);
        tX.setWidth(200);

        encabezado.addView(tX);
        tX = new TextView(contexto);

        tX.setTextSize(16);
        tX.setPadding(5,5,5,5);
        tX.setBackgroundResource(R.drawable.border);
        tX.setGravity(Gravity.CENTER);
        tX.setWidth(200);

        tX.setTextColor(Color.BLACK);
        tX.setText("Fxi");
        encabezado.addView(tX);
        tabla.addView(encabezado);
        int contador=0;
        for(int i =0; i < nroPuntos;++i){
            TableRow fila = new TableRow(contexto);
            EditText tituloX = new EditText(contexto);
            tituloX.setTextSize(TypedValue.COMPLEX_UNIT_SP,10);
            tituloX.setHint("x"+i);
            tituloX.setHintTextColor(Color.rgb(126,138,151));

            tituloX.setTextSize(16);
            tituloX.setPadding(5,5,5,5);
            tituloX.setBackgroundResource(R.drawable.border);
            tituloX.setTextColor(Color.rgb(126,138,151));
            tituloX.setWidth(200);
            tituloX.setGravity(Gravity.CENTER);

            tituloX.setId(contador);
            contador++;
            EditText tituloFx = new EditText(contexto);
            tituloFx.setHint("Fx"+i);
            tituloFx.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
            tituloFx.setHintTextColor(Color.rgb(126,138,151));

            tituloFx.setTextSize(16);
            tituloFx.setPadding(5,5,5,5);
            tituloFx.setBackgroundResource(R.drawable.border);
            tituloFx.setTextColor(Color.rgb(126,138,151));
            tituloFx.setWidth(200);
            tituloFx.setGravity(Gravity.CENTER);

            tituloFx.setId(contador);
            contador++;
            fila.addView(tituloX);
            fila.addView(tituloFx);
            tabla.addView(fila);
        }
    }
    protected BigDecimal[][] leerEntrada(TableLayout tabla,int n) throws Exception{
        BigDecimal[][] entradas = new BigDecimal[n+1][3];
        int contador =1;
        for(int i = 0; i < n*2;i=i+2){
            EditText entrada = (EditText)tabla.findViewById(i);
            BigDecimal valor = new BigDecimal(entrada.getText().toString());
            entradas[contador][1]=valor;
            entrada = (EditText)tabla.findViewById(i+1);
            valor = new BigDecimal(entrada.getText().toString());
            entradas[contador][2]=valor;
            contador++;
        }
        return entradas;
    }
}