package eafit.geminis.actividades.utilidades;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import eafit.geminis.R;
import eafit.geminis.actividades.ActividadBase;
import eafit.geminis.utilidades.ErrorMetodo;
public class GraficadorEntradaActividad extends ActividadBase implements AdapterView.OnItemSelectedListener {
    private Spinner sp_historio_funciones;
    private EditText et_entrada_funciones;
    private EditText et_entrada_puntos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ayudaAmostrar = "entrada graficador";
        setContentView(R.layout.actividad_graficador_entrada);
        //Controles de GUI
        et_entrada_funciones=(EditText) findViewById(R.id.et_funciones);
        et_entrada_puntos = (EditText) findViewById(R.id.et_puntos);
        Button bt_graficar = (Button) findViewById(R.id.bt_graficar);
        sp_historio_funciones = (Spinner) findViewById(R.id.sp_historico_funciones);
        //Evento del boton para graficar
        bt_graficar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String nroPuntos= et_entrada_puntos.getText().toString();
                String funciones = et_entrada_funciones.getText().toString();
                String[] nombres_funciones = new String[0];
                String[] operaciones_funciones = new String[0];
                boolean cumpleformato = true;
                int nroPuntosInt=0;
                //Revisar si la entrada de Puntos a dibujar es correcta
                try {
                    nroPuntosInt = Integer.parseInt(nroPuntos);
                    if(nroPuntosInt<=0)throw new Exception("");
                } catch (Exception e) {
                    cumpleformato = false;
                    Toast.makeText(contexto, ErrorMetodo.ERROR_ENTRADA_PUNTOS_GRAFICADOR,Toast.LENGTH_SHORT).show();
                }
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
                    intent.putExtra("cantidad_puntos",nroPuntosInt);
                    startActivity(intent);
                }
            }
        });
    }
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        //TODO
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback

    }

    /**
     * Metodo para tomar los datos de la entrada
     * @param funciones es la entrada como tal
     * @param posicion si es 0 son los nombres, si es 1 son las funciones
     * @return arreglo con lo solicitado
     * @throws Exception en caso de que no se cumpla el formato <identificador>-><funcion>
     */
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