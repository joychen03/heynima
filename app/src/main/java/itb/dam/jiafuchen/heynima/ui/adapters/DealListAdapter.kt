package itb.dam.jiafuchen.heynima.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import itb.dam.jiafuchen.heynima.R
import itb.dam.jiafuchen.heynima.data.source.remote.dto.Deal
import itb.dam.jiafuchen.heynima.domain.models.Store

class DealListAdapter(private val dealList : List<Deal>, private val storeList : List<Store>, private val onClickListener : (Deal) -> Unit) : RecyclerView.Adapter<DealListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DealListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return DealListViewHolder(layoutInflater.inflate(R.layout.item_deal, parent, false))
    }

    override fun onBindViewHolder(holder: DealListViewHolder, position: Int) {
        val item = dealList[position]
        val store : Store? = storeList.find { it.id == item.storeID }
        holder.render(item, store, onClickListener)
    }

    override fun getItemCount(): Int = dealList.size
}