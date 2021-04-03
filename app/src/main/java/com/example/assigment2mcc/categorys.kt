package com.example.assigment2mcc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.fragment_categorys.view.*


class categorys : Fragment() {

    lateinit var FirebaseAnalytics : FirebaseAnalytics


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_categorys, container, false)
        val b = Bundle()

        FirebaseAnalytics = com.google.firebase.analytics.FirebaseAnalytics.getInstance(activity!!)
        screenEvent("categoryOfProducts","categorys")


        root.btnFood.setOnClickListener {
            b.putInt("category",1)
            val display = display()
            display.arguments = b
            itemEvent("btnFood","foor category","Button")
            activity!!.supportFragmentManager.beginTransaction().replace(R.id.containerLayout,display).addToBackStack("null").commit()
        }

        root.btnClothes.setOnClickListener {
            b.putInt("category",2)
            val display = display()
            display.arguments = b
            itemEvent("btnClothes","btnClothes category","Button")
            activity!!.supportFragmentManager.beginTransaction().replace(R.id.containerLayout,display).addToBackStack("null").commit()
        }

        root.btnElectronic.setOnClickListener {
            b.putInt("category",3)
            val display = display()
            display.arguments = b
            itemEvent("btnElectronic","btnElectronic category","Button")
            activity!!.supportFragmentManager.beginTransaction().replace(R.id.containerLayout,display).addToBackStack("null").commit()
        }


        return root
    }

    fun itemEvent( id : String,name :String ,type:String){
        val b2 = Bundle()
        b2.putString(com.google.firebase.analytics.FirebaseAnalytics.Param.ITEM_ID,id)
        b2.putString(com.google.firebase.analytics.FirebaseAnalytics.Param.ITEM_NAME,name)
        b2.putString(com.google.firebase.analytics.FirebaseAnalytics.Param.CONTENT_TYPE,type)
        FirebaseAnalytics.logEvent(com.google.firebase.analytics.FirebaseAnalytics.Event.SELECT_CONTENT,b2)
    }

    fun screenEvent( Name :String ,Class:String){
        val b2 = Bundle()
        b2.putString(com.google.firebase.analytics.FirebaseAnalytics.Param.SCREEN_NAME,Name)
        b2.putString(com.google.firebase.analytics.FirebaseAnalytics.Param.SCREEN_CLASS,Class)
        FirebaseAnalytics.logEvent(com.google.firebase.analytics.FirebaseAnalytics.Event.SCREEN_VIEW,b2)
    }
}