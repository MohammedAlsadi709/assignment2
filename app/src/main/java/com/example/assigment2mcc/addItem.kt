package com.example.assigment2mcc

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat.checkSelfPermission
import com.bumptech.glide.Glide
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.FirebaseAnalytics.Param.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_add_item.*
import kotlinx.android.synthetic.main.fragment_add_item.view.*

class addItem : Fragment() {


    var URI = ""
    lateinit var db : FirebaseFirestore
    lateinit var FirebaseAnalytics : FirebaseAnalytics

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_add_item, container, false)
        db = Firebase.firestore
        FirebaseAnalytics = com.google.firebase.analytics.FirebaseAnalytics.getInstance(activity!!)
        screenEvent("addToProductsItems","addItem")

        val b = arguments
        var category = 0
        if (b != null) {
            category = b.getInt("category")
            //   Toast.makeText(activity,category.toString(),Toast.LENGTH_SHORT).show()
        }


        root.btnGet.setOnClickListener {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//if api > 23
                if (activity!!.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1000)
                    return@setOnClickListener
                } else {// if accepted in Advance
                    val gallery =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(gallery, 100)
                }
            } else {// if api < 23
                val gallery =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(gallery, 100)
            }
            itemEvent("btnGet","GetFromGallery","Button")

        }


        root.btnAdd.setOnClickListener {
            if (root.txt1.text.isEmpty() || root.txt2.text.isEmpty()) {
                Toast.makeText(activity, "يرجى ملئ كل الحقول", Toast.LENGTH_SHORT).show()
            } else {
                val name = root.txt1.text.toString()
                val details = root.txt2.text.toString()
                val document = hashMapOf("name" to name,"details" to details,"image" to URI,"category" to category.toString())
                db.collection("assignment2")
                    .add(document)
                    .addOnSuccessListener {documentReference ->
                        Toast.makeText(activity!!,"Added Successfuly", Toast.LENGTH_SHORT).show()
                        activity!!.supportFragmentManager.beginTransaction().replace(R.id.containerLayout,display()).addToBackStack("null").commit()
                    }.addOnFailureListener { exception ->
                        Toast.makeText(activity!!,"error", Toast.LENGTH_SHORT).show()
                    }
            }

            itemEvent("btnAdd","AddFromGallery","Button")
        }







        return root
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 100) {
            URI = data!!.data.toString()
            Glide.with(activity).load(URI).into(img1)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            1000 -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    val gallery =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(gallery, 100)
                } else {
                    Toast.makeText(activity!!, "must accept to continue", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
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