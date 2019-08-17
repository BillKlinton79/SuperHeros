package com.example.superheros.views

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.superheros.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_hero.*
import com.makeramen.roundedimageview.RoundedTransformationBuilder




class HeroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hero)

        val name = intent.extras.getString("name")
        val full_name = intent.extras.getString("full_name")
        val image = intent.extras.getString("image")
        val intelligence = intent.extras.getInt("intelligence")
        val strength = intent.extras.getInt("strength")
        val speed = intent.extras.getInt("speed")
        val durability = intent.extras.getInt("durability")
        val power = intent.extras.getInt("power")
        val combat = intent.extras.getInt("combat")
        val race = intent.extras.getString("race")
        val height = intent.extras.getString("height")
        val weight = intent.extras.getString("weight")


        if(name.contains(" ")){
            heroName.text = "${name.subSequence(0,name.indexOf(" "))}\n${name.subSequence(name.indexOf(" ")+1,name.lastIndex+1)}"
        }else{
            heroName.text = name
        }
        heroFullName.text = if(full_name == "") "Unknown" else full_name
        heroHeight.text = height
        heroWeight.text = weight
        heroRace.text = if(race == "null") "Unknown" else race

        val transformation = RoundedTransformationBuilder()
            .borderColor(Color.BLACK)
            .borderWidthDp(2f)
            .cornerRadiusDp(5f)
            .oval(false)
            .build()

        Picasso.get()
            .load(image)
            .error(R.drawable.no_image)
            .placeholder(R.drawable.progress_animation)
            .transform(transformation)
            .into(imageView)
        seekBarIntelligence.setProgress(intelligence.toFloat())
        seekBarStrength.setProgress(strength.toFloat())
        seekBarSpeed.setProgress(speed.toFloat())
        seekBarDurability.setProgress(durability.toFloat())
        seekBarPower.setProgress(power.toFloat())
        seekBarCombat.setProgress(combat.toFloat())

        seekBarIntelligence.isEnabled = false
        seekBarStrength.isEnabled = false
        seekBarSpeed.isEnabled = false
        seekBarDurability.isEnabled = false
        seekBarPower.isEnabled = false
        seekBarCombat.isEnabled = false
    }
}
