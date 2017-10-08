package eafit.geminis.actividades;

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.ArrayList;

import eafit.geminis.R;
import eafit.geminis.actividades.utilidades.AyudasActividad;
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
//        DecimalFormat myFormatter = new DecimalFormat("##.##E");
//        String output = myFormatter.format(elementosIteracion[elementosIteracion.length-1]);
//        elementosIteracion[elementosIteracion.length-1] = output;

//        Format format = new DecimalFormat("0.0E0");
//        elementosIteracion[elementosIteracion.length-1] = format.format(elementosIteracion[elementosIteracion.length-1]);
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
}
