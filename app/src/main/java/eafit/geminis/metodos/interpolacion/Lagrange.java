package eafit.geminis.metodos.interpolacion;

import java.math.BigDecimal;

/**
 * Created by Sebas on 22/11/2017.
 */

public class Lagrange {
    public static String metodo(BigDecimal[][] puntos,int n) throws Exception{
        String polinomio = "";
        String l[] = new String[n];
        BigDecimal menosUno = new BigDecimal(-1);
        for(int i = 0; i < n; ++i){
            String pol = "";
            BigDecimal div = BigDecimal.ONE;
            for(int j = 0; j < n;++j){
                if(i != j){
                    div = div.multiply(puntos[i+1][1].subtract(puntos[j+1][1]));
                    if (puntos[j+1][1].compareTo(BigDecimal.ZERO) < 0){
                        pol += ("[(x+" + (menosUno.multiply(puntos[j+1][1])) + ")");
                    }else{
                        pol += ("[(x-" + puntos[j+1][1] + ")");
                    }
                }

            }
            pol += ("/(" + div + ")]");
            l[i] = pol;
            if (puntos[i+1][2].compareTo(BigDecimal.ZERO) < 0){
                polinomio += ( puntos[i+1][2] + "*" + l[i]);
            }else{
                polinomio += ("+"+puntos[i+1][2] + "*" + l[i]);
            }
        }
        if(polinomio.charAt(0)=='+'){
            polinomio = polinomio.substring(1);
        }
        return polinomio;
    }
}
