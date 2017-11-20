package eafit.geminis.utilidades;

import java.math.BigDecimal;

/**
 * Created by Familialv on 17/11/2017.
 */

public class MatrizMatriz {
    // Clase para almacenar dos matrices bidimencionales tipo Ab
    private BigDecimal[][] L, U;

    public MatrizMatriz(BigDecimal[][] l, BigDecimal[][] u) {
        L = l;
        U = u;
    }

    public BigDecimal[][] getL() {
        return L;
    }

    public BigDecimal[][] getU() {
        return U;
    }
}
