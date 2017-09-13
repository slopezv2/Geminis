package eafit.geminis.actividades.utilidades;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import eafit.geminis.R;

public class GraficadorEntrada extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_graficador_entrada);
        Button bt_graficar = (Button) findViewById(R.id.bt_graficar);
        bt_graficar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(GraficadorEntrada.this,GraficadorActividad.class);
                startActivity(intent);
            }
        });

    }

}
