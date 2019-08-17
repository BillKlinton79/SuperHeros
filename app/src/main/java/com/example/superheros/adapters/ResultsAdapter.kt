package com.example.superheros.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.superheros.R
import com.example.superheros.models.Result
import com.example.superheros.views.HeroActivity
import com.makeramen.roundedimageview.RoundedTransformationBuilder
import com.squareup.picasso.Picasso

class ResultsAdapter(private val resultsList: MutableList<Result>) : RecyclerView.Adapter<ResultsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hero_row, parent, false)
        return ViewHolder(view, parent.context)
    }

    override fun getItemCount() = resultsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = resultsList[position].name
        holder.publisher.text = if(resultsList[position].biography.publisher == "null") "Unknown" else resultsList[position].biography.publisher

        //Image round
        val transformation = RoundedTransformationBuilder()
            .cornerRadiusDp(80f)
            .oval(false)
            .build()

        Picasso.get()
            .load(resultsList[position].image.url)
            .error(R.drawable.no_image_75)
            .placeholder(R.drawable.progress_animation)
            .resize(200, 200)
            .transform(transformation)
            .into(holder.image)

        holder.constraintLayout.setOnClickListener {
            val name = resultsList[position].name
            val full_name = resultsList[position].biography.full_name
            val image = resultsList[position].image.url
            val intelligence =
                if (resultsList[position].powerstats.intelligence == "null") 0 else resultsList[position].powerstats.intelligence?.toInt()
            val strength =
                if (resultsList[position].powerstats.strength == "null") 0 else resultsList[position].powerstats.strength?.toInt()
            val speed =
                if (resultsList[position].powerstats.speed == "null") 0 else resultsList[position].powerstats.speed?.toInt()
            val durability =
                if (resultsList[position].powerstats.durability == "null") 0 else resultsList[position].powerstats.durability?.toInt()
            val power =
                if (resultsList[position].powerstats.power == "null") 0 else resultsList[position].powerstats.power?.toInt()
            val combat =
                if (resultsList[position].powerstats.combat == "null") 0 else resultsList[position].powerstats.combat?.toInt()
            val race = resultsList[position].appearance.race
            val height = resultsList[position].appearance.height
            val weight = resultsList[position].appearance.weight
            val intent = Intent(holder.context, HeroActivity::class.java)

            val bundle = Bundle()
            bundle.putString("name", name)
            bundle.putString("full_name", full_name)
            bundle.putString("image", image)
            bundle.putInt("intelligence", intelligence)
            bundle.putInt("strength", strength)
            bundle.putInt("speed", speed)
            bundle.putInt("durability", durability)
            bundle.putInt("power", power)
            bundle.putInt("combat", combat)
            bundle.putString("race", race)
            bundle.putString("height", height.last())
            bundle.putString("weight", weight.last())

            intent.putExtras(bundle)
            startActivity(holder.context, intent, null)
        }
    }

    class ViewHolder(view: View, val context: Context) : RecyclerView.ViewHolder(view) {
        val name = view.findViewById<TextView>(R.id.heroName)
        val publisher = view.findViewById<TextView>(R.id.heroFullName)
        val image = view.findViewById<ImageView>(R.id.heroImage)
        val constraintLayout = view.findViewById<ConstraintLayout>(R.id.constraintLayout)
    }

}