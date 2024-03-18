package by.bsuir.mobile.lab2.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import by.bsuir.mobile.lab2.R
import by.bsuir.mobile.lab2.views.DescriptionActivity
import by.bsuir.mobile.lab2.entities.SSD
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SSDAdapter(private val ssdList:ArrayList<SSD>) : RecyclerView.Adapter<SSDAdapter.SSDViewHolder>() {

    private lateinit var context: Context
    private var store = FirebaseFirestore.getInstance()
    private var auth = FirebaseAuth.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SSDViewHolder {
        context = parent.context
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return SSDViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return ssdList.size
    }

    override fun onBindViewHolder(holder: SSDViewHolder, position: Int) {
        val currentItem = ssdList[position]
        var isFeatured:Boolean = false
        holder.model.text = currentItem.getModel()
        holder.text.text = "For " + currentItem.getPrice().toString() +" USD"

        val data = hashMapOf(
            "ssd" to currentItem.getId()
        )
        var userEmail:String = auth.currentUser?.email ?: ""
        store.collection("users")
            .document(userEmail)
            .collection("featured")
            .whereEqualTo("ssd",data["ssd"]).get().addOnSuccessListener { snapshot ->
                if(snapshot != null) {
                    if (snapshot.documents.isEmpty()) {
                        holder.add.setImageResource(R.drawable.star_icon)
                        isFeatured = false
                    } else {
                        holder.add.setImageResource(R.drawable.checked_icon)
                        isFeatured = true
                    }
                }
            }
        holder.itemContainer.setOnClickListener {
            val intent = Intent(context, DescriptionActivity::class.java)
            intent.putExtra("id", currentItem.getId())
            intent.putExtra("isFeatured",isFeatured )
            context.startActivity(intent)
        }
        holder.add.setOnClickListener {
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
                             holder.add.setImageResource(R.drawable.star_icon)
                         } else {
                             store.collection("users")
                                 .document(userEmail)
                                 .collection("featured")
                                 .add(data)
                             holder.add.setImageResource(R.drawable.checked_icon)
                         }
                     }
                }
        }
    }

    inner class SSDViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val model:TextView = itemView.findViewById(R.id.ssd_model)
        val image: ImageView = itemView.findViewById(R.id.ssd_image)
        val text:TextView = itemView.findViewById(R.id.ssd_price)
        val itemContainer: LinearLayout = itemView.findViewById(R.id.item_containter)
        val add: ImageView = itemView.findViewById(R.id.add_to_featured_image)
    }
}