package com.uit.moneykeeper.sign_in


import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.navigation.NavHostController
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.uit.moneykeeper.R
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.util.UUID

@Composable
fun SignInScreen(
    navHostController: NavHostController
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val signIn: () -> Unit = {
        val credentialManager = CredentialManager.create(context)

        val rawNonce = UUID.randomUUID().toString()
        val bytes = rawNonce.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        val hashedNonce = digest.fold("") { str, it -> str + "%02x".format(it) }

        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(context.getString(R.string.web_client_id))
            .setNonce(hashedNonce)
            .build()

        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        coroutineScope.launch {
            try {
                val result = credentialManager.getCredential(request = request, context = context)
                val credential = result.credential

                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                val firebaseCredential =
                    GoogleAuthProvider.getCredential(googleIdTokenCredential.idToken, null)
//                val auth = Firebase.auth
//                auth.signInWithCredential(firebaseCredential)
                FirebaseAuth.getInstance().signInWithCredential(firebaseCredential)

                val idToken = googleIdTokenCredential.idToken

                Log.i("Id token", idToken)

                Toast.makeText(context, "You are signed in!", Toast.LENGTH_LONG).show()
                navHostController.navigate("home")
            } catch (e: GetCredentialException) {
                Log.e("Error", e.message.toString())
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            } catch (e: GoogleIdTokenParsingException) {
                Log.e("Error", e.message.toString())
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.mobile_login_amico),
                contentDescription = "ill"
            )
            OutlinedButton(onClick = signIn) {
                Image(
                    modifier = Modifier.width(24.dp),
                    imageVector = ImageVector.vectorResource(R.drawable._123025_logo_google_g_icon),
                    contentDescription = "ill"
                )
                Text(text = "Đăng nhập bằng Google")
            }
        }
    }
}
