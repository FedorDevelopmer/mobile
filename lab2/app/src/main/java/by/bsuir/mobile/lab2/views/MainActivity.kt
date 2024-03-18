package by.bsuir.mobile.lab2.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import by.bsuir.mobile.lab2.R
import by.bsuir.mobile.lab2.databinding.MainActivityBinding
import by.bsuir.mobile.lab2.fragments.FeaturedFragment
import by.bsuir.mobile.lab2.fragments.ItemsFragment
import by.bsuir.mobile.lab2.fragments.ProfileFragment
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity(){

    private lateinit var firebaseAuth:FirebaseAuth
    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        //unauthorized access protection
        if(firebaseAuth.currentUser == null){
            startActivity(Intent(this,SignInActivity::class.java))
        }
        //menu init
        replaceFragment(ItemsFragment())
        binding.bottomNavigationView.menu.findItem(R.id.items).setChecked(true)
        //menu handler
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.profile -> replaceFragment(ProfileFragment())
                R.id.featured -> replaceFragment(FeaturedFragment())
                R.id.items -> replaceFragment(ItemsFragment())
                else -> {
                  replaceFragment(fragment = ItemsFragment())
                }
            }
        }
    }

    private fun replaceFragment(fragment: Fragment): Boolean {
        var manager: FragmentManager = supportFragmentManager
        var transaction: FragmentTransaction = manager.beginTransaction()
        transaction.replace(binding.frame.id,fragment)
        transaction.commit()
        return true
    }
}