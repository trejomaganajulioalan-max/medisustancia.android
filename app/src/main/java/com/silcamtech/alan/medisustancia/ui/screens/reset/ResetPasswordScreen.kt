package com.silcamtech.alan.medisustancia.ui.screens.reset

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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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
private val ErrorRed = Color(0xFFB3261E)

@Composable
fun ResetPasswordScreen(
    token: String,
    onResetPassword: (token: String, password: String) -> Unit = { _, _ -> },
    onRequestNewLink: () -> Unit = {},
    onBackToLogin: () -> Unit = {},
    loading: Boolean = false,
    resetCompleted: Boolean = false,
    resetError: String = ""
) {
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var showPassword by remember { mutableStateOf(false) }
    var showConfirmPassword by remember { mutableStateOf(false) }

    var passwordError by remember { mutableStateOf("") }
    var confirmPasswordError by remember { mutableStateOf("") }

    val keyboardController = LocalSoftwareKeyboardController.current
    val hasToken = token.isNotBlank()

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
                        Row(
                            modifier = Modifier
                                .clickable(
                                    enabled = !loading,
                                    onClick = onBackToLogin
                                )
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(7.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_arrow_left),
                                contentDescription = null,
                                tint = Primary,
                                modifier = Modifier.size(18.dp)
                            )

                            Text(
                                text = "Volver al inicio de sesión",
                                color = Primary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

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

                        when {
                            !hasToken -> {
                                StatusIcon(
                                    symbol = "!",
                                    background = Color(0xFFFFF5E8),
                                    foreground = Color(0xFFFF9F2B)
                                )

                                Spacer(modifier = Modifier.height(19.dp))

                                Text(
                                    text = "Enlace no válido",
                                    color = Primary,
                                    fontSize = 28.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                Text(
                                    text = "El enlace no contiene un token de recuperación. " +
                                            "Solicita uno nuevo para continuar.",
                                    color = Muted,
                                    fontSize = 14.sp,
                                    lineHeight = 21.sp,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(28.dp))

                                MediSustanciaButton(
                                    label = "Solicitar nuevo enlace",
                                    variant = MediButtonVariant.GRADIENT,
                                    size = MediButtonSize.FULL,
                                    modifier = Modifier.heightIn(min = 52.dp),
                                    onClick = onRequestNewLink
                                )
                            }

                            resetCompleted -> {
                                StatusIcon(
                                    symbol = "✓",
                                    background = Color(0xFFE4F8F3),
                                    foreground = Primary
                                )

                                Spacer(modifier = Modifier.height(19.dp))

                                Text(
                                    text = "Contraseña actualizada",
                                    color = Primary,
                                    fontSize = 27.sp,
                                    lineHeight = 33.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                Text(
                                    text = "Tu contraseña se cambió correctamente. " +
                                            "Ya puedes iniciar sesión con la nueva contraseña.",
                                    color = Muted,
                                    fontSize = 14.sp,
                                    lineHeight = 21.sp,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(28.dp))

                                MediSustanciaButton(
                                    label = "Ir al inicio de sesión",
                                    variant = MediButtonVariant.GRADIENT,
                                    size = MediButtonSize.FULL,
                                    modifier = Modifier.heightIn(min = 52.dp),
                                    onClick = onBackToLogin
                                )
                            }

                            else -> {
                                Text(
                                    text = "Crea una nueva contraseña",
                                    color = Primary,
                                    fontSize = 27.sp,
                                    lineHeight = 33.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(modifier = Modifier.height(9.dp))

                                Text(
                                    text = "Elige una contraseña de al menos 8 caracteres " +
                                            "para tu cuenta de MediSustancia.",
                                    color = Muted,
                                    fontSize = 13.sp,
                                    lineHeight = 19.sp
                                )

                                Spacer(modifier = Modifier.height(26.dp))

                                MediSustanciaTextField(
                                    value = password,
                                    onValueChange = {
                                        password = it
                                        passwordError = ""
                                    },
                                    label = "Nueva contraseña",
                                    placeholder = "Ingresa tu nueva contraseña",
                                    type = MediInputType.PASSWORD,
                                    variant = MediInputVariant.SECONDARY,
                                    size = MediInputSize.FULL,
                                    enabled = !loading,
                                    required = true,
                                    leftIcon = R.drawable.ic_lock,
                                    rightIcon = if (showPassword) {
                                        R.drawable.ic_visibility_off
                                    } else {
                                        R.drawable.ic_visibility
                                    },
                                    rightIconLabel = if (showPassword) {
                                        "Ocultar contraseña"
                                    } else {
                                        "Mostrar contraseña"
                                    },
                                    onRightIconClick = {
                                        showPassword = !showPassword
                                    },
                                    visualTransformation = if (showPassword) {
                                        VisualTransformation.None
                                    } else {
                                        PasswordVisualTransformation()
                                    },
                                    errorText = passwordError
                                )

                                Spacer(modifier = Modifier.height(17.dp))

                                MediSustanciaTextField(
                                    value = confirmPassword,
                                    onValueChange = {
                                        confirmPassword = it
                                        confirmPasswordError = ""
                                    },
                                    label = "Confirmar contraseña",
                                    placeholder = "Repite tu nueva contraseña",
                                    type = MediInputType.PASSWORD,
                                    variant = MediInputVariant.SECONDARY,
                                    size = MediInputSize.FULL,
                                    enabled = !loading,
                                    required = true,
                                    leftIcon = R.drawable.ic_lock,
                                    rightIcon = if (showConfirmPassword) {
                                        R.drawable.ic_visibility_off
                                    } else {
                                        R.drawable.ic_visibility
                                    },
                                    rightIconLabel = if (showConfirmPassword) {
                                        "Ocultar contraseña"
                                    } else {
                                        "Mostrar contraseña"
                                    },
                                    onRightIconClick = {
                                        showConfirmPassword = !showConfirmPassword
                                    },
                                    visualTransformation = if (showConfirmPassword) {
                                        VisualTransformation.None
                                    } else {
                                        PasswordVisualTransformation()
                                    },
                                    imeAction = ImeAction.Done,
                                    errorText = confirmPasswordError
                                )

                                if (resetError.isNotBlank()) {
                                    Spacer(modifier = Modifier.height(15.dp))

                                    Text(
                                        text = resetError,
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

                                Spacer(modifier = Modifier.height(24.dp))

                                MediSustanciaButton(
                                    label = "Restablecer contraseña",
                                    variant = MediButtonVariant.GRADIENT,
                                    size = MediButtonSize.FULL,
                                    loading = loading,
                                    modifier = Modifier.heightIn(min = 52.dp),
                                    onClick = {
                                        passwordError = when {
                                            password.isEmpty() ->
                                                "Ingresa una nueva contraseña."

                                            password.length < 8 ->
                                                "La contraseña debe tener al menos 8 caracteres."

                                            else -> ""
                                        }

                                        confirmPasswordError = when {
                                            confirmPassword.isEmpty() ->
                                                "Confirma tu nueva contraseña."

                                            password != confirmPassword ->
                                                "Las contraseñas no coinciden."

                                            else -> ""
                                        }

                                        if (
                                            passwordError.isEmpty() &&
                                            confirmPasswordError.isEmpty()
                                        ) {
                                            keyboardController?.hide()
                                            onResetPassword(token, password)
                                        }
                                    }
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
private fun StatusIcon(
    symbol: String,
    background: Color,
    foreground: Color
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(66.dp)
                .background(background, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = symbol,
                color = foreground,
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(
    name = "Restablecer contraseña",
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun ResetPasswordPreview() {
    ResetPasswordScreen(token = "token-de-ejemplo")
}

@Preview(
    name = "Enlace no válido",
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun InvalidLinkPreview() {
    ResetPasswordScreen(token = "")
}

@Preview(
    name = "Contraseña actualizada",
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun ResetCompletedPreview() {
    ResetPasswordScreen(
        token = "token-de-ejemplo",
        resetCompleted = true
    )
}