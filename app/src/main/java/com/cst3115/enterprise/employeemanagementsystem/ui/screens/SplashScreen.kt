package com.cst3115.enterprise.employeemanagementsystem.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onSplashFinished: () -> Unit
) {
    var showWorkforce by remember { mutableStateOf(false) }
    var showCompany by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        // Start the Workforce logo animation
        showWorkforce = true

        // Reveal the company name slightly later
        delay(800)
        showCompany = true

        // Keep the full branding visible before navigating
        delay(1600)
        onSplashFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101E2B)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // WORKFORCE™ emerges upward
            AnimatedVisibility(
                visible = showWorkforce,
                enter = fadeIn(
                    animationSpec = tween(durationMillis = 900)
                ) + slideInVertically(
                    initialOffsetY = { it / 2 },
                    animationSpec = tween(durationMillis = 900)
                )
            ) {
                Row(
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "WORKFORCE",
                        fontSize = 38.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                        color = Color.White
                    )

                    Text(
                        text = "™",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF42D9D2),
                        modifier = Modifier.padding(
                            start = 2.dp,
                            top = 2.dp
                        )
                    )
                }
            }

            // THE THREE appears after WORKFORCE
            AnimatedVisibility(
                visible = showCompany,
                enter = fadeIn(
                    animationSpec = tween(durationMillis = 700)
                ) + slideInVertically(
                    initialOffsetY = { it / 3 },
                    animationSpec = tween(durationMillis = 700)
                )
            ) {
                Text(
                    text = "THE THREE",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 5.sp,
                    color = Color(0xFF42D9D2),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}