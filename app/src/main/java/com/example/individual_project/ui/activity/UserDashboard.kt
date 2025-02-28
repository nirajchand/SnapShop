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
import com.example.individual_project.adapter.ProductAdapter
import com.example.individual_project.adapter.UserDashboardAdapter
import com.example.individual_project.databinding.ActivityUserDashboardBinding
import com.example.individual_project.repository.CartRepositoryImpl
import com.example.individual_project.repository.ProductRepositoryImpl
import com.example.individual_project.ui.activity.ProductDashboardActivity
import com.example.individual_project.viewmodel.CartViewModel
import com.example.individual_project.viewmodel.ProductViewModel
import com.google.firebase.auth.FirebaseAuth

class UserDashboard : AppCompatActivity() {
    lateinit var binding: ActivityUserDashboardBinding
    lateinit var productViewModel: ProductViewModel
    lateinit var cartViewModel: CartViewModel
    lateinit var adapter: UserDashboardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUserDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var repo = ProductRepositoryImpl()
        productViewModel = ProductViewModel(repo)

        var repo2 = CartRepositoryImpl()
        cartViewModel = CartViewModel(repo2)

        adapter = UserDashboardAdapter(this@UserDashboard, ArrayList(), cartViewModel)
        productViewModel.getAllProductsFunc()

        productViewModel.getAllPRoducts.observe(this){
            it?.let {
                adapter.updateData(it)
            }
        }
        binding.userDashboardRecyclerView.adapter = adapter
        binding.userDashboardRecyclerView.layoutManager = LinearLayoutManager(this)


        productViewModel.loading.observe(this){loading->
            if (loading){
                binding.userDashboardProgressbar.visibility = View.VISIBLE
            }else{
                binding.userDashboardProgressbar.visibility = View.GONE

            }
        }
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        binding.floatingActionButton2.setOnClickListener{
            val intent = Intent(this@UserDashboard, CartActivity::class.java)
            intent.putExtra("userId",userId)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}