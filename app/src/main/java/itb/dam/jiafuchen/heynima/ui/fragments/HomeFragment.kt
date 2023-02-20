package itb.dam.jiafuchen.heynima.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import itb.dam.jiafuchen.heynima.R
import itb.dam.jiafuchen.heynima.databinding.FragmentDetailBinding
import itb.dam.jiafuchen.heynima.databinding.FragmentFavouritesGamesBinding
import itb.dam.jiafuchen.heynima.databinding.FragmentHomeBinding
import itb.dam.jiafuchen.heynima.ui.viewmodels.SharedViewModel

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    lateinit var layout : FragmentHomeBinding

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
    ): View? {
        layout = FragmentHomeBinding.inflate(inflater, container, false)
        return layout.root
    }

    @Override
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.getStores()
        sharedViewModel.favGameListFragmentItemScrollOffset = 0
        sharedViewModel.favGameListFragmentItemScrollPosition = 0

        layout.goSearch.setOnClickListener{
            val direction = HomeFragmentDirections.actionHomeFragmentToSearchFragment()
            findNavController().navigate(direction)
        }

        layout.goFav.setOnClickListener{
            val direction = HomeFragmentDirections.actionHomeFragmentToFavouritesGamesFragment()
            findNavController().navigate(direction)
        }

    }
}