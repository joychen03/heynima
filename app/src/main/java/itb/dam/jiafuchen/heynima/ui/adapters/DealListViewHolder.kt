package itb.dam.jiafuchen.heynima.ui.adapters

import android.annotation.SuppressLint
import android.view.View
import android.view.View.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import itb.dam.jiafuchen.heynima.data.source.remote.dto.Deal
import itb.dam.jiafuchen.heynima.databinding.ItemDealBinding
import itb.dam.jiafuchen.heynima.domain.models.Store
import kotlin.math.roundToInt

class  DealListViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    val layout = ItemDealBinding.bind(view)

    @SuppressLint("SetTextI18n")
    fun render(deal : Deal, store : Store?, onClickListener : (Deal) -> Unit){
//        var parent : ViewGroup = layout.detailsContainer
        val saving : Int = deal.savings.toDouble().roundToInt()

        layout.storeName.text = store?.name
        layout.dealPrice.text = "${deal.price}$"
        layout.savePercent.text = "-${saving}%"
        layout.retailPrice.text = "${deal.retailPrice}$"
        Glide.with(layout.storeImage.context).load(store?.image).into(layout.storeImage)

        if(saving > 0){
            layout.savePercent.visibility = VISIBLE
            layout.retailPrice.visibility = VISIBLE
        }else{
            layout.savePercent.visibility = GONE
            layout.retailPrice.visibility = GONE
        }

        itemView.setOnClickListener{
            onClickListener(deal)
        }

    }
}