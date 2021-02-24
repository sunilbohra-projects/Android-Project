package com.target.targetcasestudy.ui

import android.graphics.Paint
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import com.target.targetcasestudy.R
import com.target.targetcasestudy.model.Product


class DealItemFragment(product : Product) : Fragment() {

  val product = product

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_deal_item, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    loadData(view)
  }


  private fun loadData(view : View) {
    val salePriceTxt : TextView = view.findViewById(R.id.salePrice)
    val imageView :ImageView = view.findViewById(R.id.imageView)
    val regularPriceTxt : TextView =  view.findViewById(R.id.regularPrice)
    val title : TextView = view.findViewById(R.id.title)
    val description : TextView =  view.findViewById(R.id.description)
    Picasso.get().load(product.image_url).into(imageView)
    if (product.sale_price == null) {
      salePriceTxt.visibility = View.GONE
//      regularPriceTxt.gravity = Gravity.CENTER
    } else {
      salePriceTxt.visibility = View.VISIBLE
      salePriceTxt.text = product.sale_price.display_string
      regularPriceTxt.apply {
        paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
      }
    }
    regularPriceTxt.text = product.regular_price.display_string
    title.text = product.title
    description.text =  product.description
  }


}
