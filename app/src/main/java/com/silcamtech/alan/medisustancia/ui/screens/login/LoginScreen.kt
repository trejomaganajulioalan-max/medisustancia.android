package com.silcamtech.alan.medisustancia.ui.screens.login

import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.silcamtech.alan.medisustancia.R
import com.silcamtech.alan.medisustancia.ui.components.MediButtonSize
import com.silcamtech.alan.medisustancia.ui.components.MediButtonVariant
import com.silcamtech.alan.medisustancia.ui.components.MediInputSize
import com.silcamtech.alan.medisustancia.ui.components.MediInputType
import com.silcamtech.alan.medisustancia.ui.components.MediSustanciaButton
import com.silcamtech.alan.medisustancia.ui.components.MediSustanciaTextField

private val Primary = Color(0xFF0E4B43)
private val Secondary = Color(0xFF0E7469)
private val BodyText = Color(0xFF687572)
private val Divider = Color(0xFFD7DDDC)
private val ErrorColor = Color(0xFFB3261E)

@Composable
fun LoginScreen(
    onLogin: (email: String, password: String) -> Unit = { _, _ -> },
    onForgotPassword: () -> Unit = {},
    onRegister: () -> Unit = {},
    onGoogleLogin: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    val keyboardController = LocalSoftwareKeyboardController.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F8F6))
    ) {
        // La imagen cubre la pantalla y queda detrás del formulario.
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

                        Spacer(modifier = Modifier.height(30.dp))

                        Text(
                            text = "Bienvenido de nuevo",
                            color = Primary,
                            fontSize = 28.sp,
                            lineHeight = 34.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Ingresa tus datos para acceder a MediSustancia.",
                            color = BodyText,
                            fontSize = 14.sp,
                            lineHeight = 21.sp
                        )

                        Spacer(modifier = Modifier.height(28.dp))

                        MediSustanciaTextField(
                            value = email,
                            onValueChange = {
                                email = it
                                emailError = null
                            },
                            label = "Correo electrónico",
                            placeholder = "ejemplo@correo.com",
                            type = MediInputType.EMAIL,
                            size = MediInputSize.FULL,
                            leftIcon = R.drawable.ic_email
                        )

                        emailError?.let { message ->
                            FieldError(message)
                        }

                        Spacer(modifier = Modifier.height(18.dp))

                        MediSustanciaTextField(
                            value = password,
                            onValueChange = {
                                password = it
                                passwordError = null
                            },
                            label = "Contraseña",
                            placeholder = "Ingresa tu contraseña",
                            type = MediInputType.PASSWORD,
                            size = MediInputSize.FULL,
                            leftIcon = R.drawable.ic_lock,
                            rightIcon = if (passwordVisible) {
                                R.drawable.ic_visibility_off
                            } else {
                                R.drawable.ic_visibility
                            },
                            rightIconLabel = if (passwordVisible) {
                                "Ocultar contraseña"
                            } else {
                                "Mostrar contraseña"
                            },
                            onRightIconClick = {
                                passwordVisible = !passwordVisible
                            },
                            visualTransformation = if (passwordVisible) {
                                VisualTransformation.None
                            } else {
                                PasswordVisualTransformation()
                            },
                            imeAction = ImeAction.Done
                        )

                        passwordError?.let { message ->
                            FieldError(message)
                        }

                        Text(
                            text = "¿Olvidaste tu contraseña?",
                            color = Secondary,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier
                                .align(Alignment.End)
                                .clickable(onClick = onForgotPassword)
                                .padding(vertical = 16.dp)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        MediSustanciaButton(
                            label = "Iniciar sesión",
                            variant = MediButtonVariant.GRADIENT,
                            size = MediButtonSize.FULL,
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

                                    else -> null
                                }

                                passwordError = if (password.isEmpty()) {
                                    "Ingresa tu contraseña."
                                } else {
                                    null
                                }

                                if (emailError == null && passwordError == null) {
                                    keyboardController?.hide()
                                    onLogin(cleanEmail, password)
                                }
                            }
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "¿No tienes una cuenta?",
                                color = BodyText,
                                fontSize = 12.sp
                            )

                            Text(
                                text = " Regístrate",
                                color = Secondary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier
                                    .clickable(onClick = onRegister)
                                    .padding(vertical = 8.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(18.dp))

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
                                color = BodyText,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(horizontal = 14.dp)
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
                                .fillMaxWidth()
                                .heightIn(min = 52.dp)
                                .border(
                                    width = 1.dp,
                                    color = Divider,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .clickable(onClick = onGoogleLogin)
                                .padding(
                                    horizontal = 12.dp,
                                    vertical = 12.dp
                                ),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(R.drawable.ic_google),
                                contentDescription = null,
                                modifier = Modifier.size(21.dp)
                            )

                            Spacer(modifier = Modifier.width(10.dp))

                            Text(
                                text = "Continuar con Google",
                                color = Primary,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FieldError(message: String) {
    Text(
        text = message,
        color = ErrorColor,
        fontSize = 12.sp,
        lineHeight = 17.sp,
        modifier = Modifier.padding(top = 6.dp)
    )
}

@Preview(
    name = "Login MediSustancia",
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun LoginScreenPreview() {
    LoginScreen()
}