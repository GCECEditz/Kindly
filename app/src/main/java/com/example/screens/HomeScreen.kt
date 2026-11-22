package com.example.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CorporateFare
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.SentimentSatisfied
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.MainViewModel
import com.example.Screen
import com.example.ui.theme.KindlyBackground
import com.example.ui.theme.KindlyCoral
import com.example.ui.theme.KindlyInk
import com.example.ui.theme.KindlyMist

@Composable
fun HomeScreen(
    vm: MainViewModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(KindlyBackground)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Mockup Custom Logo container
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(40.dp)
                        .background(KindlyCoral, RoundedCornerShape(12.dp))
                ) {
                    Icon(
                        imageVector = Icons.Filled.People,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Kindly",
                    fontFamily = GoogleSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = KindlyCoral,
                    letterSpacing = (-0.5).sp
                )
            }

            var showInfo by remember { mutableStateOf(false) }
            IconButton(
                onClick = { showInfo = true },
                modifier = Modifier
                    .size(40.dp)
                    .border(1.dp, KindlyMist, CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = "Info",
                    tint = KindlyMist
                )
            }

            if (showInfo) {
                AlertDialog(
                    onDismissRequest = { showInfo = false },
                    title = { Text("About Kindly", fontFamily = GoogleSans, color = KindlyInk, fontWeight = FontWeight.Bold) },
                    text = {
                        Text(
                            "Kindly is a donation platform that connects NGOs with donors across Malaysia. Discover NGOs, donate securely in Ringgit (RM), and help causes reach their fundraising goals.",
                            fontFamily = GoogleSans,
                            fontSize = 14.sp
                        )
                    },
                    confirmButton = {
                        TextButton(onClick = { showInfo = false }) {
                            Text("OK", color = KindlyInk)
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Title
        Text(
            text = "HOME",
            fontFamily = GoogleSans,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 36.sp,
            color = KindlyInk,
            textAlign = TextAlign.Center,
            letterSpacing = 1.5.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Action Grid (2x3 circular items)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.padding(bottom = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                // Proposal
                GridItem(
                    label = "PROPOSAL",
                    icon = Icons.Filled.CorporateFare,
                    onClick = { vm.navigateTo(Screen.Proposals) }
                )

                // Donation
                GridItem(
                    label = "DONATION",
                    icon = Icons.Filled.VolunteerActivism,
                    onClick = { vm.navigateTo(Screen.Donation) }
                )
            }

            Row(
                modifier = Modifier.padding(bottom = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                // Social
                GridItem(
                    label = "SOCIAL",
                    icon = Icons.Filled.SentimentSatisfied,
                    onClick = { vm.navigateTo(Screen.SocialMedia) }
                )

                // Notification
                GridItem(
                    label = "NOTIFICATION",
                    icon = Icons.Filled.Notifications,
                    onClick = { vm.navigateTo(Screen.Feed) }
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                // Budget
                GridItem(
                    label = "BUDGET",
                    icon = Icons.Filled.AttachMoney,
                    onClick = { vm.navigateTo(Screen.Budget) }
                )

                // Progress
                GridItem(
                    label = "PROGRESS",
                    icon = Icons.Filled.QrCode,
                    onClick = { vm.navigateTo(Screen.QrCode) }
                )
            }
        }
    }
}

@Composable
fun GridItem(
    label: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick() }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(96.dp)
                .border(2.dp, KindlyMist, CircleShape)
                .background(Color.White, CircleShape)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = KindlyCoral,
                modifier = Modifier.size(44.dp)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = label,
            fontFamily = GoogleSans,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = KindlyInk,
            letterSpacing = 1.sp
        )
    }
}
