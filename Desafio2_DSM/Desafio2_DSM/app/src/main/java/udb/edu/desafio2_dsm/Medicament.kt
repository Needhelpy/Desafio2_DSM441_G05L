package udb.edu.desafio2_dsm

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Medicament(
    val nombre: String,
    val precio: Double
) : Parcelable
