package itb.dam.jiafuchen.heynima.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import itb.dam.jiafuchen.heynima.R
import itb.dam.jiafuchen.heynima.data.source.remote.dto.Deal
import itb.dam.jiafuchen.heynima.domain.models.Game
import itb.dam.jiafuchen.heynima.domain.models.Games
import itb.dam.jiafuchen.heynima.domain.models.Store

class FavouriteGamesListAdapter(
    private val gamesList : List<Games>,
    private val onClickListener : (Games) -> Unit,
    private val onLiked : (Games) -> Unit,
    private val onLikeRemoved : (Games) -> Unit
) : RecyclerView.Adapter<FavouriteGamesListHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteGamesListHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return FavouriteGamesListHolder(layoutInflater.inflate(R.layout.item_favourite_game, parent, false))
    }

    override fun onBindViewHolder(holder: FavouriteGamesListHolder, position: Int) {
        val item = gamesList[position]
        holder.render(item, onClickListener, onLiked, onLikeRemoved)
    }

    override fun getItemCount(): Int = gamesList.size
}