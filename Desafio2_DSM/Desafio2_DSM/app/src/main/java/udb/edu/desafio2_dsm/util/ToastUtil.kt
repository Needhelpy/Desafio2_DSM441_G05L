package udb.edu.desafio2_dsm.util

import android.content.Context
import android.widget.Toast

object ToastUtil {
    /**
     * Función para mostrar un Toast
     * @param context - contexto de la actividad o aplicación
     * @param message - mensaje a mostrar
     * @param duration - duración del Toast (Toast.LENGTH_SHORT o Toast.LENGTH_LONG)
     */
    fun showToast(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, message, duration).show()
    }
}