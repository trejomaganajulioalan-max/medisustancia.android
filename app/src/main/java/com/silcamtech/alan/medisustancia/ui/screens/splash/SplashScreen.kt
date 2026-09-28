package com.silcamtech.alan.medisustancia.ui.screens.splash

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.silcamtech.alan.medisustancia.R
import androidx.compose.ui.tooling.preview.Preview
private val DarkGreen = Color(0xFF0E4B43)
private val Turquoise = Color(0xFF009F98)

@Composable
fun SplashScreen(modifier: Modifier = Modifier) {
    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val screenHeight = maxHeight

        Image(
            painter = painterResource(R.drawable.splash_background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Image(
            painter = painterResource(R.drawable.medisustancia_logo),
            contentDescription = "Logo de MediSustancia",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = screenHeight * 0.30f)
                .width(155.dp)
                .height(145.dp)
        )

        Text(
            text = "Información confiable para decisiones\nseguras en salud",
            color = DarkGreen,
            fontSize = 13.sp,
            lineHeight = 17.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = screenHeight * 0.51f)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = screenHeight * 0.62f)
        ) {
            LoadingDots()

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "Cargando...",
                color = Turquoise,
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun LoadingDots() {
    val transition = rememberInfiniteTransition(label = "loadingDots")

    val progress = transition.animateFloat(
        initialValue = 0f,
        targetValue = 3f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 900),
            repeatMode = RepeatMode.Restart
        ),
        label = "loadingProgress"
    )

    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(3) { index ->
            val isActive = progress.value.toInt().coerceIn(0, 2) == index

            Box(
                modifier = Modifier
                    .size(if (isActive) 6.dp else 5.dp)
                    .background(
                        color = Turquoise.copy(
                            alpha = if (isActive) 1f else 0.45f
                        ),
                        shape = CircleShape
                    )
            )
        }
    }

}
@Preview(
    name = "Splash MediSustancia",
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun SplashScreenPreview() {
    SplashScreen()
}