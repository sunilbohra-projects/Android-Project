package com.target.targetcasestudy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.target.targetcasestudy.R
import com.target.targetcasestudy.model.Product
import com.target.targetcasestudy.model.ProductList
import retrofit2.Callback


class DealListFragment(
    private val productSelectedListener: ProductSelectedListener,
    products: List<Product>?
) : Fragment() {
    val products = products
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_deal_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view?.findViewById<RecyclerView>(R.id.recycler_view)?.layoutManager =
            LinearLayoutManager(requireContext())
        val dividerItemDecoration = DividerItemDecoration(
            view?.findViewById<RecyclerView>(R.id.recycler_view).context,
            DividerItemDecoration.VERTICAL
        )
        view?.findViewById<RecyclerView>(R.id.recycler_view)?.adapter =
            DealItemAdapter(productSelectedListener, products)
        view?.findViewById<RecyclerView>(R.id.recycler_view)
            ?.addItemDecoration(dividerItemDecoration);
    }

}
