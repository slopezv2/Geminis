package eafit.geminis.utilidades;

/**
 * Created by Sebas on 11/09/2017.
 */
import com.udojava.evalex.Expression;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Clase para llamar al evaluador EvalEx y retornar el resultado en BigDecimal
 */
public class EvalExEval {

    private Expression expression;
    /**
     * Metodo para llamar al evaluador sobre una expresion
     * @param expresion Expresión a evaluar
     * @param numero El valor de x
     * @param nuevaExpresion Si se requiere un nuevo objeto
     * @return BigDecimal resultado de la evaluación
     */
    public BigDecimal evaluar(String expresion, BigDecimal numero,boolean nuevaExpresion) throws Exception{
        if(nuevaExpresion|| expresion==null) {
            expression = new Expression(expresion).setPrecision(32);
            expression.setRoundingMode(RoundingMode.HALF_UP);
        }
        return expression.with("x",numero).eval();
    }
}
