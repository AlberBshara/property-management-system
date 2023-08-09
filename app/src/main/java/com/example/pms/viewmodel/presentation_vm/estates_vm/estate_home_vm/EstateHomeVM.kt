package com.example.pms.viewmodel.presentation_vm.estates_vm.estate_home_vm

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.pms.model.FavouriteEstates
import com.example.pms.model.GetAllEstateResponse
import com.example.pms.model.SearchFilterEstate
import com.example.pms.viewmodel.api.estates_services.EstateServiceImplementation
import com.example.pms.viewmodel.api.util.Resource
import com.example.pms.viewmodel.destinations.Destination
import com.example.pms.viewmodel.utils.TokenManager
import kotlinx.coroutines.launch

class EstateHomeVM(
    private val estateApiRepo: EstateServiceImplementation = EstateServiceImplementation()
) : ViewModel() {


    var state by mutableStateOf(EstateHomeState())


    companion object {
        const val PAGE_SIZE: Int = 4
        private const val DEFAULT_PAGE_NUMBER: Int = 1
    }

    private var counter: Int = 0
    var pageNumber by mutableStateOf(DEFAULT_PAGE_NUMBER)

    var estatesListScrollPosition = 0
    // private var isSearching : Boolean = false


    fun onEvent(event: EstateHomeEvents) {
        when (event) {

            is EstateHomeEvents.OnStart -> {
                getAllEstates(context = event.context)
            }

            is EstateHomeEvents.SearchQueryChanged -> {
                state = state.copy(query = event.query)
            }
            is EstateHomeEvents.ShowSearchBar -> {
                state = state.copy(showSearchBar = true)
            }
            is EstateHomeEvents.CancelSearchBar -> {
                cancelSearch(event.context)
            }
            is EstateHomeEvents.FilterTypeChanged -> {
                state = state.copy(
                    filterType = event.filterType,
                    filterText = event.filterText,
                    showDropDownFilter = !state.showDropDownFilter
                )
                changeOneFilterObject(filterType = event.filterType, filterText = event.filterText)
                filterEstate(filter = state.filter, context = event.context)
            }
            is EstateHomeEvents.ShowDropDownFilter -> {
                state =
                    state.copy(filterId = event.id, showDropDownFilter = !state.showDropDownFilter)
            }

            is EstateHomeEvents.ShowDropDownFilterAll -> {
                state = state.copy(showDropDownFilterAll = event.open)
            }


            is EstateHomeEvents.LoveChanged -> {
                like(event.estateId, event.context)

            }
            is EstateHomeEvents.ViewMore -> {
                viewMoreById(estateId = event.estateId, navController = event.navController)
            }

            is EstateHomeEvents.Paginate -> {
                paginate(event.context)
            }

            is EstateHomeEvents.SearchServer -> {
                searchEstate(event.text, event.context)
            }


            is EstateHomeEvents.OnGovernorateTypeChanged -> {
                state =
                    state.copy(enteredData = state.enteredData.copy(governorate = event.governorate))
            }

            is EstateHomeEvents.OnEstateTypeChanged -> {
                state =
                    state.copy(enteredData = state.enteredData.copy(estateType = event.estateType))
            }

            is EstateHomeEvents.OnOperationTypeChanged -> {
                state =
                    state.copy(enteredData = state.enteredData.copy(operationType = event.operationType))
            }

            is EstateHomeEvents.OnStatusChanged -> {
                state =
                    state.copy(enteredData = state.enteredData.copy(statusOFEstate = event.status))
            }

            is EstateHomeEvents.OnPriceUpChanged -> {
                state = state.copy(enteredData = state.enteredData.copy(max_price = event.price_up))
            }

            is EstateHomeEvents.OnPriceDownChanged -> {
                state =
                    state.copy(enteredData = state.enteredData.copy(min_price = event.price_down))
            }

            is EstateHomeEvents.OnSpaceUpChanged -> {
                state = state.copy(enteredData = state.enteredData.copy(max_space = event.space_up))
            }

            is EstateHomeEvents.OnSpaceDownChanged -> {
                state =
                    state.copy(enteredData = state.enteredData.copy(min_space = event.space_down))
            }

            is EstateHomeEvents.OnLevelUpChanged -> {
                state = state.copy(enteredData = state.enteredData.copy(max_level = event.level_up))
            }

            is EstateHomeEvents.OnLevelDownChanged -> {
                state =
                    state.copy(enteredData = state.enteredData.copy(min_level = event.level_down))
            }

            is EstateHomeEvents.OnClearAllTextsFilter -> {
                state = state.copy(
                    enteredData = state.enteredData.copy(
                        estateType = "",
                        governorate = "",
                        operationType = "",
                        min_level = "",
                        min_space = "",
                        min_price = "",
                        max_level = "",
                        max_space = "",
                        max_price = "",
                        statusOFEstate = ""
                    )
                )

            }

            is EstateHomeEvents.OnDoneFilter -> {
                state = state.copy(
                    filter = state.filter.copy(
                        type = "estate",
                        estateType = state.enteredData.estateType.takeIf { it.isNotBlank() },
                        operationType = state.enteredData.operationType.takeIf { it.isNotBlank() },
                        status = state.enteredData.statusOFEstate.takeIf { it.isNotBlank() },
                        governorate = state.enteredData.governorate.takeIf { it.isNotBlank() },
                        min_price = state.enteredData.min_price.toIntOrNull(),
                        max_price = state.enteredData.max_price.toIntOrNull(),
                        min_space = state.enteredData.min_space.toIntOrNull(),
                        max_space = state.enteredData.max_space.toIntOrNull(),
                        min_level = state.enteredData.min_level.toIntOrNull(),
                        max_level = state.enteredData.max_level.toIntOrNull()
                    )
                )
                filterEstate(filter = state.filter, context = event.context)
            }


            is EstateHomeEvents.OnRefresh -> {
                state = state.copy(
                    isRefreshing = event.isRefreshing
                )
                reload(event.context)

            }

            is EstateHomeEvents.OnReloadClicked -> {
                reload(event.context)
            }

        }


    }


    private fun viewMoreById(
        estateId: Int, navController: NavHostController
    ) {
        navController.currentBackStackEntry?.savedStateHandle?.set(
            Destination.ESTATE_ID_KEY,
            estateId
        )

        navController.navigate(Destination.ViewMoreScreenEstate.route)
    }


    private fun getAllEstates(context: Context) {

        if (counter == 0) {
            this.counter++
            viewModelScope.launch {

                val response = estateApiRepo.getAllEstatesHome(
                    token = TokenManager.getInstance(context = context).getToken(),
                    pageNumber = pageNumber
                )

                response.collect {
                    when (it) {
                        is Resource.Loading -> {
                            state = state.copy(
                                isLoading = it.isLoading,
                                isRefreshing = it.isLoading
                            )

                        }

                        is Resource.Success -> {
                            if (it.data != null) {
                                Log.d("qwe", it.data.toString())

                                state = state.copy(
                                    postsDataList = it.data.allPosts.postsData,
                                    lastPage = it.data.allPosts.lastPage
                                )
                            }

                        }

                        is Resource.Error -> {

                            state = state.copy(
                                timeOut = true
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
        pageNumber += 1

        viewModelScope.launch {
            if (pageNumber == state.lastPage + 1) {
                return@launch
            } else {
                val newResponse = estateApiRepo.getAllEstatesHome(
                    token = TokenManager.getInstance(context).getToken(),
                    pageNumber = pageNumber
                )

                newResponse.collect {

                    when (it) {

                        is Resource.Loading -> {
                            state = state.copy(gettingNewPosts = it.isLoading)

                        }

                        is Resource.Success -> {

                            if (it.data?.allPosts != null) {
                                if (it.data.allPosts.postsData.isNotEmpty()) {
                                    appendNewPosts(it.data.allPosts.postsData)
                                }
                            }

                        }

                        is Resource.Error -> {
                            Log.d("qwe", it.toString())
                        }
                    }

                }


            }

        }


    }


    private fun appendNewPosts(
        newPostsList: List<GetAllEstateResponse.PostData>
    ) {
        val current = ArrayList(state.postsDataList)
        current.addAll(newPostsList)
        state = state.copy(
            postsDataList = current
        )
    }


    private fun reload(
        context: Context
    ) {
        this.counter = 0
        getAllEstates(context)
        state = state.copy(
            timeOut = false
        )
    }


    private fun like(
        estateId: Int, context: Context
    ) {
        viewModelScope.launch {


            viewModelScope.launch {
                val response = estateApiRepo.addOrRemoveFromFavourites(
                    token = TokenManager.getInstance(context).getToken(),
                    favourite = FavouriteEstates.AddOrRemoveFromFavouritesRequest(
                        id = estateId,
                        type = "estate"
                    )
                )

                response.collect {
                    when (it) {

                        is Resource.Loading -> {

                        }

                        is Resource.Success -> {

                            if (it.data?.status == true) {


                                val updatedList = state.postsDataList.toMutableList()
                                updatedList.forEachIndexed { index, postData ->

                                    if (postData.estateData.estate_id == estateId) {
                                        updatedList[index] =
                                            updatedList[index].copy(favourite = !state.postsDataList[index].favourite)
                                        state = state.copy(postsDataList = updatedList)
                                    }
                                }

                            }

                        }

                        is Resource.Error -> {

                        }

                    }


                }

            }


        }
    }


    private fun searchEstate(text: String, context: Context) {


        viewModelScope.launch {

            val response = estateApiRepo.searchEstate(
                token = TokenManager.getInstance(context).getToken(),
                search = SearchFilterEstate.SearchEstateRequest(type = "estate", description = text)
            )

            response.collect {

                when (it) {
                    is Resource.Loading -> {
                        state = state.copy(
                            isLoading = it.isLoading,
                        )
                    }

                    is Resource.Success -> {
                        if (it.data?.status == true) {

                            state = state.copy(postsDataList = it.data.Posts)

                        }
                    }

                    is Resource.Error -> {

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
        state = state.copy(showSearchBar = false)
        this.counter = 0
        state.postsDataList.toMutableList().clear()
        this.pageNumber = DEFAULT_PAGE_NUMBER
        getAllEstates(context)
    }


    private fun changeOneFilterObject(filterType: String, filterText: String) {
        state = state.copy(
            filter = state.filter.copy(
                governorate = if (filterType == "governorate") filterText else null,
                status = if (filterType == "status") filterText else null,
                min_price = if (filterType == "min_price") filterText.toInt() else null,
                max_price = if (filterType == "max_price") filterText.toInt() else null,
                min_space = if (filterType == "min_space") filterText.toInt() else null,
                max_space = if (filterType == "max_space") filterText.toInt() else null,
                min_level = if (filterType == "min_level") filterText.toInt() else null,
                max_level = if (filterType == "max_level") filterText.toInt() else null,
                estateType = if (filterType == "estate_type") filterText else null,
                operationType = if (filterType == "operation_type") filterText else null
            )
        )
    }

    //private fun changeAllFilterObject()

    private fun filterEstate(filter: SearchFilterEstate.FilterEstateRequest, context: Context) {

        viewModelScope.launch {

            val response = estateApiRepo.filterEstate(
                token = TokenManager.getInstance(context).getToken(),
                filter = filter
            )

            response.collect {

                when (it) {
                    is Resource.Loading -> {
                        state = state.copy(
                            isLoading = it.isLoading,
                        )
                    }

                    is Resource.Success -> {
                        if (it.data?.status == true) {
                            state.postsDataList.toMutableList().clear()
                            state = state.copy(postsDataList = it.data.Posts)

                        }
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            timeOut = true,

                            )
                    }


                }


            }

        }

    }

}