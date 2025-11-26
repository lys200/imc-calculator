package com.example.imccalculator

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormulairePage(navController: NavController) {
    // Récupérer les données sauvegardées
    val savedPersonne = navController
        .currentBackStackEntry
        ?.savedStateHandle
        ?.get<Personne>("savedPersonne")

    // États avec données sauvegardées ou valeurs par défaut
    var nom by remember { mutableStateOf(savedPersonne?.nom ?: "") }
    var prenom by remember { mutableStateOf(savedPersonne?.prenom ?: "") }
    var dateNaissance by remember {
        mutableStateOf<LocalDate?>(
            savedPersonne?.dateNaissance?.let {
                try {
                    LocalDate.parse(it, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                } catch (e: Exception) {
                    null
                }
            }
        )
    }
    var poids by remember { mutableStateOf(savedPersonne?.poids?.toString() ?: "70.0") }
    var taille by remember { mutableStateOf(savedPersonne?.taille?.toString() ?: "1.75") }
    var sexe by remember { mutableStateOf(savedPersonne?.sexe ?: "Homme") }
    var type by remember { mutableStateOf(savedPersonne?.type ?: "Sédentaire") }
    var typeExpanded by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val scrollState = rememberScrollState()

    // Vérification si le formulaire est valide
    val isFormValid = nom.isNotBlank() && prenom.isNotBlank() && dateNaissance != null

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
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
                    "Calculateur IMC",
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

        // Contenu du formulaire
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Nom et Prénom
            OutlinedTextField(
                value = nom,
                onValueChange = { nom = it },
                label = { Text("Nom") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    focusedLabelColor = MaterialTheme.colorScheme.primary,
                    focusedTextColor = MaterialTheme.colorScheme.primary,
                    unfocusedTextColor = MaterialTheme.colorScheme.primary
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = prenom,
                onValueChange = { prenom = it },
                label = { Text("Prénom") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    focusedLabelColor = MaterialTheme.colorScheme.primary,
                    focusedTextColor = MaterialTheme.colorScheme.primary,
                    unfocusedTextColor = MaterialTheme.colorScheme.primary
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Date de naissance avec restrictions
            Button(
                onClick = {
                    val calendar = Calendar.getInstance()
                    val currentYear = calendar.get(Calendar.YEAR)
                    val currentMonth = calendar.get(Calendar.MONTH)
                    val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

                    val dialog = DatePickerDialog(
                        context,
                        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                            val selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
                            val today = LocalDate.now()

                            // Validation : la date ne peut pas être dans le futur
                            if (selectedDate.isAfter(today)) {
                                return@DatePickerDialog
                            }

                            // Validation : l'âge ne peut pas dépasser 130 ans
                            val age = Personne.calculateAge(selectedDate)
                            if (age > 130) {
                                return@DatePickerDialog
                            }

                            dateNaissance = selectedDate
                        },
                        currentYear,
                        currentMonth,
                        currentDay
                    )

                    // Restriction maximum : aujourd'hui
                    val maxDate = Calendar.getInstance()
                    dialog.datePicker.maxDate = maxDate.timeInMillis

                    // Restriction minimum : il y a 130 ans
                    val minDate = Calendar.getInstance()
                    minDate.add(Calendar.YEAR, -130)
                    dialog.datePicker.minDate = minDate.timeInMillis

                    dialog.show()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = dateNaissance?.format(formatter) ?: "Sélectionner la date de naissance",
                    color = Color.White
                )
            }

            // Affichage de la date sélectionnée
            if (dateNaissance != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Date sélectionnée : ${dateNaissance!!.format(formatter)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Âge : ${Personne.calculateAge(dateNaissance!!)} ans",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Poids avec input et boutons
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = poids,
                    onValueChange = {
                        if (it.matches(Regex("^\\d*\\.?\\d*$"))) {
                            poids = it
                        }
                    },
                    label = { Text("Poids (kg)") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                        focusedTextColor = MaterialTheme.colorScheme.primary,
                        unfocusedTextColor = MaterialTheme.colorScheme.primary
                    )
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column {
                    IconButton(
                        onClick = {
                            val current = poids.toDoubleOrNull() ?: 70.0
                            poids = String.format("%.1f", current + 0.1)
                        },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("+", color = Color.White, fontSize = 18.sp)
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    IconButton(
                        onClick = {
                            val current = poids.toDoubleOrNull() ?: 70.0
                            if (current > 20) {
                                poids = String.format("%.1f", current - 0.1)
                            }
                        },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("-", color = Color.White, fontSize = 18.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Taille avec input et boutons
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = taille,
                    onValueChange = {
                        if (it.matches(Regex("^\\d*\\.?\\d*$"))) {
                            taille = it
                        }
                    },
                    label = { Text("Taille (m)") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                        focusedTextColor = MaterialTheme.colorScheme.primary,
                        unfocusedTextColor = MaterialTheme.colorScheme.primary
                    )
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column {
                    IconButton(
                        onClick = {
                            val current = taille.toDoubleOrNull() ?: 1.75
                            if (current < 2.5) {
                                taille = String.format("%.2f", current + 0.01)
                            }
                        },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("+", color = Color.White, fontSize = 18.sp)
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    IconButton(
                        onClick = {
                            val current = taille.toDoubleOrNull() ?: 1.75
                            if (current > 0.5) {
                                taille = String.format("%.2f", current - 0.01)
                            }
                        },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("-", color = Color.White, fontSize = 18.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Sexe avec boutons radio
            Text(
                "Sexe",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = sexe == "Homme",
                        onClick = { sexe = "Homme" },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = MaterialTheme.colorScheme.primary
                        )
                    )
                    Text(
                        "Homme",
                        modifier = Modifier.padding(start = 8.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.width(24.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = sexe == "Femme",
                        onClick = { sexe = "Femme" },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = MaterialTheme.colorScheme.primary
                        )
                    )
                    Text(
                        "Femme",
                        modifier = Modifier.padding(start = 8.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Type d'activité avec dropdown
            Spacer(modifier = Modifier.height(24.dp))

// Type d'activité avec dropdown
            Text(
                "Type d'activité",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            ExposedDropdownMenuBox(
                expanded = typeExpanded,
                onExpandedChange = { typeExpanded = !typeExpanded }
            ) {
                OutlinedTextField(
                    value = type,
                    onValueChange = {},
                    label = {
                        Text(
                            "Sélectionnez votre niveau d'activité",
                            color = MaterialTheme.colorScheme.primary
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(), // ← CORRECTION COMPLÈTE
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = typeExpanded
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                        focusedTextColor = MaterialTheme.colorScheme.primary,
                        unfocusedTextColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                        cursorColor = MaterialTheme.colorScheme.primary
                    )
                )

                ExposedDropdownMenu(
                    expanded = typeExpanded,
                    onDismissRequest = { typeExpanded = false }
                ) {
                    listOf("Sédentaire", "Faible", "Actif", "Sportif", "Athlète").forEach { option ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    option,
                                    color = Color.White,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            },
                            onClick = {
                                type = option
                                typeExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Bouton Calculer CORRIGÉ
            Button(
                onClick = {
                    try {
                        val poidsValue = poids.toDoubleOrNull() ?: 70.0
                        val tailleValue = taille.toDoubleOrNull() ?: 1.75

                        val personne = Personne(
                            nom = nom,
                            prenom = prenom,
                            dateNaissance = dateNaissance!!.format(formatter),
                            sexe = sexe,
                            poids = poidsValue,
                            taille = tailleValue,
                            type = type
                        )

                        navController.currentBackStackEntry?.savedStateHandle?.set("personne", personne)
                        navController.navigate("resultat")
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary, // Couleur quand activé
                    disabledContainerColor = Color(0xFFB39DDB), // Gris quand désactivé
                    contentColor = Color.White,
                    disabledContentColor = Color.White // Texte blanc même quand désactivé
                ),
                enabled = isFormValid
            ) {
                if (isFormValid) {
                    Text("Calculer IMC", fontSize = 18.sp)
                } else {
                    Text(
                        "Remplissez tous les champs",
                        fontSize = 16.sp
                    )
                }
            }

            // Message d'information quand le formulaire n'est pas valide
            if (!isFormValid) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Veuillez remplir tous les champs obligatoires",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFFB39DDB)

                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}