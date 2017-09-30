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
        ArrayList<String> iteraciones = new ArrayList<>();
        Respuesta rp;
        if(niter < 0 ){
            rp = new Respuesta(TipoRespuesta.Error,ErrorMetodo.ERROR_NITER_INCORRECTO,null);
            return rp;
        }
        if(delta.compareTo(BigDecimal.ZERO) == 0){
            rp = new Respuesta(TipoRespuesta.Error,ErrorMetodo.ERROR_DELTA_INCORRECTO,null);
            return rp;
        }
        BigDecimal fx0 = null;
        EvalExEval evaluador = new EvalExEval();
        try {
            fx0 = evaluador.evaluar(funcion,x0,true);
        } catch (Exception e) {
            rp = new Respuesta(TipoRespuesta.Error,e.getMessage(),null);
            return rp;
        }
        if(fx0.compareTo(BigDecimal.ZERO) == 0){
            rp = new Respuesta(TipoRespuesta.RAIZ,x0,null);
            return rp;
        }
        else{
            BigDecimal x1 = x0.add(delta);
            int cont = 0;
            BigDecimal fx1 = null;
            try {
                fx1 = evaluador.evaluar(funcion,x1,false);
            } catch (Exception e) {
                rp = new Respuesta(TipoRespuesta.Error,e.getMessage(),null);
                return rp;
            }
            String iteracion;
            while(((fx0.multiply(fx1)).compareTo(BigDecimal.ZERO) > 0) && (cont <= niter)){
                iteracion = cont+" "+x0+" "+fx0;
                iteraciones.add(iteracion);
                x0 = x1;
                fx0 = fx1;
                x1 = x0.add(delta);
                try {
                    fx1 = evaluador.evaluar(funcion,x1,false);
                } catch (Exception e) {
                    rp = new Respuesta(TipoRespuesta.Error,e.getMessage(),iteraciones);
                    return rp;
                }

                cont++;
            }
            iteracion = cont+" "+x0+" "+fx0;
            iteraciones.add(iteracion);
            if(fx1.compareTo(BigDecimal.ZERO) == 0){
                rp = new Respuesta(TipoRespuesta.RAIZ,x1,iteraciones);
                return rp;
            }
            else if(((fx0.multiply(fx1)).compareTo(BigDecimal.ZERO) < 0)){
                ArrayList<BigDecimal> intervalo = new ArrayList<BigDecimal>();
                intervalo.add(x0);
                intervalo.add(x1);
                rp = new Respuesta(TipoRespuesta.INTERVALO,intervalo,iteraciones);
                return rp;
            }else{
                rp = new Respuesta(TipoRespuesta.FRACASO,ErrorMetodo.ERROR_FRACASO_NITERACIONES,iteraciones);
                return rp;
            }
        }
    }
}