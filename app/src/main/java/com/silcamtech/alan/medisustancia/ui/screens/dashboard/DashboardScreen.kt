package com.silcamtech.alan.medisustancia.ui.screens.dashboard

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.silcamtech.alan.medisustancia.R
import com.silcamtech.alan.medisustancia.ui.components.dashboard.DashboardStatCard
import com.silcamtech.alan.medisustancia.ui.components.dashboard.StatCardVariant

private val Primary = Color(0xFF0E4B43)
private val Secondary = Color(0xFF14B8A6)
private val PageBackground = Color(0xFFF5F9F7)
private val SurfaceBorder = Color(0xFFE4EDE9)
private val Heading = Color(0xFF172B25)
private val Muted = Color(0xFF687572)

private data class DashboardMedicine(
    val name: String,
    val laboratory: String,
    val presentation: String,
    val date: String
)

private data class CatalogItem(
    val label: String,
    val active: Int,
    val total: Int
)

private data class ActivityItem(
    val title: String,
    val detail: String,
    val time: String
)

private val sampleMedicines = listOf(
    DashboardMedicine(
        "Paracetamol",
        "PharmaLab",
        "Tabletas 500 mg",
        "20/08/2026"
    ),
    DashboardMedicine(
        "Ibuprofeno",
        "MediPharma",
        "Tabletas 400 mg",
        "18/08/2026"
    ),
    DashboardMedicine(
        "Amoxicilina",
        "BioHealth",
        "Cápsulas 500 mg",
        "15/08/2026"
    ),
    DashboardMedicine(
        "Loratadina",
        "PharmaLab",
        "Tabletas 10 mg",
        "12/08/2026"
    ),
    DashboardMedicine(
        "Omeprazol",
        "SaludMed",
        "Cápsulas 20 mg",
        "10/08/2026"
    )
)

private val sampleCatalog = listOf(
    CatalogItem("Medicamentos", 1210, 1245),
    CatalogItem("Sustancias", 550, 568),
    CatalogItem("Laboratorios", 29, 32)
)

private val sampleActivities = listOf(
    ActivityItem(
        "Se registró un nuevo usuario",
        "usuario@medisustancia.com",
        "Hace 10 minutos"
    ),
    ActivityItem(
        "Se actualizó un medicamento",
        "Ibuprofeno 400 mg",
        "Hace 35 minutos"
    ),
    ActivityItem(
        "Se agregó una presentación",
        "Paracetamol · Tabletas 500 mg",
        "Hace 1 hora"
    )
)

