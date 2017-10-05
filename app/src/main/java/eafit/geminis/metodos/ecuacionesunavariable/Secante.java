package eafit.geminis.metodos.ecuacionesunavariable;

import java.math.BigDecimal;
import java.util.ArrayList;

import eafit.geminis.utilidades.ErrorMetodo;
import eafit.geminis.utilidades.EvalExEval;
import eafit.geminis.utilidades.Respuesta;
import eafit.geminis.utilidades.TipoRespuesta;

/**
 * Created by Sebas on 4/10/2017.
 */

public class Secante {
    /**
     * Metodo de la secante
     * @param funcion a evaluar
     * @param x0 punto inicial
     * @param x1 punto superior
     * @param tol tolerancia deseada
     * @param niter numero de iteraciones
     * @param esAbsoluto usar error absoluto true o relativo false
     * @return Respuesta
     */
    public static Respuesta metodo(String funcion, BigDecimal x0,BigDecimal x1, BigDecimal tol, int niter, boolean esAbsoluto){
        EvalExEval evaluador = new EvalExEval();
        BigDecimal fx0 = null;
        ArrayList<String> iteraciones = new ArrayList<>();
        String iteracion = "";
        Respuesta rp;
        if(niter <= 0 ){
            rp = new Respuesta(TipoRespuesta.Error, ErrorMetodo.ERROR_NITER_INCORRECTO,null);
            return rp;
        }
        if(tol.compareTo(BigDecimal.ZERO) <= 0){
            rp = new Respuesta(TipoRespuesta.Error,ErrorMetodo.ERRROR_TOLERANCIA_CERO,null);
            return rp;
        }
        try{
            fx0 = evaluador.evaluar(funcion,x0,true);
        }catch (Exception e){
            rp = new Respuesta(TipoRespuesta.Error,e.getMessage(),null);
            return rp;
        }
        if (fx0.compareTo(BigDecimal.ZERO)==0){
            rp = new Respuesta(TipoRespuesta.RAIZ,x0,null);
            return rp;
        }else {
            BigDecimal fx1=null;
            try {
                fx1 = evaluador.evaluar(funcion,x1,false);
            } catch (Exception e) {
                rp = new Respuesta(TipoRespuesta.Error,e.getMessage(),null);
                return rp;
            }
            int contador =0;
            BigDecimal error = tol.add(BigDecimal.ONE);
            BigDecimal den = fx1.subtract(fx0);
            iteracion = contador+" "+x1+" "+fx1+" No_Determinado";
            iteraciones.add(iteracion);
            while (fx1.compareTo(BigDecimal.ZERO)!=0 && error.compareTo(tol)>0 &&
                    den.compareTo(BigDecimal.ZERO) !=0 && contador < niter){
                BigDecimal x2 = x1.subtract(fx1.multiply(x1.subtract(x0).divide(den,32,BigDecimal.ROUND_HALF_UP)));
                if(esAbsoluto){
                    error= x2.subtract(x1).abs();
                }else {
                    error= x2.subtract(x1).divide(x2,32,BigDecimal.ROUND_HALF_UP).abs();
                }
                x0 = x1;
                fx0 = fx1;
                x1 = x2;
                try {
                    fx1 = evaluador.evaluar(funcion,x1,false);
                } catch (Exception e) {
                    rp = new Respuesta(TipoRespuesta.Error,e.getMessage(),iteraciones);
                    return rp;
                }
                den = fx1.subtract(fx0);
                contador++;
                iteracion = contador+" "+x1+" "+fx1+" "+error;
                iteraciones.add(iteracion);
            }
            if (fx1.compareTo(BigDecimal.ZERO)==0){
                rp = new Respuesta(TipoRespuesta.RAIZ,x1,iteraciones);
                return rp;
            }else if (error.compareTo(tol)< 0) {
                rp = new Respuesta(TipoRespuesta.APROXIMACION,x1,tol,iteraciones);
                return rp;
            }else if (den.compareTo(BigDecimal.ZERO) == 0){
                rp = new Respuesta(TipoRespuesta.FRACASO, ErrorMetodo.DETECCION_RAIZ_MULTIPLE+x1,iteraciones);
                return rp;
            }else {
                rp = new Respuesta(TipoRespuesta.FRACASO, ErrorMetodo.ERROR_FRACASO_NITERACIONES,iteraciones);
                return rp;
            }
        }
    }
}
