package itb.dam.jiafuchen.heynima.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import itb.dam.jiafuchen.heynima.R
import itb.dam.jiafuchen.heynima.databinding.FragmentDetailBinding
import itb.dam.jiafuchen.heynima.databinding.FragmentFavouritesGamesBinding
import itb.dam.jiafuchen.heynima.databinding.FragmentHomeBinding
import itb.dam.jiafuchen.heynima.domain.models.Games
import itb.dam.jiafuchen.heynima.ui.adapters.FavouriteGamesListAdapter
import itb.dam.jiafuchen.heynima.ui.adapters.GameListAdapter
import itb.dam.jiafuchen.heynima.ui.viewmodels.FavouriteGameListViewModel
import itb.dam.jiafuchen.heynima.ui.viewmodels.ListFragmentViewModel
import itb.dam.jiafuchen.heynima.ui.viewmodels.SharedViewModel

@AndroidEntryPoint
class FavouritesGamesFragment : Fragment(R.layout.fragment_favourites_games) {

    lateinit var layout : FragmentFavouritesGamesBinding
    lateinit var rv : RecyclerView
    private val viewModel : FavouriteGameListViewModel by viewModels()
    private val sharedViewModel : SharedViewModel by activityViewModels()

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @Override
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        layout = FragmentFavouritesGamesBinding.inflate(inflater, container, false)
        return layout.root
    }

    @Override
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv = layout.favouritesGamesList

        viewModel.getGames()

        viewModel.games.observe(viewLifecycleOwner, Observer { games ->
            println(games)
            if(games.isNotEmpty()){
                rv.layoutManager = GridLayoutManager(super.getContext(),2)
                rv.adapter = FavouriteGamesListAdapter(games, {onGameClicked(it)}, {onLiked(it)}, {onLikeRemoved(it)})
            }

            scrollToPosition(sharedViewModel.favGameListFragmentItemScrollPosition, sharedViewModel.favGameListFragmentItemScrollOffset)
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer{
            layout.progressBar.isVisible = it
        })

        layout.backToHome.setOnClickListener{
            val direction = FavouritesGamesFragmentDirections.actionFavouritesGamesFragmentToHomeFragment()
            findNavController().navigate(direction)
        }

        layout.favouritesGamesList.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val indexItemRV = (rv.layoutManager as? GridLayoutManager)?.findFirstVisibleItemPosition()!!
                val v = (rv.layoutManager as? GridLayoutManager)?.getChildAt(0)
                val topViewRV = if(v == null) 0 else v.top - (rv.layoutManager as? GridLayoutManager)?.paddingTop!!

                sharedViewModel.favGameListFragmentScrollTo(indexItemRV, topViewRV)
            }
        })
    }

    private fun scrollToPosition(position : Int, offset : Int = 0){
        rv.stopScroll()
        (rv.layoutManager as? GridLayoutManager)?.scrollToPositionWithOffset(position,offset)
    }

    private fun onGameClicked(games : Games){

        sharedViewModel.gameLookUp(games.id)

        val direction = FavouritesGamesFragmentDirections.actionFavouritesGamesFragmentToDetailFragment(games.id)
        findNavController().navigate(direction)
    }

    private fun onLiked(games : Games){
        viewModel.liked(games)
    }

    private fun onLikeRemoved(games : Games){
        viewModel.removeLiked(games)
    }
}