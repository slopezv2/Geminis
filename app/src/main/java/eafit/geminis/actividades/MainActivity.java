package eafit.geminis.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import eafit.geminis.R;
import eafit.geminis.actividades.ecuacionesunavariable.MenuUnaVariableActividad;
import eafit.geminis.actividades.interpolacion.MenuInterpolacionActividad;
import eafit.geminis.actividades.sistemasecuaciones.MenuSistemasEcuacionesActividad;
import eafit.geminis.actividades.utilidades.GraficadorEntradaActividad;

public class MainActivity extends ActividadBase {

    private static ListView listaMetodos;
    private static String[] metodos;
    private Button btBorrarFunciones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ayudaAmostrar = "inicio";
        setContentView(R.layout.activity_main);
        listaMetodos = (ListView) findViewById(R.id.listaMetodos);
        metodos = getResources().getStringArray(R.array.OpcionesMenuInicio);
        inicializarLista();
        btBorrarFunciones =(Button) findViewById(R.id.bt_borrar_funciones);
        btBorrarFunciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrarFunciones();
            }
        });
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
                                intent = new Intent(MainActivity.this, MenuSistemasEcuacionesActividad.class);
                                startActivity(intent);
                                break;
                            case "Interpolación":
                                intent = new Intent(MainActivity.this,MenuInterpolacionActividad.class);
                                startActivity(intent);
                                break;
                            case "Graficador":
                                    intent = new Intent(MainActivity.this, GraficadorEntradaActividad.class);
                                    startActivity(intent);
                                break;
                        }

                    }
                }
        );
    }

}
