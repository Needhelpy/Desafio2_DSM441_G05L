package udb.edu.desafio2_dsm

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import udb.edu.desafio2_dsm.databinding.ActivityConfirmarPedidoBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.Timestamp
import udb.edu.desafio2_dsm.util.ToastUtil

class ConfirmarPedidoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConfirmarPedidoBinding
    private val db = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth
    private var total = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmarPedidoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        // Obtener el carrito de medicamentos
        val carrito = intent.getParcelableArrayListExtra<Medicament>("carrito") ?: return

        // Calcular el total y mostrar los medicamentos
        carrito.forEach { medicamento ->
            total += medicamento.precio
            binding.tvListaMedicamentos.append("${medicamento.nombre}: $${medicamento.precio}\n")
        }
        binding.tvTotal.text = "Total: $total"

        binding.btnConfirmar.setOnClickListener {
            val usuario = auth.currentUser?.email ?: "desconocido"

            // Crear el pedido con la fecha como Timestamp
            val pedido = hashMapOf(
                "usuario" to usuario,
                "total" to total,
                "medicamentos" to carrito.map { it.nombre },
                "fecha" to Timestamp.now() // Guarda la fecha como Timestamp
            )

            // Guardar el pedido en Firestore
            db.collection("pedidos").add(pedido).addOnSuccessListener {
                // Usar ToastUtil para mostrar el mensaje de confirmación
                ToastUtil.showToast(this, "Pedido confirmado")

                // Enviar el resultado de que el pedido fue confirmado
                setResult(Activity.RESULT_OK)

                finish() // Finalizar la actividad y regresar a la anterior
            }.addOnFailureListener {
                // Usar ToastUtil para mostrar el mensaje de error
                ToastUtil.showToast(this, "Error al confirmar el pedido")
            }
        }

        // Función del botón "Regresar"
        binding.btnRegresar.setOnClickListener {
            // Usar ToastUtil para indicar que se está regresando
            ToastUtil.showToast(this, "Regresando a la pantalla anterior")

            // Regresa a la actividad anterior sin confirmar el pedido
            finish()
        }
    }
}
