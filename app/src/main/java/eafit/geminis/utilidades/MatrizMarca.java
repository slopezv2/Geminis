package eafit.geminis.utilidades;

import java.math.BigDecimal;

/**
 * Created by Sebas on 9/11/2017.
 */

public class MatrizMarca {
    // clase para almacenar una matriz y un vector BigDecimal
    private BigDecimal[][] ab;
    private int[] marcas;

    public MatrizMarca(BigDecimal[][] ab, int[] marcas) {
        this.ab = ab;
        this.marcas = marcas;
    }

    public BigDecimal[][] getAb() {
        return ab;
    }

    public int[] getMarcas() {
        return marcas;
    }

}
