package com.example.individual_project.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.individual_project.R
import com.example.individual_project.adapter.CartAdapter
import com.example.individual_project.adapter.ProductAdapter
import com.example.individual_project.databinding.ActivityCartBinding
import com.example.individual_project.repository.CartRepositoryImpl
import com.example.individual_project.ui.activity.ProductDashboardActivity
import com.example.individual_project.viewmodel.CartViewModel

class CartActivity : AppCompatActivity() {
    lateinit var cartViewModel: CartViewModel
    lateinit var binding: ActivityCartBinding
    lateinit var adapter: CartAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var repo = CartRepositoryImpl()
        cartViewModel = CartViewModel(repo)

        val userId: String = intent.getStringExtra("userId").toString()

        adapter = CartAdapter(this@CartActivity, ArrayList(),cartViewModel)


        cartViewModel.getAllCartItem(userId)

        cartViewModel.getAllCartItems.observe(this){
            it?.let {
                adapter.updateData(it)
            }
        }
        binding.cartRecyclerView.adapter = adapter
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(this)

        cartViewModel.loading.observe(this){loading->
            if (loading){
                binding.cartProgressBar.visibility = View.VISIBLE
            }else{
                binding.cartProgressBar.visibility = View.GONE

            }
        }

        binding.button2.setOnClickListener{
            val intent = Intent(this@CartActivity, UserDashboard :: class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}