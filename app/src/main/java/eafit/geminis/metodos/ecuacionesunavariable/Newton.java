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

public class Newton {
    /**
     * Metodo para resolver un sistema por Newton
     * @param funcion funcion a evaluar
     * @param funcionD derivada de la funcion
     * @param xi punto inicial a usar
     * @param tol tolerancia
     * @param niter numero de iteraciones maximo
     * @param esAbsoluto si se usa error absoluto
     * @return
     * @throws Exception
     */
    public static Respuesta metodo(String funcion, String funcionD, BigDecimal xi, BigDecimal tol, int niter, boolean esAbsoluto) throws Exception{
        EvalExEval evaluadorFx = new EvalExEval(), evaluadorGx= new EvalExEval();
        ArrayList<String> iteraciones = new ArrayList();
        Respuesta rp;
        BigDecimal fxi = null;
        BigDecimal dfxi = null;
        String iteracion;
        if(niter <= 0 ){
            rp = new Respuesta(TipoRespuesta.Error, ErrorMetodo.ERROR_NITER_INCORRECTO,null);
            return rp;
        }
        if(tol.compareTo(BigDecimal.ZERO) <= 0){
            rp = new Respuesta(TipoRespuesta.Error,ErrorMetodo.ERRROR_TOLERANCIA_CERO,null);
            return rp;
        }
        try {
            fxi= evaluadorFx.evaluar(funcion,xi,true);
        } catch (Exception e) {
            rp = new Respuesta(TipoRespuesta.Error,e.getMessage(),null);
            return rp;
        }
        try {
            dfxi= evaluadorGx.evaluar(funcionD,xi,true);
        } catch (Exception e) {
            rp = new Respuesta(TipoRespuesta.Error,e.getMessage(),null);
            return rp;
        }
        int contador = 0;
        iteracion = contador+" "+xi+" "+fxi+" "+dfxi+"No_Determinado";
        iteraciones.add(iteracion);
        BigDecimal error = tol.add(BigDecimal.ONE);
        BigDecimal x2 = null;
        while (fxi.compareTo(BigDecimal.ZERO)!=0 && error.compareTo(tol)>0 &&
                dfxi.compareTo(BigDecimal.ZERO) != 0 && contador <= niter){
            x2 = xi.subtract(fxi.divide(dfxi,32,BigDecimal.ROUND_HALF_UP));
            try{
                fxi = evaluadorFx.evaluar(funcion,x2,false);
                dfxi = evaluadorGx.evaluar(funcionD,x2,false);
            }catch (Exception e){
                rp = new Respuesta(TipoRespuesta.Error,e.getMessage(),iteraciones);
                return rp;
            }
            if (esAbsoluto){
                error = x2.subtract(xi).abs();
            }else {
                error = x2.subtract(xi).divide(x2,32,BigDecimal.ROUND_HALF_UP).abs();
            }
            xi = x2;
            contador++;
            iteracion = contador+" "+xi+" "+fxi+" "+dfxi+" "+error;
            iteraciones.add(iteracion);
        }
        if (fxi.compareTo(BigDecimal.ZERO)==0){
            rp = new Respuesta(TipoRespuesta.RAIZ,xi,iteraciones);
            return rp;
        }else if( error.compareTo(tol) < 0){
            rp = new Respuesta(TipoRespuesta.APROXIMACION,xi,tol,iteraciones);
            return rp;
        }else if(dfxi.compareTo(BigDecimal.ZERO)==0){
            rp = new Respuesta(TipoRespuesta.FRACASO, ErrorMetodo.DETECCION_RAIZ_MULTIPLE+x2,iteraciones);
            return rp;
        }else {
            rp = new Respuesta(TipoRespuesta.FRACASO, ErrorMetodo.ERROR_FRACASO_NITERACIONES,iteraciones);
            return rp;
        }
    }
}