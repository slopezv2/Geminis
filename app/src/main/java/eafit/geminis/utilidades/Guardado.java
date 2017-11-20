package eafit.geminis.utilidades;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Familialv on 21/10/2017.
 */

public class Guardado {
    //Nombre del archivo para guardar las funciones
    public static String ARCHIVO_FUNCIONES = "funciones.txt";

    /**
     * Metodo para guardar las funciones
     * @param context
     * @param funcion
     * @return
     */
    public static boolean guardarFuncion(Context context,String funcion){
        funcion += "\n";
        FileOutputStream outputStream = null;
        try {
            outputStream = context.openFileOutput(ARCHIVO_FUNCIONES, Context.MODE_PRIVATE | Context.MODE_APPEND);
            outputStream.write(funcion.getBytes());
            outputStream.close();
        } catch (Exception e) {
            Log.e("Escritura",e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Metodo para recuperar las funciones
     * @param context
     * @return
     */
    public static String[] recuperarFunciones(Context context){
        FileInputStream entrada = null;
        String funciones = "";
        try {
            entrada = context.openFileInput(ARCHIVO_FUNCIONES);
            int caracter;
            while ((caracter=entrada.read())!= -1) {
                funciones += Character.toString((char)caracter);
            }
            entrada.close();
        } catch (Exception e) {
            Log.e("Lectura",e.getMessage());
        }
        return funciones.split("\n");
    }

    /**
     * Metodo para borrar las funciones
     * @param context
     * @return
     */
    public static boolean Borrar(Context context){
        File ubicacion = context.getFilesDir();
        File archivo = new File(ubicacion,ARCHIVO_FUNCIONES);
        return archivo.delete();
    }
}
