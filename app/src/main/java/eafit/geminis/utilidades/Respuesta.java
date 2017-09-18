package eafit.geminis.utilidades;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Sebas on 18/09/2017.
 */

public class Respuesta {
    private TipoRespuesta tipo;
    private ArrayList<BigDecimal> iteraciones_valor;
    private ArrayList<BigDecimal> error;
    private BigDecimal valor;
    private int niter;
    private String mensaje;

    public Respuesta(TipoRespuesta tipo, BigDecimal valor){
        this.tipo = tipo;
        this.valor = valor;
    }
    public Respuesta(TipoRespuesta tipo, ArrayList<BigDecimal> iteraciones_valor, ArrayList<BigDecimal> error){
        this.tipo=tipo;
        this.iteraciones_valor = iteraciones_valor;
        this.error = error;
    }
    public Respuesta(TipoRespuesta tipo, int niter){
        this.tipo = tipo;
        this.niter = niter;
    }
    public Respuesta(TipoRespuesta tipo, String mensaje){
        this.tipo = tipo;
        this.mensaje = mensaje;
    }
}
