package com.example.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.MainViewModel
import com.example.Screen
import com.example.ui.theme.KindlyBackground
import com.example.ui.theme.KindlyInk
import com.example.ui.theme.KindlyTeal

@Composable
fun SignInUpScreen(
    vm: MainViewModel,
    onGoogleSignInClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(KindlyBackground)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        KindlyLogo(size = 100)

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "WELCOME TO KINDLY",
            fontFamily = GoogleSans,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = KindlyInk,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(40.dp))

        // LOG IN WITH GOOGLE Button
        Button(
            onClick = {
                vm.authError = null
                onGoogleSignInClick()
            },
            colors = ButtonDefaults.buttonColors(containerColor = KindlyTeal),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Mail,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "LOG IN WITH GOOGLE",
                    fontFamily = GoogleSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }

        vm.authError?.let { error ->
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = error,
                color = Color.Red,
                fontSize = 12.sp,
                fontFamily = GoogleSans,
                maxLines = 2,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Open to everyone in Malaysia",
            color = KindlyInk.copy(alpha = 0.6f),
            fontSize = 11.sp,
            fontFamily = GoogleSans,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        TextButton(
            onClick = { vm.navigateTo(Screen.Register) }
        ) {
            Text(
                text = "NEW TO KINDLY? CREATE ACCOUNT",
                color = KindlyInk,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                fontFamily = GoogleSans
            )
        }
    }
}
