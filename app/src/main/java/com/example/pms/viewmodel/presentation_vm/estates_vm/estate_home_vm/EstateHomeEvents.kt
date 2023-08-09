package com.example.pms.viewmodel.presentation_vm.estates_vm.estate_home_vm

import android.content.Context
import androidx.navigation.NavHostController


sealed class EstateHomeEvents {

    data class OnStart(val context: Context) : EstateHomeEvents()

    data class SearchQueryChanged(val query: String) : EstateHomeEvents()

    object ShowSearchBar : EstateHomeEvents()

    data class CancelSearchBar(val context: Context) : EstateHomeEvents()

    data class ShowDropDownFilter(val id: Int) : EstateHomeEvents()
    data class ShowDropDownFilterAll(val open: Boolean) : EstateHomeEvents()

    data class FilterTypeChanged(
        val filterType: String,
        val filterText: String,
        val context: Context
    ) : EstateHomeEvents()


    data class LoveChanged(val estateId: Int, val context: Context) : EstateHomeEvents()

    data class SearchServer(val text: String, val context: Context) : EstateHomeEvents()

    data class ViewMore(
        val navController: NavHostController,
        val estateId: Int = -1
    ) : EstateHomeEvents()

    data class Paginate(
        val context: Context
    ) : EstateHomeEvents()


    data class OnGovernorateTypeChanged(val governorate: String) : EstateHomeEvents()
    data class OnEstateTypeChanged(val estateType: String) : EstateHomeEvents()
    data class OnOperationTypeChanged(val operationType: String) : EstateHomeEvents()
    data class OnStatusChanged(val status: String) : EstateHomeEvents()
    data class OnPriceUpChanged(val price_up: String) : EstateHomeEvents()
    data class OnPriceDownChanged(val price_down: String) : EstateHomeEvents()
    data class OnSpaceUpChanged(val space_up: String) : EstateHomeEvents()
    data class OnSpaceDownChanged(val space_down: String) : EstateHomeEvents()
    data class OnLevelUpChanged(val level_up: String) : EstateHomeEvents()
    data class OnLevelDownChanged(val level_down: String) : EstateHomeEvents()

    object OnClearAllTextsFilter : EstateHomeEvents()

    data class OnDoneFilter(val context: Context) : EstateHomeEvents()

    data class OnRefresh(
        val isRefreshing: Boolean,
        val context: Context
    ) : EstateHomeEvents()

    data class OnReloadClicked(
        val context: Context
    ) : EstateHomeEvents()


}