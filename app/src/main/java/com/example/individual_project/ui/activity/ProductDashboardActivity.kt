package com.example.individual_project.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.individual_project.R
import com.example.individual_project.adapter.ProductAdapter
import com.example.individual_project.databinding.ActivityProductDashboardBinding
import com.example.individual_project.repository.ProductRepositoryImpl
import com.example.individual_project.viewmodel.ProductViewModel

class ProductDashboardActivity : AppCompatActivity() {

    lateinit var binding: ActivityProductDashboardBinding

    lateinit var productViewModel: ProductViewModel

    lateinit var adapter: ProductAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProductDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var repo = ProductRepositoryImpl()
        productViewModel = ProductViewModel(repo)

        adapter = ProductAdapter(this@ProductDashboardActivity, ArrayList())
        productViewModel.getAllProductsFunc()

        productViewModel.getAllPRoducts.observe(this){
            it?.let {
                adapter.updateData(it)
            }
        }
        binding.recyclerProduct.adapter = adapter
        binding.recyclerProduct.layoutManager = LinearLayoutManager(this)


        productViewModel.loading.observe(this){loading->
            if (loading){
                binding.progressBar.visibility = View.VISIBLE
            }else{
                binding.progressBar.visibility = View.GONE

            }
        }
        binding.floatingActionButton.setOnClickListener{
            val intent =  Intent(this@ProductDashboardActivity, AddProductActivity :: class.java)
            startActivity(intent)
        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(
                viewHolder: RecyclerView.ViewHolder,
                direction: Int
            ) {
                var productId = adapter.getProductId((viewHolder.adapterPosition))

                productViewModel.deleteProduct(productId){
                    success,message->
                    if (success){
                        Toast.makeText(this@ProductDashboardActivity,message, Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this@ProductDashboardActivity,message, Toast.LENGTH_LONG).show()
                    }
                }
            }

        }).attachToRecyclerView(binding.recyclerProduct)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.productDashboardLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}