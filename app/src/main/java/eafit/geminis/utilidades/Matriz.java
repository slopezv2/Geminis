package eafit.geminis.utilidades;

import java.math.BigDecimal;

/**
 * Created by Sebas on 17/10/2017.
 * Se parte de que se usaran indices de i = 1, en vez de 0, hasta n.
 */
public final class Matriz {
    private Matriz(){

    }

    /**
     * Formar la matriz aumentada
     * @param a
     * @param b
     * @return
     */
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
     * Sustitucion progresiva, falta revisar en ejecucion
     * @param ab matriz Ab
     * @param n numero de ecuaciones
     * @return vector x de despeje
     * @throws Exception de division por 0
     */
    public static BigDecimal[] sustitucionProgresiva(BigDecimal[][] ab, int n)throws  Exception{
        BigDecimal[] x = new BigDecimal[n+1];
        for(BigDecimal xactual:x){
            xactual = new BigDecimal(0);
        }
        for(int i = 1; i <= n; ++i){
            BigDecimal sum = BigDecimal.ZERO;
            for(int p = 1; p < i; ++p){
                sum = sum.add(ab[i][p].multiply(x[p]));
            }
            x[i] = (ab[i][n+1].subtract(sum)).divide(ab[i][i],64,BigDecimal.ROUND_HALF_UP);
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

    public static BigDecimal[][] intercambiarColumnas(BigDecimal[][] ab, int columnaMayor, int k) {
        BigDecimal[] aux = new BigDecimal[ab.length+1];
        for (int i = 1;i<ab.length;++i){
            aux[i]= ab[i][k];
        }
        for (int i = 1;i<ab.length;++i){
            ab[i][k]= ab[i][columnaMayor];
        }
        for (int i = 1;i<ab.length;++i){
            ab[i][columnaMayor]= aux[i];
        }
        return ab;
    }

    public static int[] intercambiarMarcas(int[] marcas, int columnaMayor, int k) {
        int aux = marcas[k];
        marcas[k]=columnaMayor;
        marcas[columnaMayor]=aux;
        return marcas;
    }
    public static BigDecimal[] obtenerVectorB(BigDecimal[][] ab, int n){
        BigDecimal[] b = new BigDecimal[n+1];
        for(int i = 1;i<=n;++i){
            b[i] = ab[i][n+1];
        }
        return b;
    }
    public static void rellenarMatriz(BigDecimal[][] L, int  len){
        for (int i = 1; i <= len; ++i){
            for (int j =1 ; j <= len; ++j){
                L[i][j] = BigDecimal.ZERO;
            }
        }
    }
    public static BigDecimal sqrt(BigDecimal A) { // implementation of the Babylonian method
        BigDecimal x0 = new BigDecimal("0");
        BigDecimal x1 = new BigDecimal(Math.sqrt(A.doubleValue()));
        BigDecimal TWO = BigDecimal.valueOf(2);
        while (!x0.equals(x1)) {
            x0 = x1;
            x1 = A.divide(x0, 32, BigDecimal.ROUND_HALF_UP);
            x1 = x1.add(x0);
            x1 = x1.divide(TWO, 32, BigDecimal.ROUND_HALF_UP);
        }
        return x1;
    }
}
