package eafit.geminis.metodos.sistemasecuaciones;

import java.math.BigDecimal;

import static eafit.geminis.utilidades.Matriz.formarMatrizAumentada;

/**
 * Created by Sebas on 17/10/2017.
 */

public class EliminacionGaussianaSimple {
    public static BigDecimal[][] metodo(BigDecimal[][]ab,int n) throws Exception{
        BigDecimal abkk;
        for (int k =1; k <= n-1;++k){
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
        }
        return ab;
    }
}
