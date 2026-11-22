// TODO(account backend): create user in Supabase D3 with chosen role.
package com.example.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.MainViewModel
import com.example.Screen
import com.example.ui.theme.*

@Composable
fun RegisterScreen(
    vm: MainViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("DONOR") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 1. Header — STYLE RULES R1, title "CREATE ACCOUNT"
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 15.dp),
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
                text = "CREATE ACCOUNT",
                fontFamily = GoogleSans,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = KindlyInk
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 2. OutlinedTextField Full name
        OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it },
            label = { Text("Full name", fontFamily = GoogleSans, color = KindlySage) },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = KindlyInk,
                unfocusedBorderColor = KindlyMist,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 3. OutlinedTextField Email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email", fontFamily = GoogleSans, color = KindlySage) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = KindlyInk,
                unfocusedBorderColor = KindlyMist,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 4. OutlinedTextField Password
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password", fontFamily = GoogleSans, color = KindlySage) },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = KindlyInk,
                unfocusedBorderColor = KindlyMist,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 5. Section label "I AM JOINING AS"
        Text(
            text = "I AM JOINING AS",
            fontFamily = GoogleSans,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = KindlyInk,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
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

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Fundraisers prepare proposals and run campaigns; donors give to any campaign.",
            fontFamily = GoogleSans,
            fontSize = 11.sp,
            color = KindlySage,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 6. REGISTER Button
        Button(
            onClick = {
                Toast.makeText(context, "Registration is UI-only — Supabase D3 wiring comes later", Toast.LENGTH_SHORT).show()
                vm.navigateTo(Screen.Home)
            },
            enabled = fullName.isNotBlank() && email.isNotBlank() && password.isNotBlank(),
            colors = ButtonDefaults.buttonColors(
                containerColor = KindlyTeal,
                disabledContainerColor = KindlyMist.copy(alpha = 0.5f)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "REGISTER",
                fontFamily = GoogleSans,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 7. "or" and Continue with Google instead
        Text(
            text = "or",
            fontFamily = GoogleSans,
            fontSize = 12.sp,
            color = KindlySage,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        TextButton(
            onClick = { vm.navigateBack() }
        ) {
            Text(
                text = "Continue with Google instead",
                color = KindlyInk,
                fontWeight = FontWeight.Bold,
                fontFamily = GoogleSans
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 8. Caption under everything
        Text(
            text = "Open to everyone in Malaysia — donors and fundraisers.",
            fontFamily = GoogleSans,
            fontSize = 11.sp,
            color = KindlySage,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        )
    }
}
