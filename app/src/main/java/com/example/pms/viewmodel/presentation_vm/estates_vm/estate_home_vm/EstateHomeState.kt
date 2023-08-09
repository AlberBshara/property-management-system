package com.example.pms.viewmodel.presentation_vm.estates_vm.estate_home_vm

import com.example.pms.model.GetAllEstateResponse
import com.example.pms.model.SearchFilterEstate

data class EstateHomeState(
    var query: String = "",
    var showSearchBar: Boolean = false,
    var showDropDownFilter: Boolean = false,
    var showDropDownFilterAll: Boolean = false,
    val filterType: String = "",
    val filterText: String = "",
    var filter: SearchFilterEstate.FilterEstateRequest = SearchFilterEstate.FilterEstateRequest(
        type = "estate",
        estateType = null,
        operationType = null,
        governorate = null,
        status = null,
        min_price = null,
        max_price = null,
        min_space = null,
        max_space = null,
        min_level = null,
        max_level = null,
    ),
    var filterId: Int = -1,



    var isLoading: Boolean = false,
    var postsDataList: List<GetAllEstateResponse.PostData> = emptyList(),
    var endReached: Boolean = false,
    var error: String? = null,
    var page: Int = 0,
    var gettingNewPosts: Boolean = false,
    var lastPage: Int = 0,
    var noMoreData: Boolean = false,
    var isRefreshing: Boolean = false,
    var timeOut: Boolean = false,


    var enteredData: EnteredData = EnteredData(),


    )


data class EnteredData(
    val governorate: String = "",
    val estateType: String = "",
    val operationType: String = "",
    val min_price: String = "",
    val max_price: String = "",
    val min_space: String = "",
    val max_space: String = "",
    val min_level: String = "",
    val max_level: String = "",
    val statusOFEstate: String = ""

)
