    package com.example.imccalculator

    import androidx.compose.runtime.Composable
    import androidx.navigation.compose.NavHost
    import androidx.navigation.compose.composable
    import androidx.navigation.compose.rememberNavController

    @Composable
    fun AppNavigation() {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "accueil") {
            composable("accueil") { PageAccueil(navController) }
            composable("formulaire") { FormulairePage(navController) }
            composable("resultat") { ResultatPage(navController) }
            composable("about") { AboutPage(navController) }
        }
    }
