package itb.dam.jiafuchen.heynima.ui.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Path.Direction
import android.net.Uri
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
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import itb.dam.jiafuchen.heynima.R
import itb.dam.jiafuchen.heynima.data.source.remote.dto.Deal
import itb.dam.jiafuchen.heynima.databinding.FragmentDetailBinding
import itb.dam.jiafuchen.heynima.databinding.FragmentListBinding
import itb.dam.jiafuchen.heynima.domain.models.Games
import itb.dam.jiafuchen.heynima.ui.adapters.DealListAdapter
import itb.dam.jiafuchen.heynima.ui.adapters.GameListAdapter
import itb.dam.jiafuchen.heynima.ui.viewmodels.DetailFragmentViewModel
import itb.dam.jiafuchen.heynima.ui.viewmodels.SharedViewModel
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    lateinit var layout : FragmentDetailBinding

    private val args : DetailFragmentArgs by navArgs()
    private val viewModel : DetailFragmentViewModel by viewModels()
    private val sharedViewModel : SharedViewModel by activityViewModels()

    @Override
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        layout = FragmentDetailBinding.inflate(inflater, container, false)
        return layout.root
    }

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("SetTextI18n")
    @Override
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getGame(args.gameId)

        viewModel.game.observe(viewLifecycleOwner, Observer { game ->
            Glide.with(layout.gameDetailImage.context).load(game.image).into(layout.gameDetailImage)
            layout.gameDetailTitle.text = game.name
            layout.gameDetailBestPrice.text = "Best price ➜ ${game.cheapestPriceEver.price}$ at ${getDateByTimestamp(game.cheapestPriceEver.date * 1000)}"

            layout.dealList.layoutManager = LinearLayoutManager(this.context)
            layout.dealList.adapter = DealListAdapter(game.deals, sharedViewModel.storeList){ deal ->
                onDealClicked(deal)
            }

        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer{
            layout.detailLoading.isVisible = it
        })

        viewModel.isFavourite.observe(viewLifecycleOwner, Observer{
            layout.detailLikeBtn.isChecked = it
        })

        layout.backToList.setOnClickListener{
            goBackClicked()
        }

        layout.detailLikeBtn.setOnClickListener{
            onLikeBtnClicked()
        }


    }

    private fun onDealClicked(deal: Deal){
        val url = Uri.parse("https://www.cheapshark.com/redirect?dealID=${deal.dealID}")
        startActivity(Intent(Intent.ACTION_VIEW, url))
    }

    private fun onLikeBtnClicked(){
        if(layout.detailLikeBtn.isChecked){
            viewModel.likeGame()
        }else{
            viewModel.removeLikeGame()
        }

    }

    private fun getDateByTimestamp(timestamp: Long): String {
        val date = Date(timestamp)
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        return formatter.format(date)
    }

    private fun goBackClicked(){

        if(sharedViewModel.currentSearch.isNotEmpty()){
            val direction = DetailFragmentDirections.actionDetailFragmentToListFragment(sharedViewModel.currentSearch)
            findNavController().navigate(direction)
        }else{
            val direction = DetailFragmentDirections.actionDetailFragmentToFavouritesGamesFragment()
            findNavController().navigate(direction)
        }

    }
}