@Composable
fun DashboardScreen(
    userName: String = "César Silva",
    onMedicinesClick: () -> Unit = {},
    onPresentationsClick: () -> Unit = {},
    onSubstancesClick: () -> Unit = {},
    onMoreClick: () -> Unit = {},
    onNotificationsClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf("Inicio") }

    Scaffold(
        containerColor = PageBackground,
        contentWindowInsets = WindowInsets(0),
        topBar = {
            DashboardTopBar(
                userName = userName,
                onNotificationsClick = onNotificationsClick,
                onProfileClick = onProfileClick
            )
        },
        bottomBar = {
            DashboardBottomBar(
                selectedTab = selectedTab,
                onTabClick = { tab ->
                    selectedTab = tab

                    when (tab) {
                        "Medicamentos" -> onMedicinesClick()
                        "Presentaciones" -> onPresentationsClick()
                        "Sustancias" -> onSubstancesClick()
                        "Más" -> onMoreClick()
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = 20.dp,
                bottom = 28.dp
            ),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            item {
                DashboardWelcome(userName = userName)
            }

            item {
                DashboardStats()
            }

            item {
                DashboardPanel(
                    title = "Últimos medicamentos",
                    subtitle = "Registros recientes del catálogo",
                    action = "Ver todos",
                    onActionClick = onMedicinesClick
                ) {
                    sampleMedicines.forEachIndexed { index, medicine ->
                        MedicineRow(medicine)

                        if (index < sampleMedicines.lastIndex) {
                            PanelDivider()
                        }
                    }
                }
            }

            item {
                DashboardPanel(
                    title = "Estado del catálogo",
                    subtitle = "Registros activos por módulo"
                ) {
                    sampleCatalog.forEachIndexed { index, catalog ->
                        CatalogRow(catalog)

                        if (index < sampleCatalog.lastIndex) {
                            Spacer(modifier = Modifier.height(18.dp))
                        }
                    }
                }
            }

            item {
                DashboardPanel(
                    title = "Actividad reciente",
                    subtitle = "Últimos movimientos del sistema"
                ) {
                    sampleActivities.forEachIndexed { index, activity ->
                        ActivityRow(activity)

                        if (index < sampleActivities.lastIndex) {
                            PanelDivider()
                        }
                    }
                }
            }

            item {
                DashboardPanel(
                    title = "Solicitudes pendientes",
                    subtitle = "Elementos que requieren revisión",
                    action = "Ver todos",
                    onActionClick = onMoreClick
                ) {
                    PendingRow(
                        count = "5",
                        title = "Nuevos laboratorios",
                        detail = "Esperan aprobación",
                        color = Color(0xFFB76E12)
                    )

                    PanelDivider()

                    PendingRow(
                        count = "12",
                        title = "Nuevos medicamentos",
                        detail = "Esperan revisión",
                        color = Primary
                    )
                }
            }
        }
    }
}

@Composable
private fun DashboardTopBar(
    userName: String,
    onNotificationsClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    Surface(
        color = Color.White,
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .height(64.dp)
                .padding(start = 16.dp, end = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.medisustancia_logo),
                contentDescription = "MediSustancia",
                contentScale = ContentScale.Fit,
                alignment = Alignment.CenterStart,
                modifier = Modifier
                    .width(175.dp)
                    .height(52.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clickable(onClick = onNotificationsClick),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_notifications),
                    contentDescription = "Notificaciones",
                    tint = Primary,
                    modifier = Modifier.size(23.dp)
                )
            }

            Spacer(modifier = Modifier.width(4.dp))

            Box(
                modifier = Modifier
                    .size(38.dp)
                    .background(
                        color = Color(0xFFE4F8F3),
                        shape = CircleShape
                    )
                    .clickable(onClick = onProfileClick),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = userName
                        .split(" ")
                        .filter(String::isNotBlank)
                        .take(2)
                        .map { it.first().uppercaseChar() }
                        .joinToString(""),
                    color = Primary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun DashboardWelcome(userName: String) {
    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = "Resumen general",
            color = Heading,
            fontSize = 28.sp,
            lineHeight = 34.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Bienvenido, $userName. Aquí tienes un panorama general del sistema.",
            color = Muted,
            fontSize = 14.sp,
            lineHeight = 20.sp
        )
    }
}

@Composable
private fun DashboardStats() {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            DashboardStatCard(
                title = "Medicamentos",
                value = "1,245",
                description = "Registrados",
                icon = R.drawable.ic_medicines,
                modifier = Modifier.weight(1f)
            )

            DashboardStatCard(
                title = "Presentaciones",
                value = "856",
                description = "Registradas",
                icon = R.drawable.ic_presentations,
                variant = StatCardVariant.SECONDARY,
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            DashboardStatCard(
                title = "Sustancias",
                value = "568",
                description = "Registradas",
                icon = R.drawable.ic_substances,
                variant = StatCardVariant.SUCCESS,
                modifier = Modifier.weight(1f)
            )

            DashboardStatCard(
                title = "Laboratorios",
                value = "32",
                description = "Registrados",
                icon = R.drawable.ic_laboratories,
                variant = StatCardVariant.WARNING,
                modifier = Modifier.weight(1f)
            )
        }

        DashboardStatCard(
            title = "Usuarios",
            value = "24",
            description = "Activos",
            icon = R.drawable.ic_users
        )
    }
}

