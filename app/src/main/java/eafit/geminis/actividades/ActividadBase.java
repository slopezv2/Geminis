package eafit.geminis.actividades;

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.math.BigDecimal;

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
}
