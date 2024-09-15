package udb.edu.desafio2_dsm

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import udb.edu.desafio2_dsm.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        // Asignar los textos desde strings.xml
        binding.etEmail.hint = getString(R.string.login_email_hint)
        binding.etPassword.hint = getString(R.string.login_password_hint)
        binding.btnLogin.text = getString(R.string.login_button_text)
        binding.btnRegister.text = getString(R.string.register_button_text)

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Login exitoso
                        val intent = Intent(this, MedicamentosActivity::class.java)
                        startActivity(intent)
                        finish()
                        Toast.makeText(this, getString(R.string.login_success), Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, getString(R.string.login_failed), Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, getString(R.string.login_fill_fields), Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnRegister.setOnClickListener {
            // Registra al usuario
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Registro exitoso
                        val intent = Intent(this, MedicamentosActivity::class.java)
                        startActivity(intent)
                        finish()
                        Toast.makeText(this, getString(R.string.register_success), Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, getString(R.string.register_failed), Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, getString(R.string.login_fill_fields), Toast.LENGTH_SHORT).show()
            }
        }
    }
}
