package com.cardenas.diego.poketinder2024.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.cardenas.diego.poketinder2024.data.model.PokemonResponse
import com.cardenas.diego.poketinder2024.databinding.FragmentHomeBinding
import com.cardenas.diego.poketinder2024.ui.adapter.PokemonAdapter
import com.cardenas.diego.poketinder2024.ui.viewmodel.HomeViewModel
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction

class HomeFragment : Fragment(), CardStackListener {

    private var listPokemon: List<PokemonResponse> = emptyList()

    private val adapter by lazy { PokemonAdapter(listPokemon) }

    private lateinit var binding : FragmentHomeBinding

    private val viewModel by lazy { HomeViewModel() }

    private val manager by lazy { CardStackLayoutManager(context, this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentHomeBinding.inflate(layoutInflater)
        observeValues()
        initializeTinderCard()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    private fun initializeTinderCard() {
        binding.rvTinderPokemon.adapter = adapter
        binding.rvTinderPokemon.layoutManager = manager
    }

    private fun observeValues() {
        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.isVisible = isLoading
        }

        viewModel.pokemonList.observe(this) { pokemonList ->
            adapter.list = pokemonList
            adapter.notifyDataSetChanged()
        }

        viewModel.errorApi.observe(this) { errorMessage ->
            showMessage(errorMessage)
        }
    }

    private fun showMessage(message: String) {
        activity?.runOnUiThread {
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {

    }

    override fun onCardSwiped(direction: Direction?) {
        if (direction == Direction.Right) {
            val pokemon = adapter.list[manager.topPosition - 1]
            viewModel.savePokemon(pokemon, requireContext())
        }
    }

    override fun onCardRewound() {

    }

    override fun onCardCanceled() {

    }

    override fun onCardAppeared(view: View?, position: Int) {

    }

    override fun onCardDisappeared(view: View?, position: Int) {
    }
}

