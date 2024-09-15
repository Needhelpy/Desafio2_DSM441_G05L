package udb.edu.desafio2_dsm;

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import udb.edu.desafio2_dsm.databinding.ItemMedicamentoBinding

class MedicamentoAdapter(
    private val medicamentos: List<Medicament>,
    private val onMedicamentoClick: (Medicament) -> Unit
) : RecyclerView.Adapter<MedicamentoAdapter.MedicamentoViewHolder>() {

    inner class MedicamentoViewHolder(private val binding: ItemMedicamentoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(medicament: Medicament) {
            // Cambia tvNombre a tvNombreMedicamento
            binding.tvNombreMedicamento.text = medicament.nombre
            binding.tvPrecio.text = "$${medicament.precio}"
            binding.btnAgregar.setOnClickListener {
                onMedicamentoClick(medicament)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicamentoViewHolder {
        val binding = ItemMedicamentoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MedicamentoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MedicamentoViewHolder, position: Int) {
        holder.bind(medicamentos[position])
    }

    override fun getItemCount(): Int = medicamentos.size
}

