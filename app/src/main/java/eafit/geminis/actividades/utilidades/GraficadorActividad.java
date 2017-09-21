package eafit.geminis.actividades.utilidades;

import android.app.ProgressDialog;
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
import com.udojava.evalex.Expression;
import eafit.geminis.R;
import eafit.geminis.actividades.ActividadBase;
import eafit.geminis.utilidades.EvalExEval;


public class GraficadorActividad extends ActividadBase {
    /**
     * Tomado de : http://www.android-graphview.org/simple-graph/
     */
    private String[] nombre_funciones;
    private String[] operaciones_funciones;
    private GraphView grafico;
    private ProgressBar widget_cargando;
    private Button bt_volver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_graficador);
        Intent intentOriginal = getIntent();
        nombre_funciones = intentOriginal.getStringArrayExtra("nombre_funciones");
        operaciones_funciones = intentOriginal.getStringArrayExtra("operaciones_funciones");
        grafico = (GraphView) findViewById(R.id.graficador);
        widget_cargando = (ProgressBar) findViewById(R.id.pb_cargando);
        bt_volver = (Button) findViewById(R.id.bt_volver);
        bt_volver.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        CargarGrafico();
    }

    private void CargarGrafico(){
        widget_cargando.setVisibility(View.VISIBLE);
        // set manual X bounds
        grafico.getViewport().setXAxisBoundsManual(true);
        grafico.getViewport().setMinX(-100);
        grafico.getViewport().setMaxX(100);
        grafico.computeScroll();
        grafico.setTitle("Grafico");
        grafico.getLegendRenderer().setVisible(true);
        grafico.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.BOTTOM);
// set manual Y bounds
        grafico.getViewport().setYAxisBoundsManual(true);
        grafico.getViewport().setMinY(-100);
        grafico.getViewport().setMaxY(100);
        grafico.getViewport().setScrollable(true); // enables horizontal scrolling
        grafico.getViewport().setScrollableY(true); // enables vertical scrolling
        grafico.getViewport().setScalable(true); // enables horizontal zooming and scrolling
        grafico.getViewport().setScalableY(true); // enables vertical zooming and scrolling


        for(int i = 0; i < operaciones_funciones.length;++i){
            new CalcularIntervalos().execute("1000",operaciones_funciones[i],nombre_funciones[i]);
        }

    }

    private  synchronized void cambierGrafico(LineGraphSeries data){
        grafico.addSeries(data);
        widget_cargando.setVisibility(View.GONE);
    }
    private class CalcularIntervalos extends AsyncTask<String,Void,DataPoint[]> {
        public Exception excepcionLanzada;
        private String name;
        @Override
        protected DataPoint[] doInBackground(String... params) {
            String max = params[0];
            int maxInt = Integer.parseInt(max);
            String funcion = params[1];
            DataPoint[] arr = new DataPoint[maxInt * 2];
            this.name = params[2];
            for (int i = 0; i < maxInt*2; ++i) {

                try {
                    BigDecimal res = EvalExEval.evaluar(funcion, new BigDecimal((i - maxInt)), true);
                    arr[i] = new DataPoint(i - maxInt, res.doubleValue());
                } catch (ArithmeticException div0) {
                    arr[i] = new DataPoint(i - maxInt, Double.NaN);
                } catch (Expression.ExpressionException ae) {
                    excepcionLanzada = ae;
                    return arr;
                }

            }
            return arr;
        }

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
