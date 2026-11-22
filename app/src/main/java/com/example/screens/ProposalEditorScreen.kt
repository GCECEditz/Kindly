package com.example.screens

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.example.MainViewModel
import com.example.ui.theme.*

@Composable
fun ProposalEditorScreen(
    vm: MainViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val editing = editorDraft

    var ngoName by remember { mutableStateOf(editing?.ngoName ?: mockProposalNgos.first()) }
    var title by remember { mutableStateOf(editing?.title ?: "") }
    var content by remember { mutableStateOf(editing?.content ?: "") }
    var docsLink by remember { mutableStateOf(editing?.docsLink ?: "") }
    var aiSuggestion by remember { mutableStateOf<String?>(null) }
    var dropdownExpanded by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    // TODO(1.x backend): persist to Supabase D1.

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
                text = "PROPOSAL EDITOR",
                fontFamily = GoogleSans,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = KindlyInk
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // NGO Selection / Display section
        if (editing != null) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Text(
                    text = "NGO",
                    fontFamily = GoogleSans,
                    fontSize = 12.sp,
                    color = KindlySage
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = editing.ngoName,
                    fontFamily = GoogleSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = KindlyInk
                )
            }
        } else {
            Text(
                text = "CHOOSE AN NGO",
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
                        text = ngoName,
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
                    mockProposalNgos.forEach { ngoItem ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = ngoItem,
                                    fontFamily = GoogleSans,
                                    color = KindlyInk,
                                    fontWeight = if (ngoItem == ngoName) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            onClick = {
                                ngoName = ngoItem
                                dropdownExpanded = false
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "The full proposal document lives in Google Docs. Open your NGO's template from the NGO picker, edit it there, then paste the share link below.",
            fontFamily = GoogleSans,
            fontSize = 12.sp,
            color = KindlySage,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 4.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Form Fields
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Proposal Title", fontFamily = GoogleSans, color = KindlySage) },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = KindlyInk,
                    unfocusedBorderColor = KindlyMist,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("Proposal summary (this becomes the email body)", fontFamily = GoogleSans, color = KindlySage) },
                minLines = 4,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = KindlyInk,
                    unfocusedBorderColor = KindlyMist,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = docsLink,
                onValueChange = { docsLink = it },
                label = { Text("Google Docs Link", fontFamily = GoogleSans, color = KindlySage) },
                placeholder = { Text("PASTE YOUR EDITED GOOGLE DOCS LINK HERE", color = KindlyMist) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri),
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

        Spacer(modifier = Modifier.height(16.dp))

        // AI drafting assist (TODO(1.3 backend): call GeminiService with the NGO name + title.)
        TextButton(
            onClick = {
                aiSuggestion = "Suggested draft: Kindly proposes a " + title.ifBlank { "new campaign" } + " in partnership with " + ngoName + ". The campaign runs for four weeks, collects RM donations in-app, and the NGO receives 95% of confirmed funds. Kindly provides posters, progress tracking and a closing report."
            },
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.ChatBubble,
                contentDescription = null,
                tint = KindlyInk,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "ASK AI FOR A DRAFT",
                fontFamily = GoogleSans,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = KindlyInk
            )
        }

        if (aiSuggestion != null) {
            Spacer(modifier = Modifier.height(8.dp))
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
                            text = "AI SUGGESTION (ADVISORY ONLY)",
                            fontFamily = GoogleSans,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            color = KindlySage
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = aiSuggestion!!,
                            fontFamily = GoogleSans,
                            fontSize = 13.sp,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Button(
                                onClick = {
                                    content = aiSuggestion!!
                                    aiSuggestion = null
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = KindlyTeal),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text("ADOPT INTO PROPOSAL", fontFamily = GoogleSans, fontWeight = FontWeight.Bold, color = Color.White)
                            }
                            TextButton(onClick = { aiSuggestion = null }) {
                                Text("DISCARD", fontFamily = GoogleSans, color = KindlyInk, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Bottom Action Buttons
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 15.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(
                onClick = {
                    Toast.makeText(context, "Draft saved — Supabase D1 wiring comes later", Toast.LENGTH_SHORT).show()
                    editorDraft = null
                    vm.navigateBack()
                },
                enabled = title.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = KindlyTeal,
                    disabledContainerColor = KindlyMist.copy(alpha = 0.5f)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = "SAVE DRAFT",
                    fontFamily = GoogleSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = Color.White
                )
            }

            Button(
                onClick = {
                    val recipient = vm.ngos.find { it.name == ngoName }?.email ?: ""
                    val body = "Dear " + ngoName + ",\n\nI am writing to you on behalf of Kindly, a donation platform connecting NGOs with donors across Malaysia. We are interested in collaborating with your esteemed organisation.\n\n---\nPROPOSAL: " + title + "\n\n" + content + "\n---\n\n" + (if (docsLink.isNotBlank()) "The full proposal document is available at:\n" + docsLink + "\n" else "") + "Thank you for your time and consideration.\n\nBest regards,\nKindly Representative"
                    val subject = "Project Proposal - " + title
                    try {
                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            data = "mailto:".toUri()
                            putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
                            putExtra(Intent.EXTRA_SUBJECT, subject)
                            putExtra(Intent.EXTRA_TEXT, body)
                        }
                        context.startActivity(Intent.createChooser(intent, "Send Email"))
                    } catch (e: Exception) {
                        Toast.makeText(context, "No email app available", Toast.LENGTH_SHORT).show()
                    }
                },
                enabled = title.isNotBlank() && content.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = KindlyInk,
                    disabledContainerColor = KindlyMist.copy(alpha = 0.5f)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = "MARK AS SENT",
                    fontFamily = GoogleSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = Color.White
                )
            }

            if (editing?.status == "DRAFT") {
                TextButton(
                    onClick = { showDeleteDialog = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "DELETE DRAFT",
                        fontFamily = GoogleSans,
                        fontWeight = FontWeight.Bold,
                        color = Color.Red
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = {
                Text(
                    text = "DELETE DRAFT",
                    fontFamily = GoogleSans,
                    fontWeight = FontWeight.Bold,
                    color = KindlyInk
                )
            },
            text = {
                Text(
                    text = "This draft will be removed. (Backend wiring comes later.)",
                    fontFamily = GoogleSans
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        Toast.makeText(context, "Draft deleted — Supabase D1 wiring comes later", Toast.LENGTH_SHORT).show()
                        editorDraft = null
                        vm.navigateBack()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("DELETE", fontFamily = GoogleSans, fontWeight = FontWeight.Bold, color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("CANCEL", fontFamily = GoogleSans, color = KindlyInk)
                }
            }
        )
    }
}
