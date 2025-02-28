package com.example.individual_project.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.individual_project.R
import com.example.individual_project.model.ProductModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Button
import com.example.individual_project.model.CartModel
import com.example.individual_project.repository.CartRepositoryImpl
import com.example.individual_project.ui.activity.CartActivity
import com.example.individual_project.viewmodel.CartViewModel

class UserDashboardAdapter(var context: Context, var data: ArrayList<ProductModel>, var cartViewModel: CartViewModel)
    : RecyclerView.Adapter<UserDashboardAdapter.UserDashboardViewHolder>() {


    class UserDashboardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var productName: TextView = itemView.findViewById(R.id.productDisplayName)
        var productDes: TextView = itemView.findViewById(R.id.productDisplayDes)
        var productPrice: TextView = itemView.findViewById(R.id.ProductDisplayPrice)
        var productImage: ImageView = itemView.findViewById(R.id.productImageShow)
        var progrssBar: ProgressBar = itemView.findViewById(R.id.productImageProgressBar)
        var addToCartBtn: Button = itemView.findViewById(R.id.button)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserDashboardViewHolder {
        var itemView: View =
            LayoutInflater.from(context).inflate(R.layout.sampleuserdashboard, parent, false)
        return UserDashboardViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: UserDashboardViewHolder,
        position: Int
    ) {


        val product = data[position]

        holder.productName.text = product.productName
        holder.productDes.text = product.productDesc
        holder.productPrice.text = product.price.toString()



        Picasso.get().load(data[position].imageUrl).into(holder.productImage, object : Callback {
            override fun onSuccess() {
                holder.progrssBar.visibility = View.GONE
            }

            override fun onError(e: Exception?) {
            }

        })

        holder.addToCartBtn.setOnClickListener {
            val cartItem = CartModel(
                productID = product.productID,
                productName = product.productName,
                productImage = product.imageUrl,
                productDes = product.productDesc,
                price = product.price
            )

            cartViewModel.addToCart(cartItem) { success, message ->
                if (success) {
                    Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Error: $message", Toast.LENGTH_SHORT).show()
                }
            }


        }

    }

    fun updateData(products: List<ProductModel>) {
        data.clear()
        data.addAll(products)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun getProductId(position: Int): String {
        return data[position].productID
    }
}