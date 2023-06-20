package com.example.pms.viewmodel.presentation_vm.vehicles_vm.vehicle_home_vm


import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.pms.model.FilteringData
import com.example.pms.model.HomeVehiclesResponse
import com.example.pms.model.SearchData
import com.example.pms.viewmodel.api.util.Keys
import com.example.pms.viewmodel.api.util.Resource
import com.example.pms.viewmodel.api.vehicels_services.VehicleServicesImplementation
import com.example.pms.viewmodel.destinations.Destination
import com.example.pms.viewmodel.utils.TokenManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**
 * @property counter this will prevent implementing the fetchAllVehicles function more than one time at
 *                 first time.
 * @property isSearching this will prevent implementing the paginate function while getting search result.
 *                        and I will use it in filter function for the same purposes.
 */
class VehicleHomeVM(
    private val vehicleServicesImplementation: VehicleServicesImplementation = VehicleServicesImplementation()
) : ViewModel() {

    companion object {
        private const val TAG: String = "VehicleHomeVM.kt"
        private const val SEARCH_TYPE: String = "car"
        const val PAGE_SIZE: Int = 4
        private const val DEFAULT_PAGE_NUMBER: Int = 1
    }

    var state by mutableStateOf(VehicleHomeState())
    val pageNumber = mutableStateOf(DEFAULT_PAGE_NUMBER)

    var vehiclesListScrollPosition = 0
    private var counter: Int = 0
    private var isSearching: Boolean = false


    fun onEvent(event: VehicleHomeEvents) {
        when (event) {
            is VehicleHomeEvents.OnStart -> {
                fetchAllVehicles(event.context)
            }
            is VehicleHomeEvents.Paginate -> {
                paginate(event.context)
            }
            is VehicleHomeEvents.SearchQueryChanged -> {
                state = state.copy(
                    query = event.query
                )
            }
            is VehicleHomeEvents.OnDoneSearchQuery -> {
                search(event.query, event.context)
            }
            is VehicleHomeEvents.ShowSearchBar -> {
                state = state.copy(
                    showSearchBar = !state.showSearchBar
                )
            }
            is VehicleHomeEvents.OnCancelSearch -> {
                cancelSearch(event.context)
            }
            is VehicleHomeEvents.ShowDropDownFilter -> {
                state = state.copy(
                    showDropDownFilter = !state.showDropDownFilter,
                    filterId = event.id
                )
            }
            is VehicleHomeEvents.FilterTypeChanged -> {
                state = state.copy(
                    showDropDownFilter = !state.showDropDownFilter,
                    filterType = event.filterType
                )
                filter(event.context, Pair(event.filterTitle, event.filterType))
            }
            is VehicleHomeEvents.LoadingCaseChanged -> {
                state = state.copy(
                    isLoading = !state.isLoading
                )
            }
            is VehicleHomeEvents.AdvancedFiltering -> {
                if (event.filterTitle == Keys.MAX_PRICE){
                    state = state.copy(
                        pickingPrice = true
                    )
                }
            }
            is VehicleHomeEvents.FilteringByPrice -> {
                state = state.copy(
                    pickingPrice = false
                )
                filterByPriceRange(event.context, event.minimumPrice, event.minimumPrice)
            }
            is VehicleHomeEvents.OnViewMoreClicked -> {
                viewMoreById(event.carId, event.navController)
            }
            is VehicleHomeEvents.OnNeedRefresh -> {
                state = state.copy(
                    needRefresh = false
                )
                this.counter = 0
                this.pageNumber.value = DEFAULT_PAGE_NUMBER
                fetchAllVehicles(event.context)
            }
            is VehicleHomeEvents.ShowAdvanceFiltering -> {
                state = state.copy(
                    showAdvanceFiltering = !state.showAdvanceFiltering
                )
            }
        }
    }

    private fun fetchAllVehicles(
        context: Context
    ) {
        if (counter == 0) {
            this.counter++
            viewModelScope.launch {
                val response = vehicleServicesImplementation.fetchAllVehiclesPosts(
                    TokenManager.getInstance(context).getToken(),
                    pageNumber.value
                )
                response.collect {
                    when (it) {
                        is Resource.Loading -> {
                            Log.d(TAG, "fetchAllVehicles: Loading : ${it.isLoading}")
                            state = state.copy(
                                isLoading = it.isLoading
                            )
                        }
                        is Resource.Success -> {
                            Log.d(TAG, "fetchAllVehicles: Success : ${it.data?.allPosts}")
                            if (it.data?.allPosts != null) {
                                state = state.copy(
                                    postsDataList = it.data.allPosts.postsData,
                                    lastPage = it.data.allPosts.lastPage
                                )
                            }
                        }
                        is Resource.Error -> {
                            Log.d(TAG, "fetchAllVehicles: Exception : ${it.message}")
                            state = state.copy(
                                needRefresh = true
                            )
                        }
                    }
                }
            }
        }
    }

    /**
     * the following code will paginate our data.
     * @return the next page while user scrolling.
     */
    private fun paginate(
        context: Context
    ) {
        if (!isSearching) {
            viewModelScope.launch {
                if ((state.lastPage + 1) == (pageNumber.value + 1)) {
                    state = state.copy(
                        noMoreData = true
                    )
                    return@launch
                } else if ((vehiclesListScrollPosition + 1) >= (pageNumber.value * PAGE_SIZE)) {
                    incrementPageNumber()
                    Log.d(TAG, "paginate: Triggered: ${pageNumber.value}")
                    if (pageNumber.value > 1) {
                        val moreResponse = vehicleServicesImplementation.fetchAllVehiclesPosts(
                            TokenManager.getInstance(context).getToken(),
                            pageNumber.value
                        )
                        moreResponse.collect {
                            when (it) {
                                is Resource.Loading -> {
                                    state = state.copy(
                                        gettingNewPosts = it.isLoading
                                    )
                                    Log.d(
                                        TAG,
                                        "paginate: getting a new data Loading : ${it.isLoading}"
                                    )
                                }
                                is Resource.Success -> {
                                    if (it.data?.allPosts != null) {
                                        if (it.data.allPosts.postsData.isNotEmpty()) {
                                            appendNewPosts(it.data.allPosts.postsData)
                                            Log.d(
                                                TAG,
                                                "paginate: getting a new data Success : ${it.data.allPosts.postsData}"
                                            )
                                        }
                                    }
                                }
                                is Resource.Error -> {
                                    Log.d(
                                        TAG,
                                        "paginate: getting a new data Exception : ${it.message}"
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun appendNewPosts(
        newPostsList: List<HomeVehiclesResponse.PostData>
    ) {
        val current = ArrayList(state.postsDataList)
        current.addAll(newPostsList)
        state = state.copy(
            postsDataList = current
        )
    }

    private fun incrementPageNumber() {
        pageNumber.value = pageNumber.value + 1
    }

    private fun viewMoreById(
        carId: Int, navController: NavHostController
    ) {
        navController.currentBackStackEntry?.savedStateHandle
            ?.set(Destination.CAR_ID_KEY, carId)
        navController.navigate(Destination.VehicleDetailsDestination.route)
    }


    @SuppressLint("SuspiciousIndentation")
    private fun search(
        query: String,
        context: Context
    ) {
        viewModelScope.launch {
            isSearching = true
            withContext(Dispatchers.IO) {
                val searchResponse = vehicleServicesImplementation.search(
                    TokenManager.getInstance(context).getToken(),
                    SearchData(SEARCH_TYPE, query)
                )
                searchResponse.collect {
                    when (it) {
                        is Resource.Loading -> {
                            Log.d(TAG, "search: Loading ${it.isLoading}")
                            state = state.copy(
                                isLoading = it.isLoading
                            )
                        }
                        is Resource.Success -> {
                            it.data?.let { searchResponse ->
                                if (searchResponse.success) {
                                    val searchResult = it.data.vehiclesList
                                        state.postsDataList.toMutableList().clear()
                                        state = state.copy(
                                            postsDataList = searchResult
                                        )
                                }
                                Log.d(TAG, "search: Success ${searchResponse.vehiclesList}")
                            }
                        }
                        is Resource.Error -> {
                            Log.d(TAG, "search: Error ${it.message}")
                        }
                    }
                }
            }
        }
    }

    private fun cancelSearch(
        context: Context
    ) {
        state = state.copy(
            showSearchBar = !state.showSearchBar,
            query = ""
        )
        this.isSearching = false
        this.counter = 0
        state.postsDataList.toMutableList().clear()
        this.pageNumber.value = DEFAULT_PAGE_NUMBER
        fetchAllVehicles(context)
    }


    private fun <ValueType : Any> filter(
        context: Context,
        filteringWith: Pair<String, ValueType>
    ) {
        Log.d(TAG, "filter: FilterTitle ${filteringWith.first} FilteringType :${filteringWith.second}")
        when (filteringWith.first) {
            "All" -> {
                state.postsDataList.toMutableList().clear()
                this.pageNumber.value = DEFAULT_PAGE_NUMBER
                this.counter = 0
                this.isSearching = false
                fetchAllVehicles(context)
            }
            else -> {
                this.isSearching = true
                viewModelScope.launch {
                    withContext(Dispatchers.IO) {
                        val filterHelper = FilterHelper(SEARCH_TYPE)
                        val filteringData: FilteringData? = filterHelper.filteringType(filteringWith)
                        filteringData?.let {
                            val response = vehicleServicesImplementation.filter(
                                TokenManager.getInstance(context).getToken(),
                                filteringData
                            )
                            response.collect {
                                when (it) {
                                    is Resource.Loading -> {
                                        state = state.copy(
                                            isLoading = it.isLoading
                                        )
                                    }
                                    is Resource.Success -> {
                                        it.data?.let { filteringResult ->
                                            if (filteringResult.success) {
                                                if (filteringResult.vehiclesList.isNotEmpty()) {
                                                    state.postsDataList.toMutableList().clear()
                                                    state = state.copy(
                                                        postsDataList = filteringResult.vehiclesList
                                                    )
                                                }
                                            }
                                            Log.d(TAG, "filter: Success ${filteringResult.vehiclesList}")
                                        }
                                    }
                                    is Resource.Error -> {
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    private fun filterByPriceRange(
        context: Context,
        minimumPrice: Double,
        maximumPrice: Double
    ) {
        this.isSearching = true
        viewModelScope.launch {
           withContext(Dispatchers.IO) {
               val response = vehicleServicesImplementation.filter(
                   TokenManager.getInstance(context).getToken(),
                   FilteringData(
                       type = SEARCH_TYPE ,
                       minimumPrice = minimumPrice ,
                       maximumPrice = maximumPrice
                   )
               )
               response.collect {
                   when(it) {
                       is Resource.Loading -> {
                           state = state.copy(
                               isLoading = it.isLoading
                           )
                       }
                       is Resource.Success -> {
                           it.data?.let { filteringResult ->
                               if (filteringResult.success) {
                                   if (filteringResult.vehiclesList.isNotEmpty()) {
                                       state.postsDataList.toMutableList().clear()
                                       state = state.copy(
                                           postsDataList = filteringResult.vehiclesList
                                       )
                                   }
                               }
                               Log.d(TAG, "filterByPriceRange: Success ${filteringResult.vehiclesList}")
                           }
                       }
                       is Resource.Error -> {
                       }
                   }
               }
           }
       }
    }

    private fun multipleFiltering() {

    }

}