package eafit.geminis.actividades.sistemasecuaciones;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;

import eafit.geminis.R;
import eafit.geminis.actividades.ActividadBase;
import eafit.geminis.utilidades.ErrorMetodo;

public class EliminacionGaussianaSimpleActividad extends ActividadBase {
    private String[] entrada;
    private TableLayout tabla;
    private Button btIngresar;
    private EditText edNroEcuaciones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_eliminacion_gaussiana_simple);
        btIngresar = (Button) findViewById(R.id.bt_ingresar_sistemas_ecuaciones);
        edNroEcuaciones = (EditText) findViewById(R.id.et_nro_ecuaciones);
        tabla = (TableLayout) findViewById(R.id.tabla_ingreso_ecuaciones_lineales);
        btIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nroEcuaciones = edNroEcuaciones.getText().toString();
                generarMatrizEntrada(nroEcuaciones,tabla);
            }
        });
    }

}
