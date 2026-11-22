package com.example

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.enableEdgeToEdge
import android.accounts.AccountManager
import com.google.android.gms.common.AccountPicker
import androidx.compose.animation.Crossfade
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.services.drive.DriveScopes
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.screens.KindlyBottomBar
import com.example.screens.SignInUpScreen
import com.example.screens.HomeScreen
import com.example.screens.SelectNgoScreen
import com.example.screens.ProfileScreen
import com.example.screens.FeedScreen
import com.example.screens.SocialMediaScreen
import com.example.screens.AskAiScreen
import com.example.screens.BudgetScreen
import com.example.screens.QrCodeScreen
import com.example.screens.DonationScreen
import com.example.screens.DonateScreen
import com.example.screens.PaymentResultScreen
import com.example.screens.DonationHistoryScreen
import com.example.screens.ProposalsScreen
import com.example.screens.ProposalEditorScreen
import com.example.screens.RegisterScreen
import com.example.screens.EditProfileScreen
import com.example.ui.theme.KindlyTeal
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyApplicationTheme {
                val vm: MainViewModel = viewModel()
                val scope = rememberCoroutineScope()

                // Re-initialize credential if already logged in or when state changes
                LaunchedEffect(vm.isLoggedIn, vm.loggedInEmail) {
                    val email = vm.loggedInEmail.trim()
                    if (vm.isLoggedIn && email.isNotBlank()) {
                        Log.d("MainActivity", "Ensuring credentials for: [$email]")
                        val credential = GoogleAccountCredential.usingOAuth2(
                            this@MainActivity,
                            listOf(DriveScopes.DRIVE_METADATA_READONLY, "https://www.googleapis.com/auth/spreadsheets.readonly")
                        )
                        credential.selectedAccountName = email
                        vm.googleCredential = credential
                    }
                }

                // Registers a launcher for the AccountPicker result
                val accountPickerLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.StartActivityForResult()
                ) { result ->
                    if (result.resultCode == RESULT_OK) { // checks if user selected an account
                        // Retrieves the chosen email address
                        val accountName = result.data?.getStringExtra(AccountManager.KEY_ACCOUNT_NAME)
                        if (accountName != null) {
                            Log.d("MainActivity", "Account selected: $accountName")
                            vm.loggedInDisplayName = accountName.substringBefore("@") // sets the display name from the email prefix
                            vm.handleGoogleSignIn(accountName) // validate email and log user in
                            // Initialize Drive & Sheets credentials
                            val driveCredential = GoogleAccountCredential.usingOAuth2(
                                this@MainActivity,
                                listOf(DriveScopes.DRIVE_METADATA_READONLY, "https://www.googleapis.com/auth/spreadsheets.readonly")
                            )
                            driveCredential.selectedAccountName = accountName
                            vm.googleCredential = driveCredential
                        }
                    } else {
                        Log.w("MainActivity", "Account picker cancelled or failed")
                    }
                }

                // Defines the action to launch the AccountPicker intent
                val onGoogleSignInClick = {
                    // Builds an intent that shows a list of Google accounts on the device
                    val intent = AccountPicker.newChooseAccountIntent(
                        AccountPicker.AccountChooserOptions.Builder()
                            .setAllowableAccountsTypes(listOf("com.google"))
                            .build()
                    )
                    accountPickerLauncher.launch(intent)
                }

                // Overrides the back button to navigate within the app using the backstack
                BackHandler(enabled = vm.currentScreen != Screen.SignInUp) {
                    val handled = vm.navigateBack()
                    if (!handled) {
                        // If stack is at Home, let activity close naturally
                        finish()
                    }
                }

                // Bottom bar visibility conditions
                val showBottomBar = vm.isLoggedIn && vm.currentScreen != Screen.SignInUp

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (showBottomBar) {
                            KindlyBottomBar(
                                currentScreen = vm.currentScreen,
                                onNavigate = { screen -> vm.navigateTo(screen) },
                            )
                        }
                    }
                ) { innerPadding ->
                    Crossfade(
                        targetState = vm.currentScreen,
                        modifier = Modifier.padding(innerPadding),
                        label = "ScreenTransition"
                    ) { screen ->
                        when (screen) {
                            Screen.SignInUp -> SignInUpScreen(
                                vm = vm,
                                onGoogleSignInClick = { onGoogleSignInClick() }
                            )
                            Screen.Home -> HomeScreen(vm = vm)
                            Screen.SelectNgo -> SelectNgoScreen(vm = vm)
                            Screen.Profile -> ProfileScreen(vm = vm)
                            Screen.Feed -> FeedScreen(vm = vm)
                            Screen.SocialMedia -> SocialMediaScreen(vm = vm)
                            Screen.AskAi -> AskAiScreen(vm = vm)
                            Screen.Budget -> BudgetScreen(vm = vm)
                            Screen.QrCode -> QrCodeScreen(vm = vm)
                            Screen.Donation -> DonationScreen(vm = vm)
                            Screen.Donate -> DonateScreen(vm = vm)
                            Screen.PaymentResult -> PaymentResultScreen(vm = vm)
                            Screen.DonationHistory -> DonationHistoryScreen(vm = vm)
                            Screen.Proposals -> ProposalsScreen(vm = vm)
                            Screen.ProposalEditor -> ProposalEditorScreen(vm = vm)
                            Screen.Register -> RegisterScreen(vm = vm)
                            Screen.EditProfile -> EditProfileScreen(vm = vm)
                        }
                    }
                }
            }
        }
    }
}
