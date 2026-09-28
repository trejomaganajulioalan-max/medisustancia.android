package com.silcamtech.alan.medisustancia.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class MediButtonVariant {
    PRIMARY,
    SECONDARY,
    WARNING,
    DANGER,
    OUTLINE,
    OUTLINE_SECONDARY,
    OUTLINE_WARNING,
    OUTLINE_DANGER,
    NONE,
    GRADIENT
}

enum class MediButtonSize {
    SMALL,
    MEDIUM,
    LARGE,
    FULL
}

private object MediButtonColors {
    val Primary = Color(0xFF0E4B43)
    val Secondary = Color(0xFF14B8A6)
    val Warning = Color(0xFFFF9F2B)
    val Danger = Color(0xFFFA0000)
    val GradientStart = Color(0xFF0E7469)
    val GradientEnd = Color(0xFF22C7B2)
}

@Composable
fun MediSustanciaButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: MediButtonVariant = MediButtonVariant.PRIMARY,
    size: MediButtonSize = MediButtonSize.MEDIUM,
    enabled: Boolean = true,
    loading: Boolean = false,
    onlyIcon: Boolean = false,
    leftIcon: (@Composable () -> Unit)? = null,
    rightIcon: (@Composable () -> Unit)? = null
) {
    val canClick = enabled && !loading
    val shape = RoundedCornerShape(8.dp)
    val height = buttonHeight(size)
    val horizontalPadding = buttonHorizontalPadding(size)
    val background = buttonBackground(variant)
    val contentColor = buttonContentColor(variant)
    val borderColor = buttonBorderColor(variant)

    val widthModifier = when {
        onlyIcon -> Modifier.size(height)
        size == MediButtonSize.FULL -> Modifier.fillMaxWidth()
        else -> Modifier
    }

    Box(
        modifier = modifier
            .then(widthModifier)
            .heightIn(min = height)
            .alpha(if (canClick) 1f else 0.5f)
            .background(brush = background, shape = shape)
            .border(width = 1.dp, color = borderColor, shape = shape)
            .then(
                if (onlyIcon) {
                    Modifier.semantics { contentDescription = label }
                } else {
                    Modifier
                }
            )
            .clickable(
                enabled = canClick,
                role = Role.Button,
                onClick = onClick
            )
            .padding(
                horizontal = if (onlyIcon) 0.dp else horizontalPadding,
                vertical = if (onlyIcon) 0.dp else 7.dp
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(7.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    color = contentColor,
                    strokeWidth = 2.dp
                )
            } else if (leftIcon != null) {
                leftIcon()
            }

            if (!onlyIcon) {
                Text(
                    text = label,
                    color = contentColor,
                    fontSize = buttonFontSize(size),
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            if (!loading && rightIcon != null) {
                rightIcon()
            }
        }
    }
}

private fun buttonHeight(size: MediButtonSize): Dp = when (size) {
    MediButtonSize.SMALL -> 34.dp
    MediButtonSize.MEDIUM -> 40.dp
    MediButtonSize.LARGE -> 46.dp
    MediButtonSize.FULL -> 40.dp
}

private fun buttonHorizontalPadding(size: MediButtonSize): Dp = when (size) {
    MediButtonSize.SMALL -> 12.dp
    MediButtonSize.MEDIUM -> 16.dp
    MediButtonSize.LARGE -> 20.dp
    MediButtonSize.FULL -> 16.dp
}

private fun buttonFontSize(size: MediButtonSize) = when (size) {
    MediButtonSize.SMALL -> 12.sp
    MediButtonSize.MEDIUM -> 14.sp
    MediButtonSize.LARGE -> 15.sp
    MediButtonSize.FULL -> 14.sp
}

private fun buttonBackground(variant: MediButtonVariant): Brush {
    val color = when (variant) {
        MediButtonVariant.PRIMARY -> MediButtonColors.Primary
        MediButtonVariant.SECONDARY -> MediButtonColors.Secondary
        MediButtonVariant.WARNING -> MediButtonColors.Warning
        MediButtonVariant.DANGER -> MediButtonColors.Danger

        MediButtonVariant.OUTLINE,
        MediButtonVariant.OUTLINE_SECONDARY,
        MediButtonVariant.OUTLINE_WARNING,
        MediButtonVariant.OUTLINE_DANGER,
        MediButtonVariant.NONE -> Color.Transparent

        MediButtonVariant.GRADIENT -> {
            return Brush.horizontalGradient(
                listOf(
                    MediButtonColors.GradientStart,
                    MediButtonColors.GradientEnd
                )
            )
        }
    }

    return Brush.linearGradient(listOf(color, color))
}

private fun buttonContentColor(variant: MediButtonVariant): Color =
    when (variant) {
        MediButtonVariant.OUTLINE -> MediButtonColors.Primary
        MediButtonVariant.OUTLINE_SECONDARY -> MediButtonColors.Secondary
        MediButtonVariant.OUTLINE_WARNING -> MediButtonColors.Warning
        MediButtonVariant.OUTLINE_DANGER -> MediButtonColors.Danger
        MediButtonVariant.NONE -> MediButtonColors.Primary
        else -> Color.White
    }

private fun buttonBorderColor(variant: MediButtonVariant): Color =
    when (variant) {
        MediButtonVariant.PRIMARY,
        MediButtonVariant.OUTLINE -> MediButtonColors.Primary

        MediButtonVariant.SECONDARY,
        MediButtonVariant.OUTLINE_SECONDARY -> MediButtonColors.Secondary

        MediButtonVariant.WARNING,
        MediButtonVariant.OUTLINE_WARNING -> MediButtonColors.Warning

        MediButtonVariant.DANGER,
        MediButtonVariant.OUTLINE_DANGER -> MediButtonColors.Danger

        MediButtonVariant.NONE,
        MediButtonVariant.GRADIENT -> Color.Transparent
    }

@Preview(name = "Botones MediSustancia", showBackground = true)
@Composable
private fun MediSustanciaButtonPreview() {
    androidx.compose.foundation.layout.Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        MediSustanciaButton(
            label = "Primario",
            onClick = {}
        )

        MediSustanciaButton(
            label = "Secundario",
            variant = MediButtonVariant.SECONDARY,
            onClick = {}
        )

        MediSustanciaButton(
            label = "Contorno",
            variant = MediButtonVariant.OUTLINE,
            onClick = {}
        )

        MediSustanciaButton(
            label = "Eliminar",
            variant = MediButtonVariant.DANGER,
            onClick = {}
        )

        MediSustanciaButton(
            label = "Iniciar Sesión",
            variant = MediButtonVariant.GRADIENT,
            size = MediButtonSize.FULL,
            onClick = {}
        )

        MediSustanciaButton(
            label = "Guardando",
            loading = true,
            onClick = {}
        )

        MediSustanciaButton(
            label = "Deshabilitado",
            enabled = false,
            onClick = {}
        )
    }
}