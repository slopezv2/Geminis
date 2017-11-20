package eafit.geminis.metodos.sistemasecuaciones;

import java.math.BigDecimal;

/**
 * Created by Sebas on 17/10/2017.
 */

public class EliminacionGaussianaSimple {
    /**
     * Metodo de eliminacion gaussiana sin pivoteo paso a paso
     * @param ab matriz aumentada
     * @param n total ecuaciones
     * @param k etapa actual
     * @return matriz ab por cada etapa
     * @throws Exception
     */
    public static BigDecimal[][] metodo(BigDecimal[][]ab,int n, int k) throws Exception{
        BigDecimal abkk;
            for(int i = k+1; i <= n;++i){
                 abkk = ab[k][k];
                if (abkk.compareTo(BigDecimal.ZERO)==0){
                    throw new ArithmeticException("División por cero detectada");
                }
                BigDecimal multiplicador = ab[i][k].divide(abkk,32,BigDecimal.ROUND_HALF_UP);
                for(int j = k; j <= n+1;++j){
                    ab[i][j] = ab[i][j].subtract(multiplicador.multiply(ab[k][j]));
                }
            }
        return ab;
    }
}
