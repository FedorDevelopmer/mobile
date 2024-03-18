package by.bsuir.mobile.lab2.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import by.bsuir.mobile.lab2.databinding.SignUpActivityBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore

class SignUpActivity : AppCompatActivity(){
    private lateinit var binding: SignUpActivityBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var store: FirebaseFirestore
    private lateinit var alert: AlertDialog
    private var email: String = ""
    private var password: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignUpActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        store = FirebaseFirestore.getInstance()
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
                alert.setMessage("Empty credential(s),unable to sign up...")
                alert.show()
            } else {
                if (firebaseAuth.currentUser != null) {
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                        startActivity(Intent(this, MainActivity::class.java))
                        val user = hashMapOf(
                            "name" to "",
                            "lastname" to "",
                            "sex" to "",
                            "city" to "",
                            "country" to "",
                            "address" to "",
                            "mail_index" to "",
                            "phone" to "",
                            "age" to 0

                        )
                        store.collection("users").document(email).set(user)

                    }.addOnFailureListener {
                        if(it is FirebaseAuthWeakPasswordException){
                            alert.setMessage("The password is too weak(use at least 8 characters,better with numbers and special symbols)")
                        }else if(it is FirebaseAuthInvalidCredentialsException){
                            alert.setMessage("The credential(s) are invalid for sign in. Check you email and password format.")
                        }else if(it is FirebaseAuthUserCollisionException){
                            alert.setMessage("Oops... The email is already obtained by another user.")
                        }else {
                            alert.setMessage("Oops... Error in registration process.")
                        }
                        alert.show()
                    }
                }
            }
        }
        binding.toSignIn.setOnClickListener{
            startActivity(Intent(this,SignInActivity::class.java))
        }
    }
}