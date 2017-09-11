package eafit.geminis.utilidades;

/**
 * Created by Sebas on 11/09/2017.
 */
import com.udojava.evalex.Expression;
import java.math.BigDecimal;

/**
 * Clase para llamar al evaluador EvalEx y retornar el resultado en BigDecimal
 */
public class EvalExEval {
    /**
     *
     * @param expresion Expresión a evaluar
     * @param numero El valor de x
     * @return BigDecimal resultado de la evaluación
     */
    public static BigDecimal evaluar(String expresion, BigDecimal numero){
        BigDecimal resultado = new Expression(expresion).with("x",numero).eval();
        return resultado;
    }
}
