package com.silcamtech.alan.medisustancia.ui.components.dashboard

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.silcamtech.alan.medisustancia.R

enum class StatCardVariant {
    PRIMARY,
    SECONDARY,
    SUCCESS,
    WARNING,
    DANGER
}

@Composable
fun DashboardStatCard(
    title: String,
    value: String,
    description: String,
    @DrawableRes icon: Int,
    modifier: Modifier = Modifier,
    variant: StatCardVariant = StatCardVariant.PRIMARY
) {
    val accent = when (variant) {
        StatCardVariant.PRIMARY -> Color(0xFF0E4B43)
        StatCardVariant.SECONDARY -> Color(0xFF14B8A6)
        StatCardVariant.SUCCESS -> Color(0xFF2CA573)
        StatCardVariant.WARNING -> Color(0xFFFF9F2B)
        StatCardVariant.DANGER -> Color(0xFFFA0000)
    }

    val iconBackground = when (variant) {
        StatCardVariant.PRIMARY -> Color(0xFFE4F8F3)
        StatCardVariant.SECONDARY -> Color(0xFFE8F1EF)
        StatCardVariant.SUCCESS -> Color(0xFFDDF8E9)
        StatCardVariant.WARNING -> Color(0xFFFFF1DF)
        StatCardVariant.DANGER -> Color(0xFFFFE8E8)
    }

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        color = Color.White,
        shadowElevation = 3.dp
    ) {
        Column {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(3.dp)
                    .background(accent)
            )

            Row(
                modifier = Modifier.padding(13.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    Text(
                        text = title,
                        color = Color(0xFF667C76),
                        fontSize = 11.sp,
                        maxLines = 1
                    )

                    Text(
                        text = value,
                        color = Color(0xFF182A25),
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = description,
                        color = Color(0xFF87938F),
                        fontSize = 10.sp
                    )
                }

                Spacer(modifier = Modifier.width(5.dp))

                Icon(
                    painter = painterResource(icon),
                    contentDescription = null,
                    tint = accent,
                    modifier = Modifier
                        .size(43.dp)
                        .background(
                            iconBackground,
                            RoundedCornerShape(12.dp)
                        )
                        .padding(10.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DashboardStatCardPreview() {
    DashboardStatCard(
        title = "Medicamentos",
        value = "1,245",
        description = "Registrados",
        icon = R.drawable.ic_medicines,
        modifier = Modifier.padding(16.dp)
    )
}