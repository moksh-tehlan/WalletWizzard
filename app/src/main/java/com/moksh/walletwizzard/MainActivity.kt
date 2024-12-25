package com.moksh.walletwizzard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.google.firebase.Firebase
import com.google.firebase.initialize
import com.moksh.domain.usecases.user.GetUser
import com.moksh.presentation.ui.navigation.WalletWizzardGraph
import com.moksh.walletwizzard.utils.ActivityRetainer
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var getUser: GetUser
    @Inject
    lateinit var activityRetainer: ActivityRetainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRetainer.setActivity(this)
        enableEdgeToEdge()
        setContent {
            WalletWizzardGraph(getUser)
        }
    }
}