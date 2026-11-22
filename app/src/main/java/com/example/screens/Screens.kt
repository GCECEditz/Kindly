package com.example.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.Screen
import com.example.ui.theme.KindlyCoral
import com.example.ui.theme.KindlyInk
import com.example.ui.theme.KindlyMist

// --- Custom Font Definition (Google Sans Style / Sans-Serif Default) ---
val GoogleSans = FontFamily.SansSerif

// --- Bottom Navigation Component ---
@Composable
fun KindlyBottomBar(
    currentScreen: Screen,
    onNavigate: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        tonalElevation = 8.dp,
        color = Color.White,
        border = BorderStroke(1.dp, KindlyMist.copy(alpha = 0.3f)),
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
        ) {
            // Profile Item
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .clickable { onNavigate(Screen.Profile) }
                    .padding(vertical = 4.dp)
            ) {
                val activeBg = if (currentScreen == Screen.Profile) KindlyCoral.copy(alpha = 0.1f) else Color.Transparent
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(width = 64.dp, height = 32.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(activeBg)
                ) {
                    Icon(
                        imageVector = Icons.Filled.AccountCircle,
                        contentDescription = "PROFILE",
                        tint = if (currentScreen == Screen.Profile) KindlyInk else KindlyMist,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "PROFILE",
                    fontFamily = GoogleSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp,
                    letterSpacing = 1.5.sp,
                    color = if (currentScreen == Screen.Profile) KindlyInk else KindlyMist
                )
            }

            // Home Center Button (to quickly return to main dashboard)
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(KindlyInk)
                    .clickable { onNavigate(Screen.Home) }
            ) {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "HOME",
                    tint = Color.White,
                    modifier = Modifier.size(26.dp)
                )
            }

            // AI Assistant Item
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .clickable { onNavigate(Screen.AskAi) }
                    .padding(vertical = 4.dp)
            ) {
                val activeBg = if (currentScreen == Screen.AskAi) KindlyCoral.copy(alpha = 0.1f) else Color.Transparent
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(width = 64.dp, height = 32.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(activeBg)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ChatBubble,
                        contentDescription = "AI ASSISTANT",
                        tint = if (currentScreen == Screen.AskAi) KindlyInk else KindlyMist,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "AI ASSISTANT",
                    fontFamily = GoogleSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp,
                    letterSpacing = 1.5.sp,
                    color = if (currentScreen == Screen.AskAi) KindlyInk else KindlyMist
                )
            }
        }
    }
}

// --- Dynamic Logo Composable ---
@Composable
fun KindlyLogo(modifier: Modifier = Modifier, size: Int = 80) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(size.dp)
    ) {
        Image(
            painter = painterResource(
                id = R.drawable.kindly_logo
            ),
            contentDescription = "Kindly Logo",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(180.dp)
        )
    }
}
