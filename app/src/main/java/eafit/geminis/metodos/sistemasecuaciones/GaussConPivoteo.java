package eafit.geminis.metodos.sistemasecuaciones;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.Bidi;

import eafit.geminis.utilidades.ErrorMetodo;
import eafit.geminis.utilidades.Matriz;
import eafit.geminis.utilidades.MatrizMarca;
import eafit.geminis.utilidades.TipoPivoteo;

/**
 * Created by Sebas on 9/11/2017.
 */

public class GaussConPivoteo {
    /**
     * Calcula paso a paso variando k la eliminacion gaussiana con pivoteo
     * @param ab matriz aumentada
     * @param k punto desde donde va
     * @param n cantidad de ecuaciones
     * @param tipo tipo de pivoteo a usar
     * @return ab después de la iteracion k
     * @throws Exception división por cero o multiples soluciones
     */
    public static MatrizMarca metodo(BigDecimal[][]ab, int k, int n, TipoPivoteo tipo, int[] marcas) throws Exception{
        if (tipo == TipoPivoteo.PARCIAL){
            ab = pivoteoParcial(ab,n,k);
        }else if(tipo == TipoPivoteo.TOTAL){
            MatrizMarca aux = pivoteoTotal(ab,n,k,marcas);
            ab = aux.getAb();
            marcas = aux.getMarcas();
        }
        BigDecimal denon = ab[k][k];
        for(int i = k+1; i <= n;++i){
            if(denon.compareTo(BigDecimal.ZERO)==0){
                throw new ArithmeticException(ErrorMetodo.ERROR_DIVISION_CERO);
            }
            BigDecimal multiplicador = ab[i][k].divide(denon,32, RoundingMode.HALF_UP);
            for(int j = k; j <= n+1;++j){
                ab[i][j]=ab[i][j].subtract(multiplicador.multiply(ab[k][j]));
            }
        }
        return new MatrizMarca(ab,marcas);
    }

    private static MatrizMarca pivoteoTotal(BigDecimal[][] ab, int n, int k,int[] marcas) throws Exception{
        BigDecimal mayor = BigDecimal.ZERO;
        int filaMayor =k;
        int columnaMayor = k;
        for(int r = k;r<=n;++r){
            for(int s = k; s <=n;++s){
                BigDecimal aux = ab[r][s].abs();
                if(aux.compareTo(mayor)>0){
                    mayor = aux;
                    filaMayor = r;
                    columnaMayor = s;
                }
            }
        }
        if(mayor.compareTo(BigDecimal.ZERO)==0){
            throw new Exception(ErrorMetodo.DETECCION_MULTIPLES_SOLUCIONES);
        }else{
            if(filaMayor != k){
                ab = Matriz.intercambioFilas(ab,filaMayor,k);
            }
            if (columnaMayor !=k){
                ab = Matriz.intercambiarColumnas(ab,columnaMayor,k);
                marcas = Matriz.intercambiarMarcas(marcas,columnaMayor,k);
            }
            return new MatrizMarca(ab,marcas);
        }
    }

    private static BigDecimal[][] pivoteoParcial(BigDecimal[][] ab,int n, int k) throws Exception {
        BigDecimal mayor = ab[k][k].abs();
        int filaMayor = k;
        for (int s = k+1;s<=n;++s){
            if (ab[s][k].abs().compareTo(mayor)>0){
                mayor = ab[s][k].abs();
                filaMayor = s;
            }
        }
        if (mayor.compareTo(BigDecimal.ZERO)==0){
            throw new Exception(ErrorMetodo.DETECCION_MULTIPLES_SOLUCIONES);
        }else {
            if (filaMayor != k) {
                ab = Matriz.intercambioFilas(ab, filaMayor, k);
            }
            return ab;
        }
    }
}