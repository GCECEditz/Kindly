// TODO(account backend): update D3 and refresh ProfileScreen.
package com.example.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.MainViewModel
import com.example.ui.theme.*

@Composable
fun EditProfileScreen(
    vm: MainViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var name by remember { mutableStateOf(vm.loggedInDisplayName) }
    var role by remember { mutableStateOf("DONOR") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        // 1. Header — R1, title "EDIT PROFILE"
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
                text = "EDIT PROFILE",
                fontFamily = GoogleSans,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = KindlyInk
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 2. OutlinedTextField Display name (padding horizontal 20.dp)
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Display name", fontFamily = GoogleSans, color = KindlySage) },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = KindlyInk,
                unfocusedBorderColor = KindlyMist,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 3. Section label "ACCOUNT ROLE" (R7 but 14.sp) + chips (padding horizontal 20.dp)
        Text(
            text = "ACCOUNT ROLE",
            fontFamily = GoogleSans,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = KindlyInk,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            listOf("DONOR", "FUNDRAISER").forEach { choice ->
                val isSelected = (role == choice)
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(if (isSelected) KindlyCoral.copy(alpha = 0.1f) else Color.Transparent)
                        .border(BorderStroke(1.dp, if (isSelected) KindlyInk else KindlyMist), RoundedCornerShape(16.dp))
                        .clickable { role = choice }
                        .padding(horizontal = 14.dp, vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = choice,
                        fontFamily = GoogleSans,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = if (isSelected) KindlyInk else KindlySage
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 4. SAVE CHANGES Button
        Button(
            onClick = {
                Toast.makeText(context, "Profile update is UI-only — Supabase D3 wiring comes later", Toast.LENGTH_SHORT).show()
                vm.navigateBack()
            },
            enabled = name.isNotBlank(),
            colors = ButtonDefaults.buttonColors(
                containerColor = KindlyTeal,
                disabledContainerColor = KindlyMist.copy(alpha = 0.5f)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .height(52.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "SAVE CHANGES",
                fontFamily = GoogleSans,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.White
            )
        }
    }
}
