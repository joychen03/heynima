package itb.dam.jiafuchen.heynima.ui.adapters

import android.annotation.SuppressLint
import android.view.View
import androidx.compose.runtime.currentCompositeKeyHash
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import itb.dam.jiafuchen.heynima.databinding.ItemGamesBinding
import itb.dam.jiafuchen.heynima.domain.models.Game
import itb.dam.jiafuchen.heynima.domain.models.Games

class GameListViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    val layout = ItemGamesBinding.bind(view)

    @SuppressLint("SetTextI18n")
    fun render(
        listGamesItem : Games,
        listFavGames  : List<Games>,
        onClickListener : (Games) -> Unit,
        onLiked : (Games) -> Unit,
        onLikeRemoved : (Games) -> Unit
    ){
        layout.gameTitle.text = listGamesItem.name
        layout.gameBestPrice.text = "${listGamesItem.cheapestPrice}$"
        Glide.with(layout.gameImage.context).load(listGamesItem.image).into(layout.gameImage)

        layout.listLikeBtn.isChecked = listFavGames.find { it.id == listGamesItem.id } != null

        itemView.setOnClickListener{
            onClickListener(listGamesItem)
        }

        layout.listLikeBtn.setOnClickListener{
            if(layout.listLikeBtn.isChecked){
                onLiked(listGamesItem)
            }else{
                onLikeRemoved(listGamesItem)
            }

        }
    }
}