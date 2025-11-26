package com.example.imccalculator

import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

data class Personne(
    val nom: String,
    val prenom: String,
    val dateNaissance: String,
    val sexe: String,
    val poids: Double,
    val taille: Double,
    val type: String
) : java.io.Serializable {  // ← IMPORTANT: Ajouter Serializable

    fun calculIMC(): Double {
        return try {
            if (taille > 0) {
                val imc = poids / (taille * taille)
                // Limiter les valeurs extrêmes
                if (imc.isNaN() || imc.isInfinite()) 0.0 else imc
            } else {
                0.0
            }
        } catch (e: Exception) {
            0.0
        }
    }

    fun categorieIMC(): String {
        return try {
            val imc = calculIMC()
            when {
                imc < 18.5 -> "Maigreur"
                imc < 25 -> "Normal"
                imc < 30 -> "Surpoids"
                else -> "Obésité"
            }
        } catch (e: Exception) {
            "Erreur de calcul"
        }
    }

    companion object {
        fun calculateAge(dateNaissance: LocalDate): Int {
            return try {
                Period.between(dateNaissance, LocalDate.now()).years
            } catch (e: Exception) {
                0
            }
        }
    }
}