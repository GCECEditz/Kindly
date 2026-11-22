package com.example.screens

import android.widget.Toast
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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.MainViewModel
import com.example.Screen
import com.example.ui.theme.*

@Composable
fun ProposalsScreen(
    vm: MainViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var approvalProposal by remember { mutableStateOf<MockProposal?>(null) }
    var approvalDecision by remember { mutableStateOf<String?>(null) }
    var noteText by remember { mutableStateOf("") }

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
                text = "PROPOSALS",
                fontFamily = GoogleSans,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = KindlyInk
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Proposals List
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(mockProposals) { proposal ->
                Card(
                    onClick = {
                        editorDraft = proposal
                        vm.navigateTo(Screen.ProposalEditor)
                    },
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
                                    text = proposal.ngoInitials,
                                    fontFamily = GoogleSans,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = KindlyCoral
                                )
                            }
                            Spacer(modifier = Modifier.width(15.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = proposal.title,
                                    fontFamily = GoogleSans,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = KindlyInk,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = proposal.ngoName,
                                    fontFamily = GoogleSans,
                                    fontSize = 12.sp,
                                    color = KindlySage
                                )
                                Text(
                                    text = "Updated " + proposal.updated,
                                    fontFamily = GoogleSans,
                                    fontSize = 11.sp,
                                    color = KindlySage
                                )
                            }

                            val chipColor = proposalStatusColor(proposal.status)
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(chipColor.copy(alpha = 0.15f))
                                    .padding(horizontal = 10.dp, vertical = 3.dp)
                            ) {
                                Text(
                                    text = proposal.status,
                                    fontFamily = GoogleSans,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp,
                                    color = chipColor
                                )
                            }
                        }

                        if (proposal.docsLink.isNotBlank()) {
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = proposal.docsLink,
                                fontFamily = GoogleSans,
                                fontSize = 12.sp,
                                color = KindlyTeal,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = proposal.content,
                            fontFamily = GoogleSans,
                            fontSize = 13.sp,
                            color = Color.Black,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis
                        )

                        if (proposal.status == "SENT") {
                            Spacer(modifier = Modifier.height(8.dp))
                            TextButton(
                                onClick = {
                                    approvalProposal = proposal
                                    approvalDecision = null
                                    noteText = ""
                                },
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Text(
                                    text = "RECORD NGO DECISION",
                                    fontFamily = GoogleSans,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = KindlyInk
                                )
                            }
                        }
                    }
                }
            }
        }

        // + NEW PROPOSAL Button
        Button(
            onClick = {
                editorDraft = null
                vm.navigateTo(Screen.SelectNgo)
            },
            colors = ButtonDefaults.buttonColors(containerColor = KindlyTeal),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 15.dp)
                .height(54.dp)
        ) {
            Text(
                text = "+ NEW PROPOSAL",
                fontFamily = GoogleSans,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.White
            )
        }
    }

    // Record-approval dialog (TODO(1.5 backend): write status to D1 + addFeedNotification)
    if (approvalProposal != null) {
        AlertDialog(
            onDismissRequest = { approvalProposal = null },
            title = {
                Text(
                    text = "RECORD NGO DECISION",
                    fontFamily = GoogleSans,
                    fontWeight = FontWeight.Bold,
                    color = KindlyInk
                )
            },
            text = {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        val isApproved = (approvalDecision == "APPROVED")
                        if (isApproved) {
                            Button(
                                onClick = { approvalDecision = "APPROVED" },
                                colors = ButtonDefaults.buttonColors(containerColor = KindlyTeal),
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text("APPROVED", fontFamily = GoogleSans, fontWeight = FontWeight.Bold, color = Color.White)
                            }
                        } else {
                            OutlinedButton(
                                onClick = { approvalDecision = "APPROVED" },
                                border = BorderStroke(1.dp, KindlyMist),
                                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White),
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text("APPROVED", fontFamily = GoogleSans, fontWeight = FontWeight.Bold, color = KindlyInk)
                            }
                        }

                        val isRejected = (approvalDecision == "REJECTED")
                        if (isRejected) {
                            Button(
                                onClick = { approvalDecision = "REJECTED" },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text("REJECTED", fontFamily = GoogleSans, fontWeight = FontWeight.Bold, color = Color.White)
                            }
                        } else {
                            OutlinedButton(
                                onClick = { approvalDecision = "REJECTED" },
                                border = BorderStroke(1.dp, KindlyMist),
                                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White),
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text("REJECTED", fontFamily = GoogleSans, fontWeight = FontWeight.Bold, color = KindlyInk)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = noteText,
                        onValueChange = { noteText = it },
                        label = { Text("Optional note (e.g. reason, follow-up date)", fontFamily = GoogleSans, color = KindlySage) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = KindlyInk,
                            unfocusedBorderColor = KindlyMist,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        Toast.makeText(context, "Decision recorded — Supabase D1 wiring comes later", Toast.LENGTH_SHORT).show()
                        approvalProposal = null
                        approvalDecision = null
                    },
                    enabled = approvalDecision != null,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = KindlyTeal,
                        disabledContainerColor = KindlyMist.copy(alpha = 0.5f)
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("CONFIRM", fontFamily = GoogleSans, fontWeight = FontWeight.Bold, color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { approvalProposal = null }) {
                    Text("CANCEL", fontFamily = GoogleSans, color = KindlyInk)
                }
            }
        )
    }
}

private fun proposalStatusColor(status: String): Color = when (status) {
    "APPROVED" -> KindlyTeal
    "SENT" -> KindlyCoral
    "REJECTED" -> Color.Red
    else -> KindlySage
}
