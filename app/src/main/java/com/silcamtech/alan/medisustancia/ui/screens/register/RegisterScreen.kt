package com.silcamtech.alan.medisustancia.ui.screens.register

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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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

data class RegisterFormData(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
)

@Composable
fun RegisterScreen(
    onBack: () -> Unit = {},
    onLoginClick: () -> Unit = {},
    onRegister: (RegisterFormData) -> Unit = {},
    onGoogleRegister: () -> Unit = {},
    onTermsClick: () -> Unit = {},
    onPrivacyClick: () -> Unit = {},
    loading: Boolean = false,
    registrationError: String = ""
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var acceptedTerms by remember { mutableStateOf(false) }
    var showPassword by remember { mutableStateOf(false) }
    var showConfirmPassword by remember { mutableStateOf(false) }

    var firstNameError by remember { mutableStateOf("") }
    var lastNameError by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var confirmPasswordError by remember { mutableStateOf("") }
    var termsError by remember { mutableStateOf("") }

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
                                vertical = 28.dp
                            ),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Row(
                            modifier = Modifier
                                .clickable(
                                    enabled = !loading,
                                    onClick = onBack
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
                                text = "Volver",
                                color = Primary,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

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

                        Spacer(modifier = Modifier.height(18.dp))

                        Text(
                            text = "Crea tu cuenta",
                            color = Primary,
                            fontSize = 28.sp,
                            lineHeight = 34.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Regístrate para consultar el catálogo farmacéutico.",
                            color = Muted,
                            fontSize = 13.sp,
                            lineHeight = 19.sp
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        MediSustanciaTextField(
                            value = firstName,
                            onValueChange = {
                                firstName = it
                                firstNameError = ""
                            },
                            label = "Nombre(s)",
                            placeholder = "Tu nombre",
                            variant = MediInputVariant.SECONDARY,
                            size = MediInputSize.FULL,
                            enabled = !loading,
                            required = true,
                            leftIcon = R.drawable.ic_user,
                            errorText = firstNameError
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        MediSustanciaTextField(
                            value = lastName,
                            onValueChange = {
                                lastName = it
                                lastNameError = ""
                            },
                            label = "Apellidos",
                            placeholder = "Tus apellidos",
                            variant = MediInputVariant.SECONDARY,
                            size = MediInputSize.FULL,
                            enabled = !loading,
                            required = true,
                            leftIcon = R.drawable.ic_users,
                            errorText = lastNameError
                        )

                        Spacer(modifier = Modifier.height(14.dp))

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
                            errorText = emailError
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        MediSustanciaTextField(
                            value = password,
                            onValueChange = {
                                password = it
                                passwordError = ""
                            },
                            label = "Contraseña",
                            placeholder = "Ingresa una contraseña",
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

                        Spacer(modifier = Modifier.height(14.dp))

                        MediSustanciaTextField(
                            value = confirmPassword,
                            onValueChange = {
                                confirmPassword = it
                                confirmPasswordError = ""
                            },
                            label = "Confirmar contraseña",
                            placeholder = "Repite tu contraseña",
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

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.Top
                        ) {
                            Checkbox(
                                checked = acceptedTerms,
                                onCheckedChange = {
                                    acceptedTerms = it
                                    termsError = ""
                                },
                                enabled = !loading,
                                colors = CheckboxDefaults.colors(
                                    checkedColor = Primary,
                                    uncheckedColor = Muted
                                ),
                                modifier = Modifier.size(40.dp)
                            )

                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(top = 9.dp)
                            ) {
                                Text(
                                    text = "Acepto los Términos y Condiciones y la Política de Privacidad.",
                                    color = Muted,
                                    fontSize = 12.sp,
                                    lineHeight = 17.sp
                                )

                                Row {
                                    Text(
                                        text = "Ver términos",
                                        color = Secondary,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        modifier = Modifier
                                            .clickable(
                                                enabled = !loading,
                                                onClick = onTermsClick
                                            )
                                            .padding(top = 5.dp, end = 14.dp)
                                    )

                                    Text(
                                        text = "Ver privacidad",
                                        color = Secondary,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        modifier = Modifier
                                            .clickable(
                                                enabled = !loading,
                                                onClick = onPrivacyClick
                                            )
                                            .padding(top = 5.dp)
                                    )
                                }
                            }
                        }

                        if (termsError.isNotBlank()) {
                            Text(
                                text = termsError,
                                color = ErrorRed,
                                fontSize = 12.sp,
                                lineHeight = 17.sp,
                                modifier = Modifier.padding(top = 6.dp)
                            )
                        }

                        if (registrationError.isNotBlank()) {
                            Text(
                                text = registrationError,
                                color = ErrorRed,
                                fontSize = 12.sp,
                                lineHeight = 17.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 14.dp)
                                    .background(
                                        color = Color(0xFFFFF3F3),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(11.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        MediSustanciaButton(
                            label = "Crear cuenta",
                            variant = MediButtonVariant.GRADIENT,
                            size = MediButtonSize.FULL,
                            loading = loading,
                            modifier = Modifier.heightIn(min = 52.dp),
                            onClick = {
                                val cleanFirstName = firstName.trim()
                                val cleanLastName = lastName.trim()
                                val cleanEmail = email.trim()

                                firstNameError = if (cleanFirstName.isEmpty()) {
                                    "Ingresa tu nombre."
                                } else {
                                    ""
                                }

                                lastNameError = if (cleanLastName.isEmpty()) {
                                    "Ingresa tus apellidos."
                                } else {
                                    ""
                                }

                                emailError = when {
                                    cleanEmail.isEmpty() ->
                                        "Ingresa tu correo electrónico."

                                    !Patterns.EMAIL_ADDRESS
                                        .matcher(cleanEmail)
                                        .matches() ->
                                        "Ingresa un correo electrónico válido."

                                    else -> ""
                                }

                                passwordError = if (password.isEmpty()) {
                                    "Ingresa una contraseña."
                                } else {
                                    ""
                                }

                                confirmPasswordError = when {
                                    confirmPassword.isEmpty() ->
                                        "Confirma tu contraseña."

                                    confirmPassword != password ->
                                        "Las contraseñas no coinciden."

                                    else -> ""
                                }

                                termsError = if (!acceptedTerms) {
                                    "Debes aceptar los términos y la política de privacidad."
                                } else {
                                    ""
                                }

                                val isValid = listOf(
                                    firstNameError,
                                    lastNameError,
                                    emailError,
                                    passwordError,
                                    confirmPasswordError,
                                    termsError
                                ).all(String::isEmpty)

                                if (isValid) {
                                    keyboardController?.hide()

                                    onRegister(
                                        RegisterFormData(
                                            firstName = cleanFirstName,
                                            lastName = cleanLastName,
                                            email = cleanEmail,
                                            password = password
                                        )
                                    )
                                }
                            }
                        )

                        Spacer(modifier = Modifier.height(22.dp))

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

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 52.dp)
                                .border(
                                    width = 1.dp,
                                    color = Divider,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .clickable(
                                    enabled = !loading,
                                    onClick = onGoogleRegister
                                )
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
                                text = "Registrarse con Google",
                                color = Primary,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Spacer(modifier = Modifier.height(22.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "¿Ya tienes una cuenta?",
                                color = Muted,
                                fontSize = 12.sp
                            )

                            Text(
                                text = " Inicia sesión",
                                color = Secondary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier
                                    .clickable(
                                        enabled = !loading,
                                        onClick = onLoginClick
                                    )
                                    .padding(vertical = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(
    name = "Registro MediSustancia",
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun RegisterScreenPreview() {
    RegisterScreen()
}