package eafit.geminis.metodos.ecuacionesunavariable;

import java.math.BigDecimal;
import java.util.ArrayList;

import eafit.geminis.utilidades.ErrorMetodo;
import eafit.geminis.utilidades.EvalExEval;
import eafit.geminis.utilidades.Respuesta;
import eafit.geminis.utilidades.TipoRespuesta;

/**
 * Created by Familialv on 1/10/2017.
 */

public class RaizMultiple {
    public static Respuesta metodo(String funcion,String funcionPrima, String funcion2Prima,
                                   BigDecimal x0, BigDecimal tol, int niter, boolean errorAbsoluto){
        EvalExEval evaluadorF = new EvalExEval();
        EvalExEval evaluadorFPrima = new EvalExEval();
        EvalExEval evaluadorF2Prima = new EvalExEval();
        BigDecimal fx0 = null;
        BigDecimal dfx0 = null;
        BigDecimal ddfx0 = null;
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
        try {
            fx0 = evaluadorF.evaluar(funcion,x0,true);
            dfx0 = evaluadorFPrima.evaluar(funcionPrima,x0,true);
            ddfx0 = evaluadorF2Prima.evaluar(funcion2Prima,x0,true);
        }catch (Exception e){
            rp = new Respuesta(TipoRespuesta.Error,e.getMessage(),null);
            return rp;
        }
        BigDecimal den = dfx0.multiply(dfx0).subtract(fx0.multiply(ddfx0));
        int contador = 0;
        BigDecimal error = tol.add(BigDecimal.ONE);
        iteracion = contador+" "+x0+" "+fx0+" "+dfx0+" "+ddfx0+"  "+"No_Determinado";
        iteraciones.add(iteracion);
        while (fx0.compareTo(BigDecimal.ZERO)!=0 && den.compareTo(BigDecimal.ZERO)!=0 &&
                error.compareTo(tol) > 0 && contador < niter){
            BigDecimal xn = x0.subtract(fx0.multiply(dfx0).divide(den,32,BigDecimal.ROUND_HALF_UP));
            try {
                fx0 = evaluadorF.evaluar(funcion,xn,false);
                dfx0 = evaluadorFPrima.evaluar(funcionPrima,xn,false);
                ddfx0 = evaluadorF2Prima.evaluar(funcion2Prima,xn,false);
            }catch (Exception e){
                rp = new Respuesta(TipoRespuesta.Error,e.getMessage(),iteraciones);
                return rp;
            }
            if(errorAbsoluto){
                error= xn.subtract(x0).abs();
            }else {
                error= xn.subtract(x0).divide(xn,32,BigDecimal.ROUND_HALF_UP).abs();
            }
            den = dfx0.multiply(dfx0).subtract(fx0.multiply(ddfx0));
            x0 = xn;
            contador++;
            iteracion = contador+" "+x0+" "+fx0+" "+dfx0+" "+ddfx0+"  "+error;
            iteraciones.add(iteracion);
        }
        if(fx0.compareTo(BigDecimal.ZERO)==0){
            rp = new Respuesta(TipoRespuesta.RAIZ,x0,iteraciones);
            return rp;
        }else if(error.compareTo(tol)<0){
            rp = new Respuesta(TipoRespuesta.APROXIMACION,x0,tol,iteraciones);
            return rp;
        }else if (den.compareTo(BigDecimal.ZERO) == 0){
            rp = new Respuesta(TipoRespuesta.FRACASO, ErrorMetodo.DENOMINADOR_CERO,iteraciones);
            return rp;
        }else {
            rp = new Respuesta(TipoRespuesta.FRACASO, ErrorMetodo.ERROR_FRACASO_NITERACIONES,iteraciones);
            return rp;
        }
    }
}