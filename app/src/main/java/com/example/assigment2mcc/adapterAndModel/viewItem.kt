package com.example.assigment2mcc.adapterAndModel

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.assigment2mcc.R
import kotlinx.android.synthetic.main.fragment_view_item.view.*


class viewItem : Fragment() {
  override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_view_item, container, false)
val b = arguments
      if (b != null){
          val name = b.getString("name")
          val image = b.getString("image")
          val details = b.getString("details")

          root.txtName.text = name
          root.txtDetails.text = details
          root.image1.setImageURI(Uri.parse(image))


      }


      return root
    }

}