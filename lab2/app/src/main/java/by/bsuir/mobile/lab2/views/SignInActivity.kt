package by.bsuir.mobile.lab2.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import by.bsuir.mobile.lab2.databinding.SignInActivityBinding
import by.bsuir.mobile.lab2.entities.SSD
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: SignInActivityBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var store: FirebaseFirestore
    private lateinit var alert: AlertDialog
    private var ssds: ArrayList<SSD> = ArrayList()
    private var email: String = ""
    private var password: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignInActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        if (firebaseAuth.currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
        }
        var builder = AlertDialog.Builder(this)
        builder.setPositiveButton("OK") { dialog, which ->

        }
        alert = builder.create()

        binding.login.setOnClickListener {
            email = binding.email.text.toString()
            password = binding.password.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                alert.setMessage("Empty credential(s),unable to authorize...")
                alert.show()
            } else {
                if (firebaseAuth.currentUser != null) {
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                        startActivity(Intent(this, MainActivity::class.java))
                    }.addOnFailureListener {
                        alert.setMessage("Oops... Error in authorization process.")
                        alert.show()
                    }
                }
            }
        }
        binding.toSignUp.setOnClickListener{
            startActivity(Intent(this,SignUpActivity::class.java))
        }
    }
}