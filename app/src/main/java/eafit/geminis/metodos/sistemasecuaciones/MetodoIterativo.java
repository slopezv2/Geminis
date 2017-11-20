package eafit.geminis.metodos.sistemasecuaciones;

import java.math.BigDecimal;
import java.util.ArrayList;

import eafit.geminis.utilidades.ErrorMetodo;
import eafit.geminis.utilidades.Matriz;
import eafit.geminis.utilidades.MatrizMarca;
import eafit.geminis.utilidades.Respuesta;
import eafit.geminis.utilidades.TipoIterativo;
import eafit.geminis.utilidades.TipoRespuesta;

/**
 * Created by Familialv on 19/11/2017.
 */

public class MetodoIterativo {
    public static Respuesta metodo(BigDecimal[][]A, BigDecimal[] b, BigDecimal W, BigDecimal[] x0,
                                   BigDecimal tol, int n, int niter, TipoIterativo tipo, boolean esErrorAbsoluto) throws Exception{
        ArrayList<String> iteraciones = new ArrayList<String>();
        int contador =0;
        BigDecimal dispersion = tol.add(BigDecimal.ONE);
        String iteracion = "";
        String X0ac = Matriz.deVectorATexto(x0);
        iteracion = "0 "+X0ac+"N.A";
        iteraciones.add(iteracion);
        while (dispersion.compareTo(tol)>0 && contador<niter){
            iteracion = "";
            BigDecimal[] x1 = null;
            if (tipo == TipoIterativo.JACOBI_RELAJADO){
                calcularNuevoJacobi(x0,W,A,b,n);
            }else if (tipo == TipoIterativo.GAUSS_SEIDEL_RELAJADO){

            }
            dispersion = Matriz.norma(x1,x0);
            if(!esErrorAbsoluto){
                BigDecimal norma1 = Matriz.norma(x1);
                if(norma1.compareTo(BigDecimal.ZERO)==0){
                    throw new ArithmeticException(ErrorMetodo.ERROR_DIVISION_CERO);
                }
                dispersion = dispersion.divide(norma1,32,BigDecimal.ROUND_HALF_UP);
            }
            x0 = x1;
            String actualesX0 = Matriz.deVectorATexto(x0);
            iteracion = contador+" "+actualesX0+" "+dispersion.toString();
            iteraciones.add(iteracion);
            contador++;
        }
        if(dispersion.compareTo(tol)<0){
            return new Respuesta(TipoRespuesta.APROXIMACION,x0,iteraciones);
        }else{
            return new Respuesta(TipoRespuesta.FRACASO,ErrorMetodo.ERROR_FRACASO_NITERACIONES,iteraciones);
        }
    }
    private static BigDecimal[] calcularNuevoJacobi(BigDecimal[] x0,BigDecimal W, BigDecimal[][] A, BigDecimal[] b, int n){
        int len = n;
        BigDecimal[] x1 = new BigDecimal[len+1];
        for(int i = 1; i <= len ; ++i){
            BigDecimal sum = BigDecimal.ZERO;
            for(int j = 1; j <= len; ++j){
                if (j != i){
                    sum = sum.add(A[i][j].multiply(x0[j]));
                }
            }
            if (A[i][i].compareTo(BigDecimal.ZERO) != 0 ){
                x1[i] = (b[i].subtract(sum)).divide(A[i][i],32,BigDecimal.ROUND_HALF_UP);
                x1[i] = (W.multiply(x1[i])).add(x0[i].multiply(BigDecimal.ONE.subtract(W)));

            }
        }
        return x1;
    }
}
