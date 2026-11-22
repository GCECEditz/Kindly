package com.example.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.MainViewModel
import com.example.Screen
import com.example.ui.theme.*
import java.util.Locale

@Composable
fun DonateScreen(
    vm: MainViewModel,
    modifier: Modifier = Modifier
) {
    var amountText by remember { mutableStateOf("") }
    var donorName by remember { mutableStateOf("Kindly User") }
    var message by remember { mutableStateOf("") }
    var selectedCampaign by remember { mutableStateOf(mockCampaigns.first()) }
    var dropdownExpanded by remember { mutableStateOf(false) }

    val amount = amountText.toDoubleOrNull() ?: 0.0

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(KindlyBackground)
            .verticalScroll(rememberScrollState())
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
                text = "MAKE A DONATION",
                fontFamily = GoogleSans,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = KindlyInk
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // CHOOSE A CAMPAIGN section
        Text(
            text = "CHOOSE A CAMPAIGN",
            fontFamily = GoogleSans,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = KindlyInk,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            OutlinedButton(
                onClick = { dropdownExpanded = true },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, KindlyInk),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White)
            ) {
                Text(
                    text = selectedCampaign.title,
                    fontFamily = GoogleSans,
                    color = KindlyInk,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Start
                )
                Text(
                    text = "▼",
                    color = KindlyInk,
                    fontSize = 12.sp
                )
            }

            DropdownMenu(
                expanded = dropdownExpanded,
                onDismissRequest = { dropdownExpanded = false },
                modifier = Modifier.background(Color.White)
            ) {
                mockCampaigns.forEach { campaign ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = campaign.title,
                                fontFamily = GoogleSans,
                                color = KindlyInk,
                                fontWeight = if (campaign.id == selectedCampaign.id) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        onClick = {
                            selectedCampaign = campaign
                            dropdownExpanded = false
                        }
                    )
                }
            }
        }

        Text(
            text = "by ${selectedCampaign.ngoName}",
            fontFamily = GoogleSans,
            fontSize = 12.sp,
            color = KindlySage,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 4.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // DONATION AMOUNT (RM) section
        Text(
            text = "DONATION AMOUNT (RM)",
            fontFamily = GoogleSans,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = KindlyInk,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))

        val quickAmounts = listOf("10.00" to "RM 10", "25.00" to "RM 25", "50.00" to "RM 50", "100.00" to "RM 100")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            quickAmounts.forEach { (valStr, label) ->
                val isSelected = (amountText == valStr)
                if (isSelected) {
                    Button(
                        onClick = { amountText = valStr },
                        colors = ButtonDefaults.buttonColors(containerColor = KindlyTeal),
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = label,
                            fontFamily = GoogleSans,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = Color.White
                        )
                    }
                } else {
                    OutlinedButton(
                        onClick = { amountText = valStr },
                        border = BorderStroke(1.dp, KindlyMist),
                        colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White),
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = label,
                            fontFamily = GoogleSans,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = KindlyInk
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = amountText,
            onValueChange = { newValue ->
                if (newValue.all { it.isDigit() || it == '.' }) {
                    amountText = newValue
                }
            },
            label = {
                Text(
                    text = "Enter a custom amount (RM)",
                    fontFamily = GoogleSans,
                    color = KindlySage
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = KindlyInk,
                unfocusedBorderColor = KindlyMist,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // YOUR DETAILS section
        Text(
            text = "YOUR DETAILS",
            fontFamily = GoogleSans,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = KindlyInk,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = donorName,
            onValueChange = { donorName = it },
            label = {
                Text(
                    text = "Display name (optional)",
                    fontFamily = GoogleSans,
                    color = KindlySage
                )
            },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = KindlyInk,
                unfocusedBorderColor = KindlyMist,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            shape = RoundedCornerShape(12.dp)
        )

        Text(
            text = "Leave blank to donate anonymously.",
            fontFamily = GoogleSans,
            fontSize = 12.sp,
            color = KindlySage,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 4.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // MESSAGE TO THE NGO (OPTIONAL) section
        Text(
            text = "MESSAGE TO THE NGO (OPTIONAL)",
            fontFamily = GoogleSans,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = KindlyInk,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = message,
            onValueChange = { message = it },
            label = {
                Text(
                    text = "Write an encouraging message...",
                    fontFamily = GoogleSans,
                    color = KindlySage
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = KindlyInk,
                unfocusedBorderColor = KindlyMist,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // DONATION SUMMARY section
        Text(
            text = "DONATION SUMMARY",
            fontFamily = GoogleSans,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = KindlyInk,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))

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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Campaign",
                            fontFamily = GoogleSans,
                            fontSize = 12.sp,
                            color = KindlySage
                        )
                        Text(
                            text = selectedCampaign.title,
                            fontFamily = GoogleSans,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = KindlyInk,
                            textAlign = TextAlign.End,
                            modifier = Modifier.weight(1.5f)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "You donate",
                            fontFamily = GoogleSans,
                            fontSize = 12.sp,
                            color = KindlySage
                        )
                        Text(
                            text = "RM ${String.format(Locale.US, "%.2f", amount)}",
                            fontFamily = GoogleSans,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = KindlyInk
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "NGO receives (95%)",
                            fontFamily = GoogleSans,
                            fontSize = 12.sp,
                            color = KindlySage
                        )
                        Text(
                            text = "RM ${String.format(Locale.US, "%.2f", amount * 0.95)}",
                            fontFamily = GoogleSans,
                            fontSize = 13.sp,
                            color = Color.Black
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Platform fee (5%, borne by the NGO)",
                            fontFamily = GoogleSans,
                            fontSize = 12.sp,
                            color = KindlySage
                        )
                        Text(
                            text = "RM ${String.format(Locale.US, "%.2f", amount * 0.05)}",
                            fontFamily = GoogleSans,
                            fontSize = 13.sp,
                            color = Color.Black
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "You pay exactly RM ${String.format(Locale.US, "%.2f", amount)} — Kindly never adds charges for donors.",
                        fontFamily = GoogleSans,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = KindlyTeal
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { vm.navigateTo(Screen.PaymentResult) },
            enabled = amount > 0.0,
            colors = ButtonDefaults.buttonColors(
                containerColor = KindlyTeal,
                disabledContainerColor = KindlyMist.copy(alpha = 0.5f)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .height(48.dp),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(
                text = "PROCEED TO PAYMENT",
                fontFamily = GoogleSans,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = Color.White
            )
        }

        Text(
            text = "Payment gateway integration comes later — this button previews the receipt screen for now.",
            fontFamily = GoogleSans,
            fontSize = 12.sp,
            color = KindlySage,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))
    }
}
