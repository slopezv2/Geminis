package eafit.geminis.actividades;

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.ArrayList;
import java.util.StringTokenizer;

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
        switch (item.getItemId()) {
            case R.id.AcercaDe:
                // do what you want here
                Intent intent = new Intent(contexto, AyudasActividad.class);
                intent.putExtra("ayuda",ayudaAmostrar);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
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
    protected void guardarFuncion(String funcion){
        boolean res = Guardado.guardarFuncion(contexto,funcion);
        if (!res){
            Toast.makeText(contexto, ErrorMetodo.NO_GUARDO_FUNCION,Toast.LENGTH_SHORT).show();
        }
    }
    protected void recuperarFunciones(AutoCompleteTextView ed){
        String[] funciones = Guardado.recuperarFunciones(contexto);
        if (funciones != null && funciones[0].compareTo("")!=0){
            ArrayAdapter<String> adaptador = new ArrayAdapter<String>(contexto,android.R.layout.simple_dropdown_item_1line,funciones);
            ed.setAdapter(adaptador);
        }
    }
    protected void borrarFunciones(){
        boolean res =Guardado.Borrar(contexto);
        if (!res){
            Toast.makeText(contexto, ErrorMetodo.NO_BORRADO_FUNCION,Toast.LENGTH_SHORT).show();
        }
    }
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
                    textView.setPadding(5,3,5,5);
                    textView.setGravity(Gravity.CENTER);
                    textView.setTextSize(15);
                    fila.addView(textView);
                }
                fila.setLayoutParams(tbParams);
                tabla.addView(fila);
                int idEditText =0;
                for (int j=0;j<intEcuaciones;++j){
                    TableRow fila2 = new TableRow(contexto);
                    fila.setLayoutParams(tbParams);
                    for (int i = 0;i<= intEcuaciones;++i){
                        EditText editText = new EditText(contexto);
                        editText.setId(idEditText);
                        editText.setTextSize(15);
                        editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                        editText.setHint("0");
                        editText.setPadding(5,3,5,5);
                        editText.setWidth(90);
                        editText.setTextColor(Color.BLACK);
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
}
