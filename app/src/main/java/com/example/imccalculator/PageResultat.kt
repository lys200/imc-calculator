package com.example.imccalculator

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ResultatPage(navController: NavController) {
    val personne = navController
        .previousBackStackEntry
        ?.savedStateHandle
        ?.get<Personne>("personne")

    if (personne == null) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "Aucune donnée disponible",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text("Retour au formulaire", color = Color.White)
            }
        }
        return
    }

    val imc = try {
        personne.calculIMC()
    } catch (e: Exception) {
        0.0
    }

    val categorie = try {
        personne.categorieIMC()
    } catch (e: Exception) {
        "Erreur"
    }

    val bgColor = when (categorie) {
        "Normal" -> Color(0xFF4CAF50) // vert
        "Maigreur" -> Color(0xFF0000FF) // bleu
        "Surpoids" -> Color(0xFFFF9800) // orange
        "Obésité" -> Color(0xFFF44336) // rouge
        else -> Color.Gray
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Header avec fond violet #2B006A
        Surface(
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .statusBarsPadding(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    colors = IconButtonDefaults.iconButtonColors(contentColor = Color.White)
                ) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Retour")                }
                Text(
                    "Résultat IMC",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White
                )
                IconButton(
                    onClick = { navController.navigate("about") },
                    colors = IconButtonDefaults.iconButtonColors(contentColor = Color.White)
                ) {
                    Icon(Icons.Default.Info, contentDescription = "About")
                }
            }
        }

        // Contenu des résultats
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Informations personnelles - STYLE CORRIGÉ
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF5F0FA)
                )
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text(
                        "Informations Personnelles",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Informations sur la même ligne - STYLE CORRIGÉ
                    InfoRow("Nom", personne.nom)
                    Spacer(modifier = Modifier.height(12.dp))
                    InfoRow("Prénom", personne.prenom)
                    Spacer(modifier = Modifier.height(12.dp))
                    InfoRow("Date de naissance", personne.dateNaissance)
                    Spacer(modifier = Modifier.height(12.dp))
                    InfoRow("Sexe", personne.sexe)
                    Spacer(modifier = Modifier.height(12.dp))
                    InfoRow("Poids", "${personne.poids} kg")
                    Spacer(modifier = Modifier.height(12.dp))
                    InfoRow("Taille", "${personne.taille} m")
                    Spacer(modifier = Modifier.height(12.dp))
                    InfoRow("Type d'activité", personne.type)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Résultat IMC
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(containerColor = bgColor)
            ) {
                Column(
                    modifier = Modifier
                        .padding(32.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Votre IMC",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        String.format("%.1f", imc),
                        style = MaterialTheme.typography.displayMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        categorie,
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Deux boutons : Retour (garde les données) et Nouveau Calcul (efface)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Bouton Retour - garde les données
                Button(
                    onClick = {
                        // Sauvegarder les données avant de retourner
                        navController.currentBackStackEntry?.savedStateHandle?.set("savedPersonne", personne)
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6A4C93) // Violet différent
                    )
                ) {
                    Text("Retour", fontSize = 16.sp, color = Color.White)
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Bouton Nouveau Calcul - efface les données
                Button(
                    onClick = {
                        // Effacer les données sauvegardées
                        navController.currentBackStackEntry?.savedStateHandle?.remove<Personne>("savedPersonne")
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("Nouveau Calcul", fontSize = 16.sp, color = Color.White)
                }
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "$label :",
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            value,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}