package eafit.geminis.metodos.ecuacionesunavariable;

import java.math.BigDecimal;
import java.util.ArrayList;

import eafit.geminis.utilidades.EvalExEval;
import eafit.geminis.utilidades.Respuesta;
import eafit.geminis.utilidades.TipoRespuesta;

/**
 * Created by Sebas on 3/10/2017.
 */

public class Newton {
    public static Respuesta metodo(String funcion, String funcionD, BigDecimal xi, BigDecimal tol, int niter, boolean esAbsoluto){
        EvalExEval evaluadorFx = new EvalExEval(), evaluadorGx= new EvalExEval();
        ArrayList<String> iteraciones = new ArrayList();
        Respuesta rp;
        BigDecimal fxi = null;
        BigDecimal dfxi = null;
        String iteracion;
        try {
            fxi= evaluadorFx.evaluar(funcion,xi,true);
        } catch (Exception e) {
            rp = new Respuesta(TipoRespuesta.Error,e.getMessage(),null);
        }
        try {
            dfxi= evaluadorFx.evaluar(funcion,xi,true);
        } catch (Exception e) {
            rp = new Respuesta(TipoRespuesta.Error,e.getMessage(),null);
        }
        int contador = 0;
        BigDecimal error = tol.add(BigDecimal.ONE);
        while (fxi.compareTo(BigDecimal.ZERO)!=0 && error.compareTo(tol)>0 &&
                dfxi.compareTo(BigDecimal.ZERO) != 0 && contador <= niter){
            BigDecimal x2 = xi.subtract(fxi.divide(dfxi,32,BigDecimal.ROUND_HALF_UP));
            //TODO
        }
        return null;
    }
}
