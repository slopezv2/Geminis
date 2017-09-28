package eafit.geminis.utilidades;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Sebas on 18/09/2017.
 */

public class Respuesta {
    private TipoRespuesta tipo;
    private ArrayList<String> tablaIteraciones;
    private ArrayList<BigDecimal> intervalo;
    private BigDecimal valor;
    private int niter;
    private String mensaje;

    public Respuesta(TipoRespuesta tipo, BigDecimal valor, ArrayList<String> tablaIteraciones){
        this.tipo = tipo;
        this.valor = valor;
    }
    public Respuesta(TipoRespuesta tipo, ArrayList<BigDecimal> intervalo, ArrayList<String> tablaIteraciones){
        this.tipo=tipo;
        this.tablaIteraciones = tablaIteraciones;
        this.intervalo = intervalo;
    }
    public Respuesta(TipoRespuesta tipo, int niter, ArrayList<String> tablaIteraciones){
        this.tipo = tipo;
        this.niter = niter;
    }
    public Respuesta(TipoRespuesta tipo, String mensaje, ArrayList<String> tablaIteraciones){
        this.tipo = tipo;
        this.mensaje = mensaje;
    }
}
