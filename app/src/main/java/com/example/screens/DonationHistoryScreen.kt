package com.example.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.MainViewModel
import com.example.Screen
import com.example.ui.theme.*
import java.util.Locale

@Composable
fun DonationHistoryScreen(
    vm: MainViewModel,
    modifier: Modifier = Modifier
) {
    var filter by remember { mutableStateOf("ALL") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(KindlyBackground)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { vm.navigateBack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = KindlyInk
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "DONATION HISTORY",
                fontFamily = GoogleSans,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = KindlyInk
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // StatCards row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Box(modifier = Modifier.weight(1f)) {
                StatCard(
                    title = "TOTAL DONATED",
                    value = "RM 850.00",
                    icon = Icons.Filled.Favorite
                )
            }
            Box(modifier = Modifier.weight(1f)) {
                StatCard(
                    title = "DONATIONS MADE",
                    value = "14",
                    icon = Icons.Filled.History
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Confirmed donations only. Showing the 5 most recent.",
            fontFamily = GoogleSans,
            fontSize = 11.sp,
            color = KindlySage,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 4.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Filter chips row
        val filters = listOf("ALL", "CONFIRMED", "PENDING", "RETRACTED")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            filters.forEach { f ->
                val isSelected = (filter == f)
                val backgroundColor = if (isSelected) KindlyCoral.copy(alpha = 0.1f) else Color.Transparent
                val borderColor = if (isSelected) KindlyInk else KindlyMist
                val textColor = if (isSelected) KindlyInk else KindlySage

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(backgroundColor)
                        .border(1.dp, borderColor, RoundedCornerShape(16.dp))
                        .clickable { filter = f }
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = f,
                        fontFamily = GoogleSans,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = textColor
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        val filteredList = if (filter == "ALL") mockDonations else mockDonations.filter { it.status == filter }

        if (filteredList.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No donations with this status yet.",
                    fontFamily = GoogleSans,
                    fontSize = 13.sp,
                    color = KindlySage
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp),
                contentPadding = PaddingValues(bottom = 32.dp)
            ) {
                items(filteredList) { donation ->
                    DonationHistoryCard(donation = donation, vm = vm)
                }

                // Per-NGO totals report (Requirement 2.6)
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "PER-NGO TOTALS (REPORT)",
                        fontFamily = GoogleSans,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = KindlyInk,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, bottom = 4.dp)
                    )
                }

                items(mockNgoTotals) { ngoTotal ->
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = BorderStroke(1.dp, KindlyMist),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = ngoTotal.ngoName,
                                    fontFamily = GoogleSans,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = KindlyInk,
                                    modifier = Modifier.weight(1f)
                                )
                                Text(
                                    text = "RM " + String.format(Locale.US, "%.2f", ngoTotal.confirmed),
                                    fontFamily = GoogleSans,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = KindlyInk
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Kindly 5% (RM " + String.format(Locale.US, "%.2f", ngoTotal.fee) + ") · NGO receives 95% (RM " + String.format(Locale.US, "%.2f", ngoTotal.toNgo) + ", gateway fees borne by NGO)",
                                fontFamily = GoogleSans,
                                fontSize = 12.sp,
                                color = KindlySage
                            )
                        }
                    }
                }

                // Grand total card
                item {
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = BorderStroke(1.dp, KindlyMist),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "TOTAL (ALL NGOS)",
                                    fontFamily = GoogleSans,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = KindlyTeal,
                                    modifier = Modifier.weight(1f)
                                )
                                Text(
                                    text = "RM 850.00",
                                    fontFamily = GoogleSans,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = KindlyTeal
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Kindly 5% (RM 42.50) · NGO receives 95% (RM 807.50, gateway fees borne by NGO)",
                                fontFamily = GoogleSans,
                                fontSize = 12.sp,
                                color = KindlySage
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DonationHistoryCard(donation: MockDonation, vm: MainViewModel) {
    val context = LocalContext.current
    var showRetractDialog by remember { mutableStateOf(false) }

    // TODO(2.3/2.4 backend): edit/retract write to Supabase D5 + addFeedNotification; EDIT re-opens the donate form pre-filled in edit mode.

    if (showRetractDialog) {
        AlertDialog(
            onDismissRequest = { showRetractDialog = false },
            title = {
                Text(
                    text = "RETRACT DONATION",
                    color = KindlyInk,
                    fontWeight = FontWeight.Bold,
                    fontFamily = GoogleSans
                )
            },
            text = {
                Text(
                    text = "This pending donation will be marked as RETRACTED and excluded from totals. (Backend wiring comes later.)",
                    color = KindlyInk.copy(alpha = 0.6f),
                    fontFamily = GoogleSans
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        Toast.makeText(context, "Retraction is wired to the backend later", Toast.LENGTH_SHORT).show()
                        showRetractDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "RETRACT",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { showRetractDialog = false }) {
                    Text(
                        text = "CANCEL",
                        color = KindlyInk
                    )
                }
            }
        )
    }

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, KindlyMist),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .border(1.dp, KindlyCoral, CircleShape)
                        .background(KindlyCoral.copy(alpha = 0.15f), CircleShape)
                ) {
                    Text(
                        text = donation.ngoInitials,
                        fontFamily = GoogleSans,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = KindlyCoral
                    )
                }
                Spacer(modifier = Modifier.width(15.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = donation.ngoName,
                        fontFamily = GoogleSans,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = KindlyInk
                    )
                    Text(
                        text = donation.campaignTitle,
                        fontFamily = GoogleSans,
                        fontSize = 12.sp,
                        color = KindlySage
                    )
                    Text(
                        text = donation.date,
                        fontFamily = GoogleSans,
                        fontSize = 11.sp,
                        color = KindlySage
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "RM ${String.format(Locale.US, "%.2f", donation.amount)}",
                            fontFamily = GoogleSans,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = KindlyInk
                        )
                        if (donation.status == "PENDING") {
                            Spacer(modifier = Modifier.width(4.dp))
                            Box {
                                var showMenu by remember { mutableStateOf(false) }
                                IconButton(
                                    onClick = { showMenu = true },
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.MoreVert,
                                        contentDescription = "Donation options",
                                        tint = KindlyInk
                                    )
                                }
                                DropdownMenu(
                                    expanded = showMenu,
                                    onDismissRequest = { showMenu = false }
                                ) {
                                    DropdownMenuItem(
                                        text = { Text("Edit donation", color = KindlyInk) },
                                        onClick = {
                                            showMenu = false
                                            vm.navigateTo(Screen.Donate)
                                        }
                                    )
                                    DropdownMenuItem(
                                        text = { Text("Retract donation", color = Color.Red) },
                                        onClick = {
                                            showMenu = false
                                            showRetractDialog = true
                                        }
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    val chipColor = statusColor(donation.status)
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(chipColor.copy(alpha = 0.15f))
                            .padding(horizontal = 10.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = donation.status,
                            fontFamily = GoogleSans,
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp,
                            color = chipColor
                        )
                    }
                }
            }
            if (donation.message.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = donation.message,
                    fontFamily = GoogleSans,
                    fontSize = 12.sp,
                    color = KindlySage
                )
            }
        }
    }
}

private fun statusColor(status: String): Color = when (status) {
    "CONFIRMED" -> KindlyTeal
    "PENDING" -> KindlySage
    else -> Color.Red
}
