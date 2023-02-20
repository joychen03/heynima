package itb.dam.jiafuchen.heynima.ui.adapters

import android.annotation.SuppressLint
import android.view.View
import android.view.View.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import itb.dam.jiafuchen.heynima.data.source.remote.dto.Deal
import itb.dam.jiafuchen.heynima.databinding.ItemDealBinding
import itb.dam.jiafuchen.heynima.databinding.ItemFavouriteGameBinding
import itb.dam.jiafuchen.heynima.domain.models.Game
import itb.dam.jiafuchen.heynima.domain.models.Games
import itb.dam.jiafuchen.heynima.domain.models.Store
import kotlin.math.roundToInt

class  FavouriteGamesListHolder(view : View) : RecyclerView.ViewHolder(view) {

    val layout = ItemFavouriteGameBinding.bind(view)

    @SuppressLint("SetTextI18n")
    fun render(
        games : Games,
        onClickListener : (Games) -> Unit,
        onLiked : (Games) -> Unit,
        onLikeRemoved : (Games) -> Unit
    ){

        layout.gameTitle.text = games.name
        layout.gameBestPrice.text = "${games.cheapestPrice}$"


        Glide.with(layout.gameImage.context).load(games.image).into(layout.gameImage)

        itemView.setOnClickListener{
            onClickListener(games)
        }

        layout.listLikeBtn.setOnClickListener{
            if(layout.listLikeBtn.isChecked){
                onLiked(games)
            }else{
                onLikeRemoved(games)
            }
        }

    }
}