package eafit.geminis.actividades.utilidades;

import android.content.Intent;
import android.graphics.Color;
import java.math.BigDecimal;
import java.util.Random;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import eafit.geminis.R;
import eafit.geminis.actividades.ActividadBase;
import eafit.geminis.utilidades.EvalExEval;
/**
 * Clase del graficador
 */
public class GraficadorActividad extends ActividadBase {
    /**
     * Tomado de : http://www.android-graphview.org/simple-graph/
     */
    private String[] nombre_funciones;
    private String[] operaciones_funciones;
    private GraphView grafico;
    private ProgressBar widget_cargando;
    private Button bt_volver;
    private int cantidadPuntos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_graficador);
        ayudaAmostrar = "graficador";
        //Intent para obtener los parametros enviados desde graficador entrada
        Intent intentOriginal = getIntent();
        nombre_funciones = intentOriginal.getStringArrayExtra("nombre_funciones");
        operaciones_funciones = intentOriginal.getStringArrayExtra("operaciones_funciones");
        cantidadPuntos = intentOriginal.getIntExtra("cantidad_puntos",0);
        //Elementos de la GUI
        grafico = (GraphView) findViewById(R.id.graficador);
        widget_cargando = (ProgressBar) findViewById(R.id.pb_cargando);
        bt_volver = (Button) findViewById(R.id.bt_volver);
        //Terminar la actividad al dar clic en volver
        bt_volver.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //Lanzar hilos para calcular dibujo
        CargarGrafico();
    }
    /**
     * Metodo para cargar las configuraciones del gráfico
     */
    private void CargarGrafico(){
        //Se puede ver el cargando
        widget_cargando.setVisibility(View.VISIBLE);
        // Reorganizar el visor
        grafico.getViewport().setXAxisBoundsManual(true);
        grafico.getViewport().setMinX(-cantidadPuntos);
        grafico.getViewport().setMaxX(cantidadPuntos);
        // Leyendas y titulo
        grafico.setTitle("Grafico");
        grafico.getLegendRenderer().setVisible(true);
        grafico.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.BOTTOM);
        // set manual Y bounds
        grafico.getViewport().setYAxisBoundsManual(true);
        grafico.getViewport().setMinY(-cantidadPuntos);
        grafico.getViewport().setMaxY(cantidadPuntos);
        grafico.getViewport().setScrollable(true); // enables horizontal scrolling
        grafico.getViewport().setScrollableY(true); // enables vertical scrolling
        grafico.getViewport().setScalable(true); // enables horizontal zooming and scrolling
        grafico.getViewport().setScalableY(true); // enables vertical zooming and scrolling
        //Lanzar en hilos Multithread
        for(int i = 0; i < operaciones_funciones.length;++i){
            // Se calcula las gráficas en el intervarlo [-cantidadPuntos,cantidadPuntos]
            new CalcularIntervalos().execute(cantidadPuntos+"",operaciones_funciones[i],nombre_funciones[i]);
        }
    }
    /**
     * Método que asegura un sólo hilo a la vez cambiando la GUI
     * @param data LineGraphSeries para dibujar en GUI
     */
    private  synchronized void cambierGrafico(LineGraphSeries data){
        grafico.addSeries(data);
        //Esconder el icono de cargar
        widget_cargando.setVisibility(View.GONE);
    }
    /**
     * Clase para calcular los hilos de manera paralela
     */
    private class CalcularIntervalos extends AsyncTask<String,Void,DataPoint[]> {
        public Exception excepcionLanzada;
        private String name;
        /**
         * Metodo que corre en hilo separado
         * @param params cantidad de puntos, funcion y nombre funcion
         * @return DataPoints[] para dibujar
         */
        @Override
        protected DataPoint[] doInBackground(String... params) {
            String max = params[0];
            int maxInt = Integer.parseInt(max);
            String funcion = params[1];
            DataPoint[] arr = new DataPoint[maxInt * 2];
            this.name = params[2];
            //Crear evaluador
            EvalExEval evaluador = new EvalExEval();
            BigDecimal res = new BigDecimal(0);
            //Primera evaluacion, notese el try para las excepciones
            try{
                res =evaluador.evaluar(funcion, new BigDecimal(( - maxInt)), true);
            } catch (ArithmeticException div0) {
                arr[0] = new DataPoint( - maxInt, Double.NaN);
            } catch (Exception ae) {
                excepcionLanzada = ae;
                return arr;
            }
            arr[0] = new DataPoint(- maxInt, res.doubleValue());
            //Resto del procesamiento, se hace de esta forma por rendimiento
            for (int i = 1; i < maxInt*2; ++i) {
                    try {
                        res =evaluador.evaluar(funcion, new BigDecimal((i - maxInt)), false);
                        arr[i] = new DataPoint(i - maxInt, res.doubleValue());
                    } catch (ArithmeticException div0) {
                        arr[i] = new DataPoint(i - maxInt, Double.NaN);
                    } catch (Exception ae) {
                        excepcionLanzada = ae;
                        return arr;
                    }
            }
            return arr;
        }
        /**
         * Metodo que corre desde el hilo de la GUI
         * @param dataPoints para crear LineGraphSeries y agregar al grafico
         */
        @Override
        protected void onPostExecute(DataPoint[] dataPoints) {
            if(this.excepcionLanzada!=null) {
                Toast.makeText(contexto, "Error en la funcion: "+name, Toast.LENGTH_LONG).show();
            }else{
                LineGraphSeries<DataPoint> serie = new LineGraphSeries<>(dataPoints);
                serie.setTitle(this.name);
                Random aleatorio = new Random();
                int color = colorCaso(aleatorio.nextInt(6));
                serie.setColor(color);
                cambierGrafico(serie);
            }
        }
        /**
         * Solo para un color aleatorio de los predeterminados
         * @param numero aleatorio para un color
         * @return color predeterminado
         */
        private int colorCaso(int numero){
            switch (numero){
                case 0:
                    return Color.BLACK;
                case 1:
                    return Color.BLUE;
                case 2:
                    return Color.GREEN;
                case 3:
                    return Color.RED;
                case 4:
                    return Color.YELLOW;
                case 5:
                    return Color.MAGENTA;
                default:
                    return Color.BLACK;
            }
        }
    }
}
