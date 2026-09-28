package com.silcamtech.alan.medisustancia.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class MediInputType {
    TEXT,
    EMAIL,
    PASSWORD,
    NUMBER,
    SEARCH,
    TEL,
    URL
}

enum class MediInputVariant {
    PRIMARY,
    SECONDARY,
    WARNING,
    DANGER,
    SEARCH
}

enum class MediInputSize {
    SMALL,
    MEDIUM,
    LARGE,
    FULL
}

private val InputPrimary = Color(0xFF0E4B43)
private val InputSecondary = Color(0xFF14B8A6)
private val InputWarning = Color(0xFFFF9F2B)
private val InputDanger = Color(0xFFFA0000)

@Composable
fun MediSustanciaTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "",
    placeholder: String = "",
    type: MediInputType = MediInputType.TEXT,
    variant: MediInputVariant = MediInputVariant.PRIMARY,
    size: MediInputSize = MediInputSize.FULL,
    enabled: Boolean = true,
    required: Boolean = false,
    helperText: String = "",
    errorText: String = "",
    @DrawableRes leftIcon: Int? = null,
    @DrawableRes rightIcon: Int? = null,
    rightIconLabel: String = "Acción",
    onRightIconClick: (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    imeAction: ImeAction = ImeAction.Next
) {
    var isFocused by remember { mutableStateOf(false) }

    val hasError = errorText.isNotBlank()
    val normalBorder = when (variant) {
        MediInputVariant.PRIMARY,
        MediInputVariant.SEARCH -> Color(0xFFD8DFDD)

        MediInputVariant.SECONDARY -> Color(0xFFC5D4D1)
        MediInputVariant.WARNING -> InputWarning
        MediInputVariant.DANGER -> InputDanger
    }

    val focusedBorder = when (variant) {
        MediInputVariant.PRIMARY,
        MediInputVariant.SEARCH -> InputPrimary

        MediInputVariant.SECONDARY -> InputSecondary
        MediInputVariant.WARNING -> InputWarning
        MediInputVariant.DANGER -> InputDanger
    }

    val borderColor = when {
        hasError -> InputDanger
        isFocused -> focusedBorder
        else -> normalBorder
    }

    val iconColor = when {
        hasError -> InputDanger
        isFocused -> InputSecondary
        else -> Color(0xFF71807D)
    }

    val backgroundColor = when {
        !enabled -> Color(0xFFF4F4F4)
        variant == MediInputVariant.SEARCH && !isFocused -> Color(0xFFF8FBFA)
        else -> Color.White
    }

    val fieldWidth = when (size) {
        MediInputSize.FULL -> Modifier.fillMaxWidth()
        else -> Modifier.widthIn(min = 220.dp)
    }

    val fieldHeight: Dp = when (size) {
        MediInputSize.SMALL -> 34.dp
        MediInputSize.MEDIUM,
        MediInputSize.FULL -> 50.dp
        MediInputSize.LARGE -> 46.dp
    }

    val textSize = if (size == MediInputSize.LARGE) 15.sp else 14.sp

    Column(
        modifier = modifier
            .then(fieldWidth)
            .alpha(if (enabled) 1f else 0.65f),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        if (label.isNotBlank()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = label,
                    color = Color(0xFF444444),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )

                if (required) {
                    Text(
                        text = " *",
                        color = InputDanger,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            singleLine = true,
            textStyle = TextStyle(
                color = Color(0xFF333333),
                fontSize = textSize
            ),
            cursorBrush = SolidColor(InputPrimary),
            visualTransformation = visualTransformation,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardTypeFor(type),
                imeAction = imeAction
            ),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = fieldHeight)
                .onFocusChanged { isFocused = it.isFocused }
                .background(
                    color = backgroundColor,
                    shape = RoundedCornerShape(8.dp)
                )
                .border(
                    width = 1.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(8.dp)
                ),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier.padding(
                        start = if (leftIcon == null) 11.dp else 10.dp,
                        end = if (rightIcon == null) 11.dp else 2.dp,
                        top = 2.dp,
                        bottom = 2.dp
                    ),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (leftIcon != null) {
                        Icon(
                            painter = painterResource(leftIcon),
                            contentDescription = null,
                            tint = iconColor,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (value.isEmpty()) {
                            Text(
                                text = placeholder,
                                color = Color(0xFF9A9A9A),
                                fontSize = textSize,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        innerTextField()
                    }

                    if (rightIcon != null) {
                        if (onRightIconClick != null) {
                            IconButton(
                                onClick = onRightIconClick,
                                enabled = enabled,
                                modifier = Modifier.size(36.dp)
                            ) {
                                Icon(
                                    painter = painterResource(rightIcon),
                                    contentDescription = rightIconLabel,
                                    tint = iconColor,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        } else {
                            Icon(
                                painter = painterResource(rightIcon),
                                contentDescription = null,
                                tint = iconColor,
                                modifier = Modifier
                                    .padding(end = 9.dp)
                                    .size(18.dp)
                            )
                        }
                    }
                }
            }
        )

        if (hasError) {
            Text(
                text = errorText,
                color = InputDanger,
                fontSize = 11.sp,
                lineHeight = 15.sp
            )
        } else if (helperText.isNotBlank()) {
            Text(
                text = helperText,
                color = Color(0xFF777777),
                fontSize = 11.sp,
                lineHeight = 15.sp
            )
        }
    }
}

private fun keyboardTypeFor(type: MediInputType): KeyboardType = when (type) {
    MediInputType.TEXT,
    MediInputType.SEARCH -> KeyboardType.Text

    MediInputType.EMAIL -> KeyboardType.Email
    MediInputType.PASSWORD -> KeyboardType.Password
    MediInputType.NUMBER -> KeyboardType.Number
    MediInputType.TEL -> KeyboardType.Phone
    MediInputType.URL -> KeyboardType.Uri
}

@Preview(name = "Campos MediSustancia", showBackground = true)
@Composable
private fun MediSustanciaTextFieldPreview() {
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        MediSustanciaTextField(
            value = email,
            onValueChange = { email = it },
            label = "Correo electrónico",
            placeholder = "ejemplo@correo.com",
            type = MediInputType.EMAIL,
            required = true
        )

        MediSustanciaTextField(
            value = "",
            onValueChange = {},
            label = "Buscar",
            placeholder = "Buscar medicamentos",
            variant = MediInputVariant.SEARCH
        )

        MediSustanciaTextField(
            value = "correo incorrecto",
            onValueChange = {},
            label = "Correo electrónico",
            errorText = "Ingresa un correo válido."
        )
    }
}