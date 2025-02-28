package com.example.individual_project.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.individual_project.R
import com.example.individual_project.databinding.ActivityUpdateProductBinding
import com.example.individual_project.model.ProductModel
import com.example.individual_project.repository.ProductRepositoryImpl
import com.example.individual_project.ui.activity.AddProductActivity
import com.example.individual_project.viewmodel.ProductViewModel

class UpdateProductActivity : AppCompatActivity() {
    lateinit var binding: ActivityUpdateProductBinding
    lateinit var productViewModel: ProductViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUpdateProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var repo = ProductRepositoryImpl()
        productViewModel = ProductViewModel(repo)

//        var products: ProductModel? = intent.getParcelableExtra("products")
//
        var productID: String = intent.getStringExtra("products").toString()
        productViewModel.getProductByID(productID)

        productViewModel.products.observe(this){
            binding.UpdateproductName.setText(it?.productName.toString())
            binding.UpdateproductDes.setText(it?.productDesc.toString())
            binding.UpdateproductPrice.setText(it?.price.toString())
        }

        binding.Updateaddbutton.setOnClickListener{
            var name = binding.UpdateproductName.text.toString()
            var des = binding.UpdateproductDes.text.toString()
            var price = binding.UpdateproductPrice.text.toString().toInt()

            var updatedData = mutableMapOf<String,Any>()

            updatedData["productName"] = name
            updatedData["productDesc"] = des
            updatedData["price"] = price

            productViewModel.updateProduct(productID,updatedData){
                success,message->
                if (success){
                    Toast.makeText(this@UpdateProductActivity,message, Toast.LENGTH_LONG).show()
                    finish()
                }else{
                    Toast.makeText(this@UpdateProductActivity,message, Toast.LENGTH_LONG).show()
                }
            }
        }
//        products.let {
//            binding.UpdateproductName.setText(it?.productName.toString())
//            binding.UpdateproductDes.setText(it?.productDesc.toString())
//            binding.UpdateproductPrice.setText(it?.price.toString())
//        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.updateProductLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}