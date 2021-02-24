package com.target.targetcasestudy

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.GsonBuilder
import com.target.targetcasestudy.model.Product
import com.target.targetcasestudy.model.ProductList
import com.target.targetcasestudy.model.ProductResponseApi
import com.target.targetcasestudy.ui.DealItemFragment
import com.target.targetcasestudy.ui.DealListFragment
import com.target.targetcasestudy.ui.ProductSelectedListener
import com.target.targetcasestudy.ui.payment.PaymentDialogFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var dialog: AlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestData()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.credit_card -> {
                PaymentDialogFragment().show(supportFragmentManager, "CreditCardValidation")
                true
            }
            else -> false
        }
    }

    private fun requestData() {
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
        showProgressDialog()
        call.enqueue(object : Callback<ProductList>, ProductSelectedListener {
            override fun onResponse(call: Call<ProductList>, response: Response<ProductList>) {
                if (response.isSuccessful) {
                    dismissProgressDialog()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, DealListFragment( this, response.body()?.products))
                        .commit()
                }
            }

                override fun onFailure(call: Call<ProductList>, t: Throwable) {
                    Log.d("this@MainActivity", "${t.message}")
                }

            override fun setSelectedProduct(item: Product) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, DealItemFragment(item))
                    .addToBackStack(null)
                    .commit()
            }
        })

    }

     private fun showProgressDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setCancelable(false) // if you want user to wait for some process to finish,
        builder.setView(R.layout.layout_loading_dialog)
        dialog = builder.create()
        dialog.show()
    }

   private fun dismissProgressDialog() {
        dialog.dismiss()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        dialog.dismiss()
    }


}
