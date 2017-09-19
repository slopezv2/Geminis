package eafit.geminis.actividades.utilidades;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.udojava.evalex.Expression;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Dictionary;

import eafit.geminis.R;
import eafit.geminis.actividades.ActividadBase;
import eafit.geminis.utilidades.ErrorMetodo;

public class GraficadorEntradaActividad extends ActividadBase implements AdapterView.OnItemSelectedListener {
    private LinearLayout layout;
    private ArrayList<String> funciones;
    private Spinner sp_historio_funciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        funciones = new ArrayList<String>();
        mensajeAyuda = "entrada_graficador";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_graficador_entrada);
        Button bt_graficar = (Button) findViewById(R.id.bt_graficar);
        bt_graficar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText ET_entrada_funciones =(EditText) findViewById(R.id.et_funciones);
                String funciones = ET_entrada_funciones.getText().toString();
                String[] nombres_funciones = new String[0];
                String[] operaciones_funciones = new String[0];
                boolean cumpleformato = true;
                try{
                    nombres_funciones = obtenerDatos(funciones,0);
                    operaciones_funciones = obtenerDatos(funciones,1);
                }catch (Exception ea){
                    Toast.makeText(contexto, ErrorMetodo.ERROR_FORMATO_METODO,Toast.LENGTH_SHORT).show();
                    cumpleformato = false;
                }
                if(cumpleformato) {
                    Intent intent = new Intent(GraficadorEntradaActividad.this, GraficadorActividad.class);
                    intent.putExtra("nombre_funciones",nombres_funciones);
                    intent.putExtra("operaciones_funciones",operaciones_funciones);
                    startActivity(intent);
                }

            }
        });
        sp_historio_funciones = (Spinner) findViewById(R.id.sp_historico_funciones);
    }
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        //TODO
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
        //TODO
    }

    private String[] obtenerDatos(String funciones,int posicion) throws Exception{
        if(funciones.isEmpty())throw new Exception("Funciones está vacío");
        String[] funciones_separadas = funciones.split("\n");
        String[] resultados = new String[funciones_separadas.length];
        for(int i = 0;i < funciones_separadas.length;++i){
            String[] temp = funciones_separadas[i].split("->");
            resultados[i] = temp[posicion];
        }
        return resultados;
    }

}
