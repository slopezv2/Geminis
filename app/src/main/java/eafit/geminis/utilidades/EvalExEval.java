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

    private static Expression expression;
    /**
     *
     * @param expresion Expresión a evaluar
     * @param numero El valor de x
     * @param nuevaExpresion Si se requiere un nuevo objeto
     * @return BigDecimal resultado de la evaluación
     */
    public static BigDecimal evaluar(String expresion, BigDecimal numero,boolean nuevaExpresion){
        if(nuevaExpresion|| expresion==null) {
            expression = new Expression(expresion);
        }
        return expression.with("x",numero).eval();

    }
}
