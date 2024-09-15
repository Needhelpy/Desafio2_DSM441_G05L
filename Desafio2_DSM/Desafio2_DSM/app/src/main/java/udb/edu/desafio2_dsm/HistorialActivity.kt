package udb.edu.desafio2_dsm

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.Timestamp
import udb.edu.desafio2_dsm.databinding.ActivityHistorialBinding
import java.text.SimpleDateFormat
import java.util.*

class HistorialActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistorialBinding
    private val db = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistorialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        val usuario = auth.currentUser?.email ?: return

        db.collection("pedidos")
            .whereEqualTo("usuario", usuario)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    Toast.makeText(this, "No se encontraron pedidos", Toast.LENGTH_SHORT).show()
                    return@addOnSuccessListener
                }

                // Recorremos los documentos obtenidos para mostrar los pedidos
                for (document in documents) {
                    val total = document.getDouble("total")
                    val timestamp = document.getTimestamp("fecha") // Obtén la fecha como Timestamp
                    val medicamentos = document.get("medicamentos") as? List<*>

                    // Formatea el timestamp en una cadena legible
                    val fechaFormateada = if (timestamp != null) {
                        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
                        sdf.format(timestamp.toDate()) // Convierte el Timestamp a Date y luego lo formatea
                    } else {
                        "Fecha desconocida"
                    }

                    // Mostrar cada pedido con la lista de medicamentos y fecha
                    binding.tvHistorial.append("Pedido:\n")
                    medicamentos?.forEach { medicamento ->
                        binding.tvHistorial.append("- $medicamento\n")
                    }
                    binding.tvHistorial.append("Total: $${total}\n")
                    binding.tvHistorial.append("Fecha: $fechaFormateada\n\n")
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al cargar el historial", Toast.LENGTH_SHORT).show()
            }
        // Función del botón "Regresar"
        binding.btnRegresar.setOnClickListener {
            finish() // Cierra la actividad actual y regresa a la anterior
        }
    }
}
