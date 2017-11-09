package eafit.geminis.utilidades;

import java.math.BigDecimal;

/**
 * Created by Sebas on 17/10/2017.
 * Se parte de que se usaran indices de i = 1, en vez de 0, hasta n.
 */
public final class Matriz {
    private Matriz(){

    }
    public static BigDecimal[][] formarMatrizAumentada(BigDecimal[][] a, BigDecimal[] b){
        BigDecimal[][] ab = new BigDecimal[a.length][a[0].length+1];
        for(int i = 1; i < a.length;++i){
            for (int j = 1; j < a[0].length;++j){
                ab[i][j] = a[i][j];
            }
            ab[i][a[0].length]=b[i];
        }
        return ab;
    }

    /**
     * Sustitucion regresiva
     * @param ab matriz
     * @param n numero de ecuaciones
     * @return BigDecimal[] las X despejadas
     * @throws Exception en caso de división por 0
     */
    public static BigDecimal[] sustitucionRegresiva(BigDecimal[][] ab, int n)throws  Exception{
        BigDecimal[] x = new BigDecimal[n+1];
        x[n] = ab[n][n+1].divide(ab[n][n],32,BigDecimal.ROUND_HALF_UP);
        for(int i = n-1;i>=1;--i){
            BigDecimal sumatoria=new BigDecimal(0);
            for(int p = i+1; p <= n;++p){
                sumatoria = sumatoria.add(ab[i][p].multiply(x[p]));
            }
            x[i]= (ab[i][n+1].subtract(sumatoria).divide(ab[i][i],32,BigDecimal.ROUND_HALF_UP));
        }
        return x;
    }

    /**
     * Intercambio de filas para una matriz
     * @param ab la matriz
     * @param origen la fila inicial
     * @param destino la fila a donde va
     * @return ab con el cambio hecho
     */
    public static BigDecimal[][] intercambioFilas(BigDecimal[][] ab,int origen, int destino){
        BigDecimal[] aux = ab[destino];
        ab[destino] = ab[origen];
        ab[origen] = aux;
        return ab;
    }
}
