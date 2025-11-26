package com.example.imccalculator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack  // ← CORRECTION : remplacer automirrored
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun AboutPage(navController: NavController) {
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
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    colors = IconButtonDefaults.iconButtonColors(contentColor = Color.White)
                ) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Retour")  // ← CORRECTION
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    "À propos",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White
                )
            }
        }

        // Contenu de la page À propos AVEC SCROLL
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Titre principal
            Text(
                "Calculateur IMC",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "Version 1.1",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Carte d'information
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
                    // Section Développeur
                    Text(
                        "Développement",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        "Développé par : \n\t\t\t- Nherlyse Morisset\n\t\t- Belceus Samienove R. \n\t\t- Christine St-Preux\n" +
                                "Plateforme : Android\n" +
                                "Technologie : Jetpack Compose + Kotlin",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                        lineHeight = 24.sp
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Section Description
                    Text(
                        "Description",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        "Cette application permet de calculer votre Indice de Masse Corporelle (IMC) " +
                                "et d'évaluer votre corpulence selon les standards de l'Organisation Mondiale de la Santé.\n\n" +
                                "L'IMC est un indicateur reconnu internationalement pour évaluer la corpulence " +
                                "et identifier les risques potentiels pour la santé.\n\n" +
                                "Fonctionnalités :\n" +
                                "• Calcul précis de l'IMC\n" +
                                "• Classification selon les normes OMS\n" +
                                "• Interface intuitive et moderne\n" +
                                "• Sauvegarde des données de calcul",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                        lineHeight = 22.sp,
                        textAlign = TextAlign.Justify
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Section Catégories IMC
                    Text(
                        "Catégories IMC",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Column {
                        CategoryItem("Maigreur", "< 18.5", Color(0xFF0000FF))
                        CategoryItem("Normal", "18.5 - 24.9", Color(0xFF4CAF50))
                        CategoryItem("Surpoids", "25 - 29.9", Color(0xFFFF9800))
                        CategoryItem("Obésité", "≥ 30", Color(0xFFF44336))
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Section Informations supplémentaires
                    Text(
                        "Informations importantes",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        "L'IMC est un indicateur général qui ne tient pas compte de :\n" +
                                "• La masse musculaire\n" +
                                "• La densité osseuse\n" +
                                "• La répartition des graisses\n" +
                                "• L'âge et le sexe (bien que des ajustements existent)\n\n" +
                                "Consultez un professionnel de santé pour une évaluation complète.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                        lineHeight = 22.sp,
                        textAlign = TextAlign.Justify
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // NOUVELLE SECTION : Copyright
                    Text(
                        "Copyright",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        "© 2025 Calculateur IMC - CNS Group. \nTous droits réservés..",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                        lineHeight = 22.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Bouton retour
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text("Retour", fontSize = 18.sp, color = Color.White)
            }

            // Espace supplémentaire pour le scroll
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun CategoryItem(categorie: String, range: String, color: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Point de couleur
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(color, shape = androidx.compose.foundation.shape.CircleShape)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                categorie,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium
            )
            Text(
                range,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
            )
        }
    }
}