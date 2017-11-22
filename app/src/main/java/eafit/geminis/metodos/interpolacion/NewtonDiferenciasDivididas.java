package eafit.geminis.metodos.interpolacion;

import java.math.BigDecimal;

import eafit.geminis.utilidades.ErrorMetodo;
import eafit.geminis.utilidades.Respuesta;

/**
 * Created by Familialv on 20/11/2017.
 */

public class NewtonDiferenciasDivididas {
    public static BigDecimal[][] metodo(BigDecimal[][] puntos, int n) throws Exception{
        BigDecimal m[][] = new BigDecimal[n][n+1];

        for (int i = 0; i < m.length; ++i){
            for(int j = 0; j <  m.length;++j){
                m[i][0] = puntos[i+1][1];
                m[i][1] = puntos[i+1][2];
                m[i][j] = BigDecimal.ZERO;
            }
        }
        for (int j = 2; j <= n+1; ++j){
            for (int i = j-1; i < n; ++i){
                if((m[i][0].subtract(m[i-j+1][0])).compareTo(BigDecimal.ZERO) == 0){
                    throw new ArithmeticException(ErrorMetodo.ERROR_DIVISION_CERO);
                }
                else{
                    m[i][j] = (m[i][j-1].subtract(m[i - 1][j-1])).divide(m[i][0].subtract(m[i-
                            j+1][0]),32,BigDecimal.ROUND_HALF_UP);
                }
            }
        }
        return m;
    }
    public static String obtenerPolinomio(BigDecimal[][] m,int n){
        String pol="";
        for(int i = 0; i < n;++i){
            pol+= (m[i][i+1].toString());
            for(int j = 0; j < i;++j){
                pol+=("(x-"+m[j][0].toString()+")");
            }
            if (i < n-1) {
                pol += "+";
            }
        }
        return pol;
    }
}
