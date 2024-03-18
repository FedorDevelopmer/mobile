package by.bsuir.mobile.lab2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.bsuir.mobile.lab2.R
import by.bsuir.mobile.lab2.adapters.SSDAdapter
import by.bsuir.mobile.lab2.databinding.ItemsFragmentBinding
import by.bsuir.mobile.lab2.entities.SSD
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore



class ItemsFragment : Fragment() {
    private lateinit var binding:ItemsFragmentBinding
    private lateinit var auth:FirebaseAuth
    private lateinit var store:FirebaseFirestore
    private var ssds:ArrayList<SSD> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ItemsFragmentBinding.inflate(layoutInflater)
        var recycleview:RecyclerView = view.findViewById(R.id.items)
        val adapter = SSDAdapter(ssds)
        recycleview.apply {
            recycleview.hasFixedSize()
            recycleview.layoutManager = LinearLayoutManager(context)
            recycleview.adapter = adapter
        }
        store = Firebase.firestore
        store.collection("ssd").orderBy("model").get().addOnSuccessListener{ snapshot ->
            ssds.clear()
            for(doc in snapshot.documents) {
                val item = doc.toObject(SSD::class.java)

                item?.let {
                    it.setId(doc.id)
                    ssds.add(it)
                }
            }
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.items_fragment, container, false)
    }
}