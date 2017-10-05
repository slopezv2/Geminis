package eafit.geminis.metodos.ecuacionesunavariable;

import java.math.BigDecimal;
import java.util.ArrayList;

import eafit.geminis.utilidades.ErrorMetodo;
import eafit.geminis.utilidades.EvalExEval;
import eafit.geminis.utilidades.Respuesta;
import eafit.geminis.utilidades.TipoRespuesta;

/**
 * Created by Sebas on 2/10/2017.
 */

public class ReglaFalsa {
    public static Respuesta metodo(String funcion, BigDecimal xi, BigDecimal xs, BigDecimal tol, int niter,boolean esAbsoluto){
        ArrayList<String> iteraciones = new ArrayList<>();
        BigDecimal two = new BigDecimal(2);
        Respuesta rp;
        if(niter <= 0 ){
            rp = new Respuesta(TipoRespuesta.Error, ErrorMetodo.ERROR_NITER_INCORRECTO,null);
            return rp;
        }
        if(tol.compareTo(BigDecimal.ZERO) <= 0){
            rp = new Respuesta(TipoRespuesta.Error,ErrorMetodo.ERRROR_TOLERANCIA_CERO,null);
            return rp;
        }
        BigDecimal fxi =null
                , fxs= null;
        EvalExEval evaluador = new EvalExEval();
        try{
            fxi = evaluador.evaluar(funcion,xi,true);
            fxs = evaluador.evaluar(funcion,xs,false);
        }catch (Exception ae){
            rp = new Respuesta(TipoRespuesta.Error,ae.getMessage(),null);
            return rp;
        }
        if(fxi.compareTo(BigDecimal.ZERO) == 0){
            rp = new Respuesta(TipoRespuesta.RAIZ,xi,iteraciones);
            return rp;
        }else if(fxs.compareTo(BigDecimal.ZERO)==0) {
            rp = new Respuesta(TipoRespuesta.RAIZ, xs, iteraciones);
            return rp;
        }else if(fxi.multiply(fxs).compareTo(BigDecimal.ZERO) < 0){
            BigDecimal xm = (xi.add(xs)).divide(two);
            BigDecimal fxm = null;
            try {
               fxm  = evaluador.evaluar(funcion,xm,false);
            } catch (Exception e) {
                rp = new Respuesta(TipoRespuesta.Error, e.getMessage(), iteraciones);
                return rp;
            }
            String iteracion = 0+" "+xi+" "+xs+" "+xm+ " "+fxm+" "+"No_Determinado";
            iteraciones.add(iteracion);
            int cont = 1;
            BigDecimal error = tol.add(two);
            BigDecimal div = fxi.subtract(fxs);
            while( (fxm.compareTo(BigDecimal.ZERO) != 0)&& (error.compareTo(tol) > 0)  && (cont <= niter)
                    && (div.compareTo(BigDecimal.ZERO) != 0)){
                if(fxi.multiply(fxm).compareTo(BigDecimal.ZERO) < 0){
                    xs = xm;
                    fxs = fxm;
                }else{
                    xi = xm;
                    fxi = fxm;
                }
                BigDecimal xAux = xm;
                xm = xi.subtract((fxi.multiply(xi.subtract(xs))).divide(fxi.subtract(fxs),32,BigDecimal.ROUND_HALF_UP));
                try {
                    fxm  = evaluador.evaluar(funcion,xm,false);
                } catch (Exception e) {
                    rp = new Respuesta(TipoRespuesta.Error, e.getMessage(), iteraciones);
                    return rp;
                }
                div = fxi.subtract(fxs);
                if(esAbsoluto) error = (xm.subtract(xAux)).abs();
                else error = (xm.subtract(xAux).divide(xm,32,BigDecimal.ROUND_HALF_UP)).abs();
                iteracion = cont+" "+xi+" "+xs+" "+xm+ " "+fxm+" "+error;
                iteraciones.add(iteracion);
                cont++;
            }
            if(fxm.compareTo(BigDecimal.ZERO) == 0){
                rp = new Respuesta(TipoRespuesta.RAIZ, xm, iteraciones);
                return rp;
            }else if(error.compareTo(tol) < 0){
                rp = new Respuesta(TipoRespuesta.APROXIMACION, xm,tol, iteraciones);
                return rp;
            }else if (div.compareTo(BigDecimal.ZERO) == 0){
                rp = new Respuesta(TipoRespuesta.Error, ErrorMetodo.ERROR_DIVISION_CERO, iteraciones);
                return rp;
            }else {
                rp = new Respuesta(TipoRespuesta.FRACASO,ErrorMetodo.ERROR_FRACASO_NITERACIONES,iteraciones);
                return rp;
            }
        }else {
            rp = new Respuesta(TipoRespuesta.FRACASO, ErrorMetodo.ERROR_INTERVALOS_INADECUADOS, iteraciones);
            return rp;
        }
    }
}
