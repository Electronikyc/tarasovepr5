package com.example.pr5tarasova.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Твои цвета
val FitnessBlue = Color(0xFF617FB9) // Синий фон
val FitnessDark = Color(0xFF000000) // Черный текст

private val LightColorScheme = lightColorScheme(
    primary = FitnessDark,          // Цвет акцентов (рамка, имя при клике)
    secondary = FitnessDark,        // Цвет имени автора
    background = FitnessBlue,       // Цвет всего экрана
    surface = Color.White,          // Цвет "облачка" сообщения (белый, чтобы черный текст читался)
    onBackground = FitnessDark,     // Цвет текста на фоне
    onSurface = FitnessDark         // Цвет текста в облачке
)

// Темная тема (на всякий случай)
private val DarkColorScheme = darkColorScheme(
    primary = Color.White,
    secondary = Color.White,
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun PR5TarasovaTheme(
    darkTheme: Boolean = false, // Всегда светлая (синяя) тема
    dynamicColor: Boolean = false, // Отключаем динамические цвета
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}