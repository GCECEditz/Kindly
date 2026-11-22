package com.example.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.MainViewModel
import com.example.Screen
import com.example.ui.theme.*

@Composable
fun PaymentResultScreen(
    vm: MainViewModel,
    modifier: Modifier = Modifier
) {
    var showFailure by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(KindlyBackground)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
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
                text = "RECEIPT",
                fontFamily = GoogleSans,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = KindlyInk
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (!showFailure) {
            // TODO(backend): drive showFailure from the real gateway result and delete the preview button.
            // Success icon circle
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(80.dp)
                    .background(KindlyTeal.copy(alpha = 0.15f), androidx.compose.foundation.shape.CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = "Payment successful",
                    tint = KindlyTeal,
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "DONATION CONFIRMED",
                fontFamily = GoogleSans,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = KindlyInk,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Thank you for your kindness.",
                fontFamily = GoogleSans,
                fontSize = 14.sp,
                color = KindlySage,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Receipt Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
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
                        Text(
                            text = "RECEIPT",
                            fontFamily = GoogleSans,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            color = KindlySage
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Key-value rows
                        val rows = listOf(
                            "Receipt no." to "KN-2026-001248",
                            "Campaign" to "Subsidised Cancer Treatment Fund",
                            "NGO" to "Mount Miriam Cancer Hospital",
                            "Date" to "21 Nov 2026"
                        )

                        rows.forEach { (label, value) ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = label,
                                    fontFamily = GoogleSans,
                                    fontSize = 12.sp,
                                    color = KindlySage
                                )
                                Text(
                                    text = value,
                                    fontFamily = GoogleSans,
                                    fontWeight = if (label == "Receipt no." || label == "Campaign") FontWeight.Bold else FontWeight.Normal,
                                    fontSize = if (label == "Receipt no." || label == "Campaign") 13.sp else 13.sp,
                                    color = if (label == "Receipt no." || label == "Campaign") KindlyInk else Color.Black,
                                    textAlign = TextAlign.End,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(KindlyMist)
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        // Amount rows
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Amount paid",
                                fontFamily = GoogleSans,
                                fontSize = 12.sp,
                                color = KindlySage
                            )
                            Text(
                                text = "RM 50.00",
                                fontFamily = GoogleSans,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = KindlyInk
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "To NGO (95%)",
                                fontFamily = GoogleSans,
                                fontSize = 12.sp,
                                color = KindlySage
                            )
                            Text(
                                text = "RM 47.50",
                                fontFamily = GoogleSans,
                                fontSize = 13.sp,
                                color = Color.Black
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Platform fee (5%, borne by NGO)",
                                fontFamily = GoogleSans,
                                fontSize = 12.sp,
                                color = KindlySage
                            )
                            Text(
                                text = "RM 2.50",
                                fontFamily = GoogleSans,
                                fontSize = 12.sp,
                                color = KindlySage
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(KindlyTeal.copy(alpha = 0.15f))
                                .padding(horizontal = 10.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = "CONFIRMED",
                                fontFamily = GoogleSans,
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp,
                                color = KindlyTeal
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Sample receipt — real gateway confirmation arrives with the payment integration step.",
                            fontFamily = GoogleSans,
                            fontSize = 11.sp,
                            color = KindlySage
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { vm.navigateTo(Screen.Home) },
                colors = ButtonDefaults.buttonColors(containerColor = KindlyTeal),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .height(48.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = "BACK TO HOME",
                    fontFamily = GoogleSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(
                onClick = { vm.navigateTo(Screen.DonationHistory) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "VIEW DONATION HISTORY",
                    fontFamily = GoogleSans,
                    color = KindlyInk,
                    fontWeight = FontWeight.Bold
                )
            }
        } else {
            // TODO(backend): drive showFailure from the real gateway result and delete the preview button.
            // Failure icon circle
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(80.dp)
                    .background(Color.Red.copy(alpha = 0.15f), androidx.compose.foundation.shape.CircleShape)
                    .border(1.dp, Color.Red, androidx.compose.foundation.shape.CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Filled.Cancel,
                    contentDescription = "Payment failed",
                    tint = Color.Red,
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "DONATION FAILED",
                fontFamily = GoogleSans,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = KindlyInk,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Your payment was not completed — no money was taken.",
                fontFamily = GoogleSans,
                fontSize = 14.sp,
                color = KindlySage,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Failure Receipt Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
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
                        Text(
                            text = "RECEIPT",
                            fontFamily = GoogleSans,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            color = KindlySage
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        val failureRows = listOf(
                            "Receipt no." to "KN-2026-001249",
                            "Campaign" to "Subsidised Cancer Treatment Fund",
                            "NGO" to "Mount Miriam Cancer Hospital",
                            "Date" to "21 Nov 2026"
                        )

                        failureRows.forEach { (label, value) ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = label,
                                    fontFamily = GoogleSans,
                                    fontSize = 12.sp,
                                    color = KindlySage
                                )
                                Text(
                                    text = value,
                                    fontFamily = GoogleSans,
                                    fontWeight = if (label == "Receipt no." || label == "Campaign") FontWeight.Bold else FontWeight.Normal,
                                    fontSize = 13.sp,
                                    color = if (label == "Receipt no." || label == "Campaign") KindlyInk else Color.Black,
                                    textAlign = TextAlign.End,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(KindlyMist)
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Amount attempted",
                                fontFamily = GoogleSans,
                                fontSize = 12.sp,
                                color = KindlySage
                            )
                            Text(
                                text = "RM 50.00",
                                fontFamily = GoogleSans,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = KindlyInk
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color.Red.copy(alpha = 0.15f))
                                .padding(horizontal = 10.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = "FAILED",
                                fontFamily = GoogleSans,
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp,
                                color = Color.Red
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Sample data — the real state arrives with the gateway callback.",
                            fontFamily = GoogleSans,
                            fontSize = 11.sp,
                            color = KindlySage
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { vm.navigateTo(Screen.Donate) },
                colors = ButtonDefaults.buttonColors(containerColor = KindlyTeal),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .height(48.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = "TRY AGAIN",
                    fontFamily = GoogleSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(
                onClick = { vm.navigateTo(Screen.Home) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "BACK TO HOME",
                    fontFamily = GoogleSans,
                    color = KindlyInk,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Demo control preview button
        TextButton(
            onClick = { showFailure = !showFailure }
        ) {
            Text(
                text = if (showFailure) "UI PREVIEW: SHOW SUCCESS STATE" else "UI PREVIEW: SHOW FAILURE STATE",
                fontSize = 11.sp,
                fontFamily = GoogleSans,
                color = KindlySage
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}
