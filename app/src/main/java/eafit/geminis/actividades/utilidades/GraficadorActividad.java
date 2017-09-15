package eafit.geminis.actividades.utilidades;

import android.app.Activity;
import android.os.Bundle;

import com.androidplot.Plot;
import com.androidplot.xy.XYPlot;

import eafit.geminis.R;
import eafit.geminis.actividades.ActividadBase;

public class GraficadorActividad extends ActividadBase {
    private XYPlot grafico;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_graficador_actividad);
        grafico = (XYPlot) findViewById(R.id.grafico);
        //grafico.setRenderMode(Plot.RenderMode.USE_BACKGROUND_THREAD);
    }
}
