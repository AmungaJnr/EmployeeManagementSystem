package com.cst3115.enterprise.employeemanagementsystem.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = ForceCyan,
    onPrimary = ForceDarkBackground,

    secondary = ForceTeal,

    background = ForceDarkBackground,
    onBackground = ForceDarkText,

    surface = ForceDarkSurface,
    onSurface = ForceDarkText,

    onSurfaceVariant = ForceDarkTextSecondary,
    outline = ForceDarkBorder

)

private val LightColorScheme = lightColorScheme(
    primary = ForceTeal,
    onPrimary = ForceSurface,

    secondary = ForceCyan,

    background = ForceBackground,
    onBackground = ForceTextPrimary,

    surface = ForceSurface,
    onSurface = ForceTextPrimary,

    onSurfaceVariant = ForceTextSecondary,

    outline = ForceBorder

)

@Composable
fun EmployeeManagementSystemTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    //dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme){
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}