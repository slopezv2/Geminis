package eafit.geminis.utilidades;

/**
 * Created by Sebas on 18/09/2017.
 */

public class ErrorMetodo {
    public static String ERROR_NITER_INCORRECTO="El número de iteraciones es incorrecto, verificar: " +
            "número iteraciones > 0 y número entero";
    public static String ERROR_DELTA_INCORRECTO="El delta es incorrecto, verificar que sea un dato numérico y delta != 0";
    public static String ERROR_FRACASO_NITERACIONES="Fracasó en las iteraciones especificadas";
    public static String ERROR_FORMATO_METODO="Por favor revise cada función, se ha encontrado un problema";
    public static String ERROR_ENTRADA_PUNTOS_GRAFICADOR="Por favor revise los puntos, recuerde " +
            "debe ser un número entero y positivo mayor qur 0";
    public static String ERROR_ENTRADA_FUNCION="Revisa la función dada";
    public static String ERROR_VALOR_INICIAL="Revisa el valor inicial, recuerda debe ser un valor numérico";
    public static String ERRROR_TOLERANCIA_CERO = "Revisa el valor de la tolerancia, recuerda que tol > 0";
    public static String ERROR_INTERVALOS_INADECUADOS = "Se llegó a un intervalo inadecuado";
    public static String ERROR_LIMITE_INFERIOR = "Por favor revise el límite inferior proporcionado, " +
            "Xi no debe estar vacío y debe ser un valor numérico";
    public static String ERROR_LIMITE_Superior = "Por favor revise el límite superior proporcionado, " +
            "Xs no debe estar vacío y debe ser un valor numérico";
    public static String ERROR_TOLERANCIA = "Error en la tolerancia, recuerde debe ser un valor numérico, tol > 0";
    public static String ESPERAR_PROCESANDO="Espere mientras se procesa el resultado";
}
