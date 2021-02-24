package com.target.targetcasestudy.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.target.targetcasestudy.R
import com.target.targetcasestudy.model.Product

class DealItemAdapter(productSelectedListener : ProductSelectedListener, body: List<Product>?) : RecyclerView.Adapter<DealItemViewHolder>() {

   private var productList = body
  private var productSelectedListener = productSelectedListener

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DealItemViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val view = inflater.inflate(R.layout.deal_list_item, parent, false)
    return DealItemViewHolder(view)
  }


  override fun getItemCount(): Int {
    return productList?.size!!
  }

  override fun onBindViewHolder(viewHolder: DealItemViewHolder, position: Int) {
    val item = this?.productList?.get(position)
    viewHolder.itemView.findViewById<TextView>(R.id.deal_list_item_title).text = item?.title
    viewHolder.itemView.findViewById<TextView>(R.id.deal_list_item_price).text = if(item?.sale_price == null)
      item?.regular_price?.display_string else item?.sale_price?.display_string
    Picasso.get().load(item?.image_url)
      .into(viewHolder.itemView.findViewById<ImageView>(R.id.deal_list_item_image_view))
    viewHolder.itemView.findViewById<TextView>(R.id.deal_list_item_aisle).text = item?.aisle?.toUpperCase()
    viewHolder.itemView.setOnClickListener(View.OnClickListener {
      productSelectedListener.setSelectedProduct(item!!)
    })

  }
}

class DealItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

}

interface ProductSelectedListener {
  fun setSelectedProduct(item: Product)
}