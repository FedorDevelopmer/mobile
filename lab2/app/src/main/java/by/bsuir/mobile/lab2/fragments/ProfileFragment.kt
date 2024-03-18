package by.bsuir.mobile.lab2.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import by.bsuir.mobile.lab2.R
import by.bsuir.mobile.lab2.views.SignInActivity
import by.bsuir.mobile.lab2.views.SignUpActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class ProfileFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var store: FirebaseFirestore
    private var userEmail:String = ""
    private lateinit var inputName: EditText
    private lateinit var inputLastname: EditText
    private lateinit var inputAge: EditText
    private lateinit var inputCity: EditText
    private lateinit var inputCountry: EditText
    private lateinit var inputSex: EditText
    private lateinit var inputPhone: EditText
    private lateinit var inputMail: EditText
    private lateinit var inputAddress: EditText
    private lateinit var inputEmail: EditText
    private lateinit var nameLabel: TextView


    override  fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        store = FirebaseFirestore.getInstance()
        if(auth.currentUser != null) {
            userEmail = auth.currentUser?.email.toString()
            store.collection("users").document(userEmail).get().addOnSuccessListener {
                nameLabel.setText(it["name"].toString().toCharArray(),0,it["name"].toString().length)
                inputAddress.setText(it["address"].toString().toCharArray(),0,it["address"].toString().length)
                inputName.setText(it["name"].toString().toCharArray(),0,it["name"].toString().length)
                inputLastname.setText(it["lastname"].toString().toCharArray(),0,it["lastname"].toString().length)
                inputSex.setText(it["sex"].toString().toCharArray(),0,it["sex"].toString().length)
                inputCity.setText(it["city"].toString().toCharArray(),0,it["city"].toString().length)
                inputCountry.setText(it["country"].toString().toCharArray(),0,it["country"].toString().length)
                inputMail.setText(it["mail_index"].toString().toCharArray(),0,it["mail_index"].toString().length)
                inputAge.setText(it["age"].toString().toCharArray(),0,it["age"].toString().length)
                inputPhone.setText(it["phone"].toString().toCharArray(),0,it["phone"].toString().length)
                inputEmail.setText(userEmail)
            }
        }else{
            startActivity(Intent(context, SignUpActivity::class.java))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.profile_fragment, container, false)
        var log_out: Button = view.findViewById(R.id.log_out)
        var delete: Button = view.findViewById(R.id.delete_account)
        var edit:Button = view.findViewById(R.id.edit_name)
        inputAge = view.findViewById(R.id.input_age)
        inputName = view.findViewById(R.id.input_name)
        inputLastname = view.findViewById(R.id.input_lastname)
        inputSex = view.findViewById(R.id.input_sex)
        inputCountry = view.findViewById(R.id.input_country)
        inputCity = view.findViewById(R.id.input_city)
        inputMail = view.findViewById(R.id.input_mail)
        inputAddress = view.findViewById(R.id.input_address)
        inputPhone = view.findViewById(R.id.input_phone)
        inputEmail = view.findViewById(R.id.input_email)
        nameLabel = view.findViewById(R.id.name_header)
        inputEmail.isEnabled = false
        changeEnable(false)
        log_out.setOnClickListener {
            auth.signOut()
            startActivity(Intent(context, SignInActivity::class.java))
        }
        delete.setOnClickListener {
            store.collection("users").document(userEmail).delete()
            auth.currentUser!!.delete()
            startActivity(Intent(context, SignUpActivity::class.java))
        }
        edit.setOnClickListener {
            if(!(inputAge.isEnabled)){
                changeEnable(true)
                edit.setBackgroundResource(R.drawable.round_deny_button_style)
                edit.setText("Save changes")
            }else{
                changeEnable(false)
                val data = hashMapOf(
                    "address" to inputAddress.text.toString(),
                    "lastname" to inputLastname.text.toString(),
                    "age" to Integer.parseInt(inputAge.text.toString()),
                    "sex" to  inputSex.text.toString(),
                    "country" to inputCountry.text.toString(),
                    "city" to  inputCity.text.toString(),
                    "phone" to  inputPhone.text.toString(),
                    "mail_index" to inputMail.text.toString(),
                    "name" to inputName.text.toString()
                )
                store.collection("users").document(userEmail).set(data)
                edit.setBackgroundResource(R.drawable.round_default_button_style)
                edit.setText("Edit")
            }
        }
        return view
    }

    private fun changeEnable(state:Boolean){
        inputAddress.isEnabled = state
        inputSex.isEnabled = state
        inputName.isEnabled = state
        inputLastname.isEnabled = state
        inputCity.isEnabled = state
        inputCountry.isEnabled = state
        inputMail.isEnabled = state
        inputPhone.isEnabled = state
        inputAge.isEnabled = state
    }
}