package com.silcamtech.alan.medisustancia.ui.screens.recovery

import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.silcamtech.alan.medisustancia.R
import com.silcamtech.alan.medisustancia.ui.components.MediButtonSize
import com.silcamtech.alan.medisustancia.ui.components.MediButtonVariant
import com.silcamtech.alan.medisustancia.ui.components.MediInputSize
import com.silcamtech.alan.medisustancia.ui.components.MediInputType
import com.silcamtech.alan.medisustancia.ui.components.MediInputVariant
import com.silcamtech.alan.medisustancia.ui.components.MediSustanciaButton
import com.silcamtech.alan.medisustancia.ui.components.MediSustanciaTextField

private val Primary = Color(0xFF0E4B43)
private val Secondary = Color(0xFF0E7469)
private val Muted = Color(0xFF687572)
private val Divider = Color(0xFFD7DDDC)
private val ErrorRed = Color(0xFFB3261E)

@Composable
fun RecoveryPasswordScreen(
    onBackToLogin: () -> Unit = {},
    onRequestReset: (String) -> Unit = {},
    requestSent: Boolean = false,
    loading: Boolean = false,
    requestError: String = ""
) {
    var email by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }

    val keyboardController = LocalSoftwareKeyboardController.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F8F6))
    ) {
        Image(
            painter = painterResource(R.drawable.login_background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { alpha = 0.35f }
        )

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
                .imePadding()
        ) {
            val horizontalMargin = if (maxWidth < 380.dp) 12.dp else 18.dp
            val panelPadding = if (maxWidth < 380.dp) 20.dp else 24.dp
            val availableHeight = maxHeight

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = availableHeight)
                        .padding(
                            horizontal = horizontalMargin,
                            vertical = 18.dp
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier
                            .widthIn(max = 440.dp)
                            .fillMaxWidth()
                            .shadow(
                                elevation = 12.dp,
                                shape = RoundedCornerShape(22.dp)
                            )
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(22.dp)
                            )
                            .padding(
                                horizontal = panelPadding,
                                vertical = 30.dp
                            ),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Image(
                            painter = painterResource(R.drawable.medisustancia_logo),
                            contentDescription = "MediSustancia",
                            contentScale = ContentScale.Fit,
                            alignment = Alignment.Center,
                            modifier = Modifier
                                .width(250.dp)
                                .height(110.dp)
                                .align(Alignment.CenterHorizontally)
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        if (requestSent) {
                            RecoverySuccessContent(
                                email = email,
                                onBackToLogin = onBackToLogin
                            )
                        } else {
                            Text(
                                text = "Recupera tu contraseña",
                                color = Primary,
                                fontSize = 28.sp,
                                lineHeight = 34.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = "Ingresa el correo asociado a tu cuenta y te " +
                                        "enviaremos instrucciones para restablecer tu contraseña.",
                                color = Muted,
                                fontSize = 13.sp,
                                lineHeight = 19.sp
                            )

                            Spacer(modifier = Modifier.height(27.dp))

                            MediSustanciaTextField(
                                value = email,
                                onValueChange = {
                                    email = it
                                    emailError = ""
                                },
                                label = "Correo electrónico",
                                placeholder = "ejemplo@correo.com",
                                type = MediInputType.EMAIL,
                                variant = MediInputVariant.SECONDARY,
                                size = MediInputSize.FULL,
                                enabled = !loading,
                                required = true,
                                leftIcon = R.drawable.ic_email,
                                errorText = emailError,
                                imeAction = ImeAction.Done
                            )

                            if (requestError.isNotBlank()) {
                                Spacer(modifier = Modifier.height(15.dp))

                                Text(
                                    text = requestError,
                                    color = ErrorRed,
                                    fontSize = 12.sp,
                                    lineHeight = 17.sp,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(
                                            color = Color(0xFFFFF3F3),
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .padding(11.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(22.dp))

                            MediSustanciaButton(
                                label = "Enviar instrucciones",
                                variant = MediButtonVariant.GRADIENT,
                                size = MediButtonSize.FULL,
                                loading = loading,
                                modifier = Modifier.heightIn(min = 52.dp),
                                onClick = {
                                    val cleanEmail = email.trim()

                                    emailError = when {
                                        cleanEmail.isEmpty() ->
                                            "Ingresa tu correo electrónico."

                                        !Patterns.EMAIL_ADDRESS
                                            .matcher(cleanEmail)
                                            .matches() ->
                                            "Ingresa un correo electrónico válido."

                                        else -> ""
                                    }

                                    if (emailError.isEmpty()) {
                                        keyboardController?.hide()
                                        onRequestReset(cleanEmail)
                                    }
                                }
                            )

                            Spacer(modifier = Modifier.height(25.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(1.dp)
                                        .background(Divider)
                                )

                                Text(
                                    text = "o",
                                    color = Muted,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(horizontal = 15.dp)
                                )

                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(1.dp)
                                        .background(Divider)
                                )
                            }

                            Spacer(modifier = Modifier.height(18.dp))

                            Row(
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .clickable(
                                        enabled = !loading,
                                        onClick = onBackToLogin
                                    )
                                    .padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(9.dp)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_arrow_left),
                                    contentDescription = null,
                                    tint = Secondary,
                                    modifier = Modifier.size(18.dp)
                                )

                                Text(
                                    text = "Volver al inicio de sesión",
                                    color = Secondary,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RecoverySuccessContent(
    email: String,
    onBackToLogin: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(66.dp)
                .background(
                    color = Color(0xFFE4F8F3),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "✓",
                color = Primary,
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }

    Spacer(modifier = Modifier.height(20.dp))

    Text(
        text = "Revisa tu correo",
        color = Primary,
        fontSize = 28.sp,
        lineHeight = 34.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(12.dp))

    Text(
        text = "Si existe una cuenta asociada a ${email.trim()}, " +
                "recibirás instrucciones para restablecer tu contraseña.",
        color = Muted,
        fontSize = 14.sp,
        lineHeight = 21.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(30.dp))

    MediSustanciaButton(
        label = "Volver al inicio de sesión",
        variant = MediButtonVariant.GRADIENT,
        size = MediButtonSize.FULL,
        modifier = Modifier.heightIn(min = 52.dp),
        onClick = onBackToLogin
    )
}

@Preview(
    name = "Recuperar contraseña",
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun RecoveryPasswordPreview() {
    RecoveryPasswordScreen()
}

@Preview(
    name = "Correo solicitado",
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun RecoverySuccessPreview() {
    RecoveryPasswordScreen(requestSent = true)
}