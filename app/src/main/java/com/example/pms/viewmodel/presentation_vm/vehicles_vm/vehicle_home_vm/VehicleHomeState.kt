package com.example.pms.viewmodel.presentation_vm.vehicles_vm.vehicle_home_vm

import com.example.pms.model.HomeVehiclesResponse

data class VehicleHomeState(
    var query: String = "",
    var showSearchBar: Boolean = false,
    var showDropDownFilter: Boolean = false,
    var filterType: String = "",
    var filterId: Int = -1,
    var isLoading: Boolean = false,
    var postsDataList: List<HomeVehiclesResponse.PostData> = emptyList(),
    var endReached: Boolean = false,
    var error: String? = null,
    var page: Int = 0,
    var gettingNewPosts: Boolean = false,
    var lastPage: Int = 0,
    var noMoreData: Boolean = false,
    var needRefresh: Boolean = false,
    var pickingPrice: Boolean = false,
    var showAdvanceFiltering: Boolean = false
)
