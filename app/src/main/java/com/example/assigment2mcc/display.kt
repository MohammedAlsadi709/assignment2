package com.example.assigment2mcc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assigment2mcc.adapterAndModel.adapter
import com.example.assigment2mcc.adapterAndModel.model
import com.example.assigment2mcc.adapterAndModel.viewItem
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.FirebaseAnalytics.Param.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_display.view.*


class display : Fragment(),adapter.onItemClick {

    lateinit var FirebaseAnalytics : FirebaseAnalytics

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_display, container, false)

        val db = Firebase.firestore
         FirebaseAnalytics = com.google.firebase.analytics.FirebaseAnalytics.getInstance(activity!!)
        screenEvent("displayProductsItems","displayFragment")

        val b= arguments
        var category = 0
        if (b != null) {
             category = b.getInt("category")
        }

        root.add.setOnClickListener {
            val b1 = Bundle()
            val addItem = addItem()
            b1.putInt("category",category)
            addItem.arguments = b1
            activity!!.supportFragmentManager.beginTransaction().replace(R.id.containerLayout,addItem).addToBackStack("null").commit()
        }


        val    data = mutableListOf<model>()
        db.collection("assignment2").whereEqualTo("category",category.toString()).get().addOnSuccessListener { querySnapshot ->
            for (doc in querySnapshot) {
                var name = doc.getString("name")
                var details = doc.getString("details")
                var image = doc.getString("image")

                if (name != null&& details != null && image != null){
                data.add(model(doc.id,name,details,image))
            }
            }
            val adapter = adapter(activity!!, data,this)
            root.RV1.adapter = adapter
            root.RV1.layoutManager = LinearLayoutManager(activity!!)
            Toast.makeText(activity,"image sorted as URI",Toast.LENGTH_SHORT).show()
        }


        return root
    }

    override fun onClickItem(p: Int,data : MutableList<model>) {
        val b = Bundle()
        b.putInt("p",p)
        b.putString("name",data[p].name)
        b.putString("image",data[p].image)
        b.putString("details",data[p].details)
        val viewItem = viewItem()
        viewItem.arguments = b

        itemEvent("prodectItem",data[p].name,"itemOfAdapter")

        activity!!.supportFragmentManager.beginTransaction().replace(R.id.containerLayout,viewItem).addToBackStack("null").commit()
    }

    fun itemEvent( id : String,name :String ,type:String){
        val b2 = Bundle()
        b2.putString(ITEM_ID,id)
        b2.putString(ITEM_NAME,name)
        b2.putString(CONTENT_TYPE,type)
        FirebaseAnalytics.logEvent(com.google.firebase.analytics.FirebaseAnalytics.Event.SELECT_CONTENT,b2)
    }

    fun screenEvent( Name :String ,Class:String){
        val b2 = Bundle()
        b2.putString(SCREEN_NAME,Name)
        b2.putString(SCREEN_CLASS,Class)
        FirebaseAnalytics.logEvent(com.google.firebase.analytics.FirebaseAnalytics.Event.SCREEN_VIEW,b2)
    }
}