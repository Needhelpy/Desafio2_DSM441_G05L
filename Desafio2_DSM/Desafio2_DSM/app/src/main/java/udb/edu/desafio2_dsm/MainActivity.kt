package udb.edu.desafio2_dsm

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Crear un usuario con email y contraseña (ejemplo)
        val email = "usuario@ejemplo.com"
        val password = "contraseñaSegura123"

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // El registro fue exitoso
                    val user = auth.currentUser
                    Log.d("FirebaseAuth", "Usuario creado con éxito, UID: ${user?.uid}")
                    Toast.makeText(this, "Usuario creado", Toast.LENGTH_SHORT).show()
                } else {
                    // Si la creación de usuario falla, muestra un mensaje al usuario
                    Log.w("FirebaseAuth", "Error en la creación de usuario", task.exception)
                    Toast.makeText(this, "Error en la creación de usuario", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
