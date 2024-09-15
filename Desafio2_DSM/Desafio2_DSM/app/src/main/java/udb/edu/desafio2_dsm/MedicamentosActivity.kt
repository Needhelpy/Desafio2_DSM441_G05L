package udb.edu.desafio2_dsm

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import udb.edu.desafio2_dsm.databinding.ActivityMedicamentosBinding
import com.google.firebase.auth.FirebaseAuth

const val REQUEST_CONFIRMAR_PEDIDO = 1

class MedicamentosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMedicamentosBinding
    private lateinit var auth: FirebaseAuth
    private val medicamentos = listOf(
        Medicament("Paracetamol", 2.5),
        Medicament("Ibuprofeno", 3.0),
        Medicament("Aspirina", 1.5),
        Medicament("Amoxicilina", 5.0),
        Medicament("Omeprazol", 2.8),
        Medicament("Clorfenamina", 1.2),
        Medicament("Vitamina C", 0.9),
        Medicament("Loratadina", 1.0),
        Medicament("Captopril", 4.5),
        Medicament("Losartán", 6.0)
    )
    private val carrito = mutableListOf<Medicament>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMedicamentosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.rvMedicamentos.layoutManager = LinearLayoutManager(this)
        val adapter = MedicamentoAdapter(medicamentos) { medicamento ->
            carrito.add(medicamento)
            Toast.makeText(this, "${medicamento.nombre} añadido al carrito", Toast.LENGTH_SHORT).show()
        }
        binding.rvMedicamentos.adapter = adapter

        // Botón para ver el carrito y pasar a ConfirmarPedidoActivity
        binding.btnVerCarrito.setOnClickListener {
            val intent = Intent(this, ConfirmarPedidoActivity::class.java)
            intent.putParcelableArrayListExtra("carrito", ArrayList(carrito))
            startActivityForResult(intent, REQUEST_CONFIRMAR_PEDIDO)
        }

        // Botón para ver el historial de compras del usuario
        binding.btnHistorial.setOnClickListener {
            val intent = Intent(this, HistorialActivity::class.java)
            startActivity(intent)
        }
    }

    // Sobreescribir onActivityResult para recibir el resultado de ConfirmarPedidoActivity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CONFIRMAR_PEDIDO && resultCode == Activity.RESULT_OK) {
            // El pedido fue confirmado, vaciar el carrito
            carrito.clear()
            Toast.makeText(this, "Carrito vaciado después de confirmar el pedido", Toast.LENGTH_SHORT).show()
        }
    }
}
