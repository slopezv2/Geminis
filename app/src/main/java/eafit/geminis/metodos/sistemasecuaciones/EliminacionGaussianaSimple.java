package eafit.geminis.metodos.sistemasecuaciones;

import java.math.BigDecimal;

import static eafit.geminis.utilidades.Matriz.formarMatrizAumentada;

/**
 * Created by Sebas on 17/10/2017.
 */

public class EliminacionGaussianaSimple {
    public static BigDecimal[][] metodo(BigDecimal[][]ab,int n){

        for (int k =1; k <= n-1;++k){
            for(int i = k+1; i <= n;++i){
                BigDecimal multiplicador = ab[i][k].divide(ab[k][k],32,BigDecimal.ROUND_HALF_UP);
                for(int j = k; j <= n+1;++j){
                    ab[i][j] = ab[i][j].subtract(multiplicador.multiply(ab[k][j]));
                }
            }
        }
        return ab;
    }
}
