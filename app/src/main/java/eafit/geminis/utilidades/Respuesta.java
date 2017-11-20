package eafit.geminis.utilidades;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Sebas on 18/09/2017.
 */

public class Respuesta {
    // Clase que representa la respuesta de la mayoría de métodos de ecuaciones de una variable
    private TipoRespuesta tipo;
    private ArrayList<String> tablaIteraciones;
    private ArrayList<BigDecimal> intervalo;
    private BigDecimal valor;
    private String mensaje;
    private BigDecimal tolerancia;

    public Respuesta(TipoRespuesta tipo, BigDecimal valor, ArrayList<String> tablaIteraciones){
        this.tipo = tipo;
        this.valor = valor;
        this.tablaIteraciones = tablaIteraciones;
    }
    public Respuesta(TipoRespuesta tipo, ArrayList<BigDecimal> intervalo, ArrayList<String> tablaIteraciones){
        this.tipo=tipo;
        this.tablaIteraciones = tablaIteraciones;
        this.intervalo = intervalo;
        this.tablaIteraciones = tablaIteraciones;
    }

    public Respuesta(TipoRespuesta tipo, String mensaje, ArrayList<String> tablaIteraciones){
        this.tipo = tipo;
        this.mensaje = mensaje;
        this.tablaIteraciones = tablaIteraciones;
    }

    public Respuesta(TipoRespuesta aproximacion, BigDecimal valor, BigDecimal tol,ArrayList<String> tablaIteraciones) {
        this.tipo = aproximacion;
        this.valor = valor;
        this.tolerancia = tol;
        this.tablaIteraciones = tablaIteraciones;
    }

    public TipoRespuesta getTipo() {
        return tipo;
    }

    public ArrayList<String> getTablaIteraciones() {
        return tablaIteraciones;
    }

    public ArrayList<BigDecimal> getIntervalo() {
        return intervalo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public String getMensaje() {
        return mensaje;
    }

    public BigDecimal getTolerancia() {
        return tolerancia;
    }
}
