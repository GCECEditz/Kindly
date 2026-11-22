package com.example.screens

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.example.MainViewModel
import com.example.Screen
import com.example.ui.theme.KindlyBackground
import com.example.ui.theme.KindlyCoral
import com.example.ui.theme.KindlyInk
import com.example.ui.theme.KindlyMist
import com.example.ui.theme.KindlyTeal

@Composable
fun SelectNgoScreen(
    vm: MainViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current // needed to display toasts and start intents
    var searchQuery by remember { mutableStateOf("") } // holds current search text
    val filteredNgos = vm.ngos.filter { it.name.contains(searchQuery, ignoreCase = true) }
    // case-insensitive NGO list filter

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
            IconButton(
                onClick = { vm.navigateBack() }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = KindlyInk
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "SELECT AN NGO",
                fontFamily = GoogleSans,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = KindlyInk
            )
        }

        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search NGO...", color = KindlyMist) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search",
                    tint = KindlyInk
                )
            },
            shape = RoundedCornerShape(30.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = KindlyInk,
                unfocusedBorderColor = KindlyMist,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        // NGO List
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 20.dp)
        ) {
            items(filteredNgos) { ngo ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Profile Circle Placeholder
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(50.dp)
                            .border(1.dp, KindlyMist, CircleShape)
                            .background(Color.White, CircleShape)
                    ) {
                        Text(
                            text = ngo.initials,
                            fontFamily = GoogleSans,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = KindlyCoral
                        )
                    }

                    Spacer(modifier = Modifier.width(15.dp))

                    // NGO Details
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = ngo.name,
                            fontFamily = GoogleSans,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = KindlyInk,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis // ...
                        )
                        Text(
                            text = ngo.description,
                            fontFamily = GoogleSans,
                            fontSize = 12.sp,
                            color = KindlyInk.copy(alpha = 0.6f),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    // Clickable Dropdown trigger icon
                    Box {
                        IconButton(
                            onClick = {
                                vm.activeProposalNgoId = if (vm.activeProposalNgoId == ngo.id) null else ngo.id
                                // if opened close, else open
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.AddCircle,
                                contentDescription = "Options",
                                tint = KindlyMist,
                                modifier = Modifier.size(30.dp)
                            )
                        }

                        // Dropdown Menu
                        DropdownMenu(
                            expanded = vm.activeProposalNgoId == ngo.id,
                            onDismissRequest = { vm.activeProposalNgoId = null }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Visit website", fontFamily = GoogleSans, color = KindlyInk) },
                                onClick = {
                                    vm.activeProposalNgoId = null
                                    try {
                                        val intent = Intent(Intent.ACTION_VIEW, ngo.website.toUri()) // describes content of Intent
                                        context.startActivity(intent)
                                    } catch (e: Exception) {
                                        Toast.makeText(context, "No browser available", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Project proposal", fontFamily = GoogleSans, color = KindlyInk) },
                                onClick = {
                                    vm.activeProposalNgoId = null
                                    try {
                                        val intent = Intent(Intent.ACTION_VIEW, ngo.docTemplateUrl.toUri())
                                        context.startActivity(intent)
                                    } catch (e: Exception) {
                                        Toast.makeText(context, "No browser available", Toast.LENGTH_SHORT).show()
                                    }
                                    editorDraft = null
                                    vm.navigateTo(Screen.ProposalEditor)
                                }
                            )
                        }
                    }
                }
                HorizontalDivider(color = KindlyMist, thickness = 0.5.dp)
            }
        }

        // Primary Action Prepare Proposal Button at the bottom
        Button(
            onClick = {
                editorDraft = null
                vm.navigateTo(Screen.ProposalEditor)
            },
            colors = ButtonDefaults.buttonColors(containerColor = KindlyTeal),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 15.dp)
                .height(54.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = null,
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "PREPARE PROPOSAL",
                    fontFamily = GoogleSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }
    }
}
