package com.example.imccalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IMCAppTheme {
                AppNavigation()
            }
        }
    }
}
@Composable
fun IMCAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        darkColorScheme(
            primary = Color(0xFF2B006A),  // Nouveau violet foncé #2B006A
            secondary = Color(0xFF3B3344), // Violet un peu plus clair
            primaryContainer = Color(0xFF35007A),
            onPrimary = Color.White,
            onSecondary = Color.White,
            surface = Color(0xFF121212),
            background = Color(0xFF121212)
        )
    } else {
        lightColorScheme(
            primary = Color(0xFF2B006A),  // Nouveau violet foncé #2B006A
            secondary = Color(0xFF3B3344), // Violet un peu plus clair
            primaryContainer = Color(0xFF35007A),
            onPrimary = Color.White,
            onSecondary = Color.White,
            surface = Color.White,
            background = Color(0xFFF5F0FA) // Fond très légèrement violet
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}