package com.silcamtech.alan.medisustancia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.silcamtech.alan.medisustancia.ui.screens.login.LoginScreen
import com.silcamtech.alan.medisustancia.ui.screens.recovery.RecoveryPasswordScreen
import com.silcamtech.alan.medisustancia.ui.screens.register.RegisterScreen
import com.silcamtech.alan.medisustancia.ui.screens.splash.SplashScreen
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MaterialTheme {
                var showSplash by remember { mutableStateOf(true) }
                var currentScreen by remember { mutableStateOf("login") }

                LaunchedEffect(Unit) {
                    delay(2000)
                    showSplash = false
                }

                if (showSplash) {
                    SplashScreen()
                } else {
                    when (currentScreen) {
                        "register" -> {
                            RegisterScreen(
                                onBack = {
                                    currentScreen = "login"
                                },
                                onLoginClick = {
                                    currentScreen = "login"
                                }
                            )
                        }

                        "recovery" -> {
                            RecoveryPasswordScreen(
                                onBackToLogin = {
                                    currentScreen = "login"
                                }
                            )
                        }

                        else -> {
                            LoginScreen(
                                onRegister = {
                                    currentScreen = "register"
                                },
                                onForgotPassword = {
                                    currentScreen = "recovery"
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}