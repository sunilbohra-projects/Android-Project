package com.target.targetcasestudy.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.target.targetcasestudy.R
import com.target.targetcasestudy.model.ProductList
import com.target.targetcasestudy.model.ProductResponseApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class DealListFragment(
    productListListener: ProductListListener,
    productSelectedListener: ProductSelectedListener
) : Fragment() {
    val productListListener = productListListener
    val productSelectedListener  = productSelectedListener
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_deal_list, container, false)
        requestData(view)
        return view
    }

    private fun requestData(view: View) {
        val url = "https://api.target.com/"

        val gson = GsonBuilder()
            .setLenient()
            .create()
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        val jsonPlaceHolderApi: ProductResponseApi? =
            retrofit.create(ProductResponseApi::class.java)
        val call: Call<ProductList> = jsonPlaceHolderApi?.getProducts()!!
        productListListener?.showProgressDialog()
        call.enqueue(object : Callback<ProductList> {
            override fun onResponse(call: Call<ProductList>, response: Response<ProductList>) {
                if (response.isSuccessful) {
                    productListListener.dismissProgressDialog()
                    view?.findViewById<RecyclerView>(R.id.recycler_view)?.layoutManager =
                        LinearLayoutManager(requireContext())
                    val dividerItemDecoration = DividerItemDecoration(
                        view?.findViewById<RecyclerView>(R.id.recycler_view).context,
                        DividerItemDecoration.VERTICAL
                    )
                    view?.findViewById<RecyclerView>(R.id.recycler_view)?.adapter =
                        DealItemAdapter(productSelectedListener, response.body()?.products)
                    view?.findViewById<RecyclerView>(R.id.recycler_view)
                        ?.addItemDecoration(dividerItemDecoration);
                }
            }

            override fun onFailure(call: Call<ProductList>, t: Throwable) {
                Log.d("this@MainActivity", "${t.message}")
            }
        })

    }

    interface ProductListListener {
        fun showProgressDialog()
        fun dismissProgressDialog()
    }

}
