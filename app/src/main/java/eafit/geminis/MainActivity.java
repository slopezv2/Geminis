package eafit.geminis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Console;

import eafit.geminis.actividades.ecuacionesunavariable.MenuUnaVariableActividad;
import eafit.geminis.actividades.interpolacion.MenuInterpolacionActividad;
import eafit.geminis.actividades.utilidades.GraficadorActividad;

public class MainActivity extends Activity {

    private static ListView listaMetodos;
    private static String[] metodos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listaMetodos = (ListView) findViewById(R.id.listaMetodos);
        metodos = getResources().getStringArray(R.array.OpcionesMenuInicio);
        inicializarLista();
    }
    private void inicializarLista(){
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(this,R.layout.item_metodos_lista,metodos);
        listaMetodos.setAdapter(adapter);
        listaMetodos.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String value = listaMetodos.getItemAtPosition(position).toString();
                        Intent intent;
                        switch(value){
                            case "Ecuaciones de una variable":
                                intent = new Intent(MainActivity.this,MenuUnaVariableActividad.class);
                                startActivity(intent);
                                break;
                            case "Sistema de ecuaciones":
                                intent = new Intent(MainActivity.this,MenuUnaVariableActividad.class);
                                startActivity(intent);
                                break;
                            case "Interpolación":
                                intent = new Intent(MainActivity.this,MenuInterpolacionActividad.class);
                                startActivity(intent);
                                break;
                            case "Graficador":
                                try {
                                    intent = new Intent(MainActivity.this, GraficadorActividad.class);
                                    startActivity(intent);
                                }catch (Exception ea){
                                    Toast.makeText(null,ea.getMessage(),Toast.LENGTH_LONG);
                                }
                                break;
                        }

                    }
                }
        );
    }

}
