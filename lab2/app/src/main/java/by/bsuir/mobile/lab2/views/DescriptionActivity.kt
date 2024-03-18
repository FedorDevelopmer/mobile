package by.bsuir.mobile.lab2.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.bsuir.mobile.lab2.R
import by.bsuir.mobile.lab2.databinding.DescriptionActivityBinding
import com.bumptech.glide.Glide
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class DescriptionActivity : AppCompatActivity() {

    private lateinit var binding: DescriptionActivityBinding
    private lateinit var auth:FirebaseAuth
    private lateinit var store:FirebaseFirestore
    private lateinit var storage:FirebaseStorage
    private lateinit var storageRef:StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DescriptionActivityBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()
        store = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()
        setContentView(binding.root)
        var sliderView = binding.slider
        var id:String = intent.getStringExtra("id") ?: ""
        var isFeatured = intent.getBooleanExtra("isFeatured",false)
        var imagePath:String = "/"
        var sliderModels = ArrayList<SlideModel>()
        store.collection("ssd").document(id).get().addOnSuccessListener {
            binding.modelLabel.text = "Model: " + it["model"].toString()
            binding.priceLabel.text = "Price: " + it["price"].toString() + "$"
            binding.model.text = it["model"].toString()
            binding.memory.text = it["memory"].toString() + " GB"
            binding.writeSpeed.text = it["writeSpeed"].toString() + " MB/s"
            binding.readSpeed.text = it["readSpeed"].toString() + " MB/s"
            imagePath = it["imagePath"].toString()
            var imageUrl = ArrayList<String>()
            Glide.with(this).load(R.drawable.no_connection_icon).into(binding.mainImage)
            storageRef =  storage.getReference(imagePath)
            storageRef.listAll().addOnSuccessListener { images ->
                for(item in images.items){
                    item.downloadUrl.addOnSuccessListener {
                        imageUrl.add(it.toString())
                        sliderModels.clear()

                        if(imageUrl.size == 1){
                            Glide.with(this).load(imageUrl[0]).into(binding.mainImage)
                        }
                        imageUrl.sort()
                        for(item in imageUrl){
                            sliderModels.add(SlideModel(item,ScaleTypes.FIT))
                        }
                        sliderView.setImageList(sliderModels)
                    }
                }
            }
        }

        if(isFeatured){
            binding.addToFeatured.setBackgroundResource(R.drawable.round_deny_button_style)
            binding.addToFeatured.setText("Remove from featured")

        }

        binding.addToFeatured.setOnClickListener {
            val data = hashMapOf(
                "ssd" to id
            )
            var userEmail:String = auth.currentUser?.email ?: ""
            store.collection("users")
                .document(userEmail)
                .collection("featured")
                .whereEqualTo("ssd",data["ssd"]).get().addOnSuccessListener { snapshot ->
                    if(snapshot != null) {
                        if (snapshot.documents.isNotEmpty()) {
                            for (doc in snapshot.documents) {
                                store.collection("users")
                                    .document(userEmail)
                                    .collection("featured")
                                    .document(doc.id).delete()

                            }
                            binding.addToFeatured.setBackgroundResource(R.drawable.round_default_button_style)
                            binding.addToFeatured.setText("Add to featured")
                        } else {
                            store.collection("users")
                                .document(userEmail)
                                .collection("featured")
                                .add(data)
                            binding.addToFeatured.setBackgroundResource(R.drawable.round_deny_button_style)
                            binding.addToFeatured.setText("Remove from featured")
                        }
                    }
                }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this,MainActivity::class.java))
    }

}