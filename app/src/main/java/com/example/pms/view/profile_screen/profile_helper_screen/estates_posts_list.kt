package com.example.pms.view.profile_screen.profile_helper_screen

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.pms.view.estates_screen.estate_home.post_card.EstateCard
import com.example.pms.viewmodel.destinations.Destination
import com.example.pms.viewmodel.presentation_vm.profile_vm.profile_helper_vm.ProfileHelperScreenVM
import com.example.pms.viewmodel.presentation_vm.profile_vm.profile_helper_vm.ProfileHelperState


@Composable
fun ListEstatesContent(
    from: String,
    state: ProfileHelperState,
    viewModel: ProfileHelperScreenVM,
    context: Context,
    navController: NavHostController,
    onDeleteClicked: (index: Int, vehicleId: Int) -> Unit = { _, _ -> }
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(state.estatesPostsList) { index, item ->
                EstateCard(
                    navController = navController,
                    onClick = {
                    },
                    lengthOfPagerIndicator = item.images.size,
                    operationType = item.estateData.operation_type,
                    price = item.estateData.price,
                    location = item.estateData.address,
                    bed = item.estateData.beds.toInt(),
                    bath = item.estateData.baths.toInt(),
                    garage = item.estateData.garage.toInt(),
                    level = item.estateData.level.toInt(),
                    idOfEstate = item.estateData.estate_id,
                    images = item.images,
                    loved = item.liked,
                    context = context,
                    enableDeleting = (from == Destination.ProfileHelperScreen.FROM_MY_POST_CLICKED),
                    onDeletingListener = {
                        onDeleteClicked(index, item.estateData.estate_id)
                    }
                )
            }
        }
    }
}