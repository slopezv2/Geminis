package eafit.geminis.actividades.utilidades;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import java.text.DecimalFormat;
import java.util.Random;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidplot.Plot;
import com.androidplot.xy.*;

import eafit.geminis.R;
import eafit.geminis.actividades.ActividadBase;

public class GraficadorActividad extends ActividadBase {
    /**
     * Tomado de :
     * https://github.com/halfhp/androidplot/blob/master/demoapp/src/main/java/com/androidplot/demos/TouchZoomExampleActivity.java
     */
    private XYPlot grafico;
    private static final int Tamanio_Series = 3000;
    private static final int SERIES_ALPHA = 255;
    private static final int NUM_GRIDLINES = 3;
    private PanZoom panZoom;
    private Button resetButton;
    private Spinner panSpinner;
    private Spinner zoomSpinner;
    private String[] nombre_funciones;
    private String[] operaciones_funciones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_graficador);
        Intent intentOriginal = getIntent();
        nombre_funciones = intentOriginal.getStringArrayExtra("nombre_funciones");
        operaciones_funciones = intentOriginal.getStringArrayExtra("operaciones_funciones");
        grafico = (XYPlot) findViewById(R.id.grafico);
        resetButton = (Button) findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });
        //punto origen
        grafico.setUserDomainOrigin(0);
        grafico.setUserRangeOrigin(0);
        // predefine the stepping of both axis
        // increment will be chosen from list to best fit NUM_GRIDLINES grid lines
        double[] inc_domain = new double[]{10,50,100,500};
        double[] inc_range = new double[]{1,5,10,20,50,100};
        grafico.setDomainStepModel(new StepModelFit(grafico.getBounds().getxRegion(),inc_domain,NUM_GRIDLINES));
        grafico.setRangeStepModel( new StepModelFit(grafico.getBounds().getyRegion(),inc_range,NUM_GRIDLINES));

        panSpinner = (Spinner) findViewById(R.id.pan_spinner);
        zoomSpinner = (Spinner) findViewById(R.id.zoom_spinner);
        grafico.getGraph().setLinesPerRangeLabel(2);
        grafico.getGraph().setLinesPerDomainLabel(2);
        grafico.getGraph().getBackgroundPaint().setColor(Color.TRANSPARENT);
        grafico.getGraph().getLineLabelStyle(XYGraphWidget.Edge.LEFT).
                setFormat(new DecimalFormat("#####"));
        grafico.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).
                setFormat(new DecimalFormat("#####.#"));
        grafico.setBorderStyle(Plot.BorderStyle.NONE, null, null);
        panZoom = PanZoom.attach(grafico, PanZoom.Pan.BOTH, PanZoom.Zoom.STRETCH_BOTH, PanZoom.ZoomLimit.MIN_TICKS);
        grafico.getOuterLimits().set(0, 3000, 0, 1000);
        initSpinners();
        // enable autoselect of sampling level based on visible boundaries:
        grafico.getRegistry().setEstimator(new ZoomEstimator());
        generateSeriesData();
        reset();
        Toast.makeText(contexto,operaciones_funciones[0],Toast.LENGTH_SHORT).show();

    }
    private void reset() {
        grafico.setDomainBoundaries(0, 10000, BoundaryMode.FIXED);
        grafico.setRangeBoundaries(0, 1000, BoundaryMode.FIXED);
        grafico.redraw();
    }

    private ProgressDialog progress;

    private void generateSeriesData() {
        progress = ProgressDialog.show(this, "Loading", "Please wait...", true);
        new AsyncTask() {

            @Override
            protected Object doInBackground(Object[] objects) {
                generateAndAddSeries(625, new LineAndPointFormatter(Color.rgb(50, 0, 0), null,
                        Color.argb(SERIES_ALPHA, 100, 0, 0), null));
                generateAndAddSeries(125, new LineAndPointFormatter(Color.rgb(50, 50, 0), null,
                        Color.argb(SERIES_ALPHA, 100, 100, 0), null));
                generateAndAddSeries(25, new LineAndPointFormatter(Color.rgb(0, 50, 0), null,
                        Color.argb(SERIES_ALPHA, 0, 100, 0), null));
                generateAndAddSeries(5, new LineAndPointFormatter(Color.rgb(0, 0, 0), null,
                        Color.argb(SERIES_ALPHA, 0, 0, 150), null));
                return null;
            }

            @Override
            protected void onPostExecute(Object result) {
                progress.dismiss();
                grafico.redraw();
            }
        }.execute(nombre_funciones,operaciones_funciones);
    }

    private void generateAndAddSeries(int max, LineAndPointFormatter formatter) {
        final FixedSizeEditableXYSeries series = new FixedSizeEditableXYSeries("s" + max, Tamanio_Series);
        Random r = new Random();
        for(int i = 0; i < Tamanio_Series; i++) {
            series.setX(i, i);
            series.setY(r.nextInt(max), i);
        }

        // wrap our series in a SampledXYSeries with a threshold of 1000.
        final SampledXYSeries sampledSeries =
                new SampledXYSeries(series, OrderedXYSeries.XOrder.ASCENDING, 2,100);
        grafico.addSeries(sampledSeries, formatter);
    }

    private void initSpinners() {
        panSpinner.setAdapter(
                new ArrayAdapter<>(this, R.layout.spinner_item, PanZoom.Pan.values()));
        panSpinner.setSelection(panZoom.getPan().ordinal());
        panSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                panZoom.setPan(PanZoom.Pan.values()[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // nothing to do
            }
        });

        zoomSpinner.setAdapter(
                new ArrayAdapter<>(this, R.layout.spinner_item, PanZoom.Zoom.values()));
        zoomSpinner.setSelection(panZoom.getZoom().ordinal());
        zoomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                panZoom.setZoom(PanZoom.Zoom.values()[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // nothing to do
            }
        });
    }
}
