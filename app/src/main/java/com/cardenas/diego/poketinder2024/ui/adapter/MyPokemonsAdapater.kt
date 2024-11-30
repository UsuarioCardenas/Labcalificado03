package com.cardenas.diego.poketinder2024.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cardenas.diego.poketinder2024.data.database.entities.MyPokemonEntity
import com.cardenas.diego.poketinder2024.databinding.ItemPokemonSavedBinding
import com.cardenas.diego.poketinder2024.ui.holder.MyPokemonsHolder

class MyPokemonsAdapater(val list: List<MyPokemonEntity>): RecyclerView.Adapter<MyPokemonsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPokemonsHolder {
        val view = ItemPokemonSavedBinding.inflate(LayoutInflater.from(parent.context),parent ,false)
        return MyPokemonsHolder(view.root)
    }

    override fun onBindViewHolder(holder: MyPokemonsHolder, position: Int) {
        val pokemon = list[position]
        holder.bind(pokemon)
    }

    override fun getItemCount(): Int = list.size
}

