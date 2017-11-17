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
    public static String ERROR_ENTRADA_FUNCION_G="Revisa la función g(x) dada";
    public static String ERROR_ENTRADA_FUNCION_DF="Revisa la función f'(x) dada";
    public static String ERROR_VALOR_INICIAL="Revisa el valor inicial, recuerda debe ser un valor numérico";
    public static String ERRROR_TOLERANCIA_CERO = "Revisa el valor de la tolerancia, recuerda que tol > 0";
    public static String ERROR_INTERVALOS_INADECUADOS = "Se llegó a un intervalo inadecuado";
    public static String ERROR_LIMITE_INFERIOR = "Por favor revise el límite inferior proporcionado, " +
            "Xi no debe estar vacío y debe ser un valor numérico";
    public static String ERROR_LIMITE_Superior = "Por favor revise el límite superior proporcionado, " +
            "Xs no debe estar vacío y debe ser un valor numérico";
    public static String ERROR_TOLERANCIA = "Error en la tolerancia, recuerde debe ser un valor numérico, tol > 0";
    public static String ESPERAR_PROCESANDO="Espere mientras se procesa el resultado";
    public static String ERROR_DIVISION_CERO="Error, Se ha detectado una división por cero";
    public static String DETECCION_RAIZ_MULTIPLE="Existe una posible raíz múltiple en: ";
    public static String ERROR_DESPEJE_REGRESIVO="No se pudo llevar a cabo la sustitución regresiva";
    public static String DENOMINADOR_CERO="Error, denominador=0";
    public static String NO_GUARDO_FUNCION="No se pudo guardar la funcion";
    public static String NO_BORRADO_FUNCION ="No se pudo borrar las funciones";
    public static String NRO_ECUACIONES_VACIO="Por favor revise el número de ecuaciones que tiene " +
            "el sistema, recuerde debe ser un entero positivo > 0";
    public static String ERROR_ENTRADA_TABLA_SISTEMAS_ECUACIONES="Por favor verifique cada valor " +
            "pasado a la tabla";
    public static String DETECCION_MULTIPLES_SOLUCIONES = "El sistema no tiene solución única";
    public static String ERROR_ENTRADA_NRO_ECUACIONES = "No se pudo generar la matriz de entrada";
}