@Composable
private fun DashboardPanel(
    title: String,
    subtitle: String,
    action: String? = null,
    onActionClick: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = Color.White,
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = SurfaceBorder
        ),
        shadowElevation = 1.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    Text(
                        text = title,
                        color = Heading,
                        fontSize = 16.sp,
                        lineHeight = 21.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = subtitle,
                        color = Muted,
                        fontSize = 11.sp,
                        lineHeight = 16.sp
                    )
                }

                if (action != null) {
                    Text(
                        text = action,
                        color = Primary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .clickable(onClick = onActionClick)
                            .padding(start = 8.dp, top = 4.dp, bottom = 8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            content()
        }
    }
}

@Composable
private fun MedicineRow(medicine: DashboardMedicine) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .background(
                    color = Color(0xFFE4F8F3),
                    shape = RoundedCornerShape(12.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_medicines),
                contentDescription = null,
                tint = Primary,
                modifier = Modifier.size(21.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Text(
                text = medicine.name,
                color = Heading,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = "${medicine.presentation} · ${medicine.laboratory}",
                color = Muted,
                fontSize = 11.sp,
                maxLines = 2,
                lineHeight = 15.sp,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = medicine.date,
                color = Muted,
                fontSize = 10.sp
            )
        }
    }
}

@Composable
private fun PendingRow(
    count: String,
    title: String,
    detail: String,
    color: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 11.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    color = color.copy(alpha = 0.12f),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = count,
                color = color,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Text(
                text = title,
                color = Heading,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = detail,
                color = Muted,
                fontSize = 11.sp
            )
        }
    }
}

@Composable
private fun CatalogRow(item: CatalogItem) {
    val progress = if (item.total == 0) {
        0f
    } else {
        item.active.toFloat() / item.total.toFloat()
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = item.label,
                color = Heading,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "${(progress * 100).toInt()}%",
                color = Primary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }

        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxWidth(),
            color = Secondary,
            trackColor = Color(0xFFE8F5F2)
        )

        Text(
            text = "${item.active} activos de ${item.total}",
            color = Muted,
            fontSize = 11.sp
        )
    }
}

@Composable
private fun ActivityRow(item: ActivityItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(34.dp)
                .background(
                    color = Color(0xFFE4F8F3),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(9.dp)
                    .background(Secondary, CircleShape)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Text(
                text = item.title,
                color = Heading,
                fontSize = 12.sp,
                lineHeight = 17.sp,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = item.detail,
                color = Muted,
                fontSize = 11.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = item.time,
                color = Muted,
                fontSize = 10.sp
            )
        }
    }
}

@Composable
private fun PanelDivider() {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(SurfaceBorder)
    )
}

private data class BottomTab(
    val label: String,
    @DrawableRes val icon: Int
)

private val bottomTabs = listOf(
    BottomTab("Inicio", R.drawable.ic_home),
    BottomTab("Medicamentos", R.drawable.ic_medicines),
    BottomTab("Presentaciones", R.drawable.ic_presentations),
    BottomTab("Sustancias", R.drawable.ic_substances),
    BottomTab("Más", R.drawable.ic_grid)
)

@Composable
private fun DashboardBottomBar(
    selectedTab: String,
    onTabClick: (String) -> Unit
) {
    Surface(
        color = Primary,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 6.dp, vertical = 9.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            bottomTabs.forEach { tab ->
                val selected = selectedTab == tab.label

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onTabClick(tab.label) }
                        .padding(vertical = 3.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(width = 48.dp, height = 32.dp)
                            .background(
                                color = if (selected) {
                                    Color.White.copy(alpha = 0.17f)
                                } else {
                                    Color.Transparent
                                },
                                shape = RoundedCornerShape(12.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(tab.icon),
                            contentDescription = null,
                            tint = if (selected) Secondary else Color.White,
                            modifier = Modifier.size(21.dp)
                        )
                    }

                    Text(
                        text = tab.label,
                        color = if (selected) Secondary else Color.White,
                        fontSize = 9.sp,
                        fontWeight = if (selected) {
                            FontWeight.SemiBold
                        } else {
                            FontWeight.Normal
                        },
                        maxLines = 1
                    )
                }
            }
        }
    }
}

@Preview(
    name = "Dashboard MediSustancia",
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun DashboardScreenPreview() {
    DashboardScreen()
}