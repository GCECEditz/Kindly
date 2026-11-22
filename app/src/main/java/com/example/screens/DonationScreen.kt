package com.example.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.MainViewModel
import com.example.Screen
import com.example.ui.theme.*
import java.util.Locale

@Composable
fun DonationScreen(
    vm: MainViewModel,
    modifier: Modifier = Modifier
) {
    // TODO(2.x backend): replace mock data with Supabase D5/D6.
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(KindlyBackground),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        // Header
        item {
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
                    text = "DONATION",
                    fontFamily = GoogleSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = KindlyInk
                )
            }
        }

        // YOUR IMPACT section label
        item {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "YOUR IMPACT",
                fontFamily = GoogleSans,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = KindlyInk,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        // StatCards row
        item {
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
            Spacer(modifier = Modifier.height(24.dp))
        }

        // ACTIVE CAMPAIGNS section label
        item {
            Text(
                text = "ACTIVE CAMPAIGNS",
                fontFamily = GoogleSans,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = KindlyInk,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        // Campaign cards
        items(mockCampaigns) { campaign ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 6.dp)
            ) {
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
                                    text = campaign.ngoInitials,
                                    fontFamily = GoogleSans,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = KindlyCoral
                                )
                            }
                            Spacer(modifier = Modifier.width(15.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = campaign.title,
                                    fontFamily = GoogleSans,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = KindlyInk
                                )
                                Text(
                                    text = campaign.ngoName,
                                    fontFamily = GoogleSans,
                                    fontSize = 12.sp,
                                    color = KindlySage
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        val pct = (campaign.raised / campaign.goal).toFloat().coerceIn(0f, 1f)
                        LinearProgressIndicator(
                            progress = { pct },
                            color = KindlyTeal,
                            trackColor = KindlyMist.copy(alpha = 0.3f),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(16.dp)
                                .clip(RoundedCornerShape(8.dp))
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "${(pct * 100).toInt()}% RAISED",
                                fontFamily = GoogleSans,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = KindlyInk
                            )
                            Text(
                                text = "RM ${String.format(Locale.US, "%.2f", campaign.raised)} / RM ${String.format(Locale.US, "%.2f", campaign.goal)} Goal",
                                fontFamily = GoogleSans,
                                fontWeight = FontWeight.Medium,
                                fontSize = 12.sp,
                                color = KindlyCoral
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = { vm.navigateTo(Screen.Donate) },
                            colors = ButtonDefaults.buttonColors(containerColor = KindlyTeal),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(
                                text = "DONATE",
                                fontFamily = GoogleSans,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(18.dp))
            Text(
                text = "MY RECENT DONATIONS",
                fontFamily = GoogleSans,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = KindlyInk,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        items(mockDonations.take(2)) { donation ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 6.dp)
            ) {
                DonationHistoryCard(donation = donation, vm = vm)
            }
        }

        item {
            Spacer(modifier = Modifier.height(12.dp))
            TextButton(
                onClick = { vm.navigateTo(Screen.DonationHistory) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "VIEW ALL DONATION HISTORY",
                    fontFamily = GoogleSans,
                    color = KindlyInk,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
