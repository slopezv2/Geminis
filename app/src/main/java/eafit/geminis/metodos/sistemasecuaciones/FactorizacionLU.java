package eafit.geminis.metodos.sistemasecuaciones;

import java.math.BigDecimal;
import java.math.RoundingMode;

import eafit.geminis.utilidades.Matriz;
import eafit.geminis.utilidades.MatrizMatriz;
import eafit.geminis.utilidades.TipoFactorizacion;

/**
 * Created by Familialv on 16/11/2017.
 */

public class FactorizacionLU {
    public static MatrizMatriz metodo(BigDecimal[][]ab, int n, int k, TipoFactorizacion tipo, BigDecimal[][] L,BigDecimal[][] U) throws Exception{
        MatrizMatriz LU = null;
        switch (tipo){
            case CROULT:
                LU = factorizacionCrout(ab,n,k,L,U);
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
        BigDecimal[][]L=new BigDecimal[n+1][n+1];
        BigDecimal[][] U = new BigDecimal[n+1][n+1];
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
    private static MatrizMatriz factorizacionCrout(BigDecimal[][] a, int n, int k, BigDecimal[][]L, BigDecimal[][] U)throws Exception{
        if(U == null || L == null) {
            L = new BigDecimal[n + 1][n + 1];
            U = new BigDecimal[n + 1][n + 1];
            Matriz.rellenarMatriz(L, n);
            Matriz.rellenarMatriz(U, n);
        }
        U[k][k]=BigDecimal.ONE;
        BigDecimal suma = BigDecimal.ZERO;
        for(int p = 1; p<= k;++p){
            suma = suma.add(L[k][p].multiply(U[p][k]));
        }
        L[k][k]=a[k][k].subtract(suma);
        for(int i = k+1;i <= n;++i){
            suma = BigDecimal.ZERO;
            for(int r = 1; r <= k;++r){
                suma = suma.add(L[i][r].multiply(U[r][k]));
            }
            L[i][k]= a[i][k].subtract(suma);
        }
        for(int j = k+1;j <=n;++j){
            suma = BigDecimal.ZERO;
            for(int s = 1; s <= k;++s){
                suma = suma.add(L[k][s].multiply(U[s][j]));
            }
            U[k][j]=(a[k][j].subtract(suma)).divide(L[k][k],32, RoundingMode.HALF_UP);
        }
        return new MatrizMatriz(L,U);
    }
}
