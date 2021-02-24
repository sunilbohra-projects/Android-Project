package com.target.targetcasestudy

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.target.targetcasestudy.model.Product
import com.target.targetcasestudy.ui.DealItemFragment
import com.target.targetcasestudy.ui.DealListFragment
import com.target.targetcasestudy.ui.DealListFragment.ProductListListener
import com.target.targetcasestudy.ui.ProductSelectedListener
import com.target.targetcasestudy.ui.payment.PaymentDialogFragment

class MainActivity : AppCompatActivity() , ProductListListener, ProductSelectedListener {

    private lateinit var dialog : AlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, DealListFragment(this, this))
            .commit()
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

    override fun showProgressDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setCancelable(false) // if you want user to wait for some process to finish,
        builder.setView(R.layout.layout_loading_dialog)
        dialog = builder.create()
        dialog.show()
    }

    override fun dismissProgressDialog() {
        dialog.dismiss()
    }

    override fun setSelectedProduct(item: Product) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, DealItemFragment(item))
            .commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        supportFragmentManager.popBackStackImmediate()
    }


}
