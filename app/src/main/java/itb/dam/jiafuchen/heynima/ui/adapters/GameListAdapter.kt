package itb.dam.jiafuchen.heynima.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import itb.dam.jiafuchen.heynima.R
import itb.dam.jiafuchen.heynima.domain.models.Game
import itb.dam.jiafuchen.heynima.domain.models.Games

class GameListAdapter(
    private val gameList : List<Games>,
    private val favGamesList : List<Games>,
    private val onClickListener : (Games) -> Unit,
    private val onLiked : (Games) -> Unit,
    private val onLikeRemoved : (Games) -> Unit,

) : RecyclerView.Adapter<GameListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return GameListViewHolder(layoutInflater.inflate(R.layout.item_games, parent, false))
    }

    override fun onBindViewHolder(holder: GameListViewHolder, position: Int) {
        val item = gameList[position]
        holder.render(item, favGamesList, onClickListener, onLiked, onLikeRemoved)
    }

    override fun getItemCount(): Int = gameList.size
}