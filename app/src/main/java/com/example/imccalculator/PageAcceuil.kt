package com.example.imccalculator

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color  // ← AJOUTEZ CET IMPORT
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun PageAccueil(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Image
        Image(
            painter = painterResource(id = R.drawable.ic_app_logo),
            contentDescription = "Logo de l'application",
            modifier = Modifier.size(150.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            "Calculateur IMC",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "Calculez rapidement votre IMC et découvrez dans quelle catégorie vous vous situez.",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            lineHeight = 20.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { navController.navigate("formulaire") },
            modifier = Modifier.width(200.dp),
            colors = ButtonDefaults.buttonColors(  // ← MAINTENANT RECONNU
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text("Commencer", fontSize = 18.sp, color = Color.White)  // ← MAINTENANT RECONNU
        }
    }
}