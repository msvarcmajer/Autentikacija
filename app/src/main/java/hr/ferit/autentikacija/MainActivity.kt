package hr.ferit.autentikacija

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*

import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val currentUser = FirebaseAuth.getInstance().currentUser

                LoginRegisterScreen()


        }
    }
}

@Composable
fun LoginRegisterScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { signIn(context, email, password) }) {
            Text("Login")

        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { register(context, email, password) }) {
            Text("Register")
        }
    }
}

private fun signIn(context: Context, email: String, password: String) {
    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Prijavljeno uspješno
                Toast.makeText(context, "Logged in successfully", Toast.LENGTH_SHORT).show()

            } else {
                // Prijavljivanje neuspješno
                Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show()
            }
        }
}

private fun register(context: Context, email: String, password: String) {
    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Registracija uspješna
                Toast.makeText(context, "Registered successfully", Toast.LENGTH_SHORT).show()
            } else {
                // Registracija neuspješna
                Toast.makeText(context, "Registration failed", Toast.LENGTH_SHORT).show()
            }
        }
}

