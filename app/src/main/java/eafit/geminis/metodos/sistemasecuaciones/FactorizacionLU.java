package eafit.geminis.metodos.sistemasecuaciones;

import java.math.BigDecimal;

import eafit.geminis.utilidades.MatrizMatriz;
import eafit.geminis.utilidades.TipoFactorizacion;

/**
 * Created by Familialv on 16/11/2017.
 */

public class FactorizacionLU {
    public static MatrizMatriz metodo(BigDecimal[][]ab, int n, int k, TipoFactorizacion tipo) throws Exception{
        MatrizMatriz LU = null;
        switch (tipo){
            case CROULT:
                break;
            case GAUUSS:
                LU = factorizacionGauss(ab,n,k);
            case DOOLITLE:
                break;
            case CHOLESKY:
                break;
                default:
        }
        return LU;
    }
    private static MatrizMatriz factorizacionGauss(BigDecimal[][] a, int n, int k)throws Exception{
        BigDecimal[][]L=new BigDecimal[a.length][a.length];
        BigDecimal[][] U = new BigDecimal[a.length][a.length];
        for(int i = k+1;i <= n;++i){
            a[i][k]=a[i][k].divide(a[k][k],32,BigDecimal.ROUND_HALF_UP);
            for(int j = k+1;j<= n;++j){
                a[i][j]=a[i][j].subtract(a[i][k].multiply(a[k][j]));
            }
        }
        for(int i = 1;i <= n;++i){
            for(int j = 1;j<=n;++j){
                L[i][i] = BigDecimal.ONE;
                if(j < i){
                    L[i][j]= a[i][j];
                }else if(j>=i){
                    U[i][j] = a[i][j];
                }
            }
        }
        return new MatrizMatriz(L,U);
    }
}
