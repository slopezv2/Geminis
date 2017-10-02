package eafit.geminis.metodos.ecuacionesunavariable;

import java.math.BigDecimal;
import java.util.ArrayList;

import eafit.geminis.utilidades.ErrorMetodo;
import eafit.geminis.utilidades.EvalExEval;
import eafit.geminis.utilidades.Respuesta;
import eafit.geminis.utilidades.TipoRespuesta;

/**
 * Created by nana on 1/10/2017.
 */
public class Biseccion {
    /**
     *  Devuelve el resultado de aplicar el método de bisección
     * @param funcion funcion a evaluar
     * @param Xinf limite inferior
     * @param Xsup limite superior
     * @param tol tolerancia a emplear
     * @param niter numero maximo de iteraciones
     * @param errorAbsoluto true para error absoluto, false para error relativo
     * @return Respuesta con la informacion de los resultados
     */
    public static Respuesta metodo(String funcion, BigDecimal Xinf, BigDecimal Xsup, BigDecimal tol, int niter, boolean errorAbsoluto){
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
            fxi = evaluador.evaluar(funcion,Xinf,true);
            fxs = evaluador.evaluar(funcion,Xsup,false);
        }catch (Exception ae){
            rp = new Respuesta(TipoRespuesta.Error,ae.getMessage(),null);
            return rp;
        }
        if(fxi.compareTo(BigDecimal.ZERO) == 0){
            rp = new Respuesta(TipoRespuesta.RAIZ,Xinf,iteraciones);
            return rp;
        }else if(fxs.compareTo(BigDecimal.ZERO)==0){
            rp = new Respuesta(TipoRespuesta.RAIZ,Xsup,iteraciones);
            return rp;
        }else if (fxi.multiply(fxs).compareTo(BigDecimal.ZERO)<0){
            BigDecimal xm = Xinf.add(Xsup).divide(two,32,BigDecimal.ROUND_HALF_UP);
            BigDecimal fxm = null;
            try {
                fxm = evaluador.evaluar(funcion, xm, false);
            }catch (Exception e){
                rp = new Respuesta(TipoRespuesta.Error,e.getMessage(),null);
                return rp;
            }
            String iteracion = 0+" "+Xinf+" "+Xsup+" "+xm+ " "+fxm+" "+"No determinado";
            iteraciones.add(iteracion);
            int contador = 1;
            BigDecimal error = tol.add(two);
            while (fxm.compareTo(BigDecimal.ZERO)!=0 && tol.compareTo(error)< 0 && contador<= niter){
                if(fxi.multiply(fxm).compareTo(BigDecimal.ZERO)<0){
                    Xsup = xm;
                    fxs = fxm;
                }else {
                    Xinf = xm;
                    fxi = fxm;
                }
                BigDecimal aux = xm;
                xm = Xinf.add(Xsup).divide(two,32,BigDecimal.ROUND_HALF_UP);
                try {
                    fxm = evaluador.evaluar(funcion, xm, false);
                }catch (Exception e){
                    rp = new Respuesta(TipoRespuesta.Error,e.getMessage(),null);
                    return rp;
                }
                if(errorAbsoluto) {
                    error = xm.subtract(aux).abs();
                }else {
                    error = xm.subtract(aux).divide(xm,32,BigDecimal.ROUND_HALF_UP).abs();
                }
                iteracion = contador+" "+Xinf+" "+Xsup+" "+xm+ " "+fxm+" "+error;
                iteraciones.add(iteracion);
                contador++;
            }
            if (fxm.compareTo(BigDecimal.ZERO)==0){
                rp = new Respuesta(TipoRespuesta.RAIZ,xm,iteraciones);
                return rp;
            }else if (error.compareTo(tol)<0){
                rp = new Respuesta(TipoRespuesta.APROXIMACION,xm,tol,iteraciones);
                return rp;
            }else {
                rp = new Respuesta(TipoRespuesta.FRACASO,ErrorMetodo.ERROR_FRACASO_NITERACIONES,iteraciones);
                return rp;
            }
        }else {
            rp = new Respuesta(TipoRespuesta.FRACASO,ErrorMetodo.ERROR_INTERVALOS_INADECUADOS,iteraciones);
            return rp;
        }
    }
}
