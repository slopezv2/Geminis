package eafit.geminis.metodos.ecuacionesunavariable;

/**
 * Created by Sebas on 18/09/2017.
 */
import java.math.BigDecimal;
import java.util.ArrayList;
import eafit.geminis.utilidades.*;


/**
 *
 * @author slopezv2
 */
public class BusquedaIncremental {
    /**
     *
     * @param funcion Función a evaluar
     * @param x0 valor inicial de x
     * @param delta cambio en la búsqueda
     * @param niter Número máximo de iteraciones
     * @return Respuesta con alguna de las posibilidades internas
     */
    public static Respuesta metodo(String funcion, BigDecimal x0, BigDecimal delta, int niter){
        Respuesta rp;
        if(niter < 0 ){
            rp = new Respuesta(TipoRespuesta.Error,ErrorMetodo.ERROR_NITER_INCORRECTO);
            return rp;
        }
        if(delta.compareTo(BigDecimal.ZERO) < 1){
            rp = new Respuesta(TipoRespuesta.Error,ErrorMetodo.ERROR_DELTA_INCORRECTO);
            return rp;
        }

        BigDecimal fx0 = null;
        EvalExEval evaluador = new EvalExEval();
        try {
            fx0 = evaluador.evaluar(funcion,x0,true);
        } catch (Exception e) {
            rp = new Respuesta(TipoRespuesta.Error,e.getMessage());
            return rp;
        }
        if(fx0.compareTo(BigDecimal.ZERO) == 0){
            rp = new Respuesta(TipoRespuesta.RAIZ,x0);
            return rp;
        }
        else{
            BigDecimal x1 = x0.add(delta);
            int cont = 1;
            BigDecimal fx1 = null;
            try {
                fx1 = evaluador.evaluar(funcion,x1,false);
            } catch (Exception e) {
                rp = new Respuesta(TipoRespuesta.Error,e.getMessage());
                return rp;
            }
            while(((fx0.multiply(fx1)).compareTo(BigDecimal.ZERO) > 0) && (cont <= niter)){
                x0 = x1;
                fx0 = fx1;
                x1 = x0.add(delta);
                try {
                    fx1 = evaluador.evaluar(funcion,x1,false);
                } catch (Exception e) {
                    rp = new Respuesta(TipoRespuesta.Error,e.getMessage());
                    return rp;
                }
                cont++;
            }
            if(fx1.compareTo(BigDecimal.ZERO) == 0){
                rp = new Respuesta(TipoRespuesta.RAIZ,x1);
                return rp;
            }
            else if(((fx0.multiply(fx1)).compareTo(BigDecimal.ZERO) < 0)){
                ArrayList<BigDecimal> intervalo = new ArrayList<BigDecimal>();
                intervalo.add(x0);
                intervalo.add(x1);
                rp = new Respuesta(TipoRespuesta.INTERVALO,intervalo,null);
                return rp;
            }else{
                rp = new Respuesta(TipoRespuesta.Error,ErrorMetodo.ERROR_FRACASO_NITERACIONES);
                return rp;
            }
        }
    }
}