package itb.dam.jiafuchen.heynima.data.source.remote.dto

import itb.dam.jiafuchen.heynima.domain.models.Store

class StoreDto : ArrayList<StoreDtoItem>() {

}

fun StoreDto.toListStores() : List<Store>{

    val resultEntries = this.mapIndexed { index, entries ->
        Store(
            id = entries.storeID,
            name = entries.storeName,
            image = "https://www.cheapshark.com/${entries.images.logo}",
        )
    }

    return resultEntries
}