package com.example.vedamo

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.vedamo.ui.theme.VEDAMOTheme
import com.google.firebase.auth.FirebaseAuth

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val executor = ContextCompat.getMainExecutor(this)

        setContent {
            VEDAMOTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    var isLoggedIn by remember {
                        mutableStateOf(FirebaseAuth.getInstance().currentUser != null)
                    }
                    var isUnlocked by remember { mutableStateOf(false) }
                    var authError by remember { mutableStateOf<String?>(null) }

                    val canUseBiometrics = remember {
                        BiometricManager.from(this@MainActivity).canAuthenticate(
                            BiometricManager.Authenticators.BIOMETRIC_STRONG or
                                    BiometricManager.Authenticators.DEVICE_CREDENTIAL
                        ) == BiometricManager.BIOMETRIC_SUCCESS
                    }

                    fun launchBiometricPrompt() {
                        val prompt = BiometricPrompt(
                            this@MainActivity,
                            executor,
                            object : BiometricPrompt.AuthenticationCallback() {
                                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                                    super.onAuthenticationSucceeded(result)
                                    isUnlocked = true
                                    authError = null
                                }

                                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                                    super.onAuthenticationError(errorCode, errString)
                                    authError = errString.toString()
                                }

                                override fun onAuthenticationFailed() {
                                    super.onAuthenticationFailed()
                                    authError = "Fingerprint not recognized. Try again."
                                }
                            }
                        )

                        val promptInfo = BiometricPrompt.PromptInfo.Builder()
                            .setTitle("Unlock Vedamo")
                            .setSubtitle("Use your fingerprint to continue")
                            .setAllowedAuthenticators(
                                BiometricManager.Authenticators.BIOMETRIC_STRONG or
                                        BiometricManager.Authenticators.DEVICE_CREDENTIAL
                            )
                            .build()

                        prompt.authenticate(promptInfo)
                    }

                    when {
                        !isLoggedIn -> {
                            LoginScreen(onLoginSuccess = {
                                isLoggedIn = true
                                isUnlocked = true
                            })
                        }
                        !isUnlocked && canUseBiometrics -> {
                            LaunchedEffect(Unit) { launchBiometricPrompt() }
                            UnlockScreen(
                                errorMessage = authError,
                                onRetry = { launchBiometricPrompt() }
                            )
                        }
                        else -> {
                            HomeScreen()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun UnlockScreen(errorMessage: String?, onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Vedamo is locked", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Use your fingerprint to unlock", color = Color.Gray)
        Spacer(modifier = Modifier.height(24.dp))
        if (errorMessage != null) {
            Text(errorMessage, color = MaterialTheme.colorScheme.error)
            Spacer(modifier = Modifier.height(16.dp))
        }
        Button(onClick = onRetry) {
            Text("Try Again")
        }
    }
}