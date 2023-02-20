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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import itb.dam.jiafuchen.heynima.R
import itb.dam.jiafuchen.heynima.databinding.FragmentListBinding
import itb.dam.jiafuchen.heynima.domain.models.Games
import itb.dam.jiafuchen.heynima.ui.adapters.GameListAdapter
import itb.dam.jiafuchen.heynima.ui.viewmodels.ListFragmentViewModel
import itb.dam.jiafuchen.heynima.ui.viewmodels.SharedViewModel

@AndroidEntryPoint
class ListFragment : Fragment(R.layout.fragment_list) {

    //region Variables & Constants

    lateinit var layout : FragmentListBinding
    lateinit var rv : RecyclerView

    private val args : ListFragmentArgs by navArgs()
    private val viewModel : ListFragmentViewModel by viewModels()
    private val sharedViewModel : SharedViewModel by activityViewModels()

    //endregion

    @Override
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        layout = FragmentListBinding.inflate(inflater, container, false)

        return layout.root
    }

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @Override
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv = layout.gameList

        viewModel.getGames(args.game)

        viewModel.games.observe(viewLifecycleOwner, Observer { games ->
            if(games.isNotEmpty()){
                rv.layoutManager = LinearLayoutManager(super.getContext())
                rv.adapter = GameListAdapter(games, viewModel.favGames, {onGameClicked(it)}, {onLikeBtnClicked(it)}, {onLikeRemoved(it)})
            }

            scrollToPosition(sharedViewModel.listFragmentItemScrollPosition, sharedViewModel.listFragmentItemScrollOffset)
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer{
            layout.progressBar.isVisible = it
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer{
            layout.errorContainer.isVisible = true
            layout.errorMessage.text = it
        })

        layout.errorGoBack.setOnClickListener{
            goBackClicked()
        }

        layout.backToSearch.setOnClickListener{
            goBackClicked()
        }

        layout.gameList.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val indexItemRV = (rv.layoutManager as? LinearLayoutManager)?.findFirstVisibleItemPosition()!!
                val v = (rv.layoutManager as? LinearLayoutManager)?.getChildAt(0)
                val topViewRV = if(v == null) 0 else v.top - (rv.layoutManager as? LinearLayoutManager)?.paddingTop!!

                sharedViewModel.listFragmentScrollTo(indexItemRV, topViewRV)
            }
        })
    }

    private fun scrollToPosition(position : Int, offset : Int = 0){
        rv.stopScroll()
        (rv.layoutManager as? LinearLayoutManager)?.scrollToPositionWithOffset(position,offset)
    }

    private fun onGameClicked(games : Games){

        sharedViewModel.gameLookUp(games.id)

        val direction = ListFragmentDirections.actionListFragmentToDetailFragment(games.id)
        findNavController().navigate(direction)
    }

    private fun onLikeBtnClicked(games : Games){
        viewModel.liked(games)
    }

    private fun onLikeRemoved(games : Games){
        viewModel.likeRemoved(games)
    }


    private fun goBackClicked(){
        val direction = ListFragmentDirections.actionListFragmentToSearchFragment()
        findNavController().navigate(direction)
    }

}