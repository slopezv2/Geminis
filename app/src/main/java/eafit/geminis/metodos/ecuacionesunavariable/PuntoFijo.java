package eafit.geminis.metodos.ecuacionesunavariable;

import java.math.BigDecimal;
import java.util.ArrayList;

import eafit.geminis.utilidades.ErrorMetodo;
import eafit.geminis.utilidades.EvalExEval;
import eafit.geminis.utilidades.Respuesta;
import eafit.geminis.utilidades.TipoRespuesta;

/**
 * Created by Sebas on 3/10/2017.
 */

public class PuntoFijo {
    /**
     * Metodo abierto para encontrar raiz
     * @param funcion funcion fx
     * @param funcionG funcion gx a emplear para hallar xn
     * @param xi valor inicial
     * @param tol tolerancia de la respuesta
     * @param niter numero maximo de iteraciones
     * @param esAbsoluto el tipo de error a usar absoluto o relativo
     * @return Respuesta con el resultado del metodo
     */
    public static Respuesta metodo(String funcion,String funcionG, BigDecimal xi, BigDecimal tol, int niter, boolean esAbsoluto){
        EvalExEval evaluadorFx = new EvalExEval(), evaluadorGx= new EvalExEval();
        ArrayList<String> iteraciones = new ArrayList();
        Respuesta rp;
        BigDecimal fxi = null;
        String iteracion;
        try {
             fxi= evaluadorFx.evaluar(funcion,xi,true);
        } catch (Exception e) {
            rp = new Respuesta(TipoRespuesta.Error,e.getMessage(),null);
            return rp;
        }
        int contador = 0;
        BigDecimal error = tol.add(BigDecimal.ONE);
        BigDecimal xn = null;
        iteracion = contador+" "+xn+" "+fxi+" No_Determinado";
        iteraciones.add(iteracion);
        while (fxi.compareTo(BigDecimal.ZERO)!=0 && error.compareTo(tol)> 0 && contador <= niter){
            try {
                xn= evaluadorGx.evaluar(funcionG,xi,true);
            } catch (Exception e) {
                rp = new Respuesta(TipoRespuesta.Error,e.getMessage(),iteraciones);
                return rp;
            }
            try {
                fxi= evaluadorFx.evaluar(funcion,xn,false);
            } catch (Exception e) {
                rp = new Respuesta(TipoRespuesta.Error,e.getMessage(),iteraciones);
                return rp;
            }
            if (esAbsoluto){
                error = xn.subtract(xi).abs();
            }else {
                error = xn.subtract(xi).divide(xn,32,BigDecimal.ROUND_HALF_UP).abs();
            }
            iteracion = contador+" "+xn+" "+fxi+" "+error;
            iteraciones.add(iteracion);
            xi = xn;
            contador++;
            iteracion = contador+" "+xn+" "+fxi+" "+error;
            iteraciones.add(iteracion);
        }
        if (fxi.compareTo(BigDecimal.ZERO)==0){
            rp = new Respuesta(TipoRespuesta.RAIZ,xi,iteraciones);
            return rp;
        }else if( error.compareTo(tol) < 0){
            rp = new Respuesta(TipoRespuesta.APROXIMACION,xi,tol,iteraciones);
            return rp;
        }else {
            rp = new Respuesta(TipoRespuesta.FRACASO, ErrorMetodo.ERROR_FRACASO_NITERACIONES,iteraciones);
            return rp;
        }
    }
}
