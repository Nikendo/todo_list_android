package com.nikendo.app.todolist.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
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

private val DarkColorScheme = darkColorScheme(
    primary = Green80,
    onPrimary = Green20,
    primaryContainer = Green30,
    onPrimaryContainer = Green90,

    secondary = Olive80,
    onSecondary = Olive20,
    secondaryContainer = Olive30,
    onSecondaryContainer = Olive90,

    tertiary = Moray80,
    onTertiary = Moray20,
    tertiaryContainer = Moray30,
    onTertiaryContainer = Moray90,

    error = Red80,
    onError = Red20,
    errorContainer = Red30,
    onErrorContainer = Red90,

    background = Gray10,
    onBackground = Gray90,
    surface = Gray10,
    onSurface = Gray90,

    outline = GrayOlive60,
    surfaceVariant = GrayOlive30,
    onSurfaceVariant = GrayOlive80
)

private val LightColorScheme = lightColorScheme(
    primary = Green40,
    onPrimary = White,
    primaryContainer = Green90,
    onPrimaryContainer = Green10,

    secondary = Olive40,
    onSecondary = White,
    secondaryContainer = Olive90,
    onSecondaryContainer = Olive10,

    tertiary = Moray40,
    onTertiary = White,
    tertiaryContainer = Moray90,
    onTertiaryContainer = Moray10,

    error = Red40,
    onError = White,
    errorContainer = Red90,
    onErrorContainer = Red10,

    background = Gray99,
    onBackground = Gray10,
    surface = Gray99,
    onSurface = Gray10,

    outline = GrayOlive50,
    surfaceVariant = GrayOlive90,
    onSurfaceVariant = GrayOlive30
)

@Composable
fun MyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
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
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}