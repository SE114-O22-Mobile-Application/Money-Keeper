package com.uit.moneykeeper.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HomeScreen(
    navHostController: NavHostController
) {
    FirebaseAuth.getInstance().currentUser?.let {
        Column {
            Text(text = "Welcome ${it.email}")
            Button(onClick = {
                FirebaseAuth.getInstance().signOut()
                navHostController.navigate("sign_in")
            }) {
                Text(text = "Sign Out")
            }
        }
    }
